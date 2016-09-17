/**
 * 
 */
package org.mrye.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Alpha
 * 
 */
public class TalkServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket server = null;
			try {
				// ����һ��ServerSocket�ڶ˿�4700�����ͻ�����
				server = new ServerSocket(4700);
			} catch (Exception e2) {
				// ������ӡ������Ϣ
				System.out.println("�����can not listen to:" + e2);
			}
			Socket socket = null;
			try {
				// ʹ��accept()�����ȴ��ͻ������пͻ�
				// �����������һ��Socket���󣬲�����ִ��
				socket = server.accept();
			} catch (Exception e3) {
				System.out.println("Error." + e3);
			}
			String line;
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// ��Socket����õ�����������������Ӧ��BufferedReader����
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			// ��Socket����õ��������������PrintWriter����
			BufferedReader sin = new BufferedReader(new InputStreamReader(
					System.in));
			// ��ϵͳ��׼�����豸����BufferedReader����
			System.out.println("Client:" + is.readLine());
			// �ڱ�׼����ϴ�ӡ�ӿͻ��˶�����ַ���
			line = sin.readLine();
			// �ӱ�׼�������һ�ַ���
			while (!line.equals("bye")) {
				// ������ַ���Ϊ "bye"����ֹͣѭ��
				os.println(line);
				// ��ͻ���������ַ���
				os.flush();
				// ˢ���������ʹClient�����յ����ַ���
				System.out.println("Server:" + line);
				// ��ϵͳ��׼����ϴ�ӡ������ַ���
				System.out.println("Client:" + is.readLine());
				// ��Client����һ�ַ���������ӡ����׼�����
				line = sin.readLine();
				// ��ϵͳ��׼�������һ�ַ���
			} // ����ѭ��
			os.close(); // �ر�Socket�����
			is.close(); // �ر�Socket������
			socket.close(); // �ر�Socket
			server.close(); // �ر�ServerSocket

		} catch (Exception e1) {
			System.out.println("Error:" + e1);
		}
	}
}
