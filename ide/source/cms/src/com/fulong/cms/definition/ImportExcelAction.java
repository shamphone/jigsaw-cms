/**
 * 
 */
package com.fulong.cms.definition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.fulong.cms.form.UploadFileForm;
import com.fulong.longcon.exchange.ExcelImporter;
import com.fulong.longcon.exchange.ImporterEventListener;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 *   
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author songbo
 *
 * @version 2.0
 *
 */
public class ImportExcelAction extends DefinitionBaseAction {
	@Override
	public ActionForward definitionPerform(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UploadFileForm form = (UploadFileForm)aform;
		ActionMessages messages = new ActionMessages();
		EventListenerexcel listener = new EventListenerexcel(messages);
		ExcelImporter importer = new ExcelImporter(this.getRepository(request));		
		importer.setEventListener(listener);
		importer.doImport(form.getFile().getInputStream());
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("import.finished"));
		this.saveMessages(request, messages);
		return mapping.findForward("success");
	}
}

class EventListenerexcel implements ImporterEventListener {
	private ActionMessages messages;
	public EventListenerexcel(ActionMessages messages){
		this.messages = messages;
	}

	public void error(String message) {
		ActionMessage msg=new ActionMessage("import.error", message);
		 this.messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
		
	}

	public void info(String message) {
		ActionMessage msg=new ActionMessage("import.info", message);
		 this.messages.add(ActionMessages.GLOBAL_MESSAGE, msg);

		
	}

	public void nodeDefinitionImported(NodeDefinition definition) {
		ActionMessage msg=new ActionMessage("import.node.definition", definition.getID(), definition.getName());
		 this.messages.add(ActionMessages.GLOBAL_MESSAGE, msg);		
	}

	public void nodeImported(Node node) {
		ActionMessage msg=new ActionMessage("import.node",node.getID());
		 this.messages.add(ActionMessages.GLOBAL_MESSAGE, msg);	
		
	}

	public void propertyDefinitionImported(PropertyDefinition definition) {
		
	}

}