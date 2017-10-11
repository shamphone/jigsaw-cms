package com.fulong.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hdf.extractor.WordDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.tidy.Tidy;

/**
 * <提供常见格式的文件转换>
 * 
 * < Title: Coolink协同工作支撑平台Lucene全文检索引擎 >
 * 
 * < Description: Coolink协同工作支撑平台Lucene全文检索引擎 >
 * 
 * < Copyright: 北京中科辅龙计算机技术股份有限公司 2010 >
 * 
 * < Company: 北京中科辅龙计算机技术股份有限公司 >
 * 
 * @author LJY
 * @version 1.0.1
 */
public class FilesToTextUtils {

	private static final String GB2312 = "gb2312";
	private static FilesToTextUtils instance = new FilesToTextUtils();
	private static Log log = LogFactory.getLog(FilesToTextUtils.class);
	private FilesToTextUtils() {

	}

	public static FilesToTextUtils getInstance() {
		return instance;
	}

	public String WordToText(InputStream is) throws IOException {
		WordDocument wd = null;
		StringWriter docTextWriter = null;
		try {
			wd = new WordDocument(is);
			docTextWriter = new StringWriter();
			wd.writeAllText(new PrintWriter(docTextWriter));
			docTextWriter.close();
		} catch (IOException e) {
			throw new FileFormatException(e);
		} finally {
			try {
				is.close();
				if (wd != null)
					wd.closeDoc();
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
		String text = docTextWriter.toString();
		return text;
	}

	/**
	 * 与WordToText功能相同，推荐使用本方法
	 * 
	 * @param is
	 * @return
	 * @throws NotWordException
	 */
	public String Word2Text(InputStream is) throws IOException {
		if (is == null)
			return null;
		String text = null;
		try {
			WordExtractor extr = new WordExtractor(is);
			text = extr.getText();
		} catch (IOException e) {
			throw new FileFormatException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
		return text;
	}

	public String PDF2Text(InputStream is) throws IOException {
		if (is == null)
			return null;
		StringBuffer text = new StringBuffer();
		COSDocument cosDoc = null;
		PDFParser parser;
		try {
			parser = new PDFParser(is);
			parser.parse();
			cosDoc = parser.getDocument();
			if(cosDoc.getTrailer()!=null){
			PDFTextStripper stripper = new PDFTextStripper();
				text.append(stripper.getText(new PDDocument(cosDoc)));
			}else{
				text.append("");
				log.error("Error in building index for PDF2Text entry here ");
			}

		} catch (IOException e) {
			throw new FileFormatException(e);
		} finally {
			try {
				is.close();
				if (cosDoc != null)
					cosDoc.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
		return text.toString();
	}

	public String RTF2Text(InputStream is) throws IOException {
		DefaultStyledDocument styledDoc = new DefaultStyledDocument();
		String text = null;
		try {
			RTFEditorKit rtf = new RTFEditorKit();
			rtf.read(is, styledDoc, 0);
			text = styledDoc.getText(0, styledDoc.getLength());
		} catch (IOException e) {
			throw new FileFormatException(e);
		} catch (BadLocationException e) {
			throw new FileFormatException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
		return text;
	}

	public String HTML2Text(InputStream is) throws IOException {
		// use JTidy htmlparser
		StringBuffer text = new StringBuffer();
		Tidy tidy=new Tidy();
		tidy.setInputEncoding("UTF-8");
		tidy.setQuiet(true);
		tidy.setShowWarnings(false);
		Document root= (org.w3c.dom.Document)tidy.parseDOM(is,null);
		Element rawDoc=root.getDocumentElement();
		text.append(getTitle(rawDoc));
		text.append(getBody(rawDoc));
		return text.toString();
	}

	protected String getTitle(Element rawDoc){
		if (rawDoc == null) {
			return null;
		}
		String title = "";
		NodeList children = rawDoc.getElementsByTagName("title");		    
		if (children.getLength() > 0) {
			Element titleElement=(Element)children.item(0);
		    Text text=(Text)titleElement.getFirstChild();
		    if(text!=null){
		       title=text.getData();
		    }
		}
		return title;
	}
	protected String getBody(Element rawDoc) {
		if (rawDoc == null) {
			return null;
		}
		String body = "";
		NodeList children = rawDoc.getElementsByTagName("body");
		if(children.getLength()>0){
		   body=getText(children.item(0));
		}
		return body;
	}
	protected String getText(Node node){
		NodeList children = node.getChildNodes();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<children.getLength();i++){
		    Node child = children.item(i);
		    switch (child.getNodeType()) {
		    	case Node.ELEMENT_NODE:
		    		sb.append(getText(child));
		    		sb.append(" ");
		    		break;
		    	case Node.TEXT_NODE:
		    		sb.append(((Text) child).getData());
		    		break;
		    	default:
		    		break;
		    }
		 }
		 return sb.toString();
	}

	public String XML2Text(InputStream is) throws IOException {
		// use SAX or digester
		return null;
	}

	public String Txt2Text(InputStream is) throws IOException {
		StringBuffer buffer = new StringBuffer();
		String line;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, GB2312));
			line = reader.readLine();
			while (line != null) {
				buffer.append(line);
				buffer.append("\n");
				line = reader.readLine();
			}
		} catch (IOException e) {
			throw new FileFormatException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
		return buffer.toString();
	}

	public String Excel2Text(InputStream is) throws IOException {
		StringBuffer text = new StringBuffer();
		try {
			HSSFWorkbook work = new HSSFWorkbook(is);
			int numOfSheets = work.getNumberOfSheets();
			for (int i = 0; i < numOfSheets; i++) {
				HSSFSheet sheet = work.getSheetAt(i);
				int numOfRow = sheet.getPhysicalNumberOfRows();
				for (int r = 0; r < numOfRow; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row == null)
						continue;
					int numOfCells = row.getPhysicalNumberOfCells();
					for (short c = 0; c < numOfCells; c++) {
						HSSFCell cell = row
								.getCell(c, Row.RETURN_BLANK_AS_NULL);
						if (cell == null)
							continue;
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							text.append("" + cell.getNumericCellValue());
							break;
						case HSSFCell.CELL_TYPE_STRING:
							text.append(cell.getStringCellValue());
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						default:
						}
					}
				}
			}
		} catch (IOException e) {
			throw new FileFormatException(e);
		}
		return text.toString();
	}
}
