package com.fulong.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class StringUtils {
    public static final String PATTERN = "\\{+\\d*\\}+";
    public static final String REPLACE = "--FLAG--";
    public static String html2Text(String content) throws ParserException {
        StringBuffer content_text = new StringBuffer();
        NodeList nodeList = null;
        Parser parser = Parser.createParser(content, null);
        parser.setEncoding("utf-8");
        NodeFilter textFilter = new NodeClassFilter(TextNode.class);
        NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
        OrFilter lastFilter = new OrFilter();
        lastFilter.setPredicates(new NodeFilter[] {textFilter, linkFilter});
        nodeList = parser.parse(lastFilter);
        Node[] nodes = nodeList.toNodeArray();
        for (int i = 0; i < nodes.length; i++) {
            Node anode = (Node) nodes[i];
            String line = "";
            if (anode instanceof TextNode) {
                TextNode textnode = (TextNode) anode;
                //line = textnode.toPlainTextString().trim();
                line = textnode.getText();
            }
            else if (anode instanceof LinkTag) {
                LinkTag linknode = (LinkTag) anode;
                line = linknode.getLink();
            }
            if (line != null) {
                content_text.append(line);
            }
        }
        return content_text.toString();
    }

    public static String format(String source, Object arg1) {
        return format(source, new Object[] {arg1});
    }

    public static String format(String source, Object arg1, Object arg2) {
        return format(source, new Object[] {arg1, arg2});
    }

    public static String format(String source, Object[] args) {
        String result = source;
        for (int i = 0; i < args.length; i++) {
            String regex = "\\x7B" + i + "\\x7D";
            if (args[i] == null) {
                result = result.replaceFirst(regex, "");
            }
            else {
                String encoded;
                try {
                    encoded = URLEncoder.encode(args[i].toString(), "UTF-8");
                }
                catch (UnsupportedEncodingException ex) {
                    encoded = args[i].toString();
                }

                result = result.replaceFirst(regex, encoded);
            }
        }

        return result;

    }

    private static String[] parseReg(Object[] args) {
        String[] result = new String[args.length];
        for(int i=0;i<args.length;i++){
            result[i] = parseString((String)args[i]);
        }
        return result;
    }

    /**
     * 将s中的{\\d+\\}都用--FLAG--取代，避免解析时出现死循环
     * @param s String
     * @return String
     */
    private static String parseString(String s){
    	if(s==null)
    		return "";
        return  s.replaceAll(PATTERN, REPLACE);

    }

    /**
     * 获得args里面的{\\d+},用字符串数字保存起来。
     * @param args Object[]
     * @return String[]
     */
    private static String[] getReg(Object[] args) {
        List<String> result = new ArrayList<String>();
        for(int i=0;i<args.length;i++){
            result.addAll(getRegForEach((String)args[i]));
        }
        return (String[])result.toArray(new String[result.size()]);
    }

    /**
     * 传入一个字符串，返回其中的符合正则表达的子串组成的数组。
     * 比如"fsdasjdfkjsdklf{2}}}}}fdsfajskd姐夫大开杀戒{11}发生{1}大火反馈";，将返回{"{2}}}}}", "{11}", "{1}"}
     * @param args String
     * @return String[]
     */
    private static List<String> getRegForEach(String args) {
        List<String> v = new ArrayList<String>();
        if((args==null)||(args.endsWith("")))
        	return v;
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(args);
        while (m.find()) {
            v.add(m.group());
        }
        return v;
    }




    public static String formatAll(String source, Object[] args) {
        String[] afterParse = parseReg(args);
        String[] allRegs = getReg(args);

        String[] splits=source.split("\'");
        for(int i=0;i<splits.length;i++){
            splits[i]=formatSub(splits[i],afterParse);
        }
        String result = catenate(splits,"'");

        Pattern p = Pattern.compile(REPLACE);
        String[] temp = p.split(result);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<temp.length-1;i++){
            sb.append(temp[i]);
            sb.append(allRegs[i]);
        }
        sb.append(temp[temp.length-1]);
        return sb.toString();
    }

    private static String formatSub(String source, Object[] args) {
        String formated = MessageFormat.format(source, args);
        while (!formated.equals(MessageFormat.format(formated, args))) {
            formated = MessageFormat.format(formated, args);
        }
        return formated;
    }


    public static String[] splitKeywords(String keywords) {
        String delimit = "([\\s �?1�?7�?1�?7)+";
        ArrayList<String> result = new ArrayList<String>();
        if (keywords != null) {
            String[] split = keywords.split(delimit);
            for (int count = 0; count < split.length; count++) {
                if ( (split[count] != null) && (split[count].length() > 0)) {
                    result.add(split[count]);
                }
            }

        }
        return (String[]) result.toArray(new String[result.size()]);

    }

    public static String catenate(String[] array) {
        return catenate(array,"");
    }
    
    public static String string2Ascii(String s) {
		String unicode = "";
		char[] charAry = new char[s.length()];
		for(int i=0; i<charAry.length; i++) {
			charAry[i] = (char)s.charAt(i);
			unicode+="\\u" + Integer.toString(charAry[i], 16);
		}
		return unicode;
	}
    
    public static String ascii2String(String ascii) {    
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
        Matcher matcher = pattern.matcher(ascii);    
        char ch;    
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);     
            ascii = ascii.replace(matcher.group(1), ch + "");    
        }    
        return ascii;   
    } 

    public static String catenate(String[] array, String seperator) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if(i>0)
                buffer.append(seperator);
            buffer.append(array[i]);
        }
        return buffer.toString();
    }

    public static String[] replace(String[] source, String target,
                                   String replacement) {
        String newString[] = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            newString[i] = source[i].replace(target, replacement);
        }
        return newString;
    }

}
