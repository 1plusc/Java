package org.mrye.function;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mrye.h2.H2_D;

public class Export_txt {
	private static String App_path = System.getProperty("user.dir");
	private static String result_path = App_path + "\\result";
	private String lineSeparator = System.getProperty("line.separator", "\n");
	private int domainid = 0;
	private int floo = 0;
	private int sum = 0;
	private int sum_dir = 0;
	private int sum_file = 0;
	private String result_info = "";

	public void export(String url, int key) {
		isExist_Directory(result_path);
		switch (key) {
		case 1:
			url = get_from_url(url);
			export_txt(url, "directory_file.txt");
			break;
		case 2:
			export_url(url, "url.txt");
			break;
		default:
			break;
		}
	}

	private void export_url(String url, String filename) {
		// TODO Auto-generated method stub
		int num = 0;
		H2_D h2d;
		try {
			h2d = new H2_D();
			ResultSet rs = null;
			ResultSet rs1 = null;
			rs = h2d.select_all("select url from queue where others='1' ");
			rs1 = h2d.select_all("select * from status ");
			while (rs.next()) {
				num++;
				result_info += rs.getString("url");
				result_info += lineSeparator;
			}
			System.out.println(num);
			if (num == 0) {
				// ��URL����Ϣ
				System.out.println("��URL����Ϣ");
				append("��URL����Ϣ", "error_url.txt");
			} else {
				append("�����ֻ�߲ο�,����ʵ��Ϊ׼!!!" + lineSeparator, filename);
				while (rs1.next()) {
					append("��ʼʱ��Ϊ"
							+ timeStamp2Date(rs1.getString("begintime"),
									"yyyy-MM-dd hh:mm:ss")
							+ ",����ʱ��Ϊ"
							+ timeStamp2Date(rs1.getString("lasttime"),
									"yyyy-MM-dd hh:mm:ss"), filename);
				}
				append("���򹲻�ȡURL" + num + "��,��Ч����δ����,�������ڿ�����...."
						+ lineSeparator, filename);
				append(result_info, filename);
				append("Copyright @2016 [MRYE] Powered By [��վ����] Version 1.0.0 "
						+ lineSeparator, filename);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �����ı�
	 * 
	 * @param url
	 * @param str
	 */
	private void export_txt(String url, String filename) {
		// TODO Auto-generated method stub
		H2_D h2d;
		try {
			h2d = new H2_D();
			ResultSet rs = null;
			ResultSet rs1 = null;
			rs = h2d.select_all("select id from domain where dname='" + url
					+ "' ");
			rs1 = h2d.select_all("select * from status ");
			if (rs.next()) {
				domainid = rs.getInt("id");
				get_name(domainid, -1);
				System.out.println();
				append("�����ֻ�߲ο�,����ʵ��Ϊ׼!!!" + lineSeparator, filename);
				while (rs1.next()) {
					append("��ʼʱ��Ϊ"
							+ timeStamp2Date(rs1.getString("begintime"),
									"yyyy-MM-dd hh:mm:ss")
							+ ",����ʱ��Ϊ"
							+ timeStamp2Date(rs1.getString("lasttime"),
									"yyyy-MM-dd hh:mm:ss"), filename);
				}
				append("���򹲱�����" + sum + "��,������Ŀ¼" + sum_dir + "��,���ļ�"
						+ sum_file + "��." + lineSeparator, filename);
				append(result_info, filename);
				append("Copyright @2016 [MRYE] Powered By [��վ����] Version 1.0.0 "
						+ lineSeparator, filename);
			} else {
				// ����������Ϣ
				System.out.println("����������Ϣ");
				append("����������Ϣ", "error_dir.txt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * ��ȡĿ¼���������ļ���
	 * 
	 * @param str
	 * 
	 * @param domainid2
	 * @param i
	 */
	private void get_name(int domainid, int fatherid) {
		// TODO Auto-generated method stub
		H2_D h2d;
		try {
			h2d = new H2_D();
			ResultSet rs = null;
			ResultSet rs2 = null;
			rs = h2d.select_all("select * from directory where did='"
					+ domainid + "' and fid='" + fatherid + "' ");
			while (rs.next()) {
				sum++;
				generate_directory(rs.getString("name"), floo);
				rs2 = h2d.select_all("select * from directory where did='"
						+ domainid + "' and fid='" + rs.getInt("id") + "' ");
				if (rs2.next()) {
					sum_dir++;
					floo++;
					get_name(domainid, rs.getInt("id"));
				} else {
					sum_file++;
				}
			}
			floo--;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����Ŀ¼
	 * 
	 * @param name
	 * @param floor
	 * @param str
	 */
	private void generate_directory(String name, int floor) {
		String tab = "|";
		for (int i = 1; i <= floor; i++) {
			if (i == 1 && floor == 1) {
				tab += "-";
			} else {
				tab += "  ";
			}
			if (i == floor && i != 1) {
				tab += "   |-";
			}
		}
		tab += name;
		tab += lineSeparator;
		result_info += tab;
	}

	private void append(String content, String file) {
		RandomAccessFile randomFile = null;
		String savePath_file = result_path + "\\" + file;

		try {
			// ��һ����������ļ���������д��ʽ
			randomFile = new RandomAccessFile(savePath_file, "rw");
			// �ļ����ȣ��ֽ���
			long fileLength = randomFile.length();
			// ��д�ļ�ָ���Ƶ��ļ�β��
			randomFile.seek(fileLength);
			randomFile.write(content.getBytes("GB2312"));
			randomFile.writeBytes(lineSeparator);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �ж�Ŀ¼�Ƿ����,�������򴴽���
	 * 
	 * @param str_path
	 *            Ŀ¼·��
	 * @return true Ŀ¼���� false ����Ŀ¼ʧ��
	 */
	public boolean isExist_Directory(String str_path) {
		// TODO Auto-generated method stub
		File file = new File(str_path); // �ж��ļ����Ƿ����,����������򴴽��ļ���
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("//������");
			file.mkdir();
			if (!file.exists() && !file.isDirectory()) {
				return false;
			}
		} else {
			System.out.println("//����ִ��Ŀ¼����");
			return true;
		}
		return false;
	}

	/**
	 * ��ȡURL��Ϣ����Э�飬�����Ͷ˿�
	 * 
	 * @param url
	 * @return
	 */
	private String get_from_url(String url) {
		// TODO Auto-generated method stub
		try {
			URL hp = new URL(url);
			return hp.getProtocol() + "://" + hp.getAuthority();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-1";

	}

	/**
	 * ʱ���ת������ʱ��
	 * 
	 * @param seconds
	 * @param format
	 * @return
	 */
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
