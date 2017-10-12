package com.fulong.common;

/**
 * <p>Title: 龙驭网站内容管理系统</p>
 *
 * <p>Description: 龙驭网站内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2008</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 3.0
 */
public class HTMLWriter {
    private StringBuffer content;
    public HTMLWriter(){
        this.content=new StringBuffer("");
    }
    public void beginElement(String tag){
        this.content.append("<"+tag);
    }

    public void closeElement(){
        this.content.append(">");
    }
    public void endElement(String tag){
        this.content.append("</"+tag+">");
    }

    public void writeAttribute(String name, String value) {
     if ( (value != null) && (value.length() > 0)) {
         content.append(" " + name + "=\"" + value + "\"");
     }
    }

     public void writeText(String text){
         content.append(text);
     }

     public void writeln(){
         content.append("\r\n");
     }

     public String toString(){
         return this.content.toString();
 }

}
