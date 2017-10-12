package com.fulong.common;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.Dispatch;
import com.jacob.com.LibraryLoader;
import com.jacob.com.Variant;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lichengzhao
 * @version 1.0
 */
public class WordUtils {
	private final Pattern PATTERN_CODE = Pattern.compile(" DOCPROPERTY  (.+)  \\\\\\* MERGEFORMAT ", Pattern.CASE_INSENSITIVE);
	protected Log log = LogFactory.getLog(this.getClass());
	private ActiveXComponent word;
	private Dispatch documents;
	private Resource dllPath86;
	private Resource dllPath64;
	private Vector<File> trashCan = new Vector<File>();
	
	public void initialize() {
		try {
			this.loadJacobDll();
		} catch (Throwable ex) {
			log.error("Load Jacob Dll Faild:" + ex.getMessage());
		}
		try {
			word = new ActiveXComponent("Word.Application");
			word.setProperty("Visible", new Variant(false));
			documents = word.getProperty("Documents").toDispatch();
			log.info("Office Word Is Running");
		} catch(Throwable ex) {
			log.error("Open Office Word Faild", ex);
		}
	}

    private void loadJacobDll() throws Exception {
    	if (System.getProperty(LibraryLoader.JACOB_DLL_PATH) != null)
    		return;
        Resource path = null;
        // 不同位数的系统，用不同位数的dll
        String bits = System.getProperty("sun.arch.data.model", "?");
        if (bits.equals("32"))
            path = dllPath86;
        else if (bits.equals("64"))
            path = dllPath64;
        else {
            // this works for jRocket
            String arch = System.getProperty("java.vm.name", "?");
            if (arch.toLowerCase().indexOf("64-bit") >= 0)
                path = dllPath64;
            else
                path = dllPath86;
        }
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, path.getFile().getAbsolutePath());
    }

	public void destroy() {
		if (word != null) {
			try {
				word.invoke("Quit", new Variant[] {});
				log.info("Office Word Has Stoped");
			} catch (Exception ex) {
				log.error("Close Office Word Faild", ex);
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.warn(e);
		}
		for (int i=0; i<trashCan.size(); i++)
			this.forceDelete(trashCan.get(i));
	}
	
	private boolean forceDelete(File file) {
		if (file == null || !file.exists())
			return true;
		try {
			FileUtils.forceDelete(file);
		} catch (Exception e) {
			log.warn("Delete File " + file.getName() + " Faild", e);
			return false;
		}
		return true;
	}
	
	public void recycle(File garbage) {
		if (garbage != null)
			trashCan.add(garbage);
	}
	
	public File wordToHtml(File wordFile, int format) throws IOException {
		if (documents == null)
			return null;
		File htmlFile = File.createTempFile(FilenameUtils.getBaseName(wordFile.getName()),".htm");
		Dispatch document = null;
		synchronized (documents) {
			try {
				document = Dispatch .invoke(documents, "Open", Dispatch.Method, new Object[] { wordFile.getAbsolutePath() }, new int[1]).toDispatch();
			} catch (ComFailException ex) {
				// 有可能是word被关闭了，重启一次
				this.initialize();
			}
			try {
				if (document == null)
					document = Dispatch .invoke(documents, "Open", Dispatch.Method, new Object[] { wordFile.getAbsolutePath() }, new int[1]).toDispatch();
				// 作为html格式保存到临时文件
				Dispatch.invoke(document, "SaveAs", Dispatch.Method, new Object[] {
						htmlFile.getPath(), new Variant(format)/*,
						new Variant(false), "", new Variant(false), "", new Variant(false), new Variant(), new Variant(false), new Variant(false), 
						new Variant(false), new Variant(), new Variant(true), new Variant(false), new Variant(), new Variant(false)*/}
					, new int[1]);
			} finally {
				// 关闭当前操作的word文档
				if (document != null) {
					try {
						Dispatch.call(document, "Close", new Variant(false));
					} catch (Exception ex) {
						log.warn("Close Document " + wordFile.getName() + " Failed", ex);
					}
				}
			}
		}
		return htmlFile;
	}
	
	/**
	 * Word To Image
	 * waiting...
	 */
	public File wordToImage(File wordFile, int format) throws IOException {
		if (true)
			return null;
		if (documents == null)
			return null;
//		File htmlFile = File.createTempFile(FilenameUtils.getBaseName(wordFile.getName()),".htm");
		Dispatch document = null;
		synchronized (documents) {
			try {
				// 打开word文档
				document = Dispatch.invoke(documents, "Open", Dispatch.Method, new Object[] { wordFile.getAbsolutePath() }, new int[1]).toDispatch();
				Dispatch.call(document, "FreezeLayout");
				Dispatch content = Dispatch.get(document, "Content").toDispatch();
	//			Dispatch selection = Dispatch.get(content, "Selection").toDispatch(); 
				Dispatch.call(content, "WholeStory");
				Dispatch.call(content, "CopyAsPicture");
	
	//			Dispatch html = Dispatch.invoke(docs, "Add", Dispatch.Method, new Object[]{}, new int[1]).toDispatch();	
	//			Dispatch htmlContent = Dispatch.get(html, "Content").toDispatch();
	//			Dispatch.call(selection, "PasteAndFormat", Dispatch.Method, new Object[]{new Variant(0)});
	//			Dispatch.invoke(document, "SaveAs", Dispatch.Method, new Object[] {htmlFile.getPath(), new Variant(format) }, new int[1]);
			} finally {
				// 关闭当前操作的word文档
				if (document != null) {
					try {
						Dispatch.call(document, "Close", new Variant(false));
					} catch (Exception ex) {
						log.warn("Close Document " + wordFile.getName() + " Failed", ex);
					}
				}
			}
		}
		/*
//		Dispatch   content   =   Dispatch.get(document,   "Content ").toDispatch(); 
		Dispatch selection = objWord.getProperty("Selection").toDispatch(); 
		Dispatch.call(selection,   "WholeStory");   
		Dispatch.call(selection,   "CopyAsPicture"); 
		// Dispatch.call(selection,   "Collapse ",   Dispatch.Method,new   Object[]{0}); 
		// Dispatch   WdPasteDataType   =   Dispatch.get(view,   "WdPasteDataType ").toDispatch(); 
		// Dispatch   wdPasteBitmap   =   Dispatch.get(WdPasteDataType,   "WdPasteDataType ").toDispatch(); 
		// Dispatch   document2   =   Dispatch.call(documents,   "Open ",   "C:\\2.doc ").toDispatch(); 
		// Dispatch.call(selection,   "PasteAndFormat ",Dispatch.Method,new   Object[]{new   Variant(0)}); 
		Dispatch.call(selection,   "PasteSpecial",   Dispatch.Method,   new   Object[]{new   Variant(3)});
		*/
		return null;
	}

	public File generateWordArtImage(String text, int msoPresetTextEffect, String fontName, float fontSize, boolean fontBold, boolean fontItalic, float left, float top) throws Exception {
		if (documents == null)
			return null;
        File file = File.createTempFile(System.currentTimeMillis() + "" + new Random().nextLong(), ".html");
        Dispatch document = null;
		synchronized (documents) {
	        try {
	        	document = Dispatch.invoke(documents, "Open", Dispatch.Method, new Object[] {file.getAbsolutePath()}, new int[1]).toDispatch();
	            Dispatch shapes =Dispatch.get(document, "Shapes").toDispatch();
	            Dispatch.invoke(shapes, "AddTextEffect", Dispatch.Method, new Object[] {new Variant(msoPresetTextEffect), text, fontName
	                            , new Variant(fontSize), new Variant(fontBold ? -1 : 0), new Variant(fontItalic ? -1 : 0),
	                            new Variant(left), new Variant(top)}, new int[1]);
	            Dispatch.invoke(document, "SaveAs", Dispatch.Method, new Object[] {file.getAbsolutePath(), new Variant(10)}, new int[1]);
	        } finally {
				if (document != null) {
					try {
						Dispatch.call(document, "Close", new Variant(false));
					} catch (Exception ex) {
						log.warn("Close Document " + file.getName() + " Failed", ex);
					}
				}
	        }
		}
        String path = file.getAbsolutePath();
        File directory = new File(path.substring(0, path.lastIndexOf(".")) + ".files");
        File gif = directory.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".gif");
            }
        })[0];
        this.recycle(file);
        this.recycle(directory);
        return gif;
    }
	
	public void setDllPath86(Resource dllPath86) {
		this.dllPath86 = dllPath86;
	}

	public void setDllPath64(Resource dllPath64) {
		this.dllPath64 = dllPath64;
	}
	
	private String processCode(String code) {
		Matcher m = PATTERN_CODE.matcher(code);
		if (m.find()) {
			String str = m.group(1);
			if (str.startsWith("\""))
				str = str.substring(1, str.length() - 1);
			return str;
		}
		return code;
	}
	
	public File replaceField(Map<String, String> map, File doc) throws IOException {
		if (documents == null)
			throw new IOException("Word Documents == null!");
		File temp = File.createTempFile("word", ".doc");
		try {
			FileUtils.copyFile(doc, temp);
		} finally {
			this.recycle(temp);
		}
        Dispatch document = null;
		synchronized (documents) {
			try {
				document = Dispatch.call(documents, "Open", temp.getAbsolutePath()).toDispatch();
			} catch (ComFailException ex) {
				// 有可能是word被关闭了，重启一次
				this.initialize();
			}
	        try {
				if (document == null)
					document = Dispatch.call(documents, "Open", temp.getAbsolutePath()).toDispatch();
	        	Dispatch fields = Dispatch.get(document, "Fields").toDispatch();
	        	int count = Dispatch.get(fields, "Count").getInt();
	        	Dispatch item = null;
	        	Dispatch code = null;
	        	Dispatch result = null;
	        	String key = null;
	        	String value = null;
	            for (int i = 1; i <= count; i++) {
	            	item = Dispatch.call(fields, "Item", new Variant(i)).toDispatch();
	            	code = Dispatch.get(item, "Code").toDispatch();
	            	result = Dispatch.get(item, "Result").toDispatch();
	            	key = processCode(Dispatch.get(code, "Text").getString());
	            	value = map.get(key);
	            	if (value != null) {
	            		Dispatch.put(result, "Text", value);
	            	}
				}
	            Dispatch.call(document, "Save");
	        } finally {
				if (document != null) {
					try {
						Dispatch.call(document, "Close", new Variant(false));
					} catch (Exception ex) {
						log.warn("Close Document " + temp.getName() + " Failed", ex);
					}
				}
	        }
		}
		return temp;
	}
}
