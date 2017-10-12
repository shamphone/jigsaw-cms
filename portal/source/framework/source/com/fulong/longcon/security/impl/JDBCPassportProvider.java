package com.fulong.longcon.security.impl;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Iterator;
import org.springframework.dao.DataAccessException;
import org.springframework.security.AuthenticationException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.fulong.longcon.ItemExistsException;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.security.Organization;
import com.fulong.longcon.security.PassportException;
import com.fulong.longcon.security.PassportIdentity;
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
public class JDBCPassportProvider implements SecurityManagerExt, UserDetailsService {
	private String administrator;
	private String passportServerURL;
	private Repository repository;
	/**
	 * 根组
	 */
	private Group root;
	/**
	 * 机构根组
	 */
	private Group orgRoot;

	public JDBCPassportProvider() {
		
	}

	/**
	 * the init method initialize the passport provider
	 * 
	 * @throws IOException
	 */
	public void init() {
	
		this.root = this.getGroup(Group.ROOT);
		if (root == null)
			throw new PassportException(
					"Unable to find root group, check the database for default setting.");
		this.orgRoot = this.getGroup(Group.ORG_ROOT);
		if (orgRoot == null)
			throw new PassportException(
					"Unable to find org group, check the database for default setting.");
	}

	public void start() {

	}

	
	public void setPassportServerURL(String passportServerURL) {
		this.passportServerURL = passportServerURL;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public String getPassportServerURL() {
		return this.passportServerURL;
	}

	public String getAdministrator() {
		return administrator;
	}

	/**
	 * 
	 * @param node
	 *            Node
	 * @return Node
	 */
	public Node nodeLoading(Node node) {
		if (node.isNodeType(this.root.getNodeDefinition().getID())) {

		}
		return node;
	}

	/**
	 * 根据ID来获取机构、用户或者组。
	 * 
	 * @param ID
	 *            String
	 * @return PassportIdentity
	 * @throws SQLException 
	 */
	public PassportIdentity getPrincipal(String ID) throws SQLException {
		Group group = this.getGroup(ID);
		if (group != null)
			return group;
		return this.getUser(ID);
	}

	public Organization getOrganization(Principal admin) {
		return (Organization) admin;
	}

	/**
	 * 获取系统缺省的机构
	 * 
	 * @return Organization
	 */
	public Organization getDefaultOrganization() {
		return this.getOrganization(this.administrator);
	}

	/**
	 * 初始化方法，设置缺省机构
	 * 
	 * @param organization
	 *            String
	 */
	public void setOrganization(String organization) {
	}


	/**
	 * @param membership
	 *            Membership
	 * 
	 *            public void delete(Membership membership) { DaoFactory factory
	 *            = this.getDaoFactory(); try { factory.open(); MembershipDao
	 *            dao = (MembershipDao) factory.getDao(MembershipDao.class);
	 *            dao.deleteByPkid(membership.getId()); } catch (SQLException
	 *            ex) { factory.rollback(); throw new JdbcException(ex); }
	 * 
	 *            catch (DaoException ex) { factory.rollback(); throw ex; }
	 *            finally { factory.close(); } }
	 */

	/**
	 * @param groupId
	 *            String 组的ID
	 * @return Group
	 */
	public Group getGroup(String groupId) {
		NodeDefinition definition = this.repository.getDefinitionManager()
				.getDefinition(groupId);
		if (definition == null)
			return null;
		return new GroupImpl(this, definition);
	}

	/**
	 *使用存储过程，删除组及其子组、成员关系。将删除操作放到GroupDao中
	 * 
	 * @param group
	 *            Group
	 */
	public void delete(Principal node) {
		this.repository.delete((Node) node);
	}

	public Iterator<?> getAffiliatedGroups(Principal principal) {
		return this.getRootGroup().getAffiliatedGroups(principal);
	}


	/**
	 * 获取根组
	 * 
	 * @return Group
	 */
	public Group getRootGroup() {
		return this.root;
	}

	/**
	 * 根据ID或者UserName来获得用户。
	 * 
	 * @param ID
	 *            String
	 * @return User
	 * @throws SQLException 
	 */
	public User getUser(String ID) throws SQLException {
		Node user = (Node) this.repository.getNode(ID);
		if (user != null)
			return new PassportPrincipalImpl(user, this);
		Query query = this.repository.getQueryManager().createQuery(
				repository.getRootNode().getDefinition(), Query.SQL);
		query.filterByParent(repository.getRootNode(), true);
		query.filterByProperty("user-username", ID);
		NodeIterator<?> iterator = query.nodes();
		if (iterator.getSize() == 0)
			return null;
		return new PassportPrincipalImpl(iterator.nextNode(), this);

	}

	/**
	 * @throws ItemExistsException
	 *             the user with this username already exists.
	 * @param username
	 *            String
	 * @param password
	 *            String
	 * @return User
	 * @throws SQLException 
	 */
	public User createUser(String username, String password) throws SQLException {
		if (this.getUser(username) != null)
			throw new ItemExistsException("User with username " + username
					+ "  has exists.");
		Node node = this.repository.getRootNode().addNode(
				this.root.getNodeDefinition(), "users");
		node.setProperty("user-password", password);
		node.setProperty("user-username", username);
		return new PassportPrincipalImpl(node, this);
	}

	/**
	 * 创建机构
	 * 
	 * @param enterpriseName
	 *            String
	 * @param creator
	 *            Principal
	 * @return Organization
	 */
	public Organization createOrganization(String enterpriseName,
			Principal creator) {
		Node node = (Node) creator;
		node.addMixinDefinition(this.orgRoot.getNodeDefinition());
		node.setProperty("org-name", enterpriseName);
		if (node.getProperty("common-name").getString() == null)
			node.setProperty("common-name", enterpriseName);
		return (Organization) node;
	}

	/**
	 * 
	 * @param orgID
	 *            String
	 * @return Organization
	 */
	public Organization getOrganization(String orgID) {
		Node node = this.repository.getNode(orgID);
		if (node == null)
			return null;
		return new PassportPrincipalImpl(node, this);
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public UserDetails loadUserByUsername(String username)
			throws DataAccessException, UsernameNotFoundException {
		Query query = repository.getQueryManager().createQuery(
				this.repository.getDefinitionManager().getDefinition(
						"principal-scheme"), Query.SQL);
		query.filterByProperty("user-username", username);
		NodeIterator<?> nodes = query.nodes();
		if (!nodes.hasNext())
			throw new UsernameNotFoundException("no user found with username "
					+ username);
		return new PassportPrincipalImpl(nodes.nextNode(), this);
	}

	public UserDetails getUserDetails(String username)
			throws AuthenticationException, SQLException {
		return (UserDetails) this.getUser(username);
	}
}
