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
	 * �������к��ĳ���
	 * 
	 * @param elevator
	 * @return
	 */
	public  Elevator process_data(Elevator elevator) {
		// TODO Auto-generated method stub

		if (elevator.getQueue().size() > 0) {
			// ��ִ�ж����е�����
			if (elevator.getNow_direction().equals("up")) {
				// �����Ϳ�
				if (elevator.getUp_queue_now().size() > 0) {
					if (elevator.getNow_floor() <= getmax(elevator
							.getUp_queue_now())) {
						elevator = switch_priva(elevator);//
					} else {
						if (elevator.getNow_floor() < getmin(elevator
								.getQueue())) {
							wait_for();// ������һ��
							elevator.setNow_floor(elevator.getNow_floor() + 1);

						} else if (elevator.getNow_floor() == getmin(elevator.getQueue())) {
							// �Ϳ�
							elevator = process_passger(elevator, "post");
						}
					}
				} else {
					if (elevator.getNow_floor() < getmin(elevator
							.getQueue())) {
						wait_for();// ������һ��
						elevator.setNow_floor(elevator.getNow_floor() + 1);

					} else if (elevator.getNow_floor() == getmin(elevator
							.getQueue())) {
						// �Ϳ�
						elevator = process_passger(elevator, "post");
					}
				}
			} else {
				// �����Ϳ�
				if (elevator.getDown_queue_now().size() > 0) {
					if (elevator.getNow_floor() >= getmin(elevator
							.getDown_queue_now())) {
						elevator = switch_priva(elevator);
					} else {
						if (elevator.getNow_floor() > getmax(elevator
								.getQueue())) {
							wait_for();// ���½�һ��
							elevator.setNow_floor(elevator.getNow_floor() - 1);

						} else if (elevator.getNow_floor() == getmax(elevator.getQueue())) {
							// �Ϳ�
							elevator = process_passger(elevator, "post");
						}
					}
				} else {
					if (elevator.getNow_floor() > getmax(elevator
							.getQueue())) {
						wait_for();// ���½�һ��
						elevator.setNow_floor(elevator.getNow_floor() - 1);

					} else if (elevator.getNow_floor() == getmax(elevator
							.getQueue())) {
						// �Ϳ�
						elevator = process_passger(elevator, "post");
					}
				}
			}
			// ///////////////////11111111111111//////////////////
		} else if (elevator.getUp_queue_now().size() > 0) {
			// ִ������
			if (elevator.getNow_direction().equals("up")) {
				if (elevator.getNow_floor() < elevator.getHigh_floor()) {
					// �жϵ�ǰλ���Ƿ�����������񣬷������½���ָ����㣬Ȼ���ؿ�
					if (elevator.getNow_floor() < getmin(elevator
							.getUp_queue_now())) {
						wait_for();// ������һ��
						elevator.setNow_floor(elevator.getNow_floor() + 1);

					} else if (elevator.getNow_floor() == getmin(elevator
							.getUp_queue_now())) {
						// ��������
						elevator = process_passger(elevator, "get");
					} else if (elevator.getNow_floor() < getmax(elevator
							.getUp_queue_now())) {
						// ���ܻ��кܶ����ϳ˿�
						// ��Ҫ�ж����ͣ�Ĳ�
						elevator = judge_stop_floor(elevator);
					} else if (elevator.getNow_floor() == getmax(elevator
							.getUp_queue_now())) {
						// ֻ��1�����ϳ˿͵����
						// ��������
						elevator = process_passger(elevator, "get");
					} else if (elevator.getDown_queue_now().size() > 0) {
						if (elevator.getNow_floor() < getmax(elevator
								.getDown_queue_now())) {
							wait_for();// ������һ��
							elevator.setNow_floor(elevator.getNow_floor() + 1);

						} else if (elevator.getNow_floor() == getmax(elevator.getDown_queue_now())) {
							elevator.setNow_direction("down");
							// ��������
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
				// ִ������
				if (elevator.getNow_floor() == 1) {
					// ����������ײ㣬��ı䷽��
					elevator.setNow_direction("up");
				} else if (elevator.getNow_floor() > 1) {
					if (elevator.getDown_queue_now().size() > 0) {
						if (elevator.getNow_floor() > getmax(elevator
								.getDown_queue_now())) {
							wait_for();// ���½�һ��
							elevator.setNow_floor(elevator.getNow_floor() - 1);

						} else if (elevator.getNow_floor() == getmax(elevator.getDown_queue_now())) {
							// �������
							elevator = process_passger(elevator, "get");
						} else if (elevator.getNow_floor() > getmin(elevator.getDown_queue_now())) {
							// ��Ҫ�ж�
							elevator = judge_stop_floor(elevator);
						} else if (elevator.getNow_floor() == getmin(elevator.getDown_queue_now())) {
							// �������
							elevator = process_passger(elevator, "get");
						}
					} else {
						if (elevator.getNow_floor() > getmin(elevator
								.getUp_queue_now())) {
							wait_for();// ���½�һ��
							elevator.setNow_floor(elevator.getNow_floor() - 1);

						} else if (elevator.getNow_floor() == getmin(elevator.getUp_queue_now())) {
							elevator.setNow_direction("up");
							// ����һ������
							elevator = process_passger(elevator, "get");
						} else {
							elevator.setNow_direction("up");
						}
					}
				}
			}
			// ///////////////////222222222222222//////////////////
		} else if (elevator.getDown_queue_now().size() > 0) {
			// ִ������
			if (elevator.getNow_direction().equals("up")) {
				if (elevator.getNow_floor() < elevator.getHigh_floor()) {
					// �жϵ�ǰλ���Ƿ�����������񣬷������½���ָ����㣬Ȼ���ؿ�
					if (elevator.getNow_floor() < getmax(elevator
							.getDown_queue_now())) {
						wait_for();// ������һ��
						elevator.setNow_floor(elevator.getNow_floor() + 1);

					} else if (elevator.getNow_floor() == getmax(elevator
							.getDown_queue_now())) {
						// ��һ������
						elevator.setNow_direction("down");
						elevator = process_passger(elevator, "get");
					} else {
						elevator.setNow_direction("down");
					}
				} else {
					elevator.setNow_direction("down");
				}
			} else {
				// ִ������
				if (elevator.getNow_floor() == 1) {
					// ����������ײ㣬��ı䷽��
					elevator.setNow_direction("up");
				} else if (elevator.getNow_floor() > 1) {
					if (elevator.getNow_floor() > getmax(elevator
							.getDown_queue_now())) {
						wait_for();// ���½�һ��
						elevator.setNow_floor(elevator.getNow_floor() - 1);

					} else if (elevator.getNow_floor() == getmax(elevator
							.getDown_queue_now())) {
						// �������
						elevator = process_passger(elevator, "get");
					} else if (elevator.getNow_floor() > getmin(elevator
							.getDown_queue_now())) {
						// ��Ҫ�ж�
						elevator = judge_stop_floor(elevator);
					} else if (elevator.getNow_floor() == getmin(elevator
							.getDown_queue_now())) {
						// �������
						elevator = process_passger(elevator, "get");
					} else {
						elevator.setNow_direction("up");
					}
				}
			}

			// ///////////////////33333333333333333333333333//////////////////
		} else {
			System.out.println("---------���ݴ���----------------");
			wait_for();

		}
		return elevator;
	}
	
	/**
	 * �жϵ����Ƚ���һ�����
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
			Collections.sort(queue);// ��С��������
			for (int i = 0; i < queue.size(); i++) {
				if (elevator.getNow_floor() < queue.get(i)) {
					if (flag) {
						wait_for();// ������һ��
						elevator.setNow_floor(elevator.getNow_floor() + 1);

						flag = false;
					}
				} else if (elevator.getNow_floor() == queue.get(i)) {
					// �������
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
			Collections.sort(queue);// ��С��������
			Collections.reverse(queue);// ��ת
			for (int y = 0; y < queue.size(); y++) {
				if (elevator.getNow_floor() > queue.get(y)) {
					if (flag) {
						wait_for();// ���½�һ��
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
	 * ѡ���������ȵ�����һ��
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
			Collections.sort(queue);// ��С��������
			for (int i = 0; i < queue.size(); i++) {
				if (now < queue.get(i)) {
					if (flag) {
						queue_mix = queue.get(i);
						flag = false;
					}
				} else if (now == queue.get(i)) {
					// �������----
					if (flag) {
						queue_mix = queue.get(i);
						flag = false;
					}
				}
			}
			if (now < getmin(elevator.getQueue())) {
				if (now < queue_mix) {
					wait_for();// ������һ��
					elevator.setNow_floor(elevator.getNow_floor() + 1);
				} else if (now == queue_mix) {
					// �ӿ�
					elevator = process_passger(elevator, "get");
				}
			} else if (now == getmin(elevator.getQueue())) {
				// �Ϳ�
				if (now < queue_mix) {
					// ֻ�Ϳ�
					elevator = process_passger(elevator, "post");
				} else if (now == queue_mix) {
					// �ӿͺ��Ϳ�
					elevator = process_passger(elevator, "get");
					elevator = process_passger(elevator, "post");
				}
			}

		} else {
			queue = new ArrayList<Integer>(elevator.getDown_queue_now());
			Collections.sort(queue);// ��С��������
			Collections.reverse(queue);// ��ת
			for (int i = 0; i < queue.size(); i++) {
				if (now > queue.get(i)) {
					if (flag) {
						queue_mix = queue.get(i);
						flag = false;
					}
				} else if (now == queue.get(i)) {
					// �������----
					if (flag) {
						queue_mix = queue.get(i);
						flag = false;
					}
				}
			}
			if (now > getmax(elevator.getQueue())) {
				if (now > queue_mix) {
					wait_for();// ���½�һ��
					elevator.setNow_floor(elevator.getNow_floor() - 1);

				} else if (now == queue_mix) {
					// �ӿ�
					elevator = process_passger(elevator, "get");
				}
			} else if (now == getmax(elevator.getQueue())) {
				// �Ϳ�
				if (now > queue_mix) {
					// ֻ�Ϳ�
					elevator = process_passger(elevator, "post");
				} else if (now == queue_mix) {
					// �ӿͺ��Ϳ�
					elevator = process_passger(elevator, "get");
					elevator = process_passger(elevator, "post");
				}
			}
		}

		return elevator;
	}
	
	/**
	 * ������Ϳ�
	 * 
	 * @param elevator
	 * @param action
	 * @return
	 */
	private Elevator process_passger(Elevator elevator, String action) {
		// TODO Auto-generated method stub
		Parase_xml px = new Parase_xml();
		if (action.equals("post")) {
			// �Ϳ�
			List<Integer> queue = new ArrayList<Integer>(elevator.getQueue());
			queue = clear_data(queue, elevator.getNow_floor());
			elevator.setQueue(queue);
			px.change_xml("post", elevator.getNow_floor(),
					elevator.getNow_direction());
			System.out.println("#############����ͣ���ڵ�" + elevator.getNow_floor()
					+ "�㣬���ߵ���ò�ĳ˿�#####################���ݻ�ʣ"
					+ elevator.getQueue().size() + "��");
		} else {
			// �ӿ�
			if (elevator.getNow_direction().equals("up")) {
				List<Integer> up_queue = new ArrayList<Integer>(
						elevator.getUp_queue_now());
				List<Integer> queue = new ArrayList<Integer>(
						elevator.getQueue());
				for (int i = 0; i < up_queue.size(); i++) {
					if (up_queue.get(i) == elevator.getNow_floor()) {
						queue.add(elevator.getUp_queue_dest().get(i));
						elevator.setQueue(queue);
						System.out.println("@@@@@@@@@@@@@@@����ͣ���ڵ�"
								+ elevator.getNow_floor() + "�㣬�ӵ�ȥ����"
								+ elevator.getUp_queue_dest().get(i)
								+ "��ĳ˿�@@@@@@@@@@@@@@@����Ŀǰ��"
								+ elevator.getQueue().size() + "��");
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
						System.out.println("@@@@@@@@@@@@@@@����ͣ���ڵ�"
								+ elevator.getNow_floor() + "�㣬�ӵ�ȥ����"
								+ elevator.getDown_queue_dest().get(i)
								+ "��ĳ˿�@@@@@@@@@@@@@@@����Ŀǰ��"
								+ elevator.getQueue().size() + "��");
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
	 * �Ƴ���ͬԪ��
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
	 * ����3��
	 */
	public void wait_for() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3 * 1000);// ��ͣn�����
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("�߳�����ʧ��");
		}
	}

	/**
	 * ��ȡ��С¥��
	 * 
	 * @param down_queue_now
	 * @return
	 */
	public int getmin(List<Integer> down_queue_now2) {
		// TODO Auto-generated method stub
		return Collections.min(down_queue_now2);
	}

	/**
	 * ��ȡ���¥��
	 * 
	 * @param up_queue_now
	 * @return
	 */
	public int getmax(List<Integer> up_queue_now2) {
		// TODO Auto-generated method stub

		return Collections.max(up_queue_now2);
	}
}
