#pragma once

#include <string.h>
#import <msxml.dll>

class CXMLParser
{
public:
	CXMLParser(void);
	~CXMLParser(void);

	// 从文件装载xml文档
	BOOL Load( LPCTSTR szFileName );
	// 保存xml文件
	BOOL Save( LPCTSTR szFileName );
	// 从字符串装载xml文档
	BOOL SetDoc( LPCTSTR szDoc );
	// 添加一个节点到父节点的最后一个子节点后
	BOOL AddElem( LPCTSTR szName, LPCTSTR szData=NULL );
	// 添加一个子节点
	BOOL AddChildElem( LPCTSTR szName, LPCTSTR szData=NULL );
	// 插入一个节点到父节点的第一个子节点前
	BOOL InsertElem( LPCTSTR szName, LPCTSTR szData=NULL );
	// 插入一个子节点
	BOOL InsertChildElem( LPCTSTR szName, LPCTSTR szData=NULL );
	// 进入节点
	BOOL IntoElem();
	// 退出节点
	BOOL OutOfElem();
	// 根据序号删除一条消息记录
	BOOL RemoveMsgNodeByIndex( int nIndex );
	// 删除所有消息记录
	BOOL RemoveAllMsgNode();
	// 根据序号得到指定element的值
	string GetMsgElemTextByIndex( LPCTSTR szName, int nIndex );
    // 修改xml文件的stylesheet属性
	BOOL SetStyleSheetAttrib( LPCTSTR szValue );

private:
	BOOL addElem( LPCTSTR szName, LPCTSTR szData, BOOL bInsert, BOOL bAddChild );
	BOOL setRoot( LPCTSTR szRoot );

private:
	IXMLDOMDocument*	m_pDOMDoc;
	// 指向当前节点的父节点
	IXMLDOMNode*		m_pParent;
	// 指向当前节点
	IXMLDOMNode*		m_pMain;
	// 指向当前节点的子节点
	IXMLDOMNode*		m_pChild;
};
