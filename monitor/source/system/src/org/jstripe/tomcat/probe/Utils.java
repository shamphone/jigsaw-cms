/**
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://probe.jstripe.com/d/license.shtml
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.jstripe.tomcat.probe;

import com.uwyn.jhighlight.renderer.Renderer;
import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.modeler.Registry;
import org.jstripe.tokenizer.StringTokenizer;
import org.jstripe.tokenizer.Token;
import org.jstripe.tokenizer.Tokenizer;
import org.jstripe.tokenizer.TokenizerSymbol;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Misc. static helper methods.
 * <p/>
 * Author: Vlad Ilyushchenko
 */
public class Utils {

    private static Log logger = LogFactory.getLog(Utils.class.getName());

    public static int calcPoolUsageScore(int max, int busy, int total) {
        return max > 0 ? (busy + Math.max(0, (total - busy) / 3)) * 100 / max : 0;
    }

    /**
     * Copies a single file. This method does not interpret source and destination file names in any way.
     * It opens source file for read and destination file for write and pumps the data between the two. If the destination
     * file exists it will be overwritten.
     */
    public static void copyFile(File src, File dest) throws IOException {

        dest.getParentFile().mkdirs();

        if (dest.exists()) {
            dest.delete();
        }

        FileInputStream fis = new FileInputStream(src);
        try {
            byte buffer[] = new byte[65535];

            FileOutputStream fos = new FileOutputStream(dest);
            try {
                int ln;
                while ((ln = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, ln);
                }
            } finally {
                fos.close();
            }
        } finally {
            fis.close();
        }
    }

    /**
     * Reads a file on disk. The method uses default file encoding (see: file.encoding system property)
     *
     * @param file to be read
     * @return String representation of the file contents
     */
    public static String readFile(File file, String charsetName) throws IOException {
        String result = null;
        FileInputStream fis = new FileInputStream(file);
        try {
            result = readStream(fis, charsetName);
        } finally {
            fis.close();
        }
        return result;
    }

    /**
     * Reads strings from the intput stream using the given charset. This method closes the input stream after
     * it has been consumed.
     *
     * @param is
     * @param charsetName
     * @return
     * @throws IOException
     */
    public static String readStream(InputStream is, String charsetName) throws IOException {

        //
        // use system's default encoding if the passed encoding is unsupported
        //
        Charset charset = Charset.forName(System.getProperty("file.encoding"));
        if (Charset.isSupported(charsetName)) {
            charset = Charset.forName(charsetName);
        }

        StringBuffer out = new StringBuffer();
        BufferedReader r = new BufferedReader(new InputStreamReader(is, charset), 4096);
        try {
            String b;
            while ((b = r.readLine()) != null) {
                out.append(b).append("\n");
            }
        } finally {
            r.close();
        }

        return out.toString();
    }

    public static void delete(File f) {
        if (f != null && f.exists()) {
            if (f.isDirectory()) {
                File files[] = f.listFiles();
                for (int i = 0; i < files.length; i++) {
                    delete(files[i]);
                }
            }
            if (!f.delete()) {
                logger.debug("Cannot delete " + f.getAbsolutePath());
            }
        } else {
            logger.debug(f + " does not exist");
        }
    }

    public static int toInt(String num, int defaultValue) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int toIntHex(String num, int defaultValue) {
        try {
            if (num != null && num.startsWith("#")) num = num.substring(1);
            return Integer.parseInt(num, 16);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int toInt(Integer num, int defaultValue) {
        return num == null ? defaultValue : num.intValue();
    }

    public static long toLong(Long num, long defaultValue) {
        return num == null ? defaultValue : num.longValue();
    }

    public static String getJSPEncoding(InputStream is) throws IOException {

        String encoding = null;
        String contentType = null;

        Tokenizer jspTokenizer = new Tokenizer();
        jspTokenizer.addSymbol("\n", true);
        jspTokenizer.addSymbol(" ", true);
        jspTokenizer.addSymbol("\t", true);
        jspTokenizer.addSymbol(new TokenizerSymbol("dir", "<%@", "%>", false, false, true, false));

        StringTokenizer directiveTokenizer = new StringTokenizer();
        directiveTokenizer.addSymbol("\n", true);
        directiveTokenizer.addSymbol(" ", true);
        directiveTokenizer.addSymbol("\t", true);
        directiveTokenizer.addSymbol("=");
        directiveTokenizer.addSymbol("\"", "\"", false);
        directiveTokenizer.addSymbol("'", "'", false);

        StringTokenizer contentTypeTokenizer = new StringTokenizer();
        contentTypeTokenizer.addSymbol(" ", true);
        contentTypeTokenizer.addSymbol(";", true);


        Reader reader = new InputStreamReader(is, "ISO-8859-1");
        try {
            jspTokenizer.setReader(reader);
            while (jspTokenizer.hasMore()) {
                Token token = jspTokenizer.nextToken();
                if ("dir".equals(token.getName())) {
                    directiveTokenizer.setString(token.getInnerText());
                    if (directiveTokenizer.hasMore() && directiveTokenizer.nextToken().getText().equals("page")) {
                        while (directiveTokenizer.hasMore()) {
                            Token dTk = directiveTokenizer.nextToken();
                            if ("pageEncoding".equals(dTk.getText())) {
                                if (directiveTokenizer.hasMore() && "=".equals(directiveTokenizer.nextToken().getText()))
                                {
                                    if (directiveTokenizer.hasMore()) {
                                        encoding = directiveTokenizer.nextToken().getInnerText();
                                        break;
                                    }
                                }
                            } else if ("contentType".equals(dTk.getText())) {
                                if (directiveTokenizer.hasMore() && "=".equals(directiveTokenizer.nextToken().getText()))
                                {
                                    if (directiveTokenizer.hasMore()) {
                                        contentType = directiveTokenizer.nextToken().getInnerText();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            reader.close();
        }

        if (encoding == null && contentType != null) {
            contentTypeTokenizer.setString(contentType);
            while (contentTypeTokenizer.hasMore()) {
                String token = contentTypeTokenizer.nextToken().getText();
                if (token.startsWith("charset=")) {
                    encoding = token.substring("charset=".length());
                    break;
                }
            }
        }

        return encoding != null ? encoding : "ISO-8859-1";
    }

    public static void sendFile(HttpServletRequest request, HttpServletResponse response, File file) throws IOException {
        OutputStream out = response.getOutputStream();
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        try {
            long fileSize = raf.length();
            long rangeStart = 0;
            long rangeFinish = fileSize - 1;

            // accept attempts to resume download (if any)
            String range = request.getHeader("Range");
            if (range != null && range.startsWith("bytes=")) {
                String pureRange = range.replaceAll("bytes=", "");
                int rangeSep = pureRange.indexOf("-");

                try {
                    rangeStart = Long.parseLong(pureRange.substring(0, rangeSep));
                    if (rangeStart > fileSize || rangeStart < 0) rangeStart = 0;
                } catch (NumberFormatException e) {
                    // ignore the exception, keep rangeStart unchanged
                }

                if (rangeSep < pureRange.length() - 1) {
                    try {
                        rangeFinish = Long.parseLong(pureRange.substring(rangeSep + 1));
                        if (rangeFinish < 0 || rangeFinish >= fileSize) rangeFinish = fileSize - 1;
                    } catch (NumberFormatException e) {
                        // ignore the exception
                    }
                }
            }

            // set some headers
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", Long.toString(rangeFinish - rangeStart + 1));
            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeFinish + "/" + fileSize);

            // seek to the requested offset
            raf.seek(rangeStart);

            // send the file
            byte buffer[] = new byte[4096];

            long len;
            int totalRead = 0;
            boolean nomore = false;
            while (true) {
                len = raf.read(buffer);
                if (len > 0 && totalRead + len > rangeFinish - rangeStart + 1) {
                    // read more then required?
                    // adjust the length
                    len = rangeFinish - rangeStart + 1 - totalRead;
                    nomore = true;
                }

                if (len > 0) {
                    out.write(buffer, 0, (int) len);
                    totalRead += len;
                    if (nomore) break;
                } else {
                    break;
                }
            }
        } finally {
            raf.close();
        }
    }

    public static Thread getThreadByName(String name) {
        if (name != null) {
            //
            // get top ThreadGroup
            //
            ThreadGroup masterGroup = Thread.currentThread().getThreadGroup();
            while (masterGroup.getParent() != null) {
                masterGroup = masterGroup.getParent();
            }


            Thread threads[] = new Thread[masterGroup.activeCount()];
            int numThreads = masterGroup.enumerate(threads);

            for (int i = 0; i < numThreads; i++) {
                if (threads[i] != null && name.equals(threads[i].getName())) {
                    return threads[i];
                }
            }
        }
        return null;
    }

    public static String highlightStream(String name, InputStream input, String rendererName, String encoding) throws IOException {

        Renderer jspRenderer = XhtmlRendererFactory.getRenderer(rendererName);
        if (jspRenderer != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            jspRenderer.highlight(name, input, bos, encoding, true);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

            Tokenizer tokenizer = new Tokenizer(new InputStreamReader(bis, encoding));
            tokenizer.addSymbol(new TokenizerSymbol("EOL", "\n", null, false, false, true, false));
            tokenizer.addSymbol(new TokenizerSymbol("EOL", "\r\n", null, false, false, true, false));

            //
            // JHighlight adds HTML comment as the first line, so if
            // we number the lines we could end up with a line number and no line
            // to avoid that we just ignore the first line alltogether.
            //
            StringBuffer buffer = new StringBuffer();
            long counter = 0;
            while (tokenizer.hasMore()) {
                Token tk = tokenizer.nextToken();
                if ("EOL".equals(tk.getName())) {
                    counter++;
                    buffer.append(tk.getText());
                } else if (counter > 0) {
                    buffer.append("<span class=\"codeline\">");
                    buffer.append("<span class=\"linenum\">");
                    buffer.append(leftPad(Long.toString(counter), 6, "&nbsp;"));
                    buffer.append("</span>");
                    buffer.append(tk.getText());
                    buffer.append("</span>");
                }
            }
            return buffer.toString();
        }
        return null;
    }

    public static String leftPad(String s, int len, String fill) {
        StringBuffer sb = new StringBuffer(len);
        if (s.length() < len) {
            for (int i = s.length(); i < len; i++) {
                sb.append(fill);
            }
        }
        sb.append(s);
        return sb.toString();
    }

    public static List<String> getNamesForLocale(String baseName, Locale locale) {
        List<String> result = new ArrayList<String>(3);
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        StringBuffer temp = new StringBuffer(baseName);

        if (language.length() > 0) {
            temp.append('_').append(language);
            result.add(0, temp.toString());
        }

        if (country.length() > 0) {
            temp.append('_').append(country);
            result.add(0, temp.toString());
        }

        if (variant.length() > 0) {
            temp.append('_').append(variant);
            result.add(0, temp.toString());
        }

        return result;
    }

    public static boolean isThreadingEnabled() {
        try {
            MBeanServer mBeanServer = new Registry().getMBeanServer();
            ObjectName threadingOName = new ObjectName("java.lang:type=Threading");
            Set<?> s = mBeanServer.queryMBeans(threadingOName, null);
            return s != null && s.size() > 0;
        } catch (MalformedObjectNameException e) {
            return false;
        }
    }
}
