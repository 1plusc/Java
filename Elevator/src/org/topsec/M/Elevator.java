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
	private int weight = 0;// 电梯承重
	private int person_num = 0;// 电梯核载人数
	private int person_now = 0;// 电梯当前人数
	private int time = 3;// 电梯每层运行时间-秒默认5秒
	private int life = 0;// 电梯使用次数
	private Date utilday = null;// 最迟检修时间
	private int high_floor = 1;// 最高到达楼层
	private int now_floor = 1;// 当前楼层
	private String now_direction = "up";// 当前楼层
	private List<Integer> up_queue_now = new ArrayList<Integer>();//向上-指令发起位置
	private List<Integer> up_queue_dest = new ArrayList<Integer>();//向上-指令目标位置
	private List<Integer> up_queue_time = new ArrayList<Integer>();//向上-指令发起时间
	private List<Integer> down_queue_now = new ArrayList<Integer>();//向下-指令发起位置
	private List<Integer> down_queue_dest = new ArrayList<Integer>();//向下-指令目标位置
	private List<Integer> down_queue_time = new ArrayList<Integer>();//向下-指令发起时间
	private List<Integer> queue = new ArrayList<Integer>();//需要经停的楼层
	private boolean state = false;// false关机, true开机

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
	 * 获取电梯总核载人数
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
	 * 更改电梯状态
	 * @param state
	 */
	public void setState(boolean state) {
		this.state = state;
	}

}
