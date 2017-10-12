package com.fulong.common.watermark;

import java.io.*;
import java.util.*;

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
 * @author xiaming
 * @version 1.0
 */
public class TextWatermark {
	public TextWatermark() {
	}

	/**
	 * 在给定的html文档之</div>标签前、</p>标签前、</table>标签后，随机插入两种干扰码(每个干扰内容生成两种干扰码)之一
	 * 
	 * @param sourceStr
	 *            String --给定html内容
	 * @param insertContent
	 *            String --干扰码内容
	 * @param charsetName
	 *            String --(输入)str的编码方式名称
	 * @return String --按原编码返回处理后的string
	 * @throws UnsupportedEncodingException
	 */
	public String pressText(String sourceStr, String insertContent, String charsetName) throws UnsupportedEncodingException {
		Vector<String> interfereStr = new Vector<String>();
		interfereStr.add((String) ("<span style=\"display:none\">" + insertContent + "</span>"));
		interfereStr.add((String) ("<span style=\"font-size:0px;color:bgcolor\">" + insertContent + "</span>"));

		// Convert the coding of sourceStr to unicode
		String tmpStr = this.changeCharset(sourceStr, "gb2312");
		StringBuffer bufferHtml = new StringBuffer(tmpStr);

		// For avoiding deferent cases of tagnames, convert all the english
		// chars to lower case. Now, we should consider only the lower case of
		// tagname
		StringBuffer lowerCaseHtml = new StringBuffer(tmpStr
				.toLowerCase(java.util.Locale.ENGLISH));

		int pos = Math.max(lowerCaseHtml.indexOf("<body"), 0); // Store the
		// first
		// occurrence
		// position of
		// the specified
		// charSequence
		Vector<Integer> insertPosVec = new Vector<Integer>(); // Store all insert positions
		// ascendingly

		// Only insert interfere immediately befor </div>、</p> and immediately
		// after </table>; Afer while, the insert position is ascending.
		while ((lowerCaseHtml.indexOf("</div>", pos) >= 0)
				|| (lowerCaseHtml.indexOf("</p>", pos) >= 0)
				|| (lowerCaseHtml.indexOf("</table>", pos) >= 0)) {
			int indexDiv = lowerCaseHtml.indexOf("</div>", pos);
			int indexP = lowerCaseHtml.indexOf("</p>", pos);
			int indexTable = lowerCaseHtml.indexOf("</table>", pos);
			if (indexDiv < 0) {
				indexDiv = Integer.MAX_VALUE;
			}
			if (indexP < 0) {
				indexP = Integer.MAX_VALUE;
			}
			if (indexTable < 0) {
				indexTable = Integer.MAX_VALUE;
			}
			pos = Math.min(indexDiv, Math.min(indexP, indexTable)); // Get the
			// first
			// occurrence
			// position
			// of
			// </div>、</p>
			// or
			// </table>

			// Process only the first occurrence of </div>、</p> or </table> at a
			// time
			if (indexDiv == pos) {
				insertPosVec.add(new Integer(pos)); // Immediately befor </div>
				pos += 6;
			} else if (indexP == pos) {
				insertPosVec.add(new Integer(pos)); // Immediately befor </p>
				pos += 4;
			} else if (indexTable == pos) {
				pos += 8;
				insertPosVec.add(new Integer(pos)); // immediately after
				// </table>
			}

		} // End of while ((lowerCaseHtml.indexOf~

		// Insert interfere at the specified position descendingly
		while (!insertPosVec.isEmpty()) {
			Integer tempInteger = (Integer) (insertPosVec.remove(insertPosVec
					.size() - 1));
			bufferHtml.insert(tempInteger.intValue(), (String) interfereStr
					.elementAt((int) (Math.random() * interfereStr.size())));
		} // End of while~

		return this.changeCharset(bufferHtml.toString(), charsetName);
		// Output the HTML which has been processed
		// return bufferHtml.toString();
	}

	/**
	 * 按给定的编码方式输出给定的字符串
	 * 
	 * @param sourceStr
	 *            String --给定的字符串
	 * @param newCharset
	 *            String --给定的编码
	 * @return String --返回按新的编码方式处理后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public String changeCharset(String sourceStr, String newCharset)
			throws UnsupportedEncodingException {
		if (sourceStr != null) {
			// 用默认字符编码解码字符串。
			byte[] bs = sourceStr.getBytes();
			// 用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}

	/*
	 * public static void main(String args[]) { try { //Define the interfere
	 * codes String insertContent = "干扰码1"; TextWatermark mark = new
	 * TextWatermark();
	 * System.out.println(mark.pressText("</TaBle>l</p>   </dIv>",
	 * insertContent, "gb2312")); } catch (Exception e) { } } //
	 */
}
