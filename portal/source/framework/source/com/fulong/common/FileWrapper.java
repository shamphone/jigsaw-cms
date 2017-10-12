package com.fulong.common;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.fulong.common.util.Tree;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class FileWrapper {
	private File file;
	private int depth;
	private File root;

	public FileWrapper(String path, int depth) {
		this(new File(path), depth);
	}

	/**
	 * 
	 * @param file
	 *            File
	 * @param depth
	 *            int 相对于根目录的深度
	 */
	public FileWrapper(File file, int depth) {
		this.file = file;
		this.depth = depth;
		File parent = file;
		for (int i = 0; i < depth; i++) {
			parent = parent.getParentFile();
		}
		root = parent;
	}

	/**
	 * 
	 * @param file
	 *            File
	 * @param depth
	 *            int 相对于根目录的深度
	 */
	public FileWrapper(File file, File root) {
		this.file = file;
		this.root = root;
		File parent = file;
		int depth = 0;
		while ((parent != null) && (!parent.equals(root))) {
			parent = parent.getParentFile();
			depth++;
		}
		if ((parent != null) && !parent.equals(root)) {
			throw new IllegalArgumentException(root.getPath() + " is not parent of " + file.getPath());
		}
		this.depth = depth;
	}

	/**
	 * 获取指定相对路径的文件
	 * 
	 * @param path
	 *            String
	 * @return FileWrapper
	 */
	public FileWrapper getChild(String path) {
		if (path == null) {
			return this;
		}
		if (path.length() == 0) {
			return this;
		}
		if (path.startsWith("/") || path.startsWith("\\")) {
			return new FileWrapper(new File(root, path.substring(1)), root);
		} else {
			return new FileWrapper(new File(file, path), root);
		}
	}

	/**
	 * 获取缺省的文件过滤器
	 * 
	 * @return FileFilter
	 */
	public static FileFilter fileFilter() {
		return new FileFilter() {
			public boolean accept(File directory) {
				String name = directory.getName();
				return (directory.isFile())
						&& !(name.startsWith(".") || name.endsWith(".bak")
								|| name.endsWith(".jsp")
								|| name.endsWith(".old") || name
								.endsWith(".delete"));
			}
		};

	}

	/**
	 * 获取缺省的文件夹过滤器
	 * 
	 * @return FileFilter
	 */
	public static FileFilter folderFilter() {
		return new FileFilter() {
			public boolean accept(File directory) {
				String name = directory.getName();
				return (directory.isDirectory()) && !(name.startsWith("."));
			}
		};
	}

	/**
	 * 文件后缀过滤器，支持根据文件后缀（不区分大小写）来查找文件
	 * 
	 * @param extension
	 *            String
	 * @return FilenameFilter
	 */
	public static final FileFilter extensionFilter(final String extension) {
		return new FileFilter() {
			public boolean accept(File dir) {
				return dir.getName().toLowerCase().endsWith(
						extension.toLowerCase());
			}
		};
	}

	/**
	 * 获取文件名称
	 * 
	 * @return String
	 */
	public String getName() {
		return file.getName();
	}

	/**
	 * 获取文件相对路径
	 * 
	 * @return String
	 */
	public String getPath() {
		String path = file.getAbsolutePath().substring(
				root.getAbsolutePath().length());
		return path.replace('\\', '/');
	}

	/**
	 * 获取编码后的文件路径
	 * 
	 * @param code
	 *            String
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public String getEncodedPath(String code)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(getPath(), code);
	}

	/**
	 * 获取对应的物理文件
	 * 
	 * @return File
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 获取相对于根文件夹的深度
	 * 
	 * @return int
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * 是否是图片类型
	 * 
	 * @return boolean
	 */
	public boolean isPic() {
		return getSuffix().toLowerCase().equals("jpg")
				|| getSuffix().toLowerCase().equals("gif")
				|| getSuffix().toLowerCase().equals("bmp")
				|| getSuffix().toLowerCase().equals("png")
				|| getSuffix().toLowerCase().equals("tif");
	}

	/**
	 * 获取文件后缀
	 * 
	 * @return String
	 */
	public String getSuffix() {
		return FilenameUtils.getExtension(file.getName());
	}

	public String getDisplayPath(String split) {
		split = split == null ? "" : split;
		String subString = "";
		for (int i = 0; i < getDepth(); i++) {
			subString += split;
		}
		return subString + getName();
	}

	public FileWrapper getParent() {
		if (depth == 0) {
			return null;
		}
		return new FileWrapper(file.getParentFile(), depth - 1);
	}

	public FileWrapper[] getChildFolders() {
		File[] folders = file.listFiles(folderFilter());
		if (folders == null) {
			return new FileWrapper[0];
		}
		FileWrapper[] beans = new FileWrapper[folders.length];
		for (int i = 0; i < folders.length; i++) {
			beans[i] = new FileWrapper(folders[i], depth + 1);
		}
		return beans;
	}

	public FileWrapper[] getChildFiles() {
		File[] files = file.listFiles(fileFilter());
		if (files == null) {
			return new FileWrapper[0];
		}
		FileWrapper[] beans = new FileWrapper[files.length];
		for (int i = 0; i < files.length; i++) {
			beans[i] = new FileWrapper(files[i], depth + 1);
		}
		return beans;
	}

	/**
	 * 搜索所有符合条件的文件.
	 * 
	 * @param filter
	 *            FileFilter
	 * @return FileWrapper[]
	 */
	public FileWrapper[] searchFiles(FileFilter filter) {
		ArrayList<FileWrapper> children = new ArrayList<FileWrapper>();
		addChildFiles(this, children, filter);
		return (FileWrapper[]) children
				.toArray(new FileWrapper[children.size()]);
	}

	/**
	 * 
	 * @param folder
	 *            FileWrapper
	 * @param result
	 *            List
	 * @param filter
	 *            FileFilter
	 */
	protected void addChildFiles(FileWrapper folder, List<FileWrapper> result,
			FileFilter filter) {
		File[] files = folder.file.listFiles(filter);
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				result.add(new FileWrapper(files[i], folder.getDepth() + 1));
			}
		}
		File[] children = folder.file.listFiles(folderFilter());
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				addChildFiles(new FileWrapper(children[i],
						folder.getDepth() + 1), result, filter);
			}
		}
	}

	/**
	 * 建立子文件（夹）树
	 * 
	 * @param includeFiles
	 *            boolean
	 * @return Tree
	 */
	public Tree childTree(boolean includeFiles) {
		final Tree tree = new Tree();
		if (file.equals("")) {
			return tree;
		}
		if (getParent() != null) {
		}
		tree.addNode(getID(), getParent().getID(), this);
		preorder(tree, this, includeFiles);
		return tree;
	}

	private void preorder(Tree tree, FileWrapper root, boolean includeFiles) {
		File[] children = root.getFile().listFiles(folderFilter());

		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				FileWrapper child = new FileWrapper(children[i], root
						.getDepth() + 1);
				tree.addNode(child.getID(), root.getID(), child);
				preorder(tree, child, includeFiles);
			}
		}
		if (includeFiles) {
			File[] files = root.getFile().listFiles(fileFilter());

			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					FileWrapper child = new FileWrapper(files[i], root
							.getDepth() + 1);
					tree.addNode(child.getID(), root.getID(), child);
				}
			}
		}
	}

	private String getID() {
		return "" + Math.abs(file.hashCode());
	}
}
