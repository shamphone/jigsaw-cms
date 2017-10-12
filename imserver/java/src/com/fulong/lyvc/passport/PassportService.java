/**
 * Implementation of com.fulong.lyvc.user.UserSession
 * for Longcon passport system
 */

package com.fulong.lyvc.passport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.logging.Log;import org.apache.commons.logging.LogFactory;

import com.fulong.common.util.PageIterator;
import com.fulong.longcon.security.Group;
import com.fulong.longcon.security.Membership;
import com.fulong.longcon.security.Organization;
import com.fulong.longcon.security.UserQuery;
import com.fulong.longcon.security.impl.PassportProviderImpl;
import com.fulong.longcon.xmldb.XMLDBDaoConfig;
import com.fulong.lyvc.dao.UserDAO;
import com.fulong.lyvc.data.GroupData;
import com.fulong.lyvc.data.UserData;
import com.fulong.lyvc.user.UserContact;
import com.fulong.lyvc.user.UserLibraryException;
/**
 * 
 * PassportService
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-11
 */
public class PassportService implements UserDAO {

	// Log
	private static Log logger = LogFactory.getLog(PassportService.class);

	// Web service binding.
	private PassportProviderImpl passport;

	public void init(Properties properties) throws Exception {
		this.passport = new PassportProviderImpl();
		this.passport.setOrganization("2299324263399");
		XMLDBDaoConfig config = new XMLDBDaoConfig();
		config.setTargetURL(properties.getProperty("url"));
		config.setTimeOut(6000);
		config.setDaoMappingFile("xmldb");
		this.passport.setXMLDBDaoConfig(config);
	}

	/**
	 * Close the user session
	 */
	public void close() throws Exception {
		return;
	}

	public void modifyUser(UserData userData) throws SQLException {
		com.fulong.longcon.security.User securityUser = null;
		securityUser = passport.getUserRepository().findByPrimaryKey(String.
				valueOf(userData.getId()));
		securityUser.setEmail(userData.getEmail());
		securityUser.setCommonname(userData.getFirstName() +
								   userData.getLastName());
	}

	/**
	 * Create a user, if successful, return id of user, otherwise 0
	 */
	public long createUser(UserData userData) throws SQLException {

			com.fulong.longcon.security.User user = this.passport.
					getUserRepository().create(userData.getAccountName(),
											   userData.getPassword());
			user.setCommonname(userData.getFirstName() + userData.getLastName());
			user.setArea(this.passport.getDictionaryManager().getRegionDict().
						 getRegion("999999"));

			return Long.parseLong(user.getId());

	}
	public long createUserFromClient(UserData userData) throws SQLException{
		return 0;
	}
	// Get user from passport by username/password
	// If no user found, null is returned.
	public com.fulong.lyvc.User authenticate(String username,
												  String password) {
		try {
			com.fulong.longcon.security.User securityUser = null;
			UserQuery query = passport.getUserRepository().createQuery();
			query.addUsername(username);
			query.addPassword(password);
			PageIterator iter = query.results(0, 1);
			if (iter.hasNext()) {
				securityUser = (com.fulong.longcon.security.User) iter.next();
				return new com.fulong.lyvc.passport.PassportUser(securityUser);
			}
			return null;
		} catch (Exception ex) {
			logger.warn("Fail to authenticate user " + username + "/" +
						password, ex);
			return null;
		}
	}

	public com.fulong.lyvc.User getByAccountName(String accountName) {
		try {
			com.fulong.longcon.security.User securityUser = null;
			securityUser = passport.getUserRepository().findByUsername(
					accountName);
			return new com.fulong.lyvc.passport.PassportUser(securityUser);
		} catch (Exception ex) {
			logger.warn("Fail to get user " + accountName, ex);
			return null;
		}
	}

	public com.fulong.lyvc.User getByEmail(String email) {
		com.fulong.longcon.security.User securityUser = null;
		UserQuery query = passport.getUserRepository().createQuery();
		query.addEmail(email);
		PageIterator iter = query.results(0, 1);
		if (iter.hasNext()) {
			securityUser = (com.fulong.longcon.security.User) iter.next();
			return new com.fulong.lyvc.passport.PassportUser(securityUser);
		}
		return null;
	}

	public com.fulong.lyvc.User getUserById(long id) {
		try {
			com.fulong.longcon.security.User securityUser = null;
			securityUser = passport.getUserRepository().findByPrimaryKey(String.
					valueOf(id));
			return new com.fulong.lyvc.passport.PassportUser(securityUser);
		} catch (Exception ex) {
			logger.warn("Fail to get user by id " + id, ex);
			return null;
		}
	}

	public long addGroup(long parentGroupId, String groupName, String groupDesc,
						 long creatorId) throws SQLException {

			Organization org = this.passport.getDefaultOrganization();
			Group group = org.createDepartment(groupName, org.getGroup(String.
					valueOf(parentGroupId)));
			group.setDescription(groupDesc);
			group.setDefaultPeriod(0);

			com.fulong.longcon.security.User user = this.passport.
					getUserRepository().findByPrimaryKey(
							String.valueOf(creatorId));
			Membership membership = user.join(group);
			membership.setRole(Group.ROLE_ADMINISTRATOR);
			return Long.parseLong(group.getId());

	}
	public void addGroup(long parentGroupId, long groupId, String groupName) throws SQLException{
	}

	public void addGroupMember(long parentGroupId, long userId) throws
	SQLException {
		Organization org = this.passport.getDefaultOrganization();
		com.fulong.longcon.security.Group group = org.getGroup(String.valueOf(
				parentGroupId));
		com.fulong.longcon.security.User user = this.passport.
												getUserRepository().
												findByPrimaryKey(
				String.valueOf(userId));
		user.join(group);
	}

	public GroupData getGroup(long groupId) throws SQLException {
		
			Organization org = this.passport.getDefaultOrganization();
			com.fulong.longcon.security.Group group = org.getGroup(String.
					valueOf(groupId));
			GroupData data = new GroupData();
			data.setId(groupId);
			data.setName(group.getName());
			data.setDesc(group.getDescription());
			data.setCreatorId(Long.parseLong(group.getCreator().getId()));
			return data;
		
	}

	public void modifyGroup(long groupId, String groupName, String groupDesc) throws
	SQLException {
		Organization org = this.passport.getDefaultOrganization();
		com.fulong.longcon.security.Group group = org.getGroup(String.valueOf(
				groupId));
		group.setName(groupName);
		group.setDescription(groupDesc);
	}

	public void delGroupMember(long groupId, long memberId) throws SQLException {
		Organization org = this.passport.getDefaultOrganization();
		com.fulong.longcon.security.Group group = org.getGroup(String.valueOf(
				groupId));

		com.fulong.longcon.security.User securityUser = null;
		securityUser = passport.getUserRepository().findByPrimaryKey(String.
				valueOf(memberId));
		Membership membership = group.membership(securityUser);
		group.delete(membership);
	}

	public ArrayList getSubGroups(long groupId) throws SQLException {
	
			Organization org = this.passport.getDefaultOrganization();
			com.fulong.longcon.security.Group group = org.getGroup(String.
					valueOf(groupId));
			PageIterator groups = group.children();
			ArrayList shippers = new ArrayList();
			while (groups.hasNext()) {
				GroupData groupData = new GroupData();
				com.fulong.longcon.security.Group subGroup = (com.fulong.
						longcon.security.Group) groups.next();
				groupData.setId(Long.parseLong(subGroup.getId()));
				groupData.setName(subGroup.getName());
				groupData.setDesc(subGroup.getDescription());
				groupData.setCreatorId(Long.parseLong(subGroup.getCreator().
						getId()));
				shippers.add(groupData);
			}
			return shippers;
	
	}

	public ArrayList getMembers(long groupId) throws SQLException {
	
			ArrayList shippers = new ArrayList();
			Organization org = this.passport.getDefaultOrganization();
			com.fulong.longcon.security.Group group = org.getGroup(String.
					valueOf(groupId));
			PageIterator memberships = group.memberships();
			while (memberships.hasNext()) {
				com.fulong.longcon.security.Membership membership = (com.fulong.
						longcon.security.Membership) memberships.next();
				if (membership.getMember() instanceof com.fulong.longcon.
					security.User) {
					UserData userData = new UserData();
					com.fulong.longcon.security.User user = (com.fulong.longcon.
							security.User) membership.getMember();
					userData.setId(Long.parseLong(user.getId()));
					userData.setAccountName(user.getUsername());
					userData.setFirstName(user.getName());
					userData.setLastName("");
					userData.setPassword("");
					userData.setEmail(user.getEmail());
					shippers.add(userData);
				}
			}
			return shippers;
		
	}

	public void removeUser(long userId) throws SQLException {
		com.fulong.longcon.security.User securityUser = null;
		securityUser = passport.getUserRepository().findByPrimaryKey(String.
				valueOf(userId));
		passport.getUserRepository().delete(securityUser);
	}

	public void removeGroup(long groupId) throws SQLException {
		Organization org = this.passport.getDefaultOrganization();
		com.fulong.longcon.security.Group group = org.getGroup(String.valueOf(
				groupId));
		org.delete(group);
	}

	public void setUserPassword(long userId, String password) throws SQLException {
		com.fulong.longcon.security.User securityUser = null;
		securityUser = passport.getUserRepository().findByPrimaryKey(String.
				valueOf(userId));
		securityUser.changePassword(password);
	}

	public long getParentId(long Id) throws SQLException {
		throw new UserLibraryException("method is not implemented. ");
	}

	public long[] getAllUsersId() {
		throw new UserLibraryException("method is not implemented. ");
	}

	public UserContact getCommonContact() {
		throw new UserLibraryException("method is not implemented. ");
	}

	public void assignGroupManager(long groupId, long userId) throws SQLException {
		throw new UserLibraryException("method is not implemented. ");
	}

	public boolean isGroupManager(long userId) throws SQLException{
		throw new UserLibraryException("method is not implemented. ");
	}
	public void moveGroupMember(long fromGroupId, long toGroupId, long memberId) throws SQLException{
	}
}
