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
	 * ��ں���
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
				// System.out.println("��ַΪ "+str_URL+directory_md5(directory));
				return str_URL + directory_md5(directory, url,str_URL);// ��Ŀ¼ʱ�Ľ��
			}
		}
		return str_URL + "6666666666";

	}

	private String directory_md5(String[] directory, String url, String str_URL) {
		// TODO Auto-generated method stub
		int all_length = directory.length;// �׳�

		String result = String.valueOf(all_length);
		int d_length = 0;// ÿ��Ŀ¼�ĳ���
		String d_len = "";
		// int numeric_type=0;
		// int letter=0;
		// int num_letter=0;
		// int has_charater=0;

		System.out.println("Ŀ¼����Ϊ" + directory.length);
		if (directory.length == 1 && !url.contains("?")) {
			return str_URL;
		} else {
			for (int i = 0; i < directory.length; i++) {
				d_length = directory[i].length();// Ŀ¼����
				d_len = String.valueOf(d_length);
				result = result + d_len;
				if (get_judge(directory[i], 5)) {
					// ��������ּ����ж��Ƿ�������
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
					// �ж���ĸ��ɵ��ַ�������Сд
					if (get_judge(directory[i], 1)) {
						result = result + "0100";
					} else if (get_judge(directory[i], 2)) {
						result = result + "0200";
					} else {
						result = result + "0300";
					}
				} else if (get_judge(directory[i], 4)) {
					// �ж����ּ���ĸ
					result = result + "0010";
				} else if (get_judge(directory[i], 6)) {
					// �ж��Ƿ��ַ�
					result = result + "0001";
				} else {
					// �����������
					// d_length=8;
					// numeric_type=8;
					// letter=8;
					// num_letter=8;
					// has_charater=8;
					result = result + "8888";
				}
				// System.out.println("Ŀ¼" + directory[i]);
				// System.out.println("����Ϊ:" + get_length(directory[i]));
				// // System.out.println("�Ƿ�������:" + isNumeric(directory[i]));
				// System.out.println("�Ƿ�������:" + get_judge(directory[i], 5));
				// System.out.println("�Ƿ�����:" + has_date(directory[i]));
				// System.out.println("�Ƿ����:" + isYear(directory[i]));
				// System.out.println("�Ƿ��·�:" + isMonth(directory[i]));
				// System.out.println("��ĸ�Ƿ�ȫСд:" + get_judge(directory[i], 1));
				// System.out.println("��ĸ�Ƿ�ȫ��д:" + get_judge(directory[i], 2));
				// System.out.println("26��Ӣ����ĸ��ɵ��ַ���:" + get_judge(directory[i],
				// 3));
				// System.out
				// .println("���ֺ�26��Ӣ����ĸ��ɵ��ַ���:" + get_judge(directory[i], 4));
				// System.out.println("�ж��Ƿ���������ַ�:" + get_judge(directory[i],
				// 6));
			}
			return result;
		}
		
	}

	/**
	 * �������������
	 * 
	 * @param directory
	 * @param i
	 * @return
	 */
	private boolean get_judge(String directory, int i) {
		String reg = "";
		if (i == 1) {// �ж�Сд��ĸ
			reg = "^[a-z]+$";
			return judge(directory, reg);
		} else if (i == 2) {// �ж�ȫ��д
			reg = "^[A-Z]+$";
			return judge(directory, reg);
		} else if (i == 3) {// 26��Ӣ����ĸ��ɵ��ַ���
			reg = "^[A-Za-z]+$";
			return judge(directory, reg);
		} else if (i == 4) {// ���ֺ�26��Ӣ����ĸ��ɵ��ַ���
			reg = "^[A-Za-z0-9]+$";
			return judge(directory, reg);
		} else if (i == 5) {// �ж�����
			reg = "[0-9]";
			return judge(directory, reg);
		} else if (i == 6) {// �ж��Ƿ���������ַ�
			// reg="[~!/@#$%^&*()-_=+\\|[{}];:\'\",<.>/?]+";
			reg = "((?=[\\x21-\\x7e]+)[^A-Za-z0-9])";
			return judge(directory, reg);
		}
		return false;

	}

	/**
	 * ʹ�����������
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
	 * �ж��Ƿ�Ϊ�·�
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
	 * �ж��Ƿ�Ϊ���
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
	 * �ж��Ƿ����20140502���ָ�ʽ������
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
	 * ��ȡĿ¼����
	 * 
	 * @param directory
	 * @return
	 */
	private int get_length(String directory) {
		return directory.length();
	}

	/**
	 * ��������ʽ�ж��Ƿ�������
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * ��ascii���ж��Ƿ�������
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
	 * ��JAVA�Դ��ĺ����ж��Ƿ�������
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
