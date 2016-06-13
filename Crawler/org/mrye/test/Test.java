package org.mrye.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mrye.function.Export_txt;
import org.mrye.resource.MD5;
import org.mrye.web.Ima;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String url = "http://demo.javapms.com";
//		String url2 = "http://demo.javapms.com/China/daily/index.html";

		// url2="http://demo.javapms.com:999/sfa/fawefaw";
//		String u1 = "";
//		String u2 = "";
//		String path = "";
//		String[] directory = null;
//		try {
//			URL hp = new URL(url);
//			URL hp2 = new URL(url2);
//			System.out.println("Protocol: " + hp.getProtocol()); // 协议
//			System.out.println("Port: " + hp.getPort()); // 端口
//			System.out.println("Port: " + hp.getDefaultPort()); // 端口
//			System.out.println("Host: " + hp.getHost()); // 主机
//			System.out.println("File: " + hp.getFile()); // url对应的文件名
//			System.out.println("Path: " + hp.getPath()); // url对应的文件名
//			path = hp2.getPath();
//			// path=hp.getPath();
//			System.out.println(path); // url对应的文件名
//			System.out.println("Ext:" + hp.toExternalForm());
//			System.out.println("Authority:" + hp.getAuthority());
//			System.out.println();
//			u1 = hp.getProtocol() + "://" + hp.getAuthority();
//			u2 = hp2.getProtocol() + "://" + hp2.getAuthority();
//			System.out.println(u1);
//			System.out.println(u2);
//
//			System.out.println(u1.equals(u2));
//
//			if (!path.equals("")) {
//				path = path.substring(1);
//				directory = path.split("/");
//			}
//			for (int i = 0; i < directory.length; i++) {
//				if (directory[i].equals("")) {
//					System.out.println("+++++++++++"+directory.length);
//				}
//				System.out.println("第" + i + "个: " + directory[i]);
//			}
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		 Ima ima=new Ima();
//		 ima.get_Content_From_WebPage(url, -1);

		// MD5 md5 = new MD5();
		// System.out.println(md5.getMD5(url));
		// System.out.println(md5.getMD5("http://demo.javapms.com/3"));
		
//		System.out.println("--------------------------------");
//		url2 = "http://demo.javapms.com:999";
//		url2 = "http://demo.javapms.com/China/daily/index.html";
//		System.out.println(get_directory(url2));
		
//		Export_txt et=new Export_txt();
////		et.generate_directory("nihao", 5);
//		et.export("http://demo.javapms.com/", 1);
		System.out.println(timeStamp2Date("1465698514064",""));
	}

	/**
	 * 获取目录数组
	 * @param linkHref2
	 * @return
	 */
	private static String get_directory(String link) {
		// TODO Auto-generated method stub
		try {
			URL hp = new URL(link);
			return hp.getPath();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty()) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds)));
	}
}
