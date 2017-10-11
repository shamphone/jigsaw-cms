package com.fulong.service.content;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-12-22	
 * @version 1.0.1
 */
public class ImageCompressService extends NodeService {
	private static final Log log = LogFactory.getLog(ImageCompressService.class);
	private SiteFactory siteFactory;
	
	public void setSiteFactory(SiteFactory siteFactory){
		this.siteFactory = siteFactory;
	}
	
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception{
		String srcProp = parameters.getValue("srcProp");
		String destPropStr = parameters.getValue("destProp");
		String widthStr = parameters.getValue("width");
		String heightStr = parameters.getValue("height");
		String qualityStr = parameters.getValue("quality");
		
		String src = node.getProperty(srcProp).getString();
		String fileName = FilenameUtils.getName(src);
		Property destProp = node.getProperty(destPropStr);
		
		int width = 100;
		int height = 100;
		float quality = 100;
		try{
			width = Integer.parseInt(widthStr);
			height = Integer.parseInt(heightStr);
			quality = Float.parseFloat(qualityStr);
		}catch(Exception e){
		}
		
		
		InputStream in = getResource(request, src);
		Node owner = getSiteOwner(request);
		if(in!=null){
			InputStream input = zipImageFile(in, width, height, quality/100.0f);
			String path = uploadFile(input, "small_"+fileName, owner, request, false);
			destProp.setValue(path);
		}
	}
	//上传文件
	/**
	 * @param in		要上传的文件流
	 * @param fileName	文件名
	 * @param owner		节点所有者	
	 * @param request	
	 * @param override	是否覆盖
	 * @return
	 * @throws Exception
	 */
	protected String uploadFile(InputStream in,String fileName, Node owner, HttpServletRequest request, boolean override) throws Exception {
		Node root = owner.getNode("resources");
		if(root==null){
			root = owner.addNode(this.repository.getDefinitionManager().getDefinition("resource-scheme"),"resources");
		}
		
		Node resource = null;
		if(override&&root.getNodes(fileName).getSize() > 0){
			
			resource = root.getNodes(fileName).next();
		}else{
			//获取唯一文件名
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String name = fileName;
			while (root.getNodes(name).getSize() > 0) {	
				int pos = name.lastIndexOf(".");
				if (pos > 0)
					name = name.substring(0, pos) +  "_" + df.format(System.currentTimeMillis()) + name.substring(pos);
				else
					name = name + df.format(System.currentTimeMillis());
			}
			resource = root.addNode(this.repository.getDefinitionManager().getDefinition("resource-scheme"), name);
		}
		
		resource.setProperty("resource-content", in);
		resource.setProperty("mime", request.getSession().getServletContext().getMimeType(fileName.toLowerCase()));
		resource.setProperty("createdTime", Calendar.getInstance());
		resource.setProperty("length", resource.getProperty("resource-content").getLength());
		resource.setProperty("title", fileName);
		return resource.getPath();
	}
	//获取当前网站
    private Site getRequestSite(HttpServletRequest req){
    	String domain = req.getServerName();
    	int port = req.getServerPort();
    	if(port!=80)
    		domain = domain +":"+port;
    	return this.siteFactory.getSite(domain);
    }
    //获取当前网站的所有者
    private Node getSiteOwner(HttpServletRequest req){
    	Site site = getRequestSite(req);
    	if(site!=null){
    		return site.getOwner();
    	}
    	return null;
    }
    
    /**
     * 根据路径到内容库中获取文件流
     * @param req		
     * @param path		文件在内容库中的路径
     * @return
     */
    private InputStream getResource(HttpServletRequest req,String path){
    	Site site =this.getRequestSite(req);
    	if(site == null)
    		return null;
         Node parent = site.getOwner();
         if(parent == null)
        	 return null;
         String[] pathes = path.split("\\/");
         Node node = parent;
         for(int i=0;i<pathes.length;i++){
        	 if(pathes[i].length()>0){
        		 parent = node;
        		 node = parent.getNode(pathes[i]);
        	 }
         }
         if(node!=null){
        	 return node.getProperty("resource-content").getStream();
         }
         return null;
    }
	/**
	 * 	压缩图片文件
	 * 
	 * @param in		输入图片
	 * @param width		宽
	 * @param height	高
	 * @param quality  质量（压缩比）
	 * @return
	 */
	public static InputStream zipImageFile(InputStream in, int width, int height,float quality) {
		   ByteOutputStream out = null;
	        try {
	        	out = new ByteOutputStream();
	            Image srcFile = ImageIO.read(in);
	            //获取源图片的宽和高
	            int w = srcFile.getWidth(null);
	            int h = srcFile.getHeight(null);
	            
	            /** 宽,高设定*/
	            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);   
	            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);   
	            
	            /** 压缩之后存放在流中*/  
	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
	            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);   
	            /** 压缩质量 */  
	            jep.setQuality(quality, true);   
	            encoder.encode(tag, jep);   
	            return out.newInputStream();
	        } catch (FileNotFoundException e) {
	        	log.error(e.getMessage(),e);  
	        } catch (IOException e) {
	        	log.error(e.getMessage(),e);   
	        } finally{
	        	if(out!=null){
	        		out.close();
	        	}
	        }
	        return null;   
	   }

}
