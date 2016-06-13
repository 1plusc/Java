package org.mrye.log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MRYE_Log {
	private static String App_path = System.getProperty("user.dir");
	private static String Log_path = App_path + "\\log";
	String lineSeparator = System.getProperty("line.separator", "\n");

	public void record(String info, int level) {
		isExist_Directory(Log_path);
		switch (level) {
		case 1:
			record_Debug(info);
			break;
		case 2:
			record_info(info);
			break;
		case 3:
			record_warn(info);
			break;
		case 4:
			record_error(info);
			break;

		default:
			record_fatal(info);
			break;
		}
	}

	/**
	 * ������Ϣ
	 * 
	 * @param info
	 */
	private void record_Debug(String info) {
		append(info, "debug.txt");
	}

	/**
	 * ������Ϣ
	 * 
	 * @param info
	 */
	private void record_info(String info) {
		append(info, "info.txt");
	}

	/**
	 * ������Ϣ
	 * 
	 * @param info
	 */
	private void record_warn(String info) {
		append(info, "warn.txt");
	}

	/**
	 * ������Ϣ
	 * 
	 * @param info
	 */
	private void record_error(String info) {
		append(info, "error.txt");
	}

	/**
	 * ����/����������Ϣ
	 * 
	 * @param info
	 */
	private void record_fatal(String info) {
		append(info, "fatal.txt");
	}

	private void append(String content, String file) {
		RandomAccessFile randomFile = null;
		String savePath_file = Log_path + "/" + file;
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
}
