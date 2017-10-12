package com.fulong.longcon.security.impl;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import com.fulong.common.util.DesEncrypter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueUtils;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.security.PassportPrincipal;
import com.fulong.longcon.security.User;
import com.fulong.longcon.security.ext.SecurityManagerExt;

/**
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author Lixf
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class PassportPrincipalImpl extends NodeWrapper implements UserDetails,
		PassportPrincipal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7577648903655008086L;
	private SecurityManagerExt manager;
	protected static final String DES_KEY = "fulong";

	public PassportPrincipalImpl(Node node, SecurityManagerExt manager) {
		super(node);
		this.manager = manager;
	}

	/**
	 * 
	 * @return String
	 */
	public String getName() {
		return this.getID();
	}

	/**
	 * 
	 * @return String
	 */
	public String getUsername() {
		if (this.getProperty("user-username") != null)
			return this.getProperty("user-username").getString();
		return null;
	}

	/**
	 * 
	 * @param name
	 *            String
	 */
	public void setCommonname(String name) {
		this.getProperty("user-commonname").setValue(name);
	}

	/**
	 * 
	 * @return String
	 */
	public String getEmail() {
		if (this.getProperty("user-email") != null)
			return this.getProperty("user-email").getString();
		return null;

	}

	/**
	 * 
	 * @param email
	 *            String
	 */
	public void setEmail(String email) {
		this.getProperty("user-email").setValue(email);
	}

	/**
	 * 性别
	 * 
	 * @return int
	 */
	public int getGender() {
		if (this.getProperty("user-gender") == null)
			return 0;
		if (this.getProperty("user-gender").getReference().getID()
				.equals("man"))
			return 1;
		return 2;
	}

	/**
	 * 性别
	 * 
	 * @param gender
	 *            int
	 */
	public void setGender(int gender) {
		String property = "man";
		if (gender == 2)
			property = "woman";
		Node node = this.manager.getRepository().getNode(property);
		this.getProperty("user-gender").setValue(node);
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getBirthday() {
		Property property = this.getProperty("user-birthday");
		if (property == null)
			return null;
		if (property.getDate() == null)
			return null;
		return property.getDate().getTime();

	}

	/**
	 * 
	 * @param date
	 *            Date
	 */
	public void setBirthDay(Date date) {
		Calendar value = Calendar.getInstance();
		value.setTime(date);
		this.getProperty("user-birthday").setValue(value);
	}

	/**
	 * 
	 * @return int
	 */
	public int getAge() {
		if (this.getBirthday() != null)
			return new Date().getYear() - this.getBirthday().getYear();
		return 0;

	}

	/**
	 * 
	 * @return Node
	 */
	public Node getArea() {
		Property property = this.getProperty("org-area");
		if (property != null)
			return property.getReference();
		return null;
	}

	/**
	 * 
	 * @param region
	 *            Node
	 */
	public void setArea(Node region) {
		if (region != null)
			this.setProperty("user-area", region);
	}

	/**
	 * 获取当前用户密码
	 * 
	 * @return String
	 */
	public String getPassword() {
		DesEncrypter encrypter = new DesEncrypter(DES_KEY);
		if (this.getProperty("user-password") == null)
			return null;
		return encrypter.decrypt(this.getProperty("user-password").getString());
	}

	/**
	 * 
	 * @param newPassword
	 *            String
	 */
	public void changePassword(String newPassword) {
		DesEncrypter encrypter = new DesEncrypter(DES_KEY);
		this.getProperty("user-password").setValue(
				encrypter.encrypt(newPassword));
	}

	/**
	 * 
	 * @param password
	 *            String
	 * @return boolean
	 */
	public boolean checkPassword(String password) {
		if (this.getPassword() == null)
			return null == password;
		if (password == null)
			return false;
		return this.getPassword().equals(password);
	}

	/**
	 * 
	 * @param question
	 *            String
	 * @param answer
	 *            String
	 * @return boolean
	 */
	public boolean checkQuestion(String question, String answer) {
		if (question != null && answer != null) {
			if (this.getProperty("user-pwdquestion") != null
					&& this.getProperty("user-pwdanswer") != null) {
				if (this.getProperty("user-pwdquestion").getString().equals(
						question)
						&& this.getProperty("user-pwdanswer").getString()
								.equals(answer)) {
					return true;
				} else
					return false;
			} else
				return false;

		} else
			return false;
	}

	/**
	 * 
	 * @return String
	 */
	public String getIP() {
		if (this.getProperty("IP") != null)
			return this.getProperty("IP").getString();
		return null;

	}

	/**
	 * 
	 * @param ipAddress
	 *            String
	 */
	public void setIP(String ipAddress) {
		this.getProperty("IP").setValue(ipAddress);
	}

	/**
	 * 
	 * @return String
	 */
	public String toString() {
		return this.getCommonname();
	}

	/**
	 * 
	 * @return int
	 */
	public int hashCode() {
		return this.getID().hashCode();
	}

	/**
	 * 
	 * @return int
	 */
	public int getPoints() {
		return (int) this.getProperty("points").getLong();
	}

	/**
	 * 
	 * @param points
	 *            int
	 */
	public void setPoints(int points) {
		this.setProperty("points", (long) points);
	}

	/**
	 * 
	 * @param points
	 *            int
	 */
	public void addPoints(int points) {
		int result = this.getPoints() + points;
		this.setPoints(result);
	}

	/**
	 * 
	 * @param expiringDate
	 *            Date
	 */
	public void setExpiringDate(Date expiringDate) {
		Calendar value = Calendar.getInstance();
		value.setTime(expiringDate);
		this.getProperty("expiring-date").setValue(value);
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getExpiringDate() {
		Property property = this.getProperty("expiring-date");
		if (property == null)
			return null;
		if (property.getDate() == null)
			return null;
		return property.getDate().getTime();

	}

	/**
	 * 
	 * @return Date
	 */
	public Date getRegisterDate() {
		Property property = this.getProperty("register-date");
		if (property == null)
			return null;
		if (property.getDate() == null)
			return null;
		return property.getDate().getTime();
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getLastModifiedDate() {
		Property property = this.getProperty("update-date");
		if (property == null)
			return null;
		if (property.getDate() == null)
			return null;
		return property.getDate().getTime();

	}

	/**
	 *机构管理员和机构是同一个节点
	 * 
	 * @return User
	 */
	public User getAdministrator() {
		return this;
	}

	/**
	 * 
	 * @param admin
	 *            User
	 */
	public void setAdministrator(User admin) {
	}

	/**
	 * 
	 * @return String
	 */
	public String getEnterpriseName() {
		if (this.getProperty("org-enterprisename") != null)
			return this.getProperty("org-enterprisename").getString();
		return null;

	}

	/**
	 * 
	 * @param name
	 *            String
	 */
	public void setEnterpriseName(String name) {
		this.getProperty("org-enterprisename").setValue(name);
	}

	/**
	 * 
	 * @param lastModifiedDate
	 *            Date
	 */
	public void setLastModifiedDate(Date date) {
		Calendar value = Calendar.getInstance();
		value.setTime(date);
		this.getProperty("update-date").setValue(value);
	}

	/**
	 * 
	 * @return String
	 */
	public String getDescription() {
		if (this.getProperty("org-description") != null)
			return this.getProperty("org-description").getString();
		return null;

	}

	/**
	 * 
	 * @param description
	 *            String
	 */
	public void setDescription(String description) {
		this.getProperty("org-description").setValue(description);
	}

	/**
	 * 
	 * @return String
	 */
	public String getBusinessDescription() {
		if (this.getProperty("org-businessdescription") != null)
			return this.getProperty("org-businessdescription").getString();
		return null;

	}

	/**
	 * 
	 * @param description
	 *            String
	 */
	public void setBusinessDescription(String description) {
		this.getProperty("org-businessdescription").setValue(description);
	}

	/**
	 * 
	 * @return Node[]
	 */
	public Node[] getSiccodes() {
		Property property = this.getProperty("org-classification");
		if (property != null)
			return ValueUtils.toNodeArray(property.getValues());
		return null;
	}

	/**
	 * 
	 * @param level
	 *            String
	 * @return String
	 */
	public String getSiccode(String level) {
		int i = Integer.parseInt(level);
		Node[] codeset = this.getSiccodes();
		if (codeset.length == 0)
			return "";
		if (i < 1)
			return "";
		if (codeset[i - 1] != null) {
			return codeset[i - 1].getID();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @param codes
	 *            Node[]
	 */
	public void setSiccodes(Node[] codes) {
		if (codes != null) {
			Value[] values = new Value[codes.length];
			for (int i = 0; i < values.length && i < 5; i++) {
				values[i] = this.manager.getRepository().getValueFactory()
						.createValue(codes[i]);
			}
			// 填充这些值
			this.setProperty("org-classification", values); // oraganization表的行业字段转移到属性表中维护
		}
	}

	/**
	 * 
	 * @return String
	 */
	public String getCommonname() {
		Property property = this.getProperty("user-commonname");
		if (property == null)
			return null;
		return property.getString();
	}

	/**
	 * 
	 * @return String
	 */
	public String getId() {
		return this.getID();
	}

	/**
	 * 
	 * @return int
	 */
	public int getType() {
		if (this.getDefinition().isNodeType(Group.ORG_ROOT))
			return ORGANIZATION;
		return USER;

	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isValid() {
		return true;
	}

	/**
	 * 用户所在的组
	 * 
	 * @return Group
	 */
	public Group getGroup() {
		return new GroupImpl(this.manager, this.getDefinition());
	}

	/**
	 * 
	 * @return Principal
	 */
	public Principal getMember() {
		return this;
	}

	public Date getApplyDate() {
		Property property = this.getProperty("apply-date");
		if (property == null)
			return null;
		if (property.getDate() == null)
			return null;
		return property.getDate().getTime();

	}

	public Date getJoinDate() {
		Property property = this.getProperty("join-date");
		if (property == null)
			return null;
		if (property.getDate() == null)
			return null;
		return property.getDate().getTime();

	}

	public boolean isOrganization() {
		return this.getNode().isNodeType("org-scheme");
	}

	public boolean isUser() {
		return this.getNode().isNodeType("principal-scheme");
	}

	public GrantedAuthority[] getAuthorities() {
		if (this.getID().equals(this.manager.getAdministrator())) {
			return new GrantedAuthority[] { new GrantedAuthorityImpl(
					PassportPrincipal.ROLE_ADMIN) };
		}
		return new GrantedAuthority[0];
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public int getNodeType(String defid) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public NodeIterator<Node> getAllNodes(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaxOrderNo(int orderNo) {
		// TODO Auto-generated method stub
		
	}

}
