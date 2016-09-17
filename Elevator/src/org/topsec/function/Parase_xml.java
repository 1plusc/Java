/**
 * 
 */
package org.topsec.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

import org.topsec.M.Elevator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * ����xml����
 * 
 * @author Alpha
 * 
 */
public class Parase_xml {
	private static String App_path = System.getProperty("user.dir");
	private static String data_file = "queue.xml";

	/**
	 * ��ȡ��������״̬,��ʱ����
	 * 
	 * @return boolean
	 */
	public boolean Get_stateData() {
		// TODO Auto-generated method stub
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(App_path + "\\" + data_file));
			NodeList list = document.getElementsByTagName("rest");
			if (list.getLength() == 1) {
				// ���Ԫ�أ����ڵ�ǿ��ת��ΪԪ��
				Element element = (Element) list.item(0);
				String content = element.getElementsByTagName("shutdown")
						.item(0).getFirstChild().getNodeValue();
				if (content.equals("1"))
					return true;
				else
					return false;
			} else {
				return false;
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
		return false;
	}

	/**
	 * ��ȡ��������ʵʱ����״̬
	 * 
	 * @param elevator
	 * @return
	 */
	public Elevator get_runData(Elevator elevator) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		List<Integer> up_queue_now = new ArrayList<Integer>();//
		List<Integer> up_queue_dest = new ArrayList<Integer>();//
		List<Integer> up_queue_time = new ArrayList<Integer>();//
		List<Integer> down_queue_now = new ArrayList<Integer>();//
		List<Integer> down_queue_dest = new ArrayList<Integer>();//
		List<Integer> down_queue_time = new ArrayList<Integer>();//
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(App_path + "\\" + data_file));

			// ��ȡ��������״̬----------��ʼ
			NodeList list = document.getElementsByTagName("rest");
			// ���Ԫ�أ����ڵ�ǿ��ת��ΪԪ��
			Element element = (Element) list.item(0);
			String content = element.getElementsByTagName("shutdown").item(0)
					.getFirstChild().getNodeValue();
			if (content.equals("1"))
				elevator.setState(true);
			else
				elevator.setState(false);
			// ��ȡ��������״̬----------����

			// ��ȡ���ݻ�ȡָ��---------��ʼ
			NodeList nodelist = document.getElementsByTagName("task");
			// System.out.println("ȫ��" + list.getLength() + "������");
			for (int i = 0; i < nodelist.getLength(); i++) {
				Node node = nodelist.item(i);
				// System.out.println("׼����� " + node.getNodeName() + "����");
				Element ele = (Element) node;
				//System.out.println("----------------------------");
				if (node.getNodeType() == Element.ELEMENT_NODE) {
					// ��ȡδ��ɵ�����
					if (ele.getAttribute("done").equals("0")) {
						// ��ȡ�����ͬ����δ��ɵ�����
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
						// �����������Ҫ����ɾ�����鵵
					}
				}
			}
			elevator.setUp_queue_now(up_queue_now);
			elevator.setUp_queue_dest(up_queue_dest);
			elevator.setUp_queue_time(up_queue_time);
			elevator.setDown_queue_now(down_queue_now);
			elevator.setDown_queue_dest(down_queue_dest);
			elevator.setDown_queue_time(down_queue_time);
			// ��ȡ���ݻ�ȡָ��---------����

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
		return elevator;
	}

	public void change_xml(String flag, int floor, String direc) {
		// TODO Auto-generated method stub
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(App_path + "\\" + data_file));
			NodeList nodelist = document.getElementsByTagName("task");
			for (int i = 0; i < nodelist.getLength(); i++) {
				Node node = nodelist.item(i);
				// System.out.println("׼����� " + node.getNodeName() + "����");
				Element ele = (Element) node;
				// System.out.println("----------------------------");
				if (node.getNodeType() == Element.ELEMENT_NODE) {
					if (flag.equals("get")) {// �ӿ�
						if (ele.getAttribute("done").equals("0")
								&& ele.getAttribute("direction").equals(direc)
								&& Integer
										.parseInt(ele.getAttribute("present")) == floor) {// ״̬Ϊ0,�����¥��һ��
							ele.setAttribute("done", "1");
						}
					} else if (flag.equals("post")) {// �Ϳ�
						if (ele.getAttribute("done").equals("1")
								&& ele.getAttribute("direction").equals(direc)
								&& Integer.parseInt(ele
										.getElementsByTagName("destination")
										.item(0).getTextContent()) == floor) {// ״̬Ϊ1
							ele.setAttribute("done", "2");
						}
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			// ���ñ�������
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			StreamResult result = new StreamResult(new FileOutputStream(
					App_path + "\\" + data_file));

			transformer.transform(domSource, result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("�����ļ�����");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("���ļ�ʧ��");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO������");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
