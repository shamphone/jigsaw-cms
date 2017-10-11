package com.fulong.lyvc.message;

import com.fulong.lyvc.message.parser.*;
import com.fulong.lyvc.message.generator.*;
import java.io.*;

public class Parser {

    private static ParserConfig config;

    private static final int WORK_MODE_ADD    = 1;
    private static final int WORK_MODE_UPDATE = 2;
    private static final int WORK_MODE_DELETE = 3;
    private static int workMode;

    private static String workFile;

    private static void printUsage() {
        System.out.println("LyvcMP.bat [-d] MessageDefinitionFile");
        System.out.println("Description:");
        System.out.println("\tWhen no option specified, add or update the message definition.");
        System.out.println("\tWhen -d specified, remove the message definition.");
    }

    private static boolean parseCommandLine(String[] args) {

        if( args.length == 1) {
            workFile = args[0];
            return true;
        }

        if( args.length == 2) {
            if( !args[0].equals("-d")) {
                return false;
            }
            workMode = WORK_MODE_DELETE;
            workFile = args[1];
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws Exception {

        // Parse command line
        if( parseCommandLine(args) == false ) {
            printUsage();
            return;
        }

        // Read config
        config = new ParserConfig();
        config.load("../conf/parser.xml");

        // Parse definition file
        MessageDefinition definition = MessageDefinitionParser.parse(workFile);

        // Determine if we are adding a deinition or updating a definition
        // Or verify the parameter when we are in delete mode.
        MessageIdGenerator messageIdGenerator = new MessageIdGenerator(config.getDatabaseUrl(), config.getDatabaseUsername(), config.getDatabasePassword());
        if( workMode == WORK_MODE_DELETE ) {
            if( ! messageIdGenerator.exist(definition.name) ) {
                System.out.println("No id for name " + definition.name + " exist in database.");
                return;
            }
        } else {
            if (messageIdGenerator.exist(definition.name)) {
                workMode = WORK_MODE_UPDATE;
            } else {
                workMode = WORK_MODE_ADD;
            }
        }

        // Get id for this message
        definition.id = messageIdGenerator.getId(definition.name);

        // Do work
        CplusplusGenerator cplusplusGenerator = new CplusplusGenerator(config.getCplusplusOutputPath(), config.getCplusplusNamespace());
        JavaGenerator javaGenerator = new JavaGenerator(config.getJavaOutputPath(), config.getJavaPackage());
        CplusplusFactoryModifier cplusplusFactoryModifier = new CplusplusFactoryModifier(config.getCplusplusMessageFactoryFile());
        JavaFactoryModifier javaFactoryModifier = new JavaFactoryModifier(config.getJavaMessageFactoryFile());

        switch(workMode) {
            case WORK_MODE_ADD:
		cplusplusGenerator.generateFile(definition);
		javaGenerator.generateFile(definition);
		cplusplusFactoryModifier.addClass(definition.id, definition.name);
		javaFactoryModifier.addClass(definition.id, definition.name);
                break;

	    case WORK_MODE_UPDATE:
		cplusplusGenerator.generateFile(definition);
		javaGenerator.generateFile(definition);
		break;

	    case WORK_MODE_DELETE:
                (new File(config.getCplusplusOutputPath() + "/" + definition.name + ".h")).delete();
                (new File(config.getCplusplusOutputPath() + "/" + definition.name + ".cpp")).delete();
                (new File(config.getJavaOutputPath() + "/" + definition.name + ".java")).delete();
		cplusplusFactoryModifier.deleteClass(definition.id, definition.name);
		javaFactoryModifier.deleteClass(definition.id, definition.name);
                messageIdGenerator.remove(definition.name);
                new File(workFile).delete();
		break;

        }
        return;
    }
}
