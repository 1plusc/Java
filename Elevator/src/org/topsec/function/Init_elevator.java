/**
 * 
 */
package org.topsec.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.topsec.M.Elevator;

/**
 * ��ʼ������
 * @author Alpha
 *
 */
public class Init_elevator {
	private static int person_num = 0;
	private static int high_floor = 0;
	/**
	 * ���õ��ݻ������������������Լ���ߵ���¥��
	 * 
	 * @return null or Elevator
	 */
	public static  Elevator init() {
		// TODO Auto-generated method stub
		boolean flag = false;
		String strin = "";
		System.out.println("*****************СҶ����������¥��߶�����*********************");
		BufferedReader sin = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("������СҶ��������������");
		while (!flag) {
			try {
				strin = sin.readLine();
				if (strin.equals("bye")) {
					flag = true;
				} else {
					person_num = Integer.parseInt(strin);
					flag = true;
					System.out.println("��������" + person_num + "�˳ɹ�");
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("��������������,�����С�Ŀ������ݣ�������bye�õ����Զ��ػ�");
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}

		if (!strin.equals("bye")) {
			System.out.println("������СҶ��������������");
			while (flag) {
				try {
					strin = sin.readLine();
					if (strin.equals("bye")) {
						flag = false;
					} else {
						high_floor = Integer.parseInt(strin);
						flag = false;
						System.out.println("����¥��" + high_floor + "��ɹ�");
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					System.out.println("¥�����������,�����С�Ŀ������ݣ�������bye�õ����Զ��ػ�");
				} catch (IOException e) {
					// TODO Auto-generated catch block

				}
			}
		} else {
			System.out.println("................ϵͳ���ܹػ�������ݿ�ʼ�ػ�............");
			return null;
		}

		if (!strin.equals("bye")) {
			if (person_num <= 1 || high_floor <= 1) {
				System.out
						.println("................�������ò��������ݿ�ʼ�ػ�............");
				return null;
			} else {
				System.out
						.println("*****************СҶ����������¥��߶����óɹ�*********************");
				Elevator elevator = new Elevator(person_num, high_floor);
				return elevator;
			}
		} else {
			System.out.println("................ϵͳ���ܹػ�������ݿ�ʼ�ػ�............");
			return null;
		}

	}
}
