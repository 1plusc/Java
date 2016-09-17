/**
 * 
 */
package org.topsec.mrye;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.topsec.M.Elevator;
import org.topsec.function.Heart_of_elevator;
import org.topsec.function.Init_elevator;
import org.topsec.function.Parase_xml;

/**
 * @author Alpha
 * 
 */
public class MyElevator {
	static Heart_of_elevator hoe = new Heart_of_elevator();

	/**
	 * ����������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Elevator elevator = null;
		Parase_xml px = new Parase_xml();
		System.out.println("*****************СҶ����������*********************");
		elevator = Init_elevator.init();

		if (elevator != null) {
			elevator.setState(true);
			System.out
					.println("*****************СҶ���������ɹ�*********************");
			System.out.println("СҶ����������" + elevator.getPerson_num()
					+ "���ˣ���ߵ���" + elevator.getHigh_floor() + "��,ÿ������ʱ��Ϊ"
					+ elevator.getTime() + "��.");
			System.out.println("*****************��ӭ��������*********************");
			System.out.println("info:��ǰ�����ڵ�" + elevator.getNow_floor() + "��");
			// ��ȡ�û�ָ��

			while (elevator.isState()) {// ������û�й�ͣ��ʱ��һֱ��������
				elevator = px.get_runData(elevator);
				elevator = hoe.process_data(elevator);
				print_now_floor(elevator.getNow_floor(),
						elevator.getNow_direction());
				elevator = hoe.clear(elevator);// �������
			}
			end_elevator(elevator.getNow_floor());
		} else {
			System.out.println("*****************СҶ�����ѹر�*********************");
		}
	}

	private static void print_now_floor(int now_floor, String direction) {
		// TODO Auto-generated method stub
		System.out.println("����Ŀǰ�ڵ�" + now_floor + "�㣬��ǰ���з���Ϊ" + direction);
	}

	/**
	 * �����½���һ�㲢�ػ�
	 * 
	 * @param floor
	 */
	public static void end_elevator(int floor) {
		System.out.println("*****************СҶ����׼���ر�********************");
		if (floor > 1) {
			for (int i = floor - 1; i >= 1; i--) {
				hoe.wait_for();
				System.out.println("*****************СҶ�����½��У����½���" + i
						+ "��******************");
			}
		}
		System.out.println("*****************СҶ�����½���1��******************");
		System.out.println("************СҶ���ݹر���,��ӭ����ʹ��***********");
		hoe.wait_for();
		System.out.println("***************СҶ�����ѹرգ�ллʹ��^_^**********");
		System.out.println("*****************************************");
		System.out.println("***�ô����Ѵ��뿪Դ��-github-MRYE***");
		System.out.println("***��ַΪ:https://github.com/1plusc***");
		System.out.println("***�����κ�����������Դƽ̨����***");
	}

}
