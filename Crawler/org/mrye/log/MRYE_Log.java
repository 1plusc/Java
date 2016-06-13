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
	 * 调试信息
	 * 
	 * @param info
	 */
	private void record_Debug(String info) {
		append(info, "debug.txt");
	}

	/**
	 * 反馈信息
	 * 
	 * @param info
	 */
	private void record_info(String info) {
		append(info, "info.txt");
	}

	/**
	 * 警告信息
	 * 
	 * @param info
	 */
	private void record_warn(String info) {
		append(info, "warn.txt");
	}

	/**
	 * 错误信息
	 * 
	 * @param info
	 */
	private void record_error(String info) {
		append(info, "error.txt");
	}

	/**
	 * 严重/致命错误信息
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
			// 打开一个随机访问文件流，按读写方式
			randomFile = new RandomAccessFile(savePath_file, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
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
	 * 判断目录是否存在,不存在则创建，
	 * 
	 * @param str_path
	 *            目录路径
	 * @return true 目录存在 false 创建目录失败
	 */
	public boolean isExist_Directory(String str_path) {
		// TODO Auto-generated method stub
		File file = new File(str_path); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("//不存在");
			file.mkdir();
			if (!file.exists() && !file.isDirectory()) {
				return false;
			}
		} else {
			System.out.println("//函数执行目录存在");
			return true;
		}
		return false;
	}
}
