/**
 * 
 */
package org.topsec.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.topsec.M.Elevator;

/**
 * 初始化电梯
 * @author Alpha
 *
 */
public class Init_elevator {
	private static int person_num = 0;
	private static int high_floor = 0;
	/**
	 * 设置电梯基本参数，核载人数以及最高到达楼层
	 * 
	 * @return null or Elevator
	 */
	public static  Elevator init() {
		// TODO Auto-generated method stub
		boolean flag = false;
		String strin = "";
		System.out.println("*****************小叶电梯人数和楼层高度设置*********************");
		BufferedReader sin = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("请输入小叶电梯最多核载人数");
		while (!flag) {
			try {
				strin = sin.readLine();
				if (strin.equals("bye")) {
					flag = true;
				} else {
					person_num = Integer.parseInt(strin);
					flag = true;
					System.out.println("设置人数" + person_num + "人成功");
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("人数必须是数字,如果不小心开启电梯，请输入bye让电梯自动关机");
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}

		if (!strin.equals("bye")) {
			System.out.println("请输入小叶电梯最多核载重量");
			while (flag) {
				try {
					strin = sin.readLine();
					if (strin.equals("bye")) {
						flag = false;
					} else {
						high_floor = Integer.parseInt(strin);
						flag = false;
						System.out.println("设置楼层" + high_floor + "层成功");
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					System.out.println("楼层必须是数字,如果不小心开启电梯，请输入bye让电梯自动关机");
				} catch (IOException e) {
					// TODO Auto-generated catch block

				}
			}
		} else {
			System.out.println("................系统接受关机命令，电梯开始关机............");
			return null;
		}

		if (!strin.equals("bye")) {
			if (person_num <= 1 || high_floor <= 1) {
				System.out
						.println("................参数设置不合理，电梯开始关机............");
				return null;
			} else {
				System.out
						.println("*****************小叶电梯人数和楼层高度设置成功*********************");
				Elevator elevator = new Elevator(person_num, high_floor);
				return elevator;
			}
		} else {
			System.out.println("................系统接受关机命令，电梯开始关机............");
			return null;
		}

	}
}
