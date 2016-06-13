/**
 * 
 */
package org.mrye.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mrye.h2.H2_D;
import org.mrye.web.Ima;

/**
 * @author Alpha
 * 
 */
public class My_Crawing implements Runnable {
	private String url;
	private int allpages_nu = 0;
	private int finished_nu = 0;
	private int flag = 1;
	private int count = 1;
	private int result = 0;

	public My_Crawing(String url) {
		super();
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		getstart();
		// 循环检测队列是否爬取结束
		check_queue();
	}

	private void check_queue() {
		// TODO Auto-generated method stub
		try {
			H2_D h2d = new H2_D();
			ResultSet rs = null;
			ResultSet rs2 = null;
			rs = h2d.select_all("select url from queue where isvisit='0' and others='1' ");
			while (rs.next()) {
				Ima ima = new Ima();
				ima.get_Content_From_WebPage(rs.getString("url"), -1);
				rs2 = h2d.select_all("SELECT count(*) FROM QUEUE ");
				if (rs2.next()) {
					allpages_nu = rs2.getInt(1);
				}
				rs2 = h2d
						.select_all("SELECT count(*) FROM QUEUE where isvisit='2' ");
				if (rs2.next()) {
					finished_nu = rs2.getInt(1);
				}
				h2d.insert_one("update status set allpages = '" + allpages_nu
						+ "',count='" + count + "',finishedpages='"
						+ finished_nu + "' WHERE id = '" + result + "' ");
			}
			rs = h2d.select_all("select url from queue where isvisit='0' and others='1' ");
			if (rs.next()) {
				count++;// 循环检测次数增加
				check_queue();
			} else {
				flag = 0;// 设置已经查询结束
				h2d.insert_one("update status set flag = '0',lasttime='"
						+ System.currentTimeMillis() + "' WHERE id = '"
						+ result + "' ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getstart() {
		// TODO Auto-generated method stub
		Ima ima = new Ima();
		try {
			H2_D h2d = new H2_D();
			ResultSet rs = null;
			long time = System.currentTimeMillis();
			h2d.insert_one("INSERT INTO status (flag,begintime,count,lasttime) VALUES('"
					+ flag
					+ "','"
					+ time
					+ "','"
					+ count
					+ "','"
					+ time
					+ "');");
			rs = h2d.select_all("select id from status where flag='" + flag
					+ "' and begintime='" + time + "' and count='" + count
					+ "' ");
			if (rs.next()) {
				result = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ima.get_Content_From_WebPage(url, -1);
	}

}
