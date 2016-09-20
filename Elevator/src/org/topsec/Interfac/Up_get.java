/**
 * 
 */
package org.topsec.Interfac;

/**
 * 向上获取的任务
 * @author Alpha
 * 
 */
public interface Up_get {
	void getU_now();// 获取指令发出层

	void getU_dest();// 获取指令发出层乘客需要到达的楼层

	void getU_time();// 获取指令发出的时间
}
