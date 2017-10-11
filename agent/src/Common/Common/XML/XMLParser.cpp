#include "StdAfx.h"
#include "XMLParser.h"


CXMLParser::CXMLParser(void)
{
	// 初始化com
	::CoInitialize(NULL);
	CoCreateInstance(CLSID_DOMDocument, NULL, CLSCTX_INPROC_SERVER, IID_IXMLDOMDocument, (void**)&m_pDOMDoc);
	m_pDOMDoc->put_async(VARIANT_FALSE);
	m_pParent = NULL;
	m_pMain = NULL;
	m_pChild = NULL;
}

CXMLParser::~CXMLParser(void)
{
	if ( m_pDOMDoc )
	{
		m_pDOMDoc->Release();
	}
	// 结束对COM的使用。
	::CoUninitialize();
}

BOOL CXMLParser::Load( LPCTSTR szFileName )
{
	::_variant_t vFile;
	vFile.SetString(szFileName);
	VARIANT_BOOL vBool;
	m_pDOMDoc->load(vFile, &vBool);
	if( vBool == VARIANT_TRUE )
	{
		return setRoot("lyvc");
	}
	return FALSE;
}

BOOL CXMLParser::setRoot( LPCTSTR szRoot )
{
	IXMLDOMNodeList* pList;
	IXMLDOMNode* pNode;
	_bstr_t b;
	if( SUCCEEDED( m_pDOMDoc->get_childNodes( &pList ) ) )
	{
		long len = 0;
		if( SUCCEEDED( pList->get_length(&len) ) )
		{
			for( long i = 0; i < len; i++)
			{
				if( SUCCEEDED( pList->get_item(i, &pNode) ) )
				{
					if( SUCCEEDED( pNode->get_nodeName(b.GetAddress()) ) )
					{
						if(b == _bstr_t(szRoot))
						{
							m_pParent = pNode;
							return TRUE;
						}
					}
				}
			}
		}
	}
	return FALSE;
}

BOOL CXMLParser::Save( LPCTSTR szFileName )
{
	_variant_t varName;
	varName.SetString(szFileName);
	if( SUCCEEDED( m_pDOMDoc->save( varName ) ) )
	{
		return TRUE;
	}
	return FALSE;
}

BOOL CXMLParser::SetDoc( LPCTSTR szDoc )
{
	VARIANT_BOOL vBool;
	m_pDOMDoc->loadXML(_bstr_t(szDoc), &vBool);
	if (vBool == VARIANT_TRUE)
	{
		m_pMain = NULL;
		m_pChild = NULL;
		return setRoot("lyvc");
	}
	return FALSE;
}

BOOL CXMLParser::AddElem( LPCTSTR szName, LPCTSTR szData )
{
	return addElem(szName, szData, false, false);
}

BOOL CXMLParser::AddChildElem( LPCTSTR szName, LPCTSTR szData )
{
	return addElem(szName, szData, false, true);
}

BOOL CXMLParser::InsertElem( LPCTSTR szName, LPCTSTR szData )
{
	return addElem(szName, szData, true, false);
}

BOOL CXMLParser::InsertChildElem( LPCTSTR szName, LPCTSTR szData )
{
	return addElem(szName, szData, true, true);
}

BOOL CXMLParser::IntoElem()
{
	if ( m_pMain )
	{
		m_pParent = m_pMain;
		if ( m_pChild )
		{
			m_pMain = m_pChild;
			m_pChild = NULL;
		}
		else
		{
			m_pMain = NULL;
		}
		return TRUE;
	}
	return FALSE;
}

BOOL CXMLParser::OutOfElem()
{
	IXMLDOMNode* pParent = NULL;
	m_pParent->get_parentNode(&pParent);
	if ( pParent )
	{
		m_pChild = m_pMain;
		m_pMain = m_pParent;
		m_pParent = pParent;
		return TRUE;
	}
	return FALSE;
}

BOOL CXMLParser::addElem( LPCTSTR szName, LPCTSTR szData, BOOL bInsert, BOOL bAddChild )
{
	IXMLDOMNode *pNext = NULL, *pParent = NULL;
	if ( bAddChild )
	{
		if ( !m_pMain )
		{
			return FALSE;
		}
		pParent = m_pMain;
		pNext = m_pChild;
	}
	else
	{
		if ( m_pChild )
		{
			m_pChild = NULL;
		}
		pParent = m_pParent;
		pNext = m_pMain;
	}
	if ( bInsert )
	{
		if ( !pNext )
		{
			pParent->get_firstChild(&pNext);
		}
	}
	else
	{
		if ( pNext )
		{
			pNext->get_nextSibling(&pNext);
		}
	}

	// 生成新节点
	IXMLDOMNode* pNew;
	VARIANT varType;
	VariantInit( &varType );
	varType.vt = VT_INT;
	varType.intVal = NODE_ELEMENT;
	_bstr_t name(szName);
	m_pDOMDoc->createNode(varType, name, NULL, &pNew);
	if( szData && szData[0] )
		pNew->put_text(_bstr_t(szData));

	// 插入新节点
	VARIANT varRef;
	VariantInit( &varRef );
	varRef.vt = VT_DISPATCH;
	varRef.pdispVal = pNext;
	IXMLDOMNode* p;
	pParent->insertBefore(pNew, varRef, &p);

	if ( bAddChild )
	{
		m_pChild = p;
	}
	else
	{
		m_pMain = p;
	}
	return TRUE;
}

BOOL CXMLParser::RemoveMsgNodeByIndex( int nIndex )
{
	if( m_pParent )
	{
		int n = 1;
		IXMLDOMNode *pChild = NULL;
		m_pParent->get_firstChild( &pChild );
		while( pChild && n < nIndex )
		{
			pChild->get_nextSibling( &pChild );
			n++;
		}
		if( pChild )
		{
            m_pParent->removeChild( pChild, &pChild );
		}
		return TRUE;
	}
	return FALSE;
}

BOOL CXMLParser::RemoveAllMsgNode()
{
	if( m_pParent )
	{
		IXMLDOMNode *pChild = NULL;
		m_pParent->get_firstChild( &pChild );
		while( pChild )
		{
			IXMLDOMNode *pNext = NULL;
			pChild->get_nextSibling( &pNext );
            m_pParent->removeChild( pChild, &pChild );
			pChild = pNext;
		}
		return TRUE;
	}
	return FALSE;
}

string CXMLParser::GetMsgElemTextByIndex( LPCTSTR szName, int nIndex )
{
	string sText;
	if( m_pParent )
	{
		int n = 1;
		IXMLDOMNode *pChild = NULL;
		m_pParent->get_firstChild( &pChild );
		while( pChild && n < nIndex )
		{
			pChild->get_nextSibling( &pChild );
			n++;
		}
		if( pChild )
		{
            pChild->get_firstChild( &pChild );
			while( pChild )
			{
				_bstr_t nameToCompare(szName);
				_bstr_t name;
				pChild->get_nodeName( &name.GetBSTR() );
				if( name == nameToCompare )
				{
					_bstr_t value;
					pChild->get_text( &value.GetBSTR() );
					sText = (LPCTSTR) value;
					return sText;
				}
				pChild->get_nextSibling( &pChild );
			}
		}
	}
	return "";
}

BOOL CXMLParser::SetStyleSheetAttrib( LPCTSTR szValue )
{
	IXMLDOMNodeList* pList = NULL;
	IXMLDOMNode* pNode = NULL;
	if( SUCCEEDED( m_pDOMDoc->get_childNodes( &pList ) ) && pList )
	{
		long len = 0;
		if( SUCCEEDED( pList->get_length(&len) ) )
		{
			for( long i = 0; i < len; i++)
			{
				if( SUCCEEDED( pList->get_item(i, &pNode) ) )
				{
					_bstr_t b;
					if( SUCCEEDED( pNode->get_nodeName(b.GetAddress()) ) )
					{
						if(b == _bstr_t("xml-stylesheet"))
						{
							if( SUCCEEDED( pNode->put_text( _bstr_t( szValue ) ) ) )
								return TRUE;
						}
					}
				}
			}
		}
	}
	return FALSE;
}
