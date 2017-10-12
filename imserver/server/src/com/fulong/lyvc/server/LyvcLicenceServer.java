/**
 * 
 */
package com.fulong.lyvc.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.lyvc.LicenceServer;

/**
 * LyvcLicenceServer
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-19
 */
public class LyvcLicenceServer implements LicenceServer {
	private static Log logger = LogFactory.getLog(LyvcLicenceServer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.lyvc.LicenceServer#validate()
	 */
	public void validate() {
		// License����
		try {
			// get mac address
			Process process = Runtime.getRuntime().exec("ipconfig /all");
			InputStream input = process.getInputStream();
			byte[] b = new byte[1024];
			StringBuffer buffer = new StringBuffer();
			while (input.read(b) > 0) {
				buffer.append(new String(b));
			}
			String address = buffer.substring(0);
			String systemFlag = "Physical Address. . . . . . . . . : ";
			int index = address.indexOf(systemFlag);
			address = buffer.substring(index + systemFlag.length(), index
					+ systemFlag.length() + 17);

			// ���ļ��ж�ȡ����
			String filename = "../conf/lyvc.license";
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));
			int data = 0;
			StringBuffer sb = new StringBuffer();
			while ((data = br.read()) != -1) {
				sb.append((byte) data);
			}
			br.close();

			String fileName2 = "temp.license";
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(address.getBytes("UTF8"));
			byte[] sss = messageDigest.digest();
			java.io.File newFile = new java.io.File(fileName2);
			if (!newFile.exists()) { // ����ļ����棬��b
				newFile.createNewFile();
			}
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName2,
					false));
			pw.print(new String(sss, "UTF8")); // д����
			pw.close();

			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName2)));
			int data2 = 0;
			StringBuffer sb2 = new StringBuffer();
			while ((data2 = br2.read()) != -1) {
				sb2.append((byte) data2);
			}
			br2.close();
			newFile.delete();

			if (sb.toString().equals(sb2.toString()))
				System.out.println("Done!");
			else {
				logger.error("License invalid.");
				System.exit(-1);
			}

		} catch (Exception e) {
			System.exit(-1);
		}
	}

}
