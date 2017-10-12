package com.fulong.lyvc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;import org.apache.commons.logging.LogFactory;
/**
 * 
 * ShutdownHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-12
 */
public class ShutdownServer extends Thread {

	// Log
	private static Log logger = LogFactory.getLog(ShutdownServer.class);

	// listening port
	private int port;

	// shutdown command
	private String command;

	// Object to be notified
	private Object object;

	public void setPort(int port) {
		this.port = port;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public ShutdownServer() {

		/**
		 * Element root = (Element)
		 * document.getElementsByTagName("shutdown").item(0);
		 * 
		 * Element classNameElement =
		 * (Element)root.getElementsByTagName("port").item(0); port =
		 * Integer.parseInt(classNameElement.getFirstChild().getNodeValue());
		 * 
		 * Element urlElement =
		 * (Element)root.getElementsByTagName("command").item(0); command =
		 * urlElement.getFirstChild().getNodeValue();
		 */
	}

	// Monitor
	public void setTarget(Object object) {
		this.object = object;
	}

	public void run() {

		// Set up a server socket to wait on
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress
					.getByName("127.0.0.1"));
		} catch (Exception e) {
			logger
					.error(
							"Can't create shutdown listen socket, Conference Server will stop.",
							e);
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
			while (readCount < command.length()) {
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
			boolean match = command.toString().equals(this.command);
			if (match) {
				synchronized (object) {
					object.notify();
				}
				break;
			} else {
				logger.warn("Invalid command '" + command.toString()
						+ "' received");
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
		os.write(this.command.getBytes());
		socket.close();
	}

	/**
	 * ע�⣬��������ǰ�������ļ����޸�Ϊ���˿ڡ�����
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ShutdownServer shutdownHandler = new ShutdownServer();
		if (args.length > 0)
			shutdownHandler.setPort(Integer.parseInt(args[0]));
		if (args.length > 1)
			shutdownHandler.setCommand(args[1]);
		shutdownHandler.sendShutdownCommand();
	}
}
