/**
 * 
 */
package org.topsec.second;

import java.util.ArrayList;
import java.util.List;

import org.topsec.Interfac.Down_get;
import org.topsec.Interfac.Queue;
import org.topsec.Interfac.Up_get;

/**
 * @author Alpha
 * 
 */
public class Elevator implements Up_get, Down_get, Queue {
	private int weight = 0;// 电梯承重
	private int person_num = 0;// 电梯核载人数
	private int time = 3;// 电梯每层运行时间-秒默认3秒
	private int life = 0;// 电梯使用次数
	private int high_floor = 1;// 最高到达楼层
	private String now_direction = "up";// 当前楼层
	private boolean state = false;// false关机, true开机
	private List<Integer> up_queue_now = null;// 向上-指令发起位置
	private List<Integer> up_queue_dest = null;// 向上-指令目标位置
	private List<Integer> up_queue_time = null;// 向上-指令发起时间
	private List<Integer> down_queue_now = null;// 向下-指令发起位置
	private List<Integer> down_queue_dest = null;// 向下-指令目标位置
	private List<Integer> down_queue_time = null;// 向下-指令发起时间
	private List<Integer> queue = null;// 需要经停的楼层

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getPerson_num() {
		return person_num;
	}

	public void setPerson_num(int person_num) {
		this.person_num = person_num;
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

	public int getHigh_floor() {
		return high_floor;
	}

	public void setHigh_floor(int high_floor) {
		this.high_floor = high_floor;
	}

	public String getNow_direction() {
		return now_direction;
	}

	public void setNow_direction(String now_direction) {
		this.now_direction = now_direction;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
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

	public void now_dest() {
		// TODO Auto-generated method stub
		setQueue(new ArrayList<Integer>());
	}

	public void getD_now() {
		// TODO Auto-generated method stub
		setDown_queue_time(new ArrayList<Integer>());
	}

	public void getD_dest() {
		// TODO Auto-generated method stub
		setDown_queue_dest(new ArrayList<Integer>());
	}

	public void getD_time() {
		// TODO Auto-generated method stub
		setDown_queue_time(new ArrayList<Integer>());
	}

	public void getU_now() {
		// TODO Auto-generated method stub
		setUp_queue_now(new ArrayList<Integer>());
	}

	public void getU_dest() {
		// TODO Auto-generated method stub
		setUp_queue_dest(new ArrayList<Integer>());
	}

	public void getU_time() {
		// TODO Auto-generated method stub
		setUp_queue_time(new ArrayList<Integer>());
	}

	public List<Integer> getQueue() {
		return queue;
	}

	public void setQueue(List<Integer> queue) {
		this.queue = queue;
	}

	public List<Integer> getDown_queue_time() {
		return down_queue_time;
	}

	public void setDown_queue_time(List<Integer> down_queue_time) {
		this.down_queue_time = down_queue_time;
	}

	public List<Integer> getDown_queue_dest() {
		return down_queue_dest;
	}

	public void setDown_queue_dest(List<Integer> down_queue_dest) {
		this.down_queue_dest = down_queue_dest;
	}

}
