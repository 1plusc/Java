/**
 * 
 */
package org.mrye.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Alpha
 * 
 */
public class Test2 {
	private static String App_path = System.getProperty("user.dir");
	private static String data_file = "queue.xml";
	private static List<Integer> up_queue_now = new ArrayList<Integer>();//
	private static List<Integer> up_queue_dest = new ArrayList<Integer>();//
	private static List<Integer> up_queue_time = new ArrayList<Integer>();//
	private static List<Integer> down_queue_now = new ArrayList<Integer>();//
	private static List<Integer> down_queue_dest = new ArrayList<Integer>();//
	private static List<Integer> down_queue_time = new ArrayList<Integer>();//
	private static List<Integer> queue_nn = new ArrayList<Integer>();// 需要经停的楼层

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 queue_nn.add(20);
		 queue_nn.add(13);
		 queue_nn.add(23);
		 queue_nn.add(18);
		 queue_nn.add(18);
		 queue_nn.add(18);
		 
		 for (Integer tmp1 : queue_nn) {
				System.out.println("*********"+tmp1);
			}
		 System.out.println(queue_nn.indexOf(18));
		 
		 HashSet h  =   new  HashSet(queue_nn); 
		 queue_nn.clear(); 
		 h.remove(18);
		 queue_nn.addAll(h); 
		 
		 for (Integer tmp1 : queue_nn) {
				System.out.println("========="+tmp1);
			}
//		 Get_xml_workdata();
//		change_xml("get", 35, "up");
	}

	private static void change_xml(String flag, int floor, String direc) {
		// TODO Auto-generated method stub
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(App_path + "\\" + data_file));
			NodeList nodelist = document.getElementsByTagName("task");
			for (int i = 0; i < nodelist.getLength(); i++) {
				Node node = nodelist.item(i);
				// System.out.println("准备完成 " + node.getNodeName() + "任务");
				Element ele = (Element) node;
				// System.out.println("----------------------------");
				if (node.getNodeType() == Element.ELEMENT_NODE) {
					if (flag.equals("get")) {// 接客
						if (ele.getAttribute("done").equals("0")
								&& ele.getAttribute("direction").equals(direc)
								&& Integer
										.parseInt(ele.getAttribute("present")) == floor) {// 状态为0,方向和楼层一致
							ele.setAttribute("done", "1");
						}
					} else if (flag.equals("post")) {// 送客
						if (ele.getAttribute("done").equals("1")
								&& ele.getAttribute("direction").equals(direc)
								&& Integer.parseInt(ele
										.getElementsByTagName("destination")
										.item(0).getTextContent()) == floor) {// 状态为1
							ele.setAttribute("done", "2");
						}
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			// 设置编码类型
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			StreamResult result = new StreamResult(new FileOutputStream(
					App_path + "\\" + data_file));

			transformer.transform(domSource, result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("配置文件出错");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("打开文件失败");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO流出错");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取xml中需要处理的数据
	 */
	private static void Get_xml_workdata() {
		// TODO Auto-generated method stub
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(App_path + "\\" + data_file));
			NodeList nodelist = document.getElementsByTagName("task");
			// System.out.println("全部" + list.getLength() + "个任务");
			for (int i = 0; i < nodelist.getLength(); i++) {
				Node node = nodelist.item(i);
				// System.out.println("准备完成 " + node.getNodeName() + "任务");
				Element ele = (Element) node;
				// System.out.println("----------------------------");
				if (node.getNodeType() == Element.ELEMENT_NODE) {
					if (ele.getAttribute("done").equals("0")) {
						if (ele.getAttribute("direction").equals("up")) {
							up_queue_now.add(Integer.parseInt(ele
									.getAttribute("present")));
							up_queue_dest.add(Integer.parseInt(ele
									.getElementsByTagName("destination")
									.item(0).getTextContent()));
							up_queue_time.add(Integer.parseInt(ele
									.getAttribute("time")));
						} else {
							down_queue_now.add(Integer.parseInt(ele
									.getAttribute("present")));
							down_queue_dest.add(Integer.parseInt(ele
									.getElementsByTagName("destination")
									.item(0).getTextContent()));
							down_queue_time.add(Integer.parseInt(ele
									.getAttribute("time")));
						}
					} else {
						// 已完成任务，需要后期删除，归档
					}
				}
			}
			middle_process(get_best_choice(up_queue_now, 6, "up"), 6, "up",
					queue_nn, up_queue_now, up_queue_dest, up_queue_time,
					down_queue_now, down_queue_dest, down_queue_time);

			System.out.println("向下任务" + down_queue_now.size());
			System.out.println("向下任务最底层为" + getmin(down_queue_now));
			System.out.println("向下最优的到达位置为"
					+ get_best_choice(down_queue_now, 6, "down"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("配置文件出错");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("打开文件失败");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO流出错");
		}
	}

	/**
	 * 电梯处理下一步需要如何运行
	 * @param get_best_choice
	 * @param floor
	 * @param str
	 * @param queue_nn2
	 * @param up_queue_now2
	 * @param up_queue_dest2
	 * @param up_queue_time2
	 * @param down_queue_now2
	 * @param down_queue_dest2
	 * @param down_queue_time2
	 */
	private static void middle_process(int get_best_choice, int floor,
			String str, List<Integer> queue_nn2, List<Integer> up_queue_now2,
			List<Integer> up_queue_dest2, List<Integer> up_queue_time2,
			List<Integer> down_queue_now2, List<Integer> down_queue_dest2,
			List<Integer> down_queue_time2) {
		// TODO Auto-generated method stub
		List<Integer> queue1 = new ArrayList<Integer>(queue_nn2);
		Collections.sort(queue1);// 从小到大排序
		boolean flag = true;
		if (str.equals("up")) {
			if (get_best_choice >= getmin(queue1)) {
				if (get_best_choice > queue1.get(0) && floor < queue1.get(0)) {
					wait_for();
					floor++;
					System.out.println("00电梯当前在第" + floor + "层");
				} else if (floor == queue1.get(0)) {
					System.out.println("电梯当前停留在第" + floor + "层，欢迎再次乘坐电梯！谢谢");
					change_xml("post", floor, "up");
				}
				flag = false;
			} else {
				// 中间不用停留，因为最优层是比队列中最小楼层还小
			}
			if (flag) {
				if (get_best_choice > floor) {// 中间不用停留，因为最优层是比队列中最小楼层还小
					wait_for();
					floor++;
					System.out.println("11电梯当前在第" + floor + "层");
				}
				flag = false;
			}
			if (get_best_choice == floor) {
				System.out.println("电梯当前停留在第" + floor + "层，欢迎乘坐电梯！");
				for (int xx = 0; xx < up_queue_now2.size(); xx++) {
					if (up_queue_now2.get(xx) == get_best_choice) {
						queue_nn2.add(up_queue_dest2.get(xx));// 将所有符合条件的指令发起位置所要到达的楼层加入
					}
				}
				change_xml("get", get_best_choice, "up");
			}
		} else if (str.equals("down")) {
			Collections.reverse(queue1);// 反转
			if (get_best_choice <= getmax(queue1)) {
				if (get_best_choice < queue1.get(0) && floor > queue1.get(0)) {
					wait_for();
					floor--;
					System.out.println("00电梯当前在第" + floor + "层");
				} else if (floor == queue1.get(0)) {
					System.out.println("电梯当前停留在第" + floor + "层，欢迎再次乘坐电梯！谢谢");
					change_xml("post", floor, "down");
				}
				flag = false;
			} else {
				// 中间不用停留，因为最优层是比队列中最大楼层还大
			}
			if (flag) {
				if (get_best_choice < floor) {// 中间不用停留，因为最优层是比队列中最小楼层还小
					wait_for();
					floor--;
					System.out.println("11电梯当前在第" + floor + "层");
				}
				flag = false;
			}
			if (get_best_choice == floor) {
				System.out.println("电梯当前停留在第" + floor + "层，欢迎乘坐电梯！");
				for (int xx = 0; xx < up_queue_now2.size(); xx++) {
					if (down_queue_now2.get(xx) == get_best_choice) {
						queue_nn2.add(down_queue_dest2.get(xx));// 将所有符合条件的指令发起位置所要到达的楼层加入
					}
				}
				change_xml("get", get_best_choice, "down");
			}
		}

		// /////////////////////////////////////////////
		System.out.println("原任务队列最终为");
		for (Integer tmp1 : queue_nn2) {
			System.out.println(tmp1);
		}
		// /////////////////////////////////////////////
	}

	/**
	 * 休眠3秒
	 */
	private static void wait_for() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3 * 1000);// 暂停n秒输出
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("线程休眠失败");
		}
	}

	private static int get_best_choice(List<Integer> queue, int nowfloor,
			String str) {
		// TODO Auto-generated method stub
		List<Integer> queue1 = new ArrayList<Integer>(queue);
		Collections.sort(queue1);// 从小到大排序
		if (str.equals("up")) {
			for (int i = 0; i < queue1.size(); i++) {
				if (nowfloor <= queue1.get(i)) {
					return queue1.get(i);
				}
			}
		} else {
			Collections.reverse(queue1);// 反转
			for (int i = 0; i < queue1.size(); i++) {
				if (nowfloor >= queue1.get(i)) {
					return queue1.get(i);
				}
			}
		}
		return -1;

	}

	private static int getmin(List<Integer> down_queue_now2) {
		// TODO Auto-generated method stub
		return Collections.min(down_queue_now2);
	}

	private static int getmax(List<Integer> up_queue_now2) {
		// TODO Auto-generated method stub

		return Collections.max(up_queue_now2);
	}

}
