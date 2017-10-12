package com.fulong.portal.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.net.URLEncoder;

/**
 * 对占位符请求参数做加密解密
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
public class ParametersCipher {
	private DesEncrypter encrypter = new DesEncrypter("portal");
	private String encoding = "UTF-8";
	private static ParametersCipher instance = new ParametersCipher();

	public static ParametersCipher getInstance() {
		return instance;
	}

	public ParametersCipher() {

	}

	/**
	 * 从压缩的代码中解出参数集
	 * 
	 * @param encrypt
	 *            String
	 * @param encoding
	 *            String
	 * @return Map
	 */
	public Map<String, String[]> decode(String encrypt, String encoding) {
		if (encoding == null)
			encoding = this.encoding;
		if (encrypt == null)
			encrypt = "";
		String desc = null;
		/*
		 * try { desc = URLDecoder.decode(encrypt, encoding); } catch
		 * (UnsupportedEncodingException ex) { desc = encrypt; }
		 */
		desc = encrypter.decrypt(encrypt);
		if (desc == null)
			desc = "";
		Map<String, String[]> parameters = new HashMap<String, String[]>();
		try {
			parseParameters(parameters, desc, encoding);
		} catch (Exception e) {
		}
		return parameters;
	}

	/**
	 * 将参数集编码
	 * 
	 * @param parameters
	 *            Map
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String encode(Map parameters) {
		String result = toQueryString(parameters);
		result = encrypter.encrypt(result);
		try {
			return URLEncoder.encode(result, encoding);
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}

	public static void parseParameters(Map<String, String[]> map, String data,
			String encoding) throws UnsupportedEncodingException {

		if ((data != null) && (data.length() > 0)) {

			// use the specified encoding to extract bytes out of the
			// given string so that the encoding is not lost. If an
			// encoding is not specified, let it use platform default
			byte[] bytes = null;
			try {
				if (encoding == null) {
					bytes = data.getBytes();
				} else {
					bytes = data.getBytes(encoding);
				}
			} catch (UnsupportedEncodingException uee) {
			}

			parseParameters(map, bytes, encoding);
		}
	}

	@SuppressWarnings("unchecked")
	public static String toQueryString(Map parameters) {
		StringBuffer buffer = new StringBuffer("");
		for (Iterator keys = parameters.keySet().iterator(); keys.hasNext();) {
			String name = (String) keys.next();
			Object value = parameters.get(name);
			if (value != null) {
				String[] values = toStringArray(value);
				for (int i = 0; i < values.length; i++) {
					if (buffer.length() > 0)
						buffer.append("&");
					try {
						buffer.append(name + "="
								+ URLEncoder.encode(values[i], "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						buffer.append(name + "=" + values[i]);
					}
				}
			}
		}

		return buffer.toString();
	}

	private static String[] toStringArray(Object value) {
		if (value instanceof String[]) {
			return (String[]) value;
		} else if (value instanceof String) {
			String values[] = new String[1];
			values[0] = (String) value;
			return (values);
		} else {
			String values[] = new String[1];
			values[0] = value.toString();
			return (values);
		}

	}

	/**
	 * Put name and value pair in map. When name already exist, add value to
	 * array of values.
	 * 
	 * @param map
	 *            The map to populate
	 * @param name
	 *            The parameter name
	 * @param value
	 *            The parameter value
	 */
	private static void putMapEntry(Map<String, String[]> map, String name,
			String value) {
		String[] newValues = null;
		String[] oldValues = (String[]) map.get(name);
		if (oldValues == null) {
			newValues = new String[1];
			newValues[0] = value;
		} else {
			newValues = new String[oldValues.length + 1];
			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			newValues[oldValues.length] = value;
		}
		map.put(name, newValues);
	}

	/**
	 * Append request parameters from the specified String to the specified Map.
	 * It is presumed that the specified Map is not accessed from any other
	 * thread, so no synchronization is performed.
	 * <p>
	 * <strong>IMPLEMENTATION NOTE</strong>: URL decoding is performed
	 * individually on the parsed name and value elements, rather than on the
	 * entire query string ahead of time, to properly deal with the case where
	 * the name or value includes an encoded "=" or "&" character that would
	 * otherwise be interpreted as a delimiter.
	 * 
	 * NOTE: byte array data is modified by this method. Caller beware.
	 * 
	 * @param map
	 *            Map that accumulates the resulting parameters
	 * @param data
	 *            Input string containing request parameters
	 * @param encoding
	 *            Encoding to use for converting hex
	 * 
	 * @exception UnsupportedEncodingException
	 *                if the data is malformed
	 */
	public static void parseParameters(Map<String, String[]> map, byte[] data,
			String encoding) throws UnsupportedEncodingException {

		if (data != null && data.length > 0) {
			int ix = 0;
			int ox = 0;
			String key = null;
			String value = null;
			while (ix < data.length) {
				byte c = data[ix++];
				switch ((char) c) {
				case '&':
					value = new String(data, 0, ox, encoding);
					if (key != null) {
						putMapEntry(map, key, value);
						key = null;
					}
					ox = 0;
					break;
				case '=':
					if (key == null) {
						key = new String(data, 0, ox, encoding);
						ox = 0;
					} else {
						data[ox++] = c;
					}
					break;
				case '+':
					data[ox++] = (byte) ' ';
					break;
				case '%':
					data[ox++] = (byte) ((convertHexDigit(data[ix++]) << 4) + convertHexDigit(data[ix++]));
					break;
				default:
					data[ox++] = c;
				}
			}
			// The last value does not end in '&'. So save it now.
			if (key != null) {
				value = new String(data, 0, ox, encoding);
				putMapEntry(map, key, value);
			}
		}

	}

	/**
	 * Convert a byte character value to hexidecimal digit value.
	 * 
	 * @param b
	 *            the character value byte
	 */
	private static byte convertHexDigit(byte b) {
		if ((b >= '0') && (b <= '9'))
			return (byte) (b - '0');
		if ((b >= 'a') && (b <= 'f'))
			return (byte) (b - 'a' + 10);
		if ((b >= 'A') && (b <= 'F'))
			return (byte) (b - 'A' + 10);
		return 0;
	}

	/**
	 * Decode and return the specified URL-encoded String. When the byte array
	 * is converted to a string, the system default character encoding is
	 * used... This may be different than some other servers.
	 * 
	 * @param str
	 *            The url-encoded string
	 * 
	 * @exception IllegalArgumentException
	 *                if a '%' character is not followed by a valid 2-digit
	 *                hexadecimal number
	 */
	public static String URLDecode(String str) {
		return URLDecode(str, null);

	}

	/**
	 * Decode and return the specified URL-encoded String.
	 * 
	 * @param str
	 *            The url-encoded string
	 * @param enc
	 *            The encoding to use; if null, the default encoding is used
	 * @exception IllegalArgumentException
	 *                if a '%' character is not followed by a valid 2-digit
	 *                hexadecimal number
	 */
	public static String URLDecode(String str, String enc) {

		if (str == null)
			return (null);

		// use the specified encoding to extract bytes out of the
		// given string so that the encoding is not lost. If an
		// encoding is not specified, let it use platform default
		byte[] bytes = null;
		try {
			if (enc == null) {
				bytes = str.getBytes();
			} else {
				bytes = str.getBytes(enc);
			}
		} catch (UnsupportedEncodingException uee) {
		}

		return URLDecode(bytes, enc);

	}

	/**
	 * Decode and return the specified URL-encoded byte array.
	 * 
	 * @param bytes
	 *            The url-encoded byte array
	 * @exception IllegalArgumentException
	 *                if a '%' character is not followed by a valid 2-digit
	 *                hexadecimal number
	 */
	public static String URLDecode(byte[] bytes) {
		return URLDecode(bytes, null);
	}

	/**
	 * Decode and return the specified URL-encoded byte array.
	 * 
	 * @param bytes
	 *            The url-encoded byte array
	 * @param enc
	 *            The encoding to use; if null, the default encoding is used
	 * @exception IllegalArgumentException
	 *                if a '%' character is not followed by a valid 2-digit
	 *                hexadecimal number
	 */
	public static String URLDecode(byte[] bytes, String enc) {

		if (bytes == null)
			return (null);

		int len = bytes.length;
		int ix = 0;
		int ox = 0;
		while (ix < len) {
			byte b = bytes[ix++]; // Get byte to test
			if (b == '+') {
				b = (byte) ' ';
			} else if (b == '%') {
				b = (byte) ((convertHexDigit(bytes[ix++]) << 4) + convertHexDigit(bytes[ix++]));
			}
			bytes[ox++] = b;
		}
		if (enc != null) {
			try {
				return new String(bytes, 0, ox, enc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new String(bytes, 0, ox);

	}
}
