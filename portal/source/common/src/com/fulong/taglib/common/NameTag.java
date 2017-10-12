package com.fulong.taglib.common;

import java.security.Principal;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.ResponseUtils;

import com.fulong.longcon.security.Group;
import com.fulong.longcon.security.GroupIterator;
import com.fulong.longcon.security.Organization;
import com.fulong.longcon.security.PassportProvider;
import com.fulong.longcon.security.User;
import com.fulong.taglib.SpringTagSupport;

/**
 * 
 * <p>
 * Title: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2008
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 3.0
 */
public class NameTag extends SpringTagSupport {

	private static final long serialVersionUID = -6420726328178780386L;

	private String name;
	private String property;
	private String scope;
	private boolean ignore;
	private String key;
	private TagUtils utils = TagUtils.getInstance();
	private boolean simple;

	public NameTag() {
		ignore = true;
	}

	@SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		Principal principal = null;
		Object o = null;
		try {
			o = utils.lookup(pageContext, name, property, scope);
		} catch (Exception ex) {

		}
		if (o != null && o instanceof Principal) {
			principal = (Principal) o;
		}
		if (principal == null) {
			principal = request.getUserPrincipal();
		}
		if (principal == null) {
			if (ignore) {
				return (SKIP_BODY);
			} else {
				throw new JspException("Unable to find login users!");
			}
		}
		PassportProvider provider = (PassportProvider) this.getContext()
				.getBean("passport", PassportProvider.class);
		StringBuffer value = new StringBuffer();
		if (principal instanceof Group) {
			Group group = provider.getGroup(principal.getName());
			value.append(group.getCommonname());
		} else {
			User user = null;
			try {
				user = provider.getUser(principal.getName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Organization org = provider.getOrganization(principal);
			if (org != null) {
				GroupIterator git = this.getPassportProvider().getRootGroup()
						.children();

				while (git.hasNext()) {
					Group group = git.nextGroup();

					if (org.isNodeType(group.getID())) {
						value.append(group.getCommonname());
						value.append("&nbsp;:&nbsp;");
					}
					// }
				}
				value.append(org.getCommonname());
			} else {
				String nickname = user.getProperty("common-name").getString();
				if (nickname != null && !nickname.equals("")) {
					value.append(nickname);
				} else {
					String commonname = user.getCommonname();
					if (commonname != null && !commonname.equals("")) {
						value.append(commonname);
					} else {
						value.append(user.getUsername());
					}
				}

			}
			if (!this.simple) {
				value.append("&nbsp;[&nbsp;");
				value.append(user.getUsername());
				value.append("&nbsp;]");
			}
		}
		ResponseUtils.write(pageContext, value.toString());
		
		return (SKIP_BODY);
	}

	public int doEndTag() throws JspException {

		// Clean up our started state
		key = null;
		name = null;
		property = null;
		ignore = true;

		// Continue processing this page
		return (EVAL_PAGE);

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

}
