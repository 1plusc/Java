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
	 * 电梯主程序
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Elevator elevator = null;
		Parase_xml px = new Parase_xml();
		System.out.println("*****************小叶电梯启动中*********************");
		elevator = Init_elevator.init();

		if (elevator != null) {
			elevator.setState(true);
			System.out
					.println("*****************小叶电梯启动成功*********************");
			System.out.println("小叶电梯最多承载" + elevator.getPerson_num()
					+ "个人，最高到达" + elevator.getHigh_floor() + "层,每层运行时间为"
					+ elevator.getTime() + "秒.");
			System.out.println("*****************欢迎乘坐电梯*********************");
			System.out.println("info:当前电梯在第" + elevator.getNow_floor() + "层");
			// 获取用户指令

			while (elevator.isState()) {// 当电梯没有关停的时候一直保持运行
				elevator = px.get_runData(elevator);
				elevator = hoe.process_data(elevator);
				print_now_floor(elevator.getNow_floor(),
						elevator.getNow_direction());
				elevator = hoe.clear(elevator);// 清空数据
			}
			end_elevator(elevator.getNow_floor());
		} else {
			System.out.println("*****************小叶电梯已关闭*********************");
		}
	}

	private static void print_now_floor(int now_floor, String direction) {
		// TODO Auto-generated method stub
		System.out.println("电梯目前在第" + now_floor + "层，当前运行方向为" + direction);
	}

	/**
	 * 电梯下降至一层并关机
	 * 
	 * @param floor
	 */
	public static void end_elevator(int floor) {
		System.out.println("*****************小叶电梯准备关闭********************");
		if (floor > 1) {
			for (int i = floor - 1; i >= 1; i--) {
				hoe.wait_for();
				System.out.println("*****************小叶电梯下降中，现下降至" + i
						+ "层******************");
			}
		}
		System.out.println("*****************小叶电梯下降至1层******************");
		System.out.println("************小叶电梯关闭中,欢迎您的使用***********");
		hoe.wait_for();
		System.out.println("***************小叶电梯已关闭，谢谢使用^_^**********");
		System.out.println("*****************************************");
		System.out.println("***该代码已存入开源库-github-MRYE***");
		System.out.println("***地址为:https://github.com/1plusc***");
		System.out.println("***如有任何问题请至开源平台留言***");
	}

}
