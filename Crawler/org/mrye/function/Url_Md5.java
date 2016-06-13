/**
 * 
 */
package org.mrye.function;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.security.provider.MD5;



/**
 * @author Alpha
 * 
 */
public class Url_Md5 {


	/**
	 * 入口函数
	 * 
	 * @param url
	 * @return
	 */
	public String dispatch_url(String url) {
		URL hp;
		String str_URL = "";
		String path ="";
		String[] directory = null;
		
		try {
			hp = new URL(url);
			path= hp.getPath();
			str_URL = hp.getProtocol() + "://" + hp.getAuthority();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!path.equals("")) {
			// System.out.println("path" + path);
			path = path.substring(1);
			directory = path.split("/");
			// System.out.println(directory.length);
			if (directory.length != 0) {
				// System.out.println("地址为 "+str_URL+directory_md5(directory));
				return str_URL + directory_md5(directory, url,str_URL);// 有目录时的结果
			}
		}
		return str_URL + "6666666666";

	}

	private String directory_md5(String[] directory, String url, String str_URL) {
		// TODO Auto-generated method stub
		int all_length = directory.length;// 首长

		String result = String.valueOf(all_length);
		int d_length = 0;// 每个目录的长度
		String d_len = "";
		// int numeric_type=0;
		// int letter=0;
		// int num_letter=0;
		// int has_charater=0;

		System.out.println("目录长度为" + directory.length);
		if (directory.length == 1 && !url.contains("?")) {
			return str_URL;
		} else {
			for (int i = 0; i < directory.length; i++) {
				d_length = directory[i].length();// 目录长度
				d_len = String.valueOf(d_length);
				result = result + d_len;
				if (get_judge(directory[i], 5)) {
					// 如果是数字继续判断是否年月日
					if (isYear(directory[i])) {
						result = result + "1000";
					} else if (isMonth(directory[i])) {
						result = result + "2000";
					} else if (has_date(directory[i])) {
						result = result + "3000";
					} else {
						result = result + "4000";
					}
				} else if (get_judge(directory[i], 3)) {
					// 判断字母组成的字符串，大小写
					if (get_judge(directory[i], 1)) {
						result = result + "0100";
					} else if (get_judge(directory[i], 2)) {
						result = result + "0200";
					} else {
						result = result + "0300";
					}
				} else if (get_judge(directory[i], 4)) {
					// 判断数字加字母
					result = result + "0010";
				} else if (get_judge(directory[i], 6)) {
					// 判断是否含字符
					result = result + "0001";
				} else {
					// 其他特殊情况
					// d_length=8;
					// numeric_type=8;
					// letter=8;
					// num_letter=8;
					// has_charater=8;
					result = result + "8888";
				}
				// System.out.println("目录" + directory[i]);
				// System.out.println("长度为:" + get_length(directory[i]));
				// // System.out.println("是否是数字:" + isNumeric(directory[i]));
				// System.out.println("是否是数字:" + get_judge(directory[i], 5));
				// System.out.println("是否含日期:" + has_date(directory[i]));
				// System.out.println("是否含年份:" + isYear(directory[i]));
				// System.out.println("是否含月份:" + isMonth(directory[i]));
				// System.out.println("字母是否全小写:" + get_judge(directory[i], 1));
				// System.out.println("字母是否全大写:" + get_judge(directory[i], 2));
				// System.out.println("26个英文字母组成的字符串:" + get_judge(directory[i],
				// 3));
				// System.out
				// .println("数字和26个英文字母组成的字符串:" + get_judge(directory[i], 4));
				// System.out.println("判断是否包含特殊字符:" + get_judge(directory[i],
				// 6));
			}
			return result;
		}
		
	}

	/**
	 * 各大正则处理规则
	 * 
	 * @param directory
	 * @param i
	 * @return
	 */
	private boolean get_judge(String directory, int i) {
		String reg = "";
		if (i == 1) {// 判断小写字母
			reg = "^[a-z]+$";
			return judge(directory, reg);
		} else if (i == 2) {// 判断全大写
			reg = "^[A-Z]+$";
			return judge(directory, reg);
		} else if (i == 3) {// 26个英文字母组成的字符串
			reg = "^[A-Za-z]+$";
			return judge(directory, reg);
		} else if (i == 4) {// 数字和26个英文字母组成的字符串
			reg = "^[A-Za-z0-9]+$";
			return judge(directory, reg);
		} else if (i == 5) {// 判断数字
			reg = "[0-9]";
			return judge(directory, reg);
		} else if (i == 6) {// 判断是否包含特殊字符
			// reg="[~!/@#$%^&*()-_=+\\|[{}];:\'\",<.>/?]+";
			reg = "((?=[\\x21-\\x7e]+)[^A-Za-z0-9])";
			return judge(directory, reg);
		}
		return false;

	}

	/**
	 * 使用正则处理机制
	 * 
	 * @param directory
	 * @param rule
	 * @return
	 */
	private boolean judge(String directory, String rule) {
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(directory);
		while (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为月份
	 * 
	 * @param directory
	 * @return
	 */
	private boolean isMonth(String directory) {
		if (directory.length() <= 2) {
			String reg = "^(0?[1-9]|1[0-2])$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(directory);
			while (matcher.find()) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为年份
	 * 
	 * @param directory
	 * @return
	 */
	private boolean isYear(String directory) {
		if (directory.length() <= 4) {
			String reg = "(19|20)\\d{2}";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(directory);
			while (matcher.find()) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否存在20140502这种格式的日期
	 * 
	 * @param directory
	 * @return
	 */
	private boolean has_date(String directory) {
		if (directory.length() <= 8) {
			String reg = "[1-9]\\d{3}(((0[13578]|1[02])([0-2]\\d|3[01]))|((0[469]|11)([0-2]\\d|30))|(02([01]\\d|2[0-8])))";
			// String str ="http://www.3lian.com/edu/2013/07-22/83111.html";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(directory);
			while (matcher.find()) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 获取目录长度
	 * 
	 * @param directory
	 * @return
	 */
	private int get_length(String directory) {
		return directory.length();
	}

	/**
	 * 用正则表达式判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 用ascii码判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric2(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 用JAVA自带的函数判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric3(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
