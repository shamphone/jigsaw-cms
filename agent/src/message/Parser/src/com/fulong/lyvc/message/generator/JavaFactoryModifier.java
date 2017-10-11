/*
 * $Workfile: $
 *
 * $Revision: $
 *
 * $Author: $
 *
 * $Date: $
 *
 * Copyright (c) 2004 Zhongkefulong Corporation
 */


package com.fulong.lyvc.message.generator;

import java.io.*;

public class JavaFactoryModifier {

    private static final String codeStart = "LyvcMessage Parser Generated code start";

    private String filename;

    public JavaFactoryModifier(String filename) {
        this.filename = filename;
    }

    private void replaceOldFileWithBakFile(String filename, String bakFilename) throws Exception {

        File oldFile = new File(filename);
        oldFile.delete();

        File bakFile = new File(bakFilename);
        bakFile.renameTo(oldFile);
    }

    public void addClass(int messageId, String className) throws Exception {

        String bakFilename = filename + ".bak";
        BufferedReader br = new BufferedReader( new FileReader(filename));
        PrintWriter pw = new PrintWriter( new BufferedWriter( new FileWriter( bakFilename)));

        String line;
        while( (line = br.readLine()) != null ) {

            pw.println(line);

            // code start
            if( line.indexOf(codeStart) != -1 ) {

		// Skip next line(comment)
		line = br.readLine();
		pw.println(line);

		pw.println("\t\tcase " + messageId + ":");
		pw.println("\t\t\tbaseMessage = new " + className + "();");
		pw.println("\t\t\tbaseMessage.fromXML(xmlString);");
		pw.println("\t\t\tbreak;");
		pw.println();
            }
        }

        pw.close();
        br.close();

        replaceOldFileWithBakFile(filename, bakFilename);

    }

    public void deleteClass(int messageId, String className) throws Exception {

        String bakFilename = filename + ".bak";
        BufferedReader br = new BufferedReader( new FileReader(filename));
        PrintWriter pw = new PrintWriter( new BufferedWriter( new FileWriter( bakFilename)));

        String targetCodeLine = "case " + messageId + ":";
        String line;
        while( (line = br.readLine()) != null ) {

            if( line.indexOf(targetCodeLine) != -1 ) {

		// Skip next four lines
		line = br.readLine();
		line = br.readLine();
		line = br.readLine();
		line = br.readLine();

            } else {
		pw.println(line);
            }

        }

        pw.close();
        br.close();

        replaceOldFileWithBakFile(filename, bakFilename);
    }
}
