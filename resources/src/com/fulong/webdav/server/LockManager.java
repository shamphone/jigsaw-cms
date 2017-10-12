package com.fulong.webdav.server;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Collection;

import com.fulong.webdav.utils.XMLWriter;

/**
 * 资源锁管理器
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
public class LockManager {
	/**
	 * Repository of the locks put on single resources.
	 * <p>
	 * Key : path <br>
	 * Value : LockInfo
	 */
	private Hashtable<String, LockInfo> resourceLocks = new Hashtable<String, LockInfo>();

	/**
	 * Repository of the lock-null resources.
	 * <p>
	 * Key : path of the collection containing the lock-null resource<br>
	 * Value : Vector of lock-null resource which are members of the collection.
	 * Each element of the Vector is the path associated with the lock-null
	 * resource.
	 */
	private Hashtable<String, Vector<String>> lockNullResources = new Hashtable<String, Vector<String>>();

	/**
	 * Vector of the heritable locks.
	 * <p>
	 * Key : path <br>
	 * Value : LockInfo
	 */
	private Vector<LockInfo> collectionLocks = new Vector<LockInfo>();

	public LockManager() {
		super();
	}

	/**
	 * 判断一个空路径是否被锁定
	 * 
	 * @param path
	 *            String
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean isLockNullPath(String path) {
		int slash = path.lastIndexOf('/');
		if (slash == -1)
			return false;
		String parentPath = path.substring(0, slash);
		Vector currentLockNullResources = (Vector) lockNullResources
				.get(parentPath);
		if (currentLockNullResources != null) {
			Enumeration lockNullResourcesList = currentLockNullResources
					.elements();
			while (lockNullResourcesList.hasMoreElements()) {
				String lockNullPath = (String) lockNullResourcesList
						.nextElement();
				if (lockNullPath.equals(path)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 解除锁定
	 * 
	 * @param path
	 *            String
	 */
	public void removeLock(String path) {
		this.lockNullResources.remove(path);
	}

	/**
	 * Checking if there is already a shared lock on this path
	 * 
	 * @param path
	 *            String
	 * @return LockInfo
	 */
	public LockInfo getSharedLock(String path) {
		// Checking if there is already a shared lock on this path
		Enumeration<LockInfo> locksList = collectionLocks.elements();
		while (locksList.hasMoreElements()) {

			LockInfo currentLock = (LockInfo) locksList.nextElement();
			if (currentLock.path.equals(path)) {
				return currentLock;
			}
		}
		return null;
	}

	public LockInfo getLock(String path) {
		LockInfo lock = this.getResourceLock(path);
		if (lock != null)
			return lock;
		return this.getSharedLock(path);
	}

	/**
	 * Locking a collection (and all its member resources)
	 * 
	 * @param path
	 *            String
	 * @return Collection
	 */
	public Collection<String> lockCollection(LockInfo lock) {
		Vector<String> lockPaths = new Vector<String>();
		Enumeration<LockInfo> locksList = collectionLocks.elements();
		while (locksList.hasMoreElements()) {
			LockInfo currentLock = (LockInfo) locksList.nextElement();
			if (currentLock.hasExpired()) {
				resourceLocks.remove(currentLock.path);
				continue;
			}
			if ((currentLock.path.startsWith(lock.path))
					&& ((currentLock.isExclusive()) || (lock.isExclusive()))) {
				// A child collection of this collection is locked
				lockPaths.addElement(currentLock.path);
			}
		}
		locksList = resourceLocks.elements();
		while (locksList.hasMoreElements()) {
			LockInfo currentLock = (LockInfo) locksList.nextElement();
			if (currentLock.hasExpired()) {
				resourceLocks.remove(currentLock.path);
				continue;
			}
			if ((currentLock.path.startsWith(lock.path))
					&& ((currentLock.isExclusive()) || (lock.isExclusive()))) {
				// A child resource of this collection is locked
				lockPaths.addElement(currentLock.path);
			}
		}
		return lockPaths;

	}

	public void addCollectionLock(LockInfo lock) {
		this.collectionLocks.add(lock);
	}

	public LockInfo getResourceLock(String path) {
		return (LockInfo) this.resourceLocks.get(path);
	}

	public void addResourceLock(LockInfo lock) {
		this.resourceLocks.put(lock.path, lock);
	}

	// "Creating" a lock-null resource
	public void createLockNULL(String path) {
		// "Creating" a lock-null resource
		int slash = path.lastIndexOf('/');
		String parentPath = path.substring(0, slash);

		Vector<String> lockNulls = (Vector<String>) lockNullResources.get(parentPath);
		if (lockNulls == null) {
			lockNulls = new Vector<String>();
			lockNullResources.put(parentPath, lockNulls);
		}

		lockNulls.addElement(path);

	}

	public LockInfo removeLock(String path, String lockTokenHeader) {
		LockInfo lock = this.getResourceLock(path);
		Enumeration<?> tokenList = null;
		if (lock != null) {

			// At least one of the tokens of the locks must have been given

			tokenList = lock.tokens.elements();
			while (tokenList.hasMoreElements()) {
				String token = (String) tokenList.nextElement();
				if (lockTokenHeader.indexOf(token) != -1) {
					lock.tokens.removeElement(token);
				}
			}

			if (lock.tokens.isEmpty()) {
				resourceLocks.remove(path);
				// Removing any lock-null resource which would be present
				lockNullResources.remove(path);
			}
		}
		Enumeration<LockInfo> collectionLocksList = collectionLocks.elements();
		while (collectionLocksList.hasMoreElements()) {
			lock = (LockInfo) collectionLocksList.nextElement();
			if (path.equals(lock.path)) {

				tokenList = lock.tokens.elements();
				while (tokenList.hasMoreElements()) {
					String token = (String) tokenList.nextElement();
					if (lockTokenHeader.indexOf(token) != -1) {
						lock.tokens.removeElement(token);
						break;
					}
				}

				if (lock.tokens.isEmpty()) {
					collectionLocks.removeElement(lock);
					// Removing any lock-null resource which would be present
					lockNullResources.remove(path);
				}

			}
		}
		return lock;

	}

	/**
	 * Checking inheritable collection locks
	 * 
	 * @param path
	 *            String
	 * @param ifHeader
	 *            String
	 * @return LockInfo
	 */
	public LockInfo checkCollectionLock(String path, String ifHeader) {
		Enumeration<LockInfo> collectionLocksList = collectionLocks.elements();
		while (collectionLocksList.hasMoreElements()) {
			LockInfo toRenew = (LockInfo) collectionLocksList.nextElement();
			if (path.equals(toRenew.path)) {
				if (toRenew.checkIfHeader(ifHeader)) {
					return toRenew;
				}
			}
		}
		return null;

	}

	/**
	 * 获取一个目录下所有的Lock null资源路径
	 * 
	 * @param path
	 *            String
	 * @return Enumeration <path>
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getLockNullResources(String currentPath) {
		String lockPath = currentPath;
		if (lockPath.endsWith("/"))
			lockPath = lockPath.substring(0, lockPath.length() - 1);
		Vector currentLockNullResources = (Vector) lockNullResources
				.get(lockPath);
		if (currentLockNullResources != null)
			return currentLockNullResources.elements();
		else
			return new Vector().elements();

	}

	/**
	 * Check to see if a resource is currently write locked.
	 * 
	 * @param path
	 *            Path of the resource
	 * @param ifHeader
	 *            "If" HTTP header which was included in the request
	 * @return boolean true if the resource is locked (and no appropriate lock
	 *         token has been found for at least one of the non-shared locks
	 *         which are present on the resource).
	 */
	public boolean isLocked(String path, String ifHeader) {

		// Checking resource locks

		LockInfo lock = (LockInfo) resourceLocks.get(path);
		Enumeration<?> tokenList = null;
		if ((lock != null) && (lock.hasExpired())) {
			resourceLocks.remove(path);
		} else if (lock != null) {

			// At least one of the tokens of the locks must have been given

			tokenList = lock.tokens.elements();
			boolean tokenMatch = false;
			while (tokenList.hasMoreElements()) {
				String token = (String) tokenList.nextElement();
				if (ifHeader.indexOf(token) != -1)
					tokenMatch = true;
			}
			if (!tokenMatch)
				return true;

		}

		// Checking inheritable collection locks

		Enumeration<LockInfo> collectionLocksList = collectionLocks.elements();
		while (collectionLocksList.hasMoreElements()) {
			lock = (LockInfo) collectionLocksList.nextElement();
			if (lock.hasExpired()) {
				collectionLocks.removeElement(lock);
			} else if (path.startsWith(lock.path)) {

				tokenList = lock.tokens.elements();
				boolean tokenMatch = false;
				while (tokenList.hasMoreElements()) {
					String token = (String) tokenList.nextElement();
					if (ifHeader.indexOf(token) != -1)
						tokenMatch = true;
				}
				if (!tokenMatch)
					return true;

			}
		}

		return false;

	}

	/**
	 * Print the lock discovery information associated with a path.
	 * 
	 * @param path
	 *            Path
	 * @param generatedXML
	 *            XML data to which the locks info will be appended
	 * @return true if at least one lock was displayed
	 */
	public boolean generateLockDiscovery(String path, XMLWriter generatedXML) {

		LockInfo resourceLock = (LockInfo) resourceLocks.get(path);
		Enumeration<LockInfo> collectionLocksList = collectionLocks.elements();

		boolean wroteStart = false;

		if (resourceLock != null) {
			wroteStart = true;
			generatedXML.writeElement(null, "lockdiscovery", XMLWriter.OPENING);
			resourceLock.toXML(generatedXML);
		}

		while (collectionLocksList.hasMoreElements()) {
			LockInfo currentLock = (LockInfo) collectionLocksList.nextElement();
			if (path.startsWith(currentLock.path)) {
				if (!wroteStart) {
					wroteStart = true;
					generatedXML.writeElement(null, "lockdiscovery",
							XMLWriter.OPENING);
				}
				currentLock.toXML(generatedXML);
			}
		}

		if (wroteStart) {
			generatedXML.writeElement(null, "lockdiscovery", XMLWriter.CLOSING);
		} else {
			return false;
		}

		return true;

	}

}
