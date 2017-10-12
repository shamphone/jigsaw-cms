package com.fulong.portal.servlet;

import java.io.InputStream;
import java.util.HashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
public class PrepareActionRequest {
	private ActionRequest request;
	private String fileName;
	private String fileExt;

	public PrepareActionRequest(ActionRequest request) {
		this.request = request;
	}

	public void prepare() throws Exception {

		boolean flag1 = false;
		String s4 = null;
		String s5 = null;
		String s6 = null;
		String s7 = null;
		InputStream ois = request.getPortletInputStream();
		m_totalBytes = request.getContentLength();
		m_binArray = new byte[m_totalBytes];
		int totalBytesRead = 0;
		while (totalBytesRead < m_totalBytes) {
			totalBytesRead += ois.read(m_binArray, totalBytesRead, m_totalBytes
					- totalBytesRead);

		}
		for (; !flag1 && m_currentIndex < m_totalBytes; m_currentIndex++) {
			if (m_binArray[m_currentIndex] == 13) {
				flag1 = true;
			} else {
				m_boundary = m_boundary + (char) m_binArray[m_currentIndex];
			}
		}
		if (m_currentIndex == 1) {
			return;
		}
		for (m_currentIndex++; m_currentIndex < m_totalBytes; m_currentIndex = m_currentIndex + 2) {
			String s1 = getDataHeader();
			m_currentIndex = m_currentIndex + 2;
			boolean flag3 = s1.indexOf("filename") > 0;
			String s3 = getDataFieldValue(s1, "name");
			getDataSection();
			if (flag3) {

				s6 = getDataFieldValue(s1, "filename");
				s4 = getFileName(s6);
				if (s4 != null && !s4.equals("")) {
					prepareData();
					haveFile = true;
					fileName = s4;
				}
				s5 = getFileExt(s4);
				fileExt = s5;
				s7 = getContentType(s1);
			}
			if (flag3) {
				if (s7.indexOf("application/x-macbinary") > 0) {
					m_startData = m_startData + 128;
				}
			} else {
				String s11 = new String(m_binArray, m_startData,
						(m_endData - m_startData) + 1);
				try {
					s11 = new String(s11.getBytes("GB18030"), "UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				paremters.put(s3, s11);
			}
			if ((char) m_binArray[m_currentIndex + 1] == '-') {
				break;
			}
		}

	}

	private String getDataHeader() {
		int start = m_currentIndex;
		int end = 0;
		boolean match = false;
		while (!match) {
			if (m_binArray[m_currentIndex] == 13
					&& m_binArray[m_currentIndex + 2] == 13) {
				match = true;
				end = m_currentIndex - 1;
				m_currentIndex = m_currentIndex + 2;
			} else {
				m_currentIndex++;
			}
		}
		return new String(m_binArray, start, (end - start) + 1);

	}

	private String getDataFieldValue(String s, String s1) {
		String s2 = null;
		String s3 = null;
		int i = 0;
		s2 = s1 + "=" + '"';
		i = s.indexOf(s2);
		if (i > 0) {
			int j = i + s2.length();
			int k = j;
			s2 = "\"";
			int l = s.indexOf(s2, j);
			if (k > 0 && l > 0) {
				s3 = s.substring(k, l);
			}
		}
		return s3;
	}

	private void getDataSection() {
		int boundaryLen = m_boundary.length();
		m_startData = m_currentIndex;
		m_endData = m_currentIndex;
		int searchPosition = m_currentIndex;
		int keyPosition = 0;
		do {
			if (searchPosition >= m_totalBytes) {
				break;
			}
			if (m_binArray[searchPosition] == (byte) m_boundary
					.charAt(keyPosition)) {
				if (keyPosition == (boundaryLen - 1)) {
					m_endData = searchPosition - boundaryLen - 2;
					break;
				}
				searchPosition++;
				keyPosition++;
			} else {
				searchPosition++;
				keyPosition = 0;
			}
		} while (true);
		m_currentIndex = m_endData + boundaryLen + 3;
	}

	private String getFileExt(String s) {
		String s1 = new String();
		int i = 0;
		int j = 0;
		if (s == null) {
			return null;
		}
		i = s.lastIndexOf('.') + 1;
		j = s.length();
		s1 = s.substring(i, j);
		if (s.lastIndexOf('.') > 0) {
			return s1;
		} else {
			return "";
		}
	}

	private String getContentType(String s) {
		String s1 = new String();
		String s2 = new String();
		int i = 0;
		s1 = "Content-Type:";
		i = s.indexOf(s1) + s1.length();
		if (i != -1) {
			int j = s.length();
			s2 = s.substring(i, j);
		}
		return s2;
	}

	private String getFileName(String s) {
		int i = 0;
		i = s.lastIndexOf('/');
		if (i != -1) {
			return s.substring(i + 1, s.length());
		}
		i = s.lastIndexOf('\\');
		try {
			s = new String(s.getBytes("GB18030"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i != -1) {
			return s.substring(i + 1, s.length());
		} else {
			return s;
		}
	}

	public byte[] getM_binArray() {
		return file_bin;
	}

	public boolean isHaveFile() {
		return haveFile;
	}

	public String getParameter(String key) {
		return (String) paremters.get(key);
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileExt() {
		return fileExt;
	}

	private void prepareData() {
		file_bin = new byte[m_endData - m_startData + 1];
		for (int i = 0; i < file_bin.length; i++) {
			file_bin[i] = m_binArray[m_startData + i];
		}
	}

	protected byte m_binArray[];
	protected byte file_bin[];
	protected ActionRequest m_request;
	protected ActionResponse m_response;
	private int m_totalBytes;
	private int m_currentIndex;
	private int m_startData;
	private int m_endData;
	private String m_boundary = "";
	public static final int SAVE_AUTO = 0;
	public static final int SAVE_VIRTUAL = 1;
	public static final int SAVE_PHYSICAL = 2;
	private boolean haveFile = false;
	private HashMap<String, String> paremters = new HashMap<String, String>();

}
