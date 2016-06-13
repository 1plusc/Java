package org.mrye.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mrye.function.Url_Md5;
import org.mrye.h2.H2_D;
import org.mrye.resource.MD5;

public class Get_Url {
	private String host = "";
	private String linkHref = "";
	private int flag = 0;// �Ƿ�ͬվ��־
	private int hostid = -1;
	private int directoryid = -1;

	public void reslove_Content(String url, String content) {
		host = get_from_url(url);
		Document doc = Jsoup.parse(content);
		// ��ȡa��ǩ����------Begin
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
			linkHref = link.attr("href");
			if (linkHref.startsWith("http://")
					|| linkHref.startsWith("https://")) {
			} else {
				if(linkHref.startsWith("/")||linkHref.startsWith("#")){
					linkHref = host + linkHref;
					System.out.println("��Ҫ�����url==>>" + linkHref);
					Make_url(linkHref, 0, url);
				}else{
					//���ﴦ��a���Ӳ���/��ͷ�����·��
				}
			}
			
		}
		// ��ȡa��ǩ����------End
		
		//��url��ȡ״̬��Ϊ2��ȡ����
		H2_D h2d = null;
		ResultSet rs = null;
		try{
			h2d=new H2_D();
			rs=h2d.select_all("select id from queue where url='" + url
					+ "'");
			if (rs.next()) {
				// ����queue�е�����
				if (h2d.insert_one("update queue set isvisit = '2' WHERE id = '"
						+ rs.getInt("id") + "' ")) {
					System.out.println("���³ɹ�...");
				} else {
					System.out.println("����ʧ��...");
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
	}

	/**
	 * 
	 * @param linkHref����
	 * @param i
	 *            ״ֵ̬
	 */
	private void Make_url(String linkHref, int i, String parenturl) {
		// TODO Auto-generated method stub
		flag = compare_url(linkHref, parenturl);// �Ƿ�ͬվ��־
		Url_Md5 um5 = new Url_Md5();
		H2_D h2d = null;
		ResultSet rs = null;
		MD5 md5 = new MD5();
		String smd5 = "";
		// smd5=md5.getMD5(um5.dispatch_url(linkHref2));//�ʺϿ�����վ
		smd5 = md5.getMD5(linkHref);// �������Ƶ�URLҲ������ȡ

		// ��ʼƥ���ڴ����ݿ����Ƿ����ظ�URL��������ӣ�����ȥ��
		try {
			h2d = new H2_D();
			rs = h2d.select_all("select id from queue where hash='" + smd5
					+ "'");
			if (!rs.next()) {
				if (h2d.insert_one("INSERT INTO queue (url,isvisit,hash,parenturl,others) VALUES('"
						+ linkHref
						+ "','"
						+ i
						+ "','"
						+ smd5
						+ "','"
						+ parenturl
						+ "','"
						+ flag + "');")) {
					System.out.println("����queue�ɹ�");
				} else {
					System.out.println("����queueʧ��");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ��ʼ������ַ���ڵ�Ŀ¼�ṹ
		if (flag == 1) {// ����ͨ��վ��url
			get_domain_directory(linkHref);
		}
	}

	/**
	 * �������ַ��������Ŀ¼�ṹ
	 * 
	 * @param linkHref
	 */
	private void get_domain_directory(String linkHref) {
		// TODO Auto-generated method stub
		H2_D h2d = null;
		ResultSet rs = null;
		String hostname = "";
		
		String path = "";
		String[] directory = null;
		hostname = get_from_url(linkHref);

		// ��ʼ��ȡ����ַ����������id
		try {
			h2d = new H2_D();
			rs = h2d.select_all("select id from domain where dname='"
					+ hostname + "'");
			if (rs.next()) {
				hostid = rs.getInt("id");
			} else {
				if (h2d.insert_one("INSERT INTO domain (dname) VALUES('"
						+ hostname + "');")) {
					System.out.println("����domain�ɹ�");
					get_domain_directory(linkHref);
					
				} else {
					System.out.println("����domainʧ��");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ��ʼ����Ŀ¼�ṹ
		path = get_directory(linkHref);
		if (!path.equals("")) {
			path = path.substring(1);
			directory = path.split("/");
			for (int i = 0; i < directory.length; i++) {
				if (i == 0 && directory[0].trim().equals("")
						&& directory.length == 1) {
					try {
						h2d = new H2_D();
						rs = h2d.select_all("select id from directory where name='/' and did='"
								+ hostid + "' ");
						if (!rs.next()) {
							if (h2d.insert_one("INSERT INTO directory (name,fid,did) VALUES('/','-1','"
									+ hostid + "');")) {
								System.out.println("����directory��Ŀ¼�ɹ�");
							} else {
								System.out.println("����directory��Ŀ¼ʧ��");
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					if (i == 0) {
						try {
							h2d = new H2_D();
							rs = h2d.select_all("select id from directory where name='/' and did='"
									+ hostid + "' ");
							if (rs.next()) {
								directoryid = rs.getInt("id");
							} else {
								if (h2d.insert_one("INSERT INTO directory (name,fid,did) VALUES('/','-1','"
										+ hostid + "');")) {
									System.out.println("����directory�ɹ�");
									rs = h2d.select_all("select id from directory where name='/' and did='"
											+ hostid + "' ");
									if (rs.next()) {
										directoryid = rs.getInt("id");
									}
								}
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						h2d = new H2_D();
						rs = h2d.select_all("select id from directory where name='"
								+ directory[i] + "' and did='" + hostid + "' and fid='"+directoryid+"' ");
						if (rs.next()) {
							directoryid = rs.getInt("id");
						} else {
							if (h2d.insert_one("INSERT INTO directory (name,fid,did) VALUES('"+directory[i]+"','"+directoryid+"','"
									+ hostid + "');")) {
								System.out.println("����directory�ɹ�");
								rs = h2d.select_all("select id from directory where name='"+directory[i]+"' and did='"
										+ hostid + "' ");
								if (rs.next()) {
									directoryid = rs.getInt("id");
								}
							} else {
								System.out.println("����directoryʧ��");
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				h2d = new H2_D();
				rs = h2d.select_all("select id from directory where name='/'and did='"
						+ hostid + "'");
				if (!rs.next()) {
					if (h2d.insert_one("INSERT INTO directory (name,fid,did) VALUES('/','-1','"
							+ hostid + "');")) {
						System.out.println("����directory��Ŀ¼�ɹ�");
					} else {
						System.out.println("����directory��Ŀ¼ʧ��");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// ����Ŀ¼����

	}

	/**
	 * ��ȡĿ¼����
	 * 
	 * @param linkHref2
	 * @return
	 */
	private String get_directory(String link) {
		// TODO Auto-generated method stub
		try {
			URL hp = new URL(link);
			return hp.getPath();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	/**
	 * �Ƚ�������ַ�Ƿ�Ϊ��ͬ��վ�µ�����
	 * 
	 * @param linkHref
	 * @param parenturl
	 * @return
	 */
	private int compare_url(String linkHref, String parenturl) {
		// TODO Auto-generated method stub
		String u1 = "";
		String u2 = "";
		try {
			URL hp = new URL(linkHref);
			URL hp2 = new URL(parenturl);
			u1 = hp.getProtocol() + "://" + hp.getAuthority();
			u2 = hp2.getProtocol() + "://" + hp2.getAuthority();

			if (u1.equals(u2)) {
				return 1;
			} else {
				return 0;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * ��ȡURL��Ϣ����Э�飬�����Ͷ˿�
	 * 
	 * @param url
	 * @return
	 */
	private String get_from_url(String url) {
		// TODO Auto-generated method stub
		try {
			URL hp = new URL(url);
			return hp.getProtocol() + "://" + hp.getAuthority();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-1";

	}

	/**
	 * ��Խ����
	 * 
	 * @param url
	 * @param i
	 */
	public void _Call_(String url, int i) {
		Make_url(url, i, url);
	}
}
