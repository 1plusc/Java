package org.mrye.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2_D {
	private static String App_path = System.getProperty("user.dir");
	private static String downloadpath = App_path + "\\dbc\\crawl";
	// D:\Develope\java_code\OnePlusC\dbc\crawl
	private String url = "jdbc:h2:" + downloadpath;
	private String user = "yjs";
	private String pwds = "#8.d4fli_3";
	private Connection conn;

	public H2_D() throws SQLException, Exception {
		super();
		create_db();
	}

	private Connection get_conn() throws Exception {
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection(url, user, pwds);
		return conn;
	}

	private Statement get_stmt() throws SQLException, Exception {

		return get_conn().createStatement();
	}

	private void close_conn() throws SQLException {
		conn.close();
	}

	public boolean insert_one(String sql) throws Exception, Exception {
		Statement stmt = get_stmt();
		if (stmt.executeUpdate(sql) > 0) {// Ĭ��ִ�н����1
			close_conn();
			return true;
		} else {
			close_conn();
			return false;
		}

	}

	/**
	 * ɾ����Ӧ���ݿ��е�����
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean delete(String sql) throws SQLException, Exception {
		Statement stmt = get_stmt();
		if (stmt.executeUpdate(sql) > 0) {// Ĭ��ִ�н����1
			close_conn();
			return true;
		} else {
			close_conn();
			return false;
		}
	}

	/**
	 * ��ѯsql������õĽ��
	 * 
	 * @param sql
	 * @return ResultSet
	 */
	public ResultSet select_all(String sql) {
		try {
			return get_stmt().executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean isexist(String db, String str_Exception) throws Exception {
		Class.forName("org.h2.Driver");
		Statement stmt = get_stmt();
		String sql = "select 1 from " + db + " " + str_Exception + " limit 1;";
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			// System.out.println(rs.getInt("ID") + "," + rs.getString("NAME"));
			close_conn();
			return true;
		}
		rs.close();
		close_conn();
		return false;

	}

	/**
	 * @throws Exception
	 * @throws SQLException
	 * 
	 */
	private String create_db() throws SQLException, Exception {
		// TODO Auto-generated method stub
		Statement stmt = get_stmt();
		// �ж����ݱ��Ƿ����
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES where Table_name='DOMAIN'");

		if (rs.next()) {
			return "���ݿ����";
		} else {
			// �����ڣ��򴴽�
			System.out.println("���ݿⲻ����..������....");
			// ����Ϊ�������ݿ�Ĺ���

			stmt.executeUpdate("DROP TABLE IF EXISTS `domain`;");
			stmt.executeUpdate("CREATE TABLE `domain` (`id` int(32) NOT NULL AUTO_INCREMENT,`dname` varchar(4096) NOT NULL COMMENT '������ַdname', `port` varchar(128) DEFAULT NULL COMMENT '����port',`ip` varchar(128) DEFAULT NULL COMMENT '����ip',`info` varchar(1000) DEFAULT NULL COMMENT '˵��', PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `user`;");
			stmt.executeUpdate("CREATE TABLE `user` (`id` int(32) NOT NULL AUTO_INCREMENT,`uname` varchar(4096) NOT NULL COMMENT '�û���uname', `upwd` varchar(4096) DEFAULT NULL COMMENT '����upwd',`count` varchar(128) DEFAULT NULL COMMENT 'ʹ�ô���count',`lasttime` varchar(1000) DEFAULT NULL COMMENT '���ʹ��ʱ��lasttime', PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `directory`;");
			stmt.executeUpdate("CREATE TABLE `directory` (`id` int(32) NOT NULL AUTO_INCREMENT,`name` varchar(4096) NOT NULL COMMENT 'Ŀ¼��name', `fid` int(32) DEFAULT NULL COMMENT '��Ŀ¼fid',`did` int(32) DEFAULT NULL COMMENT '��������did', PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `queue`;");
			stmt.executeUpdate("CREATE TABLE `queue` (`id` int(32) NOT NULL AUTO_INCREMENT,`url` varchar(4096) NOT NULL COMMENT '��ַurl', `isvisit` varchar(128) DEFAULT '0' COMMENT '����״̬,0δ����,1������,2���ʽ���',`hash` varchar(128) DEFAULT NULL COMMENT 'Hashֵhash', `valid` varchar(128) DEFAULT NULL COMMENT '��վ�Ƿ���Чvalid', `parenturl` varchar(4096) DEFAULT NULL COMMENT '��һ��URL',`others` int(32) DEFAULT NULL COMMENT '��վ�Ƿ�����',PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `headers`;");
			stmt.executeUpdate("CREATE TABLE `headers` (`id` int(32) NOT NULL AUTO_INCREMENT,`Content-Type` varchar(4096) NOT NULL COMMENT 'Content-Type', `Connection` varchar(4096) DEFAULT NULL COMMENT 'Connection',`Cookie` varchar(4096) DEFAULT NULL COMMENT 'Cookie', `Server` varchar(4096) DEFAULT NULL COMMENT 'Server����', PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `status`;");
			stmt.executeUpdate("CREATE TABLE `status` (`id` int(32) NOT NULL AUTO_INCREMENT,`allpages` varchar(4096) DEFAULT NULL COMMENT '���е�ҳ��',`finishedpages` varchar(4096) DEFAULT NULL COMMENT '�����ҳ��',`flag` int(32) DEFAULT NULL COMMENT '״̬', `begintime` varchar(1000) DEFAULT NULL COMMENT '��ʼʱ��',`count` varchar(128) DEFAULT NULL COMMENT '�߳�ѭ������',`lasttime` varchar(1000) DEFAULT NULL COMMENT '���ʹ��ʱ��lasttime', PRIMARY KEY (`id`)) ");

			// ����Ϊ�������ݿ�Ĺ���
			System.out.println("������");
			rs.close();
			close_conn();
			return "���ݿ����";
		}

	}

}
