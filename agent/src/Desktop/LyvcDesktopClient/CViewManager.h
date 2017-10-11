// -=- CViewManager.h

// Creates and manages CView instances.

#ifndef __RFB_WIN32_CVIEW_MANAGER_H__
#define __RFB_WIN32_CVIEW_MANAGER_H__
#include <string>
#include <list>
#include "rfb_win32/MsgWindow.h"
#include "ClientNotifier.h"

namespace rfb {

	namespace win32 {

		class CView;

		class CViewManager : public MsgWindow 
		{
		public:
			CViewManager();
			~CViewManager();

			void remNotifier(__int64 uid);
			void addNotifier(__int64 uid, ClientNotifier* pNotifier);
			//添加客户端
			CView* addClient(__int64 uid, std::string name);
			//删除客户端
			void remClient(__int64 uid = 0);
			CView* getView(__int64 uid);

			__int64 getTopDesktopClientId();

		protected:
			LRESULT processMessage(UINT msg, WPARAM wParam, LPARAM lParam);
	
		private:
			//客户端指针
			std::list<CView*> m_viewList;

	    public:
			// 每个view关闭时要通知的对象
		    map<__int64, list<ClientNotifier*> > m_notifierMap;
		};

	};

};

#endif
