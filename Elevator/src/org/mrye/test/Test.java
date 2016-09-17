package org.mrye.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

public class Test {
	private static String App_path = System.getProperty("user.dir");
	private static String data_file = "queue.xml";
	private static List<Integer> up_queue_now = new ArrayList<Integer>();//
	private static List<Integer> up_queue_dest = new ArrayList<Integer>();//
	private static List<Integer> up_queue_time = new ArrayList<Integer>();//
	private static List<Integer> down_queue_now = new ArrayList<Integer>();//
	private static List<Integer> down_queue_dest = new ArrayList<Integer>();//
	private static List<Integer> down_queue_time = new ArrayList<Integer>();//
	private static List<Integer> queue_nn = new ArrayList<Integer>();// ��Ҫ��ͣ��¥��

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// int time1 = 1473745485;//2016/9/13 13:44:45
		// int time2 = 1473745506;//2016/9/13 13:45:6
		// System.out.println((time2 - time1) );
		// Get_xmldata();
		// ArrayList up_queue =new ArrayList();
		// up_queue.add("dsf1");
		// up_queue.add("dsf2");
		// up_queue.add("dsf3");
		// for(int i=0;i<up_queue.size();i++){
		// System.out.println(up_queue.get(i));
		// }
		// List<String> list =up_queue;
		// for(String tmp:list){
		// System.out.println(tmp);
		// }
		queue_nn.add(20);
		queue_nn.add(13);
		queue_nn.add(23);
		queue_nn.add(18);
		Get_xml_workdata();
	}

	private static void Get_xml_workdata() {
		// TODO Auto-generated method stub
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(App_path + "\\" + data_file));
			NodeList nodelist = document.getElementsByTagName("task");
			// System.out.println("ȫ��" + list.getLength() + "������");
			for (int i = 0; i < nodelist.getLength(); i++) {
				Node node = nodelist.item(i);
				// System.out.println("׼����� " + node.getNodeName() + "����");
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
						// System.out.println("task id: "
						// + ele.getAttribute("id"));
						// System.out.println("task direction: "
						// + ele.getAttribute("direction"));
						// System.out.println("task done: "
						// + ele.getAttribute("done"));
						// System.out.println("task present: "
						// + ele.getAttribute("present"));
						// System.out.println("task time: "
						// + ele.getAttribute("time"));
						// System.out.println("task destination: "
						// + ele.getElementsByTagName("destination")
						// .item(0).getTextContent());
					} else {
						// �����������Ҫ����ɾ�����鵵
					}
				}
			}
			System.out.println("��������" + up_queue_now.size());
			System.out.println("����������߲�Ϊ" + getmax(up_queue_now));
			System.out.println("�������ŵĵ���λ��Ϊ"
					+ get_best_choice(up_queue_now, 6, "up"));

			middle_process(get_best_choice(up_queue_now, 6, "up"), 6, "up",
					queue_nn, up_queue_now, up_queue_dest, up_queue_time);

			System.out.println("��������" + down_queue_now.size());
			System.out.println("����������ײ�Ϊ" + getmin(down_queue_now));
			System.out.println("�������ŵĵ���λ��Ϊ"
					+ get_best_choice(down_queue_now, 6, "down"));

			System.out.println("---------����ָ��ȴ�λ��----------------");
			for (Integer tmp1 : up_queue_now) {
				System.out.println(tmp1);
			}
			System.out.println("---------Ŀ��λ��----------------");
			for (Integer tmp1 : up_queue_dest) {
				System.out.println(tmp1);
			}
			System.out.println("---------ָ��ʱ��----------------");
			for (Integer tmp1 : up_queue_time) {
				System.out.println(tmp1);
			}
			System.out.println("---------����ָ��ȴ�λ��----------------");
			for (Integer tmp1 : down_queue_now) {
				System.out.println(tmp1);
			}
			System.out.println("---------Ŀ��λ��----------------");
			for (Integer tmp1 : down_queue_dest) {
				System.out.println(tmp1);
			}
			System.out.println("---------ָ��ʱ��----------------");
			for (Integer tmp1 : down_queue_time) {
				System.out.println(tmp1);
			}

			// System.out.println("---------�������----------------");
			// up_queue_now.clear();
			// up_queue_dest.clear();
			// up_queue_time.clear();
			// down_queue_now.clear();
			// down_queue_dest.clear();
			// down_queue_time.clear();
			// System.out.println("---------��������ָ��ȴ�λ��----------------");
			// for(String tmp1:up_queue_now){
			// System.out.println(tmp1);
			// }
			// System.out.println("---------Ŀ��λ��----------------");
			// for(String tmp1:up_queue_dest){
			// System.out.println(tmp1);
			// }
			// System.out.println("---------ָ��ʱ��----------------");
			// for(String tmp1:up_queue_time){
			// System.out.println(tmp1);
			// }
			// System.out.println("---------��������ָ��ȴ�λ��----------------");
			// for(String tmp1:down_queue_now){
			// System.out.println(tmp1);
			// }
			// System.out.println("---------Ŀ��λ��----------------");
			// for(String tmp1:down_queue_dest){
			// System.out.println(tmp1);
			// }
			// System.out.println("---------ָ��ʱ��----------------");
			// for(String tmp1:down_queue_time){
			// System.out.println(tmp1);
			// }
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("�����ļ�����");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("���ļ�ʧ��");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO������");
		}
	}

	private static void middle_process(int get_best_choice, int floor,
			String str, List<Integer> queue_nn2, List<Integer> up_queue_now2,
			List<Integer> up_queue_dest2, List<Integer> up_queue_time2) {
		// TODO Auto-generated method stub
		List<Integer> queue1 = new ArrayList<Integer>(queue_nn2);
		Collections.sort(queue1);// ��С��������
		boolean flag=true;
		// /////////////////////////////////////////////
		System.out.println("ԭ�������Ϊ");
		for (Integer tmp1 : queue_nn2) {
			System.out.println(tmp1);
		}
		System.out.println("ԭ������и�������Ϊ");
		for (Integer tmp1 : queue1) {
			System.out.println(tmp1);
		}
		// /////////////////////////////////////////////
		if (str.equals("up")) {
			if (get_best_choice >= getmin(queue1)) {
//				for (int i = 0; i < queue1.size(); i++) {
//					if (get_best_choice > queue1.get(i)) {
//						System.out.println("���ݽ���" + queue1.get(i) + "��ͣ��");
//						for (int y = floor + 1; y <= queue1.get(i); y++) {
//							try {
//								Thread.sleep(3 * 1000);// ��ͣn�����
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							floor = y;
//
//							if (floor == queue1.get(i)) {
//								System.out.println("���ݵ�ǰͣ���ڵ�" + floor
//										+ "�㣬��ӭ�ٴγ������ݣ�лл");
//							} else {
//								System.out.println("���ݵ�ǰ�ڵ�" + floor + "��");
//							}
//						}
//						queue_nn2.remove(queue1.get(i));
//					}
//				}
				if (get_best_choice > queue1.get(0)&&floor<queue1.get(0)) {
					try {
						Thread.sleep(3 * 1000);// ��ͣn�����
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					floor++;
					System.out.println("00���ݵ�ǰ�ڵ�" + floor + "��");
				}else if(floor==queue1.get(0)){
					System.out.println("���ݵ�ǰͣ���ڵ�" + floor
							+ "�㣬��ӭ�ٴγ������ݣ�лл");
				}
				flag=false;
			} else {
				// �м䲻��ͣ������Ϊ���Ų��Ǳȶ�������С¥�㻹С
			}
			if(flag){
				if (get_best_choice > floor) {// �ӿ�
					try {
						Thread.sleep(3 * 1000);// ��ͣn�����
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					floor++;
					System.out.println("11���ݵ�ǰ�ڵ�" + floor + "��");
				}
				flag=false;
			}
			if(get_best_choice == floor){
				System.out.println("���ݵ�ǰͣ���ڵ�" + floor + "�㣬��ӭ�������ݣ�");
				queue_nn2.add(up_queue_dest2.get(up_queue_now2.indexOf(get_best_choice)));
			}
//			if (get_best_choice > floor) {// �ӿ�
//				for (int y = floor + 1; y <= get_best_choice; y++) {
//					try {
//						Thread.sleep(3 * 1000);// ��ͣn�����
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					floor = y;
//					if (floor == get_best_choice) {
//						System.out.println("���ݵ�ǰͣ���ڵ�" + floor + "�㣬��ӭ�������ݣ�");
//						queue_nn2.add(up_queue_dest2.get(up_queue_now2.indexOf(get_best_choice)));
//					} else {
//						System.out.println("11���ݵ�ǰ�ڵ�" + floor + "��");
//					}
//				}
//				try {
//					Thread.sleep(3 * 1000);// ��ͣn�����
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				floor++;
//				System.out.println("11���ݵ�ǰ�ڵ�" + floor + "��");
//			}else{
//				System.out.println("���ݵ�ǰͣ���ڵ�" + floor + "�㣬��ӭ�������ݣ�");
//				queue_nn2.add(up_queue_dest2.get(up_queue_now2.indexOf(get_best_choice)));
//			}
		} else {

		}

		// /////////////////////////////////////////////
		System.out.println("ԭ�����������Ϊ");
		for (Integer tmp1 : queue_nn2) {
			System.out.println(tmp1);
		}
		// /////////////////////////////////////////////
	}

	private static int get_best_choice(List<Integer> queue, int nowfloor,
			String str) {
		// TODO Auto-generated method stub
		List<Integer> queue1 = new ArrayList<Integer>(queue);
		Collections.sort(queue1);// ��С��������
		if (str.equals("up")) {
			for (int i = 0; i < queue1.size(); i++) {
				if (nowfloor <= queue1.get(i)) {
					return queue1.get(i);
				}
			}
		} else {
			Collections.reverse(queue1);// ��ת
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

	/**
	 * ��ȡ��������״̬
	 * 
	 * @return boolean
	 */
	public static void Get_xmldata() {
		// TODO Auto-generated method stub
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(App_path + "\\" + data_file));

			NodeList list = document.getElementsByTagName("rest");
			Element element = (Element) list.item(0);
			System.out.println(element.getElementsByTagName("shutdown").item(0)
					.getFirstChild().getNodeValue());
			System.out.println(element.getTagName());// rest
			NodeList list2 = element.getElementsByTagName("shutdown");
			Element element2 = (Element) list2.item(0);
			System.out.println(element2.getTagName());// shutdown
			System.out.println(element2.getAttribute("count"));// shutdown
			element2.setAttribute("count", "0");
			System.out.println(element2.getAttribute("count"));// shutdown

			try {
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource domSource = new DOMSource(document);
				// ���ñ�������
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				StreamResult result = new StreamResult(new FileOutputStream(
						App_path + "\\" + data_file));

				transformer.transform(domSource, result);
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("�����ļ�����");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("���ļ�ʧ��");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO������");
		}

	}
}
