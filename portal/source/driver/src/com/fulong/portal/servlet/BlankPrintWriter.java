package com.fulong.portal.servlet;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class BlankPrintWriter extends PrintWriter {
    public BlankPrintWriter(Writer out) {
        super(out);
    }
    public void write(String s){

    }
     public void flush(){

     }

     /*
      * Exception-catching, synchronized output operations,
      * which also implement the write() methods of Writer
      */

     /**
      * Write a single character.
      * @param c int specifying a character to be written.
      */
     public void write(int c) {

     }

     /**
      * Write A Portion of an array of characters.
      * @param buf Array of characters
      * @param off Offset from which to start writing characters
      * @param len Number of characters to write
      */
     public void write(char buf[], int off, int len) {

     }


     /**
      * Write a portion of a string.
      * @param s A String
      * @param off Offset from which to start writing characters
      * @param len Number of characters to write
      */
     public void write(String s, int off, int len) {

     }

     /**
      * Print a string.  If the argument is <code>null</code> then the string
      * <code>"null"</code> is printed.  Otherwise, the string's characters are
      * converted into bytes according to the platform's default character
      * encoding, and these bytes are written in exactly the manner of the
      * <code>{@link #write(int)}</code> method.
      *
      * @param      s   The <code>String</code> to be printed
      */
     public void print(String s) {

     }

     /**
      * Print an object.  The string produced by the <code>{@link
      * java.lang.String#valueOf(Object)}</code> method is translated into bytes
      * according to the platform's default character encoding, and these bytes
      * are written in exactly the manner of the <code>{@link #write(int)}</code>
      * method.
      *
      * @param      obj   The <code>Object</code> to be printed
      * @see        java.lang.Object#toString()
      */
     public void print(Object obj) {

     }

     /* Methods that do terminate lines */

     /**
      * Terminate the current line by writing the line separator string.  The
      * line separator string is defined by the system property
      * <code>line.separator</code>, and is not necessarily a single newline
      * character (<code>'\n'</code>).
      */
     public void println() {

     }


     /**
      * Print a String and then terminate the line.  This method behaves as
      * though it invokes <code>{@link #print(String)}</code> and then
      * <code>{@link #println()}</code>.
      *
      * @param x the <code>String</code> value to be printed
      */
     public void println(String x) {
     }

     /**
      * Print an Object and then terminate the line.  This method behaves as
      * though it invokes <code>{@link #print(Object)}</code> and then
      * <code>{@link #println()}</code>.
      *
      * @param x the <code>Object</code> value to be printed
      */
     public void println(Object x) {
     }



}
