package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.cms.form.EditForm;
import com.fulong.longcon.repository.Node;

public class BatSaveModifyAction extends ContentBaseAction
{
	public ActionForward doExecute(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditForm form = (EditForm)aform;
		String[] colNames = form.getValueNames();
		String[] nodeList = request.getParameterValues("nodeID");
		for(int j=0;j<nodeList.length;j++)
		{
			Node node = this.getRepository(request).getNode(nodeList[j]);
			for(int i=0;i<colNames.length;i++)
			{
				String[] foo = form.getValues(colNames[i]);
				if (foo!=null)
				{
					//检查是否被勾选，需要将该列置为空
					String bar=request.getParameter("values(" + colNames[i] +")check");	
					if(bar!=null&&bar.equalsIgnoreCase("false")) //需要置空
					{
						String[] emp = new String[1];
					    emp[0]="";
					    node.setProperty(colNames[i],emp);
					} 
					else
					{
						int l = 0;
						for(int k=0;k<foo.length;k++)
							if(foo[k]!=null&&!foo[k].trim().equalsIgnoreCase(""))
							     l++;
						if(l == 0) //该列不需要更新
							continue;
						node.setProperty(colNames[i], foo);
					}
				}
			}
		}
		return this.forward(mapping,"success","Batch modified done");
	}
}
