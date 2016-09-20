/**
 * 
 */
package org.topsec.Interfac;

/**
 * 向下获取的任务
 * @author Alpha
 *
 */
public interface Down_get {
	void getD_now();// 获取指令发出层

	void getD_dest();// 获取指令发出层乘客需要到达的楼层

	void getD_time();// 获取指令发出的时间
}
