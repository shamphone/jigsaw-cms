package com.fulong.longcon;

import com.fulong.common.util.DesEncrypter;

/**
 * 生成卡用户的密码用
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author not attributable
 * @version 2.0
 */
public class StringUitils {
	/*
	 * public static void main(String[] args) { //读入文件 try { String path =
	 * "d:\\pass.txt"; File file = new File(path); FileReader fr = new
	 * FileReader(file.getName()); //关键在于读取过程中，要判断所读取的字符是否已经到了文件的末尾，
	 * //并且这个字符是不是文件中的断行符，即判断该字符值是否为13。 int c = fr.read(); //从文件中读取一个字符
	 * //判断是否已读到文件结尾 while (c != -1) { System.out.print( (char) c); //输出读到的数据 c
	 * = fr.read(); //从文件中继续读取数据 if (c == 13) { //判断是否为断行字符
	 * System.out.println(); //输出分行标签 fr.skip(1); //略过一个字符
	 * //c=fr.read();//读取一个字符 } } fr.close(); } catch (FileNotFoundException fn)
	 * { } catch (IOException fn) { }
	 * 
	 * 加密6位密码200个
	 * 
	 * 
	 * for (int i = 0; i < 200; i++) { double a = Math.random() 10000000; String
	 * s = String.valueOf(a); s = s.substring(0, 6);
	 * 
	 * DesEncrypter encrypter = new DesEncrypter("fulong"); String pa =
	 * encrypter.encrypt(s); System.out.print(s); System.out.print(" ");
	 * System.out.println(pa); }
	 * 
	 * //解密
	 * 
	 * String[] str = { "Wuds+mYJ6MwTY8Lwu0XyUA==", "zs+InqGezLQ=",
	 * "csPKpBYKSCA=", "2ju2yyFPV6H/wL1EbZMlWg==", "xE8jU8Imi30=",
	 * "QL7QTbwzVz4AYxzquaA0KA==", "T3ZpVR8KjvE=", "TLW+/E2c7N4=",
	 * "haVltyx37SU=", "UpRFezQOs3h7ysUT1l2ldA==", "eHjNZBbJJhM=",
	 * "9txKdgH07ek=", "AuiEA/apUAo=", "5dGCWvK+2OoyJimsUYcN+A==",
	 * "fnZkEyAXVkc7PNC50l3TNg=="}; for (int m = 0; m < str.length; m++) {
	 * String password = str[m]; DesEncrypter sov = new DesEncrypter("fulong");
	 * String use = sov.decrypt(password); System.out.println(password + " , " +
	 * use); } }
	 */
	public static void main(String[] args) {
		String in = "2DodYfY55CuK1n0VfUzpJA";
		DesEncrypter des = new DesEncrypter("fulong");
		System.out.print(des.decrypt(in));
		// System.out.println("DESC".endsWith("DESC"));

		/*
		 * for (int i = 0; i < 200; i++) { double a = Math.random() 10000000;
		 * String s = String.valueOf(a); s = s.substring(0, 6);
		 */

		/*
		 * DesEncrypter encrypter = new DesEncrypter("fulong"); String pa =
		 * encrypter.encrypt("syxzsp_coolink"); System.out.println(pa);
		 */
	}
}
