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

public class CplusplusFactoryModifier {

    private static final String headerStart = "LyvcMessage Parser Generated header start";
    private static final String codeStart = "LyvcMessage Parser Generated code start";

    private String filename;

    public CplusplusFactoryModifier(String filename) {
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
		pw.println("\t\t\tpBaseMessage = new " + className + "();");
		pw.println("\t\t\tpBaseMessage->fromXML(message);");
		pw.println("\t\t\tbreak;");
		pw.println();
            } else if( line.indexOf(headerStart) != -1) {

		// Skip next line(comment)
		line = br.readLine();
		pw.println(line);

		pw.println("#include \"" + className + ".h\"");
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

        String targetHeaderLine = "#include \"" + className + ".h\"";
        String targetCodeLine = "case " + messageId + ":";
        String line;
        while( (line = br.readLine()) != null ) {

            if( line.indexOf(targetCodeLine) != -1 ) {

		// Skip next four lines
		line = br.readLine();
		line = br.readLine();
		line = br.readLine();
		line = br.readLine();

            } else if( line.indexOf(targetHeaderLine) != -1) {
                // Skip this line
            } else {
		pw.println(line);
            }

        }

        pw.close();
        br.close();

        replaceOldFileWithBakFile(filename, bakFilename);
    }
}
