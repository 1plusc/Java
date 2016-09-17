/**
 * 
 */
package org.topsec.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.topsec.M.Elevator;

/**
 * @author Alpha
 * 
 */
public class Heart_of_elevator {

	public Elevator clear(Elevator elevator) {
		List<Integer> queue = new ArrayList<Integer>();
		queue.clear();
		elevator.setUp_queue_now(queue);
		elevator.setUp_queue_dest(queue);
		elevator.setUp_queue_time(queue);
		elevator.setDown_queue_now(queue);
		elevator.setDown_queue_dest(queue);
		elevator.setDown_queue_time(queue);
		return elevator;
	}
	/**
	 * 电梯运行核心程序
	 * 
	 * @param elevator
	 * @return
	 */
	public  Elevator process_data(Elevator elevator) {
		// TODO Auto-generated method stub

		if (elevator.getQueue().size() > 0) {
			// 先执行队列中的任务
			if (elevator.getNow_direction().equals("up")) {
				// 向上送客
				if (elevator.getUp_queue_now().size() > 0) {
					if (elevator.getNow_floor() <= getmax(elevator
							.getUp_queue_now())) {
						elevator = switch_priva(elevator);//
					} else {
						if (elevator.getNow_floor() < getmin(elevator
								.getQueue())) {
							wait_for();// 往上升一层
							elevator.setNow_floor(elevator.getNow_floor() + 1);

						} else if (elevator.getNow_floor() == getmin(elevator.getQueue())) {
							// 送客
							elevator = process_passger(elevator, "post");
						}
					}
				} else {
					if (elevator.getNow_floor() < getmin(elevator
							.getQueue())) {
						wait_for();// 往上升一层
						elevator.setNow_floor(elevator.getNow_floor() + 1);

					} else if (elevator.getNow_floor() == getmin(elevator
							.getQueue())) {
						// 送客
						elevator = process_passger(elevator, "post");
					}
				}
			} else {
				// 向下送客
				if (elevator.getDown_queue_now().size() > 0) {
					if (elevator.getNow_floor() >= getmin(elevator
							.getDown_queue_now())) {
						elevator = switch_priva(elevator);
					} else {
						if (elevator.getNow_floor() > getmax(elevator
								.getQueue())) {
							wait_for();// 往下降一层
							elevator.setNow_floor(elevator.getNow_floor() - 1);

						} else if (elevator.getNow_floor() == getmax(elevator.getQueue())) {
							// 送客
							elevator = process_passger(elevator, "post");
						}
					}
				} else {
					if (elevator.getNow_floor() > getmax(elevator
							.getQueue())) {
						wait_for();// 往下降一层
						elevator.setNow_floor(elevator.getNow_floor() - 1);

					} else if (elevator.getNow_floor() == getmax(elevator
							.getQueue())) {
						// 送客
						elevator = process_passger(elevator, "post");
					}
				}
			}
			// ///////////////////11111111111111//////////////////
		} else if (elevator.getUp_queue_now().size() > 0) {
			// 执行向上
			if (elevator.getNow_direction().equals("up")) {
				if (elevator.getNow_floor() < elevator.getHigh_floor()) {
					// 判断当前位置是否低于向上任务，否则先下降至指令发出层，然后载客
					if (elevator.getNow_floor() < getmin(elevator
							.getUp_queue_now())) {
						wait_for();// 往上升一层
						elevator.setNow_floor(elevator.getNow_floor() + 1);

					} else if (elevator.getNow_floor() == getmin(elevator
							.getUp_queue_now())) {
						// 加入任务
						elevator = process_passger(elevator, "get");
					} else if (elevator.getNow_floor() < getmax(elevator
							.getUp_queue_now())) {
						// 可能会有很多向上乘客
						// 需要判断最近停哪层
						elevator = judge_stop_floor(elevator);
					} else if (elevator.getNow_floor() == getmax(elevator
							.getUp_queue_now())) {
						// 只有1个向上乘客的情况
						// 加入任务
						elevator = process_passger(elevator, "get");
					} else if (elevator.getDown_queue_now().size() > 0) {
						if (elevator.getNow_floor() < getmax(elevator
								.getDown_queue_now())) {
							wait_for();// 往上升一层
							elevator.setNow_floor(elevator.getNow_floor() + 1);

						} else if (elevator.getNow_floor() == getmax(elevator.getDown_queue_now())) {
							elevator.setNow_direction("down");
							// 加入任务
							elevator = process_passger(elevator, "get");
						} else {
							elevator.setNow_direction("down");
						}
					} else {
						elevator.setNow_direction("down");
					}
				} else {
					elevator.setNow_direction("down");
				}
			} else {
				// 执行向下
				if (elevator.getNow_floor() == 1) {
					// 当电梯在最底层，则改变方向
					elevator.setNow_direction("up");
				} else if (elevator.getNow_floor() > 1) {
					if (elevator.getDown_queue_now().size() > 0) {
						if (elevator.getNow_floor() > getmax(elevator
								.getDown_queue_now())) {
							wait_for();// 往下降一层
							elevator.setNow_floor(elevator.getNow_floor() - 1);

						} else if (elevator.getNow_floor() == getmax(elevator.getDown_queue_now())) {
							// 加入队列
							elevator = process_passger(elevator, "get");
						} else if (elevator.getNow_floor() > getmin(elevator.getDown_queue_now())) {
							// 需要判断
							elevator = judge_stop_floor(elevator);
						} else if (elevator.getNow_floor() == getmin(elevator.getDown_queue_now())) {
							// 加入队列
							elevator = process_passger(elevator, "get");
						}
					} else {
						if (elevator.getNow_floor() > getmin(elevator
								.getUp_queue_now())) {
							wait_for();// 往下降一层
							elevator.setNow_floor(elevator.getNow_floor() - 1);

						} else if (elevator.getNow_floor() == getmin(elevator.getUp_queue_now())) {
							elevator.setNow_direction("up");
							// 加入一个任务
							elevator = process_passger(elevator, "get");
						} else {
							elevator.setNow_direction("up");
						}
					}
				}
			}
			// ///////////////////222222222222222//////////////////
		} else if (elevator.getDown_queue_now().size() > 0) {
			// 执行向上
			if (elevator.getNow_direction().equals("up")) {
				if (elevator.getNow_floor() < elevator.getHigh_floor()) {
					// 判断当前位置是否低于向上任务，否则先下降至指令发出层，然后载客
					if (elevator.getNow_floor() < getmax(elevator
							.getDown_queue_now())) {
						wait_for();// 往上升一层
						elevator.setNow_floor(elevator.getNow_floor() + 1);

					} else if (elevator.getNow_floor() == getmax(elevator
							.getDown_queue_now())) {
						// 加一个任务
						elevator.setNow_direction("down");
						elevator = process_passger(elevator, "get");
					} else {
						elevator.setNow_direction("down");
					}
				} else {
					elevator.setNow_direction("down");
				}
			} else {
				// 执行向下
				if (elevator.getNow_floor() == 1) {
					// 当电梯在最底层，则改变方向
					elevator.setNow_direction("up");
				} else if (elevator.getNow_floor() > 1) {
					if (elevator.getNow_floor() > getmax(elevator
							.getDown_queue_now())) {
						wait_for();// 往下降一层
						elevator.setNow_floor(elevator.getNow_floor() - 1);

					} else if (elevator.getNow_floor() == getmax(elevator
							.getDown_queue_now())) {
						// 加入队列
						elevator = process_passger(elevator, "get");
					} else if (elevator.getNow_floor() > getmin(elevator
							.getDown_queue_now())) {
						// 需要判断
						elevator = judge_stop_floor(elevator);
					} else if (elevator.getNow_floor() == getmin(elevator
							.getDown_queue_now())) {
						// 加入队列
						elevator = process_passger(elevator, "get");
					} else {
						elevator.setNow_direction("up");
					}
				}
			}

			// ///////////////////33333333333333333333333333//////////////////
		} else {
			System.out.println("---------电梯待运----------------");
			wait_for();

		}
		return elevator;
	}
	
	/**
	 * 判断电梯先接哪一层的人
	 * 
	 * @param elevator
	 * @return
	 */
	private  Elevator judge_stop_floor(Elevator elevator) {
		// TODO Auto-generated method stub
		boolean flag = true;
		List<Integer> queue = null;
		if (elevator.getNow_direction().equals("up")) {
			queue = new ArrayList<Integer>(elevator.getUp_queue_now());
			Collections.sort(queue);// 从小到大排序
			for (int i = 0; i < queue.size(); i++) {
				if (elevator.getNow_floor() < queue.get(i)) {
					if (flag) {
						wait_for();// 往上升一层
						elevator.setNow_floor(elevator.getNow_floor() + 1);

						flag = false;
					}
				} else if (elevator.getNow_floor() == queue.get(i)) {
					// 加入队列
					if (flag) {
						List<Integer> up_queue = new ArrayList<Integer>(
								elevator.getUp_queue_now());
						List<Integer> queue_nn = new ArrayList<Integer>(
								elevator.getQueue());
						for (int i1 = 0; i1 < up_queue.size(); i1++) {
							if (up_queue.get(i1) == elevator.getNow_floor()) {
								queue_nn.add(elevator.getUp_queue_dest()
										.get(i1));
							}
						}
						up_queue = clear_data(up_queue, elevator.getNow_floor());
						elevator.setQueue(queue_nn);
						elevator.setUp_queue_now(up_queue);
						flag = false;
					}
				}
			}
		} else {
			queue = new ArrayList<Integer>(elevator.getDown_queue_now());
			Collections.sort(queue);// 从小到大排序
			Collections.reverse(queue);// 反转
			for (int y = 0; y < queue.size(); y++) {
				if (elevator.getNow_floor() > queue.get(y)) {
					if (flag) {
						wait_for();// 往下降一层
						elevator.setNow_floor(elevator.getNow_floor() - 1);

						flag = false;
					}
				} else if (elevator.getNow_floor() == queue.get(y)) {
					if (flag) {
						List<Integer> down_queue = new ArrayList<Integer>(
								elevator.getDown_queue_now());
						List<Integer> queue_nn = new ArrayList<Integer>(
								elevator.getQueue());
						for (int i = 0; i < down_queue.size(); i++) {
							if (down_queue.get(i) == elevator.getNow_floor()) {
								queue_nn.add(elevator.getDown_queue_dest().get(
										i));
							}
						}
						down_queue = clear_data(down_queue,
								elevator.getNow_floor());
						elevator.setQueue(queue_nn);
						elevator.setDown_queue_now(down_queue);
						flag = false;
					}
				}
			}
		}
		return elevator;
	}
	
	/**
	 * 选出电梯优先到达哪一层
	 * 
	 * @param elevator
	 * @return
	 */
	private  Elevator switch_priva(Elevator elevator) {
		// TODO Auto-generated method stub
		int now = elevator.getNow_floor();
		List<Integer> queue = null;
		int queue_mix = -1;
		boolean flag = true;
		if (elevator.getNow_direction().equals("up")) {
			queue = new ArrayList<Integer>(elevator.getUp_queue_now());
			Collections.sort(queue);// 从小到大排序
			for (int i = 0; i < queue.size(); i++) {
				if (now < queue.get(i)) {
					if (flag) {
						queue_mix = queue.get(i);
						flag = false;
					}
				} else if (now == queue.get(i)) {
					// 加入队列----
					if (flag) {
						queue_mix = queue.get(i);
						flag = false;
					}
				}
			}
			if (now < getmin(elevator.getQueue())) {
				if (now < queue_mix) {
					wait_for();// 往上升一层
					elevator.setNow_floor(elevator.getNow_floor() + 1);
				} else if (now == queue_mix) {
					// 接客
					elevator = process_passger(elevator, "get");
				}
			} else if (now == getmin(elevator.getQueue())) {
				// 送客
				if (now < queue_mix) {
					// 只送客
					elevator = process_passger(elevator, "post");
				} else if (now == queue_mix) {
					// 接客和送客
					elevator = process_passger(elevator, "get");
					elevator = process_passger(elevator, "post");
				}
			}

		} else {
			queue = new ArrayList<Integer>(elevator.getDown_queue_now());
			Collections.sort(queue);// 从小到大排序
			Collections.reverse(queue);// 反转
			for (int i = 0; i < queue.size(); i++) {
				if (now > queue.get(i)) {
					if (flag) {
						queue_mix = queue.get(i);
						flag = false;
					}
				} else if (now == queue.get(i)) {
					// 加入队列----
					if (flag) {
						queue_mix = queue.get(i);
						flag = false;
					}
				}
			}
			if (now > getmax(elevator.getQueue())) {
				if (now > queue_mix) {
					wait_for();// 往下降一层
					elevator.setNow_floor(elevator.getNow_floor() - 1);

				} else if (now == queue_mix) {
					// 接客
					elevator = process_passger(elevator, "get");
				}
			} else if (now == getmax(elevator.getQueue())) {
				// 送客
				if (now > queue_mix) {
					// 只送客
					elevator = process_passger(elevator, "post");
				} else if (now == queue_mix) {
					// 接客和送客
					elevator = process_passger(elevator, "get");
					elevator = process_passger(elevator, "post");
				}
			}
		}

		return elevator;
	}
	
	/**
	 * 处理接送客
	 * 
	 * @param elevator
	 * @param action
	 * @return
	 */
	private Elevator process_passger(Elevator elevator, String action) {
		// TODO Auto-generated method stub
		Parase_xml px = new Parase_xml();
		if (action.equals("post")) {
			// 送客
			List<Integer> queue = new ArrayList<Integer>(elevator.getQueue());
			queue = clear_data(queue, elevator.getNow_floor());
			elevator.setQueue(queue);
			px.change_xml("post", elevator.getNow_floor(),
					elevator.getNow_direction());
			System.out.println("#############电梯停留在第" + elevator.getNow_floor()
					+ "层，送走到达该层的乘客#####################电梯还剩"
					+ elevator.getQueue().size() + "人");
		} else {
			// 接客
			if (elevator.getNow_direction().equals("up")) {
				List<Integer> up_queue = new ArrayList<Integer>(
						elevator.getUp_queue_now());
				List<Integer> queue = new ArrayList<Integer>(
						elevator.getQueue());
				for (int i = 0; i < up_queue.size(); i++) {
					if (up_queue.get(i) == elevator.getNow_floor()) {
						queue.add(elevator.getUp_queue_dest().get(i));
						elevator.setQueue(queue);
						System.out.println("@@@@@@@@@@@@@@@电梯停留在第"
								+ elevator.getNow_floor() + "层，接到去往第"
								+ elevator.getUp_queue_dest().get(i)
								+ "层的乘客@@@@@@@@@@@@@@@电梯目前有"
								+ elevator.getQueue().size() + "人");
					}
				}
				up_queue = clear_data(up_queue, elevator.getNow_floor());
				elevator.setUp_queue_now(up_queue);
			} else if (elevator.getNow_direction().equals("down")) {
				List<Integer> down_queue = new ArrayList<Integer>(
						elevator.getDown_queue_now());
				List<Integer> queue = new ArrayList<Integer>(
						elevator.getQueue());
				for (int i = 0; i < down_queue.size(); i++) {
					if (down_queue.get(i) == elevator.getNow_floor()) {
						queue.add(elevator.getDown_queue_dest().get(i));
						elevator.setQueue(queue);
						System.out.println("@@@@@@@@@@@@@@@电梯停留在第"
								+ elevator.getNow_floor() + "层，接到去往第"
								+ elevator.getDown_queue_dest().get(i)
								+ "层的乘客@@@@@@@@@@@@@@@电梯目前有"
								+ elevator.getQueue().size() + "人");
					}
				}
				down_queue = clear_data(down_queue, elevator.getNow_floor());
				elevator.setDown_queue_now(down_queue);
			}
			px.change_xml("get", elevator.getNow_floor(),
					elevator.getNow_direction());
		}
		return elevator;
	}
	
	/**
	 * 移除相同元素
	 * 
	 * @param queue
	 * @param now_floor
	 * @return
	 */
	private static List<Integer> clear_data(List<Integer> queue, int now_floor) {
		// TODO Auto-generated method stub
		HashSet<Integer> h = new HashSet<Integer>(queue);
		queue.clear();
		h.remove(now_floor);
		queue.addAll(h);
		return queue;
	}
	
	/**
	 * 休眠3秒
	 */
	public void wait_for() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3 * 1000);// 暂停n秒输出
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("线程休眠失败");
		}
	}

	/**
	 * 获取最小楼层
	 * 
	 * @param down_queue_now
	 * @return
	 */
	public int getmin(List<Integer> down_queue_now2) {
		// TODO Auto-generated method stub
		return Collections.min(down_queue_now2);
	}

	/**
	 * 获取最高楼层
	 * 
	 * @param up_queue_now
	 * @return
	 */
	public int getmax(List<Integer> up_queue_now2) {
		// TODO Auto-generated method stub

		return Collections.max(up_queue_now2);
	}
}
