/**
 * 
 */
package org.topsec.M;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author MRYE
 * @version 1.0
 */
/**
 * @author Alpha
 *
 */
public class Elevator {
	private int weight = 0;// ���ݳ���
	private int person_num = 0;// ���ݺ�������
	private int person_now = 0;// ���ݵ�ǰ����
	private int time = 3;// ����ÿ������ʱ��-��Ĭ��5��
	private int life = 0;// ����ʹ�ô���
	private Date utilday = null;// ��ټ���ʱ��
	private int high_floor = 1;// ��ߵ���¥��
	private int now_floor = 1;// ��ǰ¥��
	private String now_direction = "up";// ��ǰ¥��
	private List<Integer> up_queue_now = new ArrayList<Integer>();//����-ָ���λ��
	private List<Integer> up_queue_dest = new ArrayList<Integer>();//����-ָ��Ŀ��λ��
	private List<Integer> up_queue_time = new ArrayList<Integer>();//����-ָ���ʱ��
	private List<Integer> down_queue_now = new ArrayList<Integer>();//����-ָ���λ��
	private List<Integer> down_queue_dest = new ArrayList<Integer>();//����-ָ��Ŀ��λ��
	private List<Integer> down_queue_time = new ArrayList<Integer>();//����-ָ���ʱ��
	private List<Integer> queue = new ArrayList<Integer>();//��Ҫ��ͣ��¥��
	private boolean state = false;// false�ػ�, true����

	public Elevator(int person_num, int high_floor) {
		super();
		this.person_num = person_num;
		this.high_floor = high_floor;
	}

	public Elevator(int person_num, int time, int high_floor) {
		super();
		this.person_num = person_num;
		this.time = time;
		this.high_floor = high_floor;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * ��ȡ�����ܺ�������
	 * @return
	 */
	public int getPerson_num() {
		return person_num;
	}

	public void setPerson_num(int person_num) {
		this.person_num = person_num;
	}

	public int getPerson_now() {
		return person_now;
	}

	public void setPerson_now(int person_now) {
		this.person_now = person_now;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Date getUtilday() {
		return utilday;
	}

	public void setUtilday(Date utilday) {
		this.utilday = utilday;
	}

	public int getHigh_floor() {
		return high_floor;
	}

	public void setHigh_floor(int high_floor) {
		this.high_floor = high_floor;
	}

	public int getNow_floor() {
		return now_floor;
	}

	public void setNow_floor(int now_floor) {
		this.now_floor = now_floor;
	}

	public String getNow_direction() {
		return now_direction;
	}

	public void setNow_direction(String now_direction) {
		this.now_direction = now_direction;
	}


	public List<Integer> getUp_queue_now() {
		return up_queue_now;
	}

	public void setUp_queue_now(List<Integer> up_queue_now) {
		this.up_queue_now = up_queue_now;
	}

	public List<Integer> getUp_queue_dest() {
		return up_queue_dest;
	}

	public void setUp_queue_dest(List<Integer> up_queue_dest) {
		this.up_queue_dest = up_queue_dest;
	}

	public List<Integer> getUp_queue_time() {
		return up_queue_time;
	}

	public void setUp_queue_time(List<Integer> up_queue_time) {
		this.up_queue_time = up_queue_time;
	}

	public List<Integer> getDown_queue_now() {
		return down_queue_now;
	}

	public void setDown_queue_now(List<Integer> down_queue_now) {
		this.down_queue_now = down_queue_now;
	}

	public List<Integer> getDown_queue_dest() {
		return down_queue_dest;
	}

	public void setDown_queue_dest(List<Integer> down_queue_dest) {
		this.down_queue_dest = down_queue_dest;
	}

	public List<Integer> getDown_queue_time() {
		return down_queue_time;
	}

	public void setDown_queue_time(List<Integer> down_queue_time) {
		this.down_queue_time = down_queue_time;
	}

	public List<Integer> getQueue() {
		return queue;
	}

	public void setQueue(List<Integer> queue) {
		this.queue = queue;
	}

	public boolean isState() {
		return state;
	}

	/**
	 * ���ĵ���״̬
	 * @param state
	 */
	public void setState(boolean state) {
		this.state = state;
	}

}
