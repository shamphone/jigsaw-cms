package com.fulong.cms.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author jiangqi,lixf
 * @version 2.0
 */
public class UploadFileForm
    extends ActionForm {

  /**
	 * 
	 */
	private static final long serialVersionUID = -1050471441728305915L;
private FormFile file;
  private String isZip;

  public FormFile getFile() {
      return file;
  }

  public String getIsZip() {
      return isZip;
  }

  public void setFile(FormFile file) {
      this.file = file;
  }

  public void setIsZip(String isZip) {
      this.isZip = isZip;
  }
}
