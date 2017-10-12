package com.fulong.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * <p>
 * Title: WebMaster Core Library
 * </p>
 * 
 * <p>
 * Description: Longcon WebMaster SV3
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Beijing Zhongke Fulong Computer Tech. LTD
 * </p>
 * 
 * @author Lixf,jiangqi
 * @version 1.0
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	private static Log log = LogFactory.getLog(FileUtils.class);
	private static int BUF_SIZE = 1024 * 1024;
	private static long fileNameCounter = System.currentTimeMillis();

	/**
	 * Copy the contents of the specified input stream to the specified output
	 * stream, and ensure that both streams are closed before returning (even in
	 * the face of an exception).
	 * 
	 * @param reader
	 *            The reader to read from
	 * @param writer
	 *            The writer to write to
	 * @return Exception which occurred during processing
	 */
	public static long copy(Reader reader, Writer writer) throws IOException {
		long read = 0;
		try {
			// Copy the input stream to the output stream
			char buffer[] = new char[BUF_SIZE];
			int len = buffer.length;
			read = 0;
			while (true) {
				len = reader.read(buffer);
				if (len == -1) {
					break;
				}
				writer.write(buffer, 0, len);
				read += len;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reader.close();
			writer.close();
		} finally {
			reader.close();
			writer.close();
		}
		return read;

	}

	/**
	 * Copy the contents of the specified input stream to the specified output
	 * stream, and ensure that both streams are closed before returning (even in
	 * the face of an exception).
	 * 
	 * @param istream
	 *            The input stream to read from
	 * @param ostream
	 *            The output stream to write to
	 * @param start
	 *            Start of the range which will be copied
	 * @param end
	 *            End of the range which will be copied
	 * @return Exception which occurred during processing
	 */
	public static void copy(InputStream istream, OutputStream ostream,
			long start, long end) throws IOException {
		istream.skip(start);
		long bytesToRead = end - start + 1;
		byte buffer[] = new byte[BUF_SIZE];
		int len = buffer.length;
		try {
			while ((bytesToRead > 0) && (len >= buffer.length)) {

				len = istream.read(buffer);
				if (bytesToRead >= len) {
					ostream.write(buffer, 0, len);
					bytesToRead -= len;
				} else {
					ostream.write(buffer, 0, (int) bytesToRead);
					bytesToRead = 0;
				}

				if (len < buffer.length) {
					break;
				}
			}
		} finally {
			istream.close();
			ostream.close();
		}
	}

	/**
	 * Copy the contents of the specified input stream to the specified output
	 * stream, and ensure that both streams are closed before returning (even in
	 * the face of an exception).
	 * 
	 * @param reader
	 *            The reader to read from
	 * @param writer
	 *            The writer to write to
	 * @param start
	 *            Start of the range which will be copied
	 * @param end
	 *            End of the range which will be copied
	 * @return Exception which occurred during processing
	 */
	public static void copy(Reader reader, Writer writer, long start, long end)
			throws IOException {

		reader.skip(start);
		long bytesToRead = end - start + 1;

		char buffer[] = new char[BUF_SIZE];
		int len = buffer.length;
		try {
			while ((bytesToRead > 0) && (len >= buffer.length)) {
				len = reader.read(buffer);
				if (bytesToRead >= len) {
					writer.write(buffer, 0, len);
					bytesToRead -= len;
				} else {
					writer.write(buffer, 0, (int) bytesToRead);
					bytesToRead = 0;
				}
				if (len < buffer.length) {
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reader.close();
			writer.close();
		} finally {
			reader.close();
			writer.close();
		}
	}

	/**
	 * Copy the contents of the specified input stream to the specified output
	 * stream, and ensure that both streams are closed before returning (even in
	 * the face of an exception).
	 * 
	 * @param resourceInfo
	 *            The resource info
	 * @param writer
	 *            The writer to write to
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 */
	public static void copy(File source, Writer writer) throws IOException {

		InputStream resourceInputStream = new FileInputStream(source);
		Reader reader = new InputStreamReader(resourceInputStream);

		try {
			copy(reader, writer);
		} catch (Exception ex) {
			ex.printStackTrace();
			reader.close();
			writer.close();
		}

		finally {
			reader.close();
			writer.close();
		}

	}

	/**
	 * Copy the contents of the specified input stream to the specified output
	 * stream, and ensure that both streams are closed before returning (even in
	 * the face of an exception).
	 * 
	 * @param resourceInfo
	 *            The ResourceInfo object
	 * @param ostream
	 *            The output stream to write to
	 * @param range
	 *            Range the client wanted to retrieve
	 * @exception IOException
	 *                if an input/output error occurs
	 */
	public static void copy(File file, OutputStream ostream, long from, long end)
			throws IOException {
		InputStream resourceInputStream = new FileInputStream(file);
		InputStream istream = new BufferedInputStream(resourceInputStream,
				BUF_SIZE);

		// Clean up the input stream
		try {
			copy(istream, ostream, from, end);
		} catch (Exception ex) {
			ex.printStackTrace();
			istream.close();
			ostream.close();
		} finally {
			istream.close();
			ostream.close();
		}

	}

	/**
	 * Copy the contents of the specified input stream to the specified output
	 * stream, and ensure that both streams are closed before returning (even in
	 * the face of an exception).
	 * 
	 * @param resourceInfo
	 *            The ResourceInfo object
	 * @param writer
	 *            The writer to write to
	 * @param range
	 *            Range the client wanted to retrieve
	 * @exception IOException
	 *                if an input/output error occurs
	 */
	public static void copy(File file, PrintWriter writer, long start, long end)
			throws IOException {
		InputStream resourceInputStream = new FileInputStream(file);
		Reader reader = new InputStreamReader(resourceInputStream);
		try {
			copy(reader, writer, start, end);
		} catch (Exception ex) {
			ex.printStackTrace();
			reader.close();
			writer.close();
		}

		finally {
			reader.close();
			writer.close();
		}
	}

	/**
	 * Copy the contents of the specified input stream to the specified output
	 * stream, and ensure that both streams are closed before returning (even in
	 * the face of an exception).
	 * 
	 * @param resourceInfo
	 *            The resource information
	 * @param ostream
	 *            The output stream to write to
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 */
	public static void copy(File sourceFile, OutputStream ostream)
			throws IOException {

		InputStream resourceInputStream = new FileInputStream(sourceFile);
		InputStream istream = new BufferedInputStream(resourceInputStream,
				BUF_SIZE);
		try {
			copy(istream, ostream);
		} catch (Exception ex) {
			ex.printStackTrace();
			istream.close();
			resourceInputStream.close();
			ostream.close();

		} finally {
			istream.close();
			resourceInputStream.close();
			ostream.close();
		}
	}

	/**
	 * 将流中的数据写到文件中的指定位置
	 * 
	 * @param requestFile
	 *            File 目标文件
	 * @param is
	 *            InputStream 输入流
	 * @param from
	 *            long 起始位置
	 * @param length
	 *            long 长度
	 * @throws IOException
	 */
	public static File append(File requestFile, InputStream is, long from,
			long length) throws IOException {

		// Append data specified in ranges to existing content for this
		// resource - create a temp. file on the local filesystem to
		// perform this operation
		File contentFile = File.createTempFile("longcon" + (fileNameCounter++),
				"tmp");
		// Convert all '/' characters to '.' in resourcePath
		// String convertedResourcePath = "tmp"+(fileNameCounter++);
		// File contentFile = new File(tempDir, convertedResourcePath);
		if (contentFile.createNewFile()) {
			// Clean up contentFile when Tomcat is terminated
			contentFile.deleteOnExit();
		}

		RandomAccessFile randAccessContentFile = new RandomAccessFile(
				contentFile, "rw");

		// Copy data in oldRevisionContent to contentFile
		if (requestFile.exists()) {
			BufferedInputStream bufOldRevStream = new BufferedInputStream(
					new FileInputStream(requestFile), BUF_SIZE);

			int numBytesRead;
			byte[] copyBuffer = new byte[BUF_SIZE];
			try {
				while ((numBytesRead = bufOldRevStream.read(copyBuffer)) != -1) {
					randAccessContentFile.write(copyBuffer, 0, numBytesRead);
				}
			} finally {
				bufOldRevStream.close();
			}
		}

		randAccessContentFile.setLength(length);

		// Append data in request input stream to contentFile
		randAccessContentFile.seek(from);
		int numBytesRead;
		byte[] transferBuffer = new byte[BUF_SIZE];
		BufferedInputStream requestBufInStream = new BufferedInputStream(is,
				BUF_SIZE);
		try {
			while ((numBytesRead = requestBufInStream.read(transferBuffer)) != -1) {
				randAccessContentFile.write(transferBuffer, 0, numBytesRead);
			}
		} catch (Exception ex) {
			randAccessContentFile.close();
			requestBufInStream.close();
		} finally {
			randAccessContentFile.close();
			requestBufInStream.close();
		}
		return contentFile;
	}

	/**
	 * 文件复制
	 * 
	 * @param source
	 *            File
	 * @param dest
	 *            File
	 * @throws IOException
	 */
	public static long copy(InputStream in, OutputStream out, byte[] copyBuffer)
			throws IOException {
		long bytesCopied = 0;
		int read = -1;
		try {
			while ((read = in.read(copyBuffer, 0, copyBuffer.length)) != -1) {
				out.write(copyBuffer, 0, read);
				bytesCopied += read;
			}
		} catch (IOException ex) {
			log.debug("ClientAbortException :" + ex.getMessage());
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return bytesCopied;
	}

	public static long copy(InputStream in, OutputStream out)
			throws IOException {
		// we need a buffer of our own, so no one else interferes
		byte[] buf = new byte[BUF_SIZE];
		return copy(in, out, buf);
	}

	public static long copy(File input, File output, byte[] copyBuffer)
			throws IOException {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(input);
			out = new FileOutputStream(output);
			return copy(in, out, copyBuffer);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return 0;
	}

	public static long copy(InputStream in, File outputFile) throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputFile);
			return copy(in, out);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return 0;
	}

	public static void copyFile(File source, File dest) throws IOException {
		if (!dest.exists()) {
			dest.createNewFile();
		}
		byte[] buf = new byte[BUF_SIZE];
		copy(source, dest, buf);
	}

	public static void fastCopy(File source, File destDir) throws IOException {
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		StringBuffer command = new StringBuffer("cmd /c copy ");
		command.append(source.getPath());
		command.append(" ");
		command.append(destDir.getPath());
		Runtime rt = Runtime.getRuntime();
		rt.exec(command.toString());

	}

	public static void xcopy(File source, File destDir) throws IOException {
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		StringBuffer command = new StringBuffer("cmd /c xcopy /E /R /K /Y ");
		command.append(source.getPath());
		command.append(" ");
		command.append(destDir.getPath());
		Runtime rt = Runtime.getRuntime();
		rt.exec(command.toString());

	}

	public static void copyDirectory(File source, File dest,
			FilenameFilter filter) throws IOException {
		if (dest.getAbsolutePath().indexOf(source.getAbsolutePath()) >= 0) {
			return;
		}
		if (!dest.exists()) {
			dest.mkdir();
		}
		File[] files;
		if (filter != null) {
			files = source.listFiles(filter);
		} else {
			files = source.listFiles();
		}
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					copyDirectory(files[i], new File(dest, files[i].getName()),
							filter);
				} else {
					copyFile(files[i], new File(dest, files[i].getName()));
				}
			}
		}
	}

	public static void copyDirectory(File source, File dest,
			FilenameFilter filter, boolean isRewrite) throws IOException {
		if (dest.getAbsolutePath().indexOf(source.getAbsolutePath()) >= 0) {
			return;
		}
		if (!dest.exists()) {
			dest.mkdir();
		}
		File[] files;
		if (filter != null) {
			files = source.listFiles(filter);
		} else {
			files = source.listFiles();
		}
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					copyDirectory(files[i], new File(dest, files[i].getName()),
							filter, isRewrite);
				} else {
					File tempFile = new File(dest.getPath() + "/"
							+ files[i].getName());
					if (tempFile.exists()) {
						if (isRewrite) {
							copyFile(files[i], new File(dest, files[i]
									.getName()));
						}
					} else {
						copyFile(files[i], new File(dest, files[i].getName()));
					}
				}
			}
		}
	}

	public static void copyDirectory(File source, File dest, String[] exclude)
			throws IOException {
		if (!dest.exists()) {
			dest.mkdir();
		}
		File[] files = source.listFiles();
		for (int i = 0; i < files.length; i++) {
			boolean include = true;
			if (exclude != null) {
				for (int j = 0; j < exclude.length; j++) {
					if (files[i].getName().startsWith(exclude[j])) {
						include = false;
					}
				}
			}
			if (include) {
				if (files[i].isDirectory()) {
					copyDirectory(files[i], new File(dest.getPath() + "/"
							+ files[i].getName()), exclude);
				} else {
					copyFile(files[i], new File(dest.getPath() + "/"
							+ files[i].getName()));

				}
			}
		}

	}

	/**
	 * 将文件（夹）粘贴到指定目录下，成为新目录的子文件（夹）；
	 * 
	 * @param src
	 *            File
	 * @param destDirectory
	 *            File
	 * @return File
	 * @throws IOException
	 */
	public static File paste(File src, File destDirectory) throws IOException {
		if (src.isDirectory()) {
			File newDir = new File(destDirectory, src.getName());
			newDir.mkdirs();
			copyDirectory(src, newDir, excludedFiles());
			return newDir;
		} else {
			File newFile = new File(destDirectory, src.getName());
			copyFile(src, newFile);
			return newFile;
		}
	}

	public static boolean delete(File file) {
		return deleteAll(file);
	}

	private static boolean deleteAll(File file) {
		if (file.isFile()) {
			return file.delete();
		} else {
			File[] children = file.listFiles();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					deleteAll(children[i]);
				}
			}
			return file.delete();
		}
	}

	public static FilenameFilter excludedFiles() {
		return new FilenameFilter() {
			public boolean accept(File file, String name) {
				return !(name.startsWith("."));
			}
		};
	}

	/**
	 * make zip files.
	 * 
	 * @param directory
	 *            File the directory or file to compress;
	 * @param outputFile
	 *            File output file;
	 * @throws IOException
	 */
	public static void compress(File directory, String entry, OutputStream out)
			throws IOException {
		ZipOutputStream zipOut = new ZipOutputStream(out);
		writeZipEntry(directory, entry, zipOut);
		zipOut.close();

	}

	public static void compress(File directory, String entry, File outputFile)
			throws IOException {
		FileOutputStream out = new FileOutputStream(outputFile);
		compress(directory, entry, out);
		out.close();
	}

	public static void write(File file, Document document) throws IOException {
		OutputFormat format = new OutputFormat(document, "UTF-8", true);

		FileOutputStream fos = new FileOutputStream(file);
		// OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
		XMLSerializer xs = new XMLSerializer(fos, format);
		xs.serialize(document);
		fos.close();

	}

	private static void writeZipEntry(File file, String baseEntry,
			ZipOutputStream zipOut) throws FileNotFoundException, IOException {
		byte[] buffer = new byte[512];
		int nNumber;
		FileInputStream in;
		ZipEntry entry;
		String destEntry;
		if (baseEntry == null) {
			destEntry = file.getName();
		} else {
			destEntry = baseEntry + File.separator + file.getName();
		}
		if (file.isDirectory()) {
			File[] children = file.listFiles(excludedFiles());
			for (int j = 0; j < children.length; j++) {
				writeZipEntry(children[j], destEntry, zipOut);
			}
		} else {
			in = new FileInputStream(file);
			entry = new ZipEntry(destEntry);
			zipOut.putNextEntry(entry);
			while ((nNumber = in.read(buffer)) != -1) {
				zipOut.write(buffer, 0, nNumber);
			}
			in.close();
		}
	}

	public static void unzip(File zipFile, File destDir) throws IOException {
		InputStream is = new FileInputStream(zipFile);
		unzip(destDir, is);
		is.close();
	}

	@SuppressWarnings("unchecked")
	public static void unzip(File file, InputStream inputStream)
			throws IOException {
		String temp = "" + new Date().getTime();
		File tempFile = File.createTempFile(temp, ".zip");
		log.debug("Save zipped file to temp file " + tempFile.getPath()
				+ ", begin unzipping..");
		byte[] data = new byte[(int) inputStream.available()];
		inputStream.read(data);
		OutputStream bos = new FileOutputStream(tempFile);
		bos.write(data);
		bos.close();
		inputStream.close();
		FileOutputStream out = null;
		ZipFile zf = null;
		try {
			zf = new ZipFile(tempFile, "UTF-8");
		} catch (IOException e) {
			throw new ZipException("Not Well Zip");
		} finally {
			org.apache.commons.io.FileUtils.deleteQuietly(tempFile);
		}
		try {
			byte[] buffer = new byte[1024];
			Enumeration e = zf.getEntries();
			while (e.hasMoreElements()) {
				int nNumber = 0;
				ZipEntry entry = (ZipEntry) e.nextElement();
				String targetFilePath = file.getPath() + File.separator
						+ entry.getName();
				log.debug(" find file " + entry.getName() + ".");
				File targetFile = new File(targetFilePath);
				if (entry.isDirectory()) {
					targetFile.mkdirs();
					log.debug(" create file " + targetFile + ".");
				} else {
					targetFile.getParentFile().mkdirs();
					targetFile.createNewFile();
					out = new FileOutputStream(targetFile);
					InputStream is = zf.getInputStream(entry);
					while (nNumber != -1) {
						nNumber = is.read(buffer, 0, buffer.length);
						if (nNumber > 0) {
							out.write(buffer, 0, nNumber);
						}
					}
					out.close();
					log.debug(" unzip file " + entry.getName() + " to "
							+ targetFilePath + ".");
				}
			}
			log.debug("unzip successfully!");
		} finally {
			zf.close();
			if (out != null) {
				out.close();
			}
			tempFile.delete();
		}
	}

	/**
	 * @param toJsp
	 *            是否将html后缀转成jsp
	 * @return tenplateName String 文件夹名称 用于导入zip模板 modified by mali 2010-7-29
	 */
	@SuppressWarnings("unchecked")
	public static String unzip(File file, InputStream inputStream, boolean toJsp)
			throws IOException {
		String templateName = "";
		String temp = "" + new Date().getTime();
		File tempFile = File.createTempFile(temp, ".zip");
		log.debug("Save zipped file to temp file " + tempFile.getPath()
				+ ", begin unzipping..");
		byte[] data = new byte[(int) inputStream.available()];
		inputStream.read(data);
		OutputStream bos = new FileOutputStream(tempFile);
		bos.write(data);
		bos.close();
		inputStream.close();
		FileOutputStream out = null;
		ZipFile zf = null;
		try {
			zf = new ZipFile(tempFile, "UTF-8");
		} catch (IOException e) {
			throw new ZipException("Not Well Zip");
		} finally {
			org.apache.commons.io.FileUtils.deleteQuietly(tempFile);
		}

		try {
			byte[] buffer = new byte[1024];
			Enumeration e = zf.getEntries();
			while (e.hasMoreElements()) {
				int nNumber = 0;
				ZipEntry entry = (ZipEntry) e.nextElement();
				String targetFilePath = file.getPath() + File.separator
						+ entry.getName();
				if (toJsp)
					targetFilePath = targetFilePath.replaceAll("html", "jsp")
							.replaceAll("htm", "jsp");
				// 赋文件值
				templateName = entry.getName();
				log.debug(" find file " + entry.getName() + ".");
				File targetFile = new File(targetFilePath);
				if (entry.isDirectory()) {
					targetFile.mkdirs();
					log.debug(" create file " + targetFile + ".");
				} else {
					targetFile.getParentFile().mkdirs();
					targetFile.createNewFile();
					out = new FileOutputStream(targetFile);
					InputStream is = zf.getInputStream(entry);
					while (nNumber != -1) {
						nNumber = is.read(buffer, 0, buffer.length);
						if (nNumber > 0) {
							out.write(buffer, 0, nNumber);
						}
					}
					out.close();
					log.debug(" unzip file " + entry.getName() + " to "
							+ targetFilePath + ".");
				}
			}
			log.debug("unzip successfully!");
		} finally {
			zf.close();
			if (out != null) {
				out.close();
			}
			tempFile.delete();
		}
		return templateName.substring(0, 1);
	}

	/**
	 * fix Me!
	 * 
	 * @param file
	 *            File
	 * @param encode
	 *            String
	 * @return String
	 * @throws IOException
	 */
	public static String readJsp(File file, String encode) throws IOException {

		FileInputStream in = new FileInputStream(file);
		byte[] data = new byte[in.available()];
		in.read(data);
		String header = getByteEncode(data);
		if (header == null) {
			header = encode;
		}

		InputStreamReader reader = new InputStreamReader(new FileInputStream(
				file), header);
		StringBuffer sb = new StringBuffer();
		int i;
		while ((i = reader.read()) != -1) {
			sb.append((char) i);
		}
		reader.close();
		return sb.toString();

	}

	public static void write(File file, String content) throws IOException {
		write(file, content, "UTF-8");
	}

	public static void write(File file, String content, String encoding)
			throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream os = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(os, encoding);
		writer.write(content, 0, content.length());
		writer.flush();
		writer.close();
		os.close();
	}

	public static void writeJsp(File file, String[] contents)
			throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<%@ page contentType=\"text/html; charset=UTF-8\"%>");
		sb.append("\r\n");
		for (int i = 0; i < contents.length; i++) {
			sb.append(contents[i]);
		}
		write(file, sb.toString(), "UTF-8");
	}

	private static String getByteEncode(byte[] data) {
		String tempContent = new String(data);
		String header = "";
		int begin = tempContent.indexOf("charset");
		int end = -1;
		if (begin > -1) {
			if ((end = tempContent.indexOf("'", begin)) > -1) {
				header = tempContent.substring(
						tempContent.indexOf("=", begin) + 1, end).trim();
				try {
					"".getBytes(header);
				} catch (Exception e) {
					header = "";
				}
			}
			if (header.equals("")
					&& (end = tempContent.indexOf("\"", begin)) > -1) {
				header = tempContent.substring(
						tempContent.indexOf("=", begin) + 1, end).trim();
				try {
					"".getBytes(header);
				} catch (Exception e) {
					header = "";
				}
			}
		}
		if (header.equals("")) {
			return null;
		}
		return header;
	}

	public static Document readXML(File file) throws IOException, SAXException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			return document;
		} catch (ParserConfigurationException ex) {
			throw new InitFailedException(ex);
		}
	}

	public static void write(File file, InputStream inputStream)
			throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStream os = new FileOutputStream(file);
		try {
			copy(inputStream, os);
		} finally {
			os.close();
		}
	}

	public static void write(File file, byte[] data) throws IOException {
		OutputStream bos = new FileOutputStream(file);
		bos.write(data);
		bos.close();
	}

	public static File[] listFolders(File file) {
		return file.listFiles(new FileFilter() {
			public boolean accept(File directory) {
				return directory.isDirectory();
			}
		});

	}

	public static File[] listFiles(File file) {
		return file.listFiles(new FileFilter() {
			public boolean accept(File directory) {
				return !directory.isDirectory();
			}
		});

	}

	public static File[] listFiles(File file, final String suffix) {
		return file.listFiles(new FilenameFilter() {
			public boolean accept(File directory, String name) {
				return name.toLowerCase().endsWith(suffix.toLowerCase());
			}
		});
	}

	public static File[] listFiles(File file, final String[] suffix) {
		return file.listFiles(new FilenameFilter() {
			public boolean accept(File directory, String name) {
				String lowName = name.toLowerCase();
				for (int i = 0; i < suffix.length; i++) {
					String lowcase = suffix[i].toLowerCase();
					return (lowName.endsWith("." + lowcase) || lowName
							.toLowerCase().endsWith("." + lowcase));
				}
				return false;
			}
		});
	}

	public static File[] listAllFiles(File file) {
		Vector<File> result = new Vector<File>();
		addChildren(result, file);
		return (File[]) result.toArray(new File[result.size()]);
	}

	public static File[] listAllFiles(File file, String suffix) {
		Vector<File> result = new Vector<File>();
		addChildren(result, file, suffixFilter(suffix));
		return (File[]) result.toArray(new File[result.size()]);
	}

	private static FilenameFilter suffixFilter(final String suffix) {
		return new FilenameFilter() {
			public boolean accept(File file, String name) {
				return name.toLowerCase().endsWith("." + suffix.toLowerCase());
			};
		};
	}

	public static File[] listAllFiles(File file, FilenameFilter filter) {
		Vector<File> result = new Vector<File>();
		addChildren(result, file, filter);
		return (File[]) result.toArray(new File[result.size()]);
	}

	private static void addChildren(Vector<File> result, File folder,
			FilenameFilter filter) {
		File[] children = folder.listFiles(filter);
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				result.add(children[i]);
			}
		}

		File[] folders = listFolders(folder);
		if (folders != null) {
			for (int i = 0; i < folders.length; i++) {
				addChildren(result, folders[i], filter);
			}
		}

	}

	private static void addChildren(Vector<File> iterator, File folder) {
		File[] children = folder.listFiles();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				if (!children[i].isDirectory()) {
					iterator.add(children[i]);
				} else {
					addChildren(iterator, children[i]);
				}
			}
		}
	}

	public static File compress(File directory, File[] files)
			throws IOException {
		if (files == null) {
			files = directory.listFiles(excludedFiles());
		}

		String tempZipFileName = directory.getName();
		if (tempZipFileName.length() < 3) {
			tempZipFileName += Calendar.getInstance().get(Calendar.MILLISECOND);
		}
		File tempFile = File.createTempFile(tempZipFileName, ".zip");

		FileOutputStream out = new FileOutputStream(tempFile);
		ZipOutputStream zipOut = new ZipOutputStream(out);

		for (int i = 0; i < files.length; i++) {
			writeZipEntry(directory, files[i], zipOut);
		}

		zipOut.close();
		out.close();
		return tempFile;

	}

	private static void writeZipEntry(File directory, File file,
			ZipOutputStream zipOut) throws FileNotFoundException, IOException {
		byte[] buffer = new byte[512];
		int nNumber;
		FileInputStream in;
		ZipEntry entry;
		if (file.isDirectory()) {
			File[] children = file.listFiles(excludedFiles());
			for (int j = 0; j < children.length; j++) {
				writeZipEntry(directory, children[j], zipOut);
			}
		} else {
			in = new FileInputStream(file);
			entry = new ZipEntry(file.getAbsolutePath().substring(
					directory.getAbsolutePath().length() + 1));
			zipOut.putNextEntry(entry);
			while ((nNumber = in.read(buffer)) != -1) {
				zipOut.write(buffer, 0, nNumber);
			}
			in.close();
		}
	}

	/**
	 * 获取child相对于parent的地址
	 * 
	 * @param parent
	 *            File
	 * @param child
	 *            File
	 * @return String
	 */
	public static final String condenseFilePaths(File parent, File child) {
		return condenseFilePaths(parent, child, false, File.separator);
	}

	public static final String condenseFilePaths(File parent, File child,
			boolean caseSensitive, String delimiter) {
		String parentPath = FilenameUtils.normalize(parent.getAbsolutePath());
		String childPath = FilenameUtils.normalize(child.getAbsolutePath());
		return condenseFilePaths(parentPath, childPath, caseSensitive,
				delimiter);

	}

	/**
	 * Compares the two paths and returns a relative path that represents the
	 * childFolderPath relative to parentFolderPath or null if either of the
	 * paths passed in are already relative or invalid, or if childFolderPath
	 * cannot be expressed relative to parentFolderPath. Both c: and \folderName
	 * are invalid paths for the purposes of this function. GetCanonicalPathName
	 * is called on boths paths prior to comparison. This function is
	 * essentially Windows-specific, but should work correctly with at least
	 * Unix paths.
	 * 
	 * @param parentFolderPath
	 *            String
	 * @param childFolderPath
	 *            String
	 * @param caseSensitive
	 *            boolean
	 * @param delimiter
	 *            String
	 * @return String
	 */

	public static final String condenseFilePaths(String parentFolderPath,
			String childFolderPath, boolean caseSensitive, String delimiter) {

		String s3 = null;
		String s4 = getCanonicalPathName(parentFolderPath, caseSensitive);
		String s5 = getCanonicalPathName(childFolderPath, caseSensitive);
		StringTokenizer stringtokenizer = new StringTokenizer(s4, delimiter);
		StringTokenizer stringtokenizer1 = new StringTokenizer(s5, delimiter);
		boolean flag1 = s4.startsWith("\\\\");
		boolean flag2 = !flag1 && s4.startsWith("\\");
		if (!flag1 && s4.endsWith(":")) {
			if (parentFolderPath.endsWith(":")) {
				flag2 = true;
			} else {
				s4 += "\\";
			}
		}
		boolean flag3 = s5.startsWith("\\\\");
		boolean flag4 = !flag3 && s5.startsWith("\\");
		if (!flag3 && s5.endsWith(":")) {
			if (childFolderPath.endsWith(":")) {
				flag4 = true;
			} else {
				s5 += "\\";
			}
		}
		boolean flag5 = !(flag1 ^ flag3);
		boolean flag6 = flag2 || flag4;
		String s6 = stringtokenizer.hasMoreTokens() ? stringtokenizer
				.nextToken() : "";
		String s8 = stringtokenizer1.hasMoreTokens() ? stringtokenizer1
				.nextToken() : "";
		if (flag1) {
			s6 += stringtokenizer.hasMoreTokens() ? stringtokenizer.nextToken()
					: "";
			s8 += stringtokenizer1.hasMoreTokens() ? stringtokenizer1
					.nextToken() : "";
		}
		boolean flag7 = pathsAreEqual(s6, s8, caseSensitive);
		if ((new File(s4)).isAbsolute() && (new File(s5)).isAbsolute() && flag5
				&& !flag6 && flag7) {
			Vector<String> vector = new Vector<String>();
			s3 = "";
			while (stringtokenizer.hasMoreTokens()) {
				String s7 = stringtokenizer.nextToken();
				String s9 = stringtokenizer1.hasMoreTokens() ? stringtokenizer1
						.nextToken() : null;
				if (!pathsAreEqual(s7, s9, caseSensitive)) {
					vector.addElement("..");
					for (; stringtokenizer.hasMoreTokens(); stringtokenizer
							.nextElement()) {
						vector.addElement("..");
					}

					if (s9 != null) {
						vector.addElement(s9);
					}
					break;
				}
			}
			for (; stringtokenizer1.hasMoreTokens(); vector
					.addElement(stringtokenizer1.nextToken())) {
				;
			}
			for (Enumeration<String> enumeration = vector.elements(); enumeration
					.hasMoreElements();) {
				s3 += ((String) (enumeration.nextElement()));
				s3 += enumeration.hasMoreElements() ? delimiter : "";
			}

		}
		return s3;
	}

	private static final boolean pathsAreEqual(String s, String s1, boolean flag) {
		boolean flag1 = false;
		if (s == null && s1 == null) {
			flag1 = true;
		} else if (s != null && s1 != null) {
			if (flag) {
				flag1 = s.equals(s1);
			} else {
				flag1 = s.equalsIgnoreCase(s1);
			}
		}
		return flag1;
	}

	public static final String getCanonicalPathName(String s, boolean flag) {
		return getCanonicalPath(s, flag, defaultFileDelim());
	}

	static final String getCanonicalPath(String s, boolean flag, char c) {
		char ac[] = new char[s.length() + 1];
		s.getChars(0, s.length(), ac, 0);
		int l = 0;
		if (c != l) {
			for (int i = 0; ac[i] != l; i++) {
				if (isSlash(ac[i])) {
					ac[i] = c;
				}
			}

		}
		int j = 0;
		int k = 0;
		while (ac[j] != l) {
			if (k > 1 && isSlash(ac[k - 1]) && isSlash(ac[j])) {
				j++;
			} else {
				ac[k++] = ac[j++];
			}
		}
		ac[k] = (char) l;
		j = 0;
		k = 0;
		while (ac[j] != l) {
			if (isDot(ac[j]) && isSlash(ac[j + 1])
					&& (j == 0 || isSlash(ac[j - 1]))) {
				j += 2;
			} else {
				ac[k++] = ac[j++];
			}
		}
		if (k == 1 && isDot(ac[0])) {
			k--;
		} else if (k > 1 && isDot(ac[k - 1]) && isSlash(ac[k - 2])) {
			k--;
		}
		ac[k] = (char) l;
		for (j = 0; ac[j] != l;) {
			if (isDot(ac[j]) && isDot(ac[j + 1]) && isSlash(ac[j + 2])
					&& (j == 0 || isSlash(ac[j - 1]))) {
				int i1 = j + 3;
				j -= 2;
				if (j >= 0) {
					for (; j >= 0 && !isSlash(ac[j]); j--) {
						;
					}
					j++;
				} else {
					j = 0;
				}
				for (int j1 = j; (ac[j1] = ac[i1]) != l;) {
					j1++;
					i1++;
				}

			} else {
				j++;
			}
		}

		if (j == 2 && isDot(ac[0]) && isDot(ac[1])) {
			ac[0] = (char) l;
		} else if (j > 2 && isDot(ac[j - 1]) && isDot(ac[j - 2])
				&& isSlash(ac[j - 3])) {
			j -= 4;
			if (j >= 0) {
				for (; j >= 0 && !isSlash(ac[j]); j--) {
					;
				}
				j++;
			} else {
				j = 0;
			}
			ac[j] = (char) l;
		}
		for (k = 0; ac[k] != l; k++) {
			;
		}
		if (k != 0 && isSlash(ac[k - 1])) {
			k--;
			ac[k] = (char) l;
		}
		String s1 = new String(ac, 0, k);
		if (!flag) {
			s1 = s1.toLowerCase(Locale.getDefault());
		}
		return s1;
	}

	private static boolean isDot(char c) {
		return c == '.';
	}

	private static boolean isSlash(char c) {
		return c == '\\' || c == '/';
	}

	private static char defaultFileDelim() {
		return '\\';
	}

}
