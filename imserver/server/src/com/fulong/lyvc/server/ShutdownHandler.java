package com.fulong.lyvc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ShutdownHandler extends Thread {

	// Log
	static Log logger = LogFactory.getLog(ShutdownHandler.class);

	// listening port
	int port;

	// shutdown command
	String shutdownCommand;

	// Object to be notified
	Object object;

	public ShutdownHandler(Document document) {

		// Read shutdown setting
		Element root = (Element) document.getElementsByTagName("shutdown").item(0);

		Element classNameElement = (Element) root.getElementsByTagName("port").item(0);
		port = Integer.parseInt(classNameElement.getFirstChild().getNodeValue());

		Element urlElement = (Element) root.getElementsByTagName("command").item(0);
		shutdownCommand = urlElement.getFirstChild().getNodeValue();
	}

	// Monitor
	public void monitorShutdownCommand(Object object) {
		this.object = object;
		this.setName("Shutdown Handler");
		this.setDaemon(true);
		this.start();
	}

	public void run() {

		// Set up a server socket to wait on
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (Exception e) {
			logger.error("Can't create shutdown listen socket, Conference Server will stop.", e);
			synchronized (object) {
				object.notify();
			}
			return;
		}

		// Loop waiting for a connection and a valid command
		while (true) {

			// Wait for the next connection
			Socket socket = null;
			InputStream stream = null;
			try {
				socket = serverSocket.accept();
				socket.setSoTimeout(10 * 1000);
				stream = socket.getInputStream();
			} catch (Exception e) {
				logger.warn("StandardServer.await: accept: " + e);
				continue;
			}

			// Read a set of characters from the socket
			StringBuffer command = new StringBuffer();
			int readCount = 0;
			while (readCount < shutdownCommand.length()) {
				int ch = -1;
				try {
					ch = stream.read();
				} catch (java.net.SocketTimeoutException timeoutEx) {
					logger.warn("Timeout during reading shutdown command.");
					break;
				} catch (IOException e) {
					logger.warn("read: " + e);
					ch = -1;
				}
				try {
					command.append((char) ch);
				} catch (Exception e) {
					logger.warn("command.append((char) ch) " + e);
				}
				readCount++;
			}

			// Close the socket now that we are done with it
			try {
				socket.close();
			} catch (IOException e) {
			}

			// Match against our command string
			boolean match = command.toString().equals(shutdownCommand);
			if (match) {
				synchronized (object) {
					object.notify();
				}
				break;
			} else {
				logger.warn("Invalid command '" + command.toString() + "' received");
			}

		}

		// Close the server socket and return
		try {
			serverSocket.close();
		} catch (IOException e) {
		}

	}

	// Send command
	public void sendShutdownCommand() throws Exception {
		Socket socket = new Socket("127.0.0.1", this.port);
		OutputStream os = socket.getOutputStream();
		os.write(this.shutdownCommand.getBytes());
		socket.close();
	}

	public static void main(String[] args) throws Exception {

		// Read configuation data
		if (args.length == 0) {
			System.out.println("Config file missing!");
			System.exit(0);
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		Document document = db.parse(args[0]);

		ShutdownHandler shutdownHandler = new ShutdownHandler(document);
		shutdownHandler.sendShutdownCommand();
	}
}
