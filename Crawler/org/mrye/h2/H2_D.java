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
		if (stmt.executeUpdate(sql) > 0) {// 默认执行结果是1
			close_conn();
			return true;
		} else {
			close_conn();
			return false;
		}

	}

	/**
	 * 删除对应数据库中的数据
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean delete(String sql) throws SQLException, Exception {
		Statement stmt = get_stmt();
		if (stmt.executeUpdate(sql) > 0) {// 默认执行结果是1
			close_conn();
			return true;
		} else {
			close_conn();
			return false;
		}
	}

	/**
	 * 查询sql语句所得的结果
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
		// 判断数据表是否存在
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES where Table_name='DOMAIN'");

		if (rs.next()) {
			return "数据库存在";
		} else {
			// 表不存在，则创建
			System.out.println("数据库不存在..创建中....");
			// 以下为创建数据库的过程

			stmt.executeUpdate("DROP TABLE IF EXISTS `domain`;");
			stmt.executeUpdate("CREATE TABLE `domain` (`id` int(32) NOT NULL AUTO_INCREMENT,`dname` varchar(4096) NOT NULL COMMENT '域名地址dname', `port` varchar(128) DEFAULT NULL COMMENT '域名port',`ip` varchar(128) DEFAULT NULL COMMENT '域名ip',`info` varchar(1000) DEFAULT NULL COMMENT '说明', PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `user`;");
			stmt.executeUpdate("CREATE TABLE `user` (`id` int(32) NOT NULL AUTO_INCREMENT,`uname` varchar(4096) NOT NULL COMMENT '用户名uname', `upwd` varchar(4096) DEFAULT NULL COMMENT '密码upwd',`count` varchar(128) DEFAULT NULL COMMENT '使用次数count',`lasttime` varchar(1000) DEFAULT NULL COMMENT '最后使用时间lasttime', PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `directory`;");
			stmt.executeUpdate("CREATE TABLE `directory` (`id` int(32) NOT NULL AUTO_INCREMENT,`name` varchar(4096) NOT NULL COMMENT '目录名name', `fid` int(32) DEFAULT NULL COMMENT '父目录fid',`did` int(32) DEFAULT NULL COMMENT '所属域名did', PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `queue`;");
			stmt.executeUpdate("CREATE TABLE `queue` (`id` int(32) NOT NULL AUTO_INCREMENT,`url` varchar(4096) NOT NULL COMMENT '网址url', `isvisit` varchar(128) DEFAULT '0' COMMENT '访问状态,0未访问,1访问中,2访问结束',`hash` varchar(128) DEFAULT NULL COMMENT 'Hash值hash', `valid` varchar(128) DEFAULT NULL COMMENT '网站是否有效valid', `parenturl` varchar(4096) DEFAULT NULL COMMENT '上一层URL',`others` int(32) DEFAULT NULL COMMENT '网站是否外链',PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `headers`;");
			stmt.executeUpdate("CREATE TABLE `headers` (`id` int(32) NOT NULL AUTO_INCREMENT,`Content-Type` varchar(4096) NOT NULL COMMENT 'Content-Type', `Connection` varchar(4096) DEFAULT NULL COMMENT 'Connection',`Cookie` varchar(4096) DEFAULT NULL COMMENT 'Cookie', `Server` varchar(4096) DEFAULT NULL COMMENT 'Server主机', PRIMARY KEY (`id`)) ");

			stmt.executeUpdate("DROP TABLE IF EXISTS `status`;");
			stmt.executeUpdate("CREATE TABLE `status` (`id` int(32) NOT NULL AUTO_INCREMENT,`allpages` varchar(4096) DEFAULT NULL COMMENT '所有的页面',`finishedpages` varchar(4096) DEFAULT NULL COMMENT '处理的页面',`flag` int(32) DEFAULT NULL COMMENT '状态', `begintime` varchar(1000) DEFAULT NULL COMMENT '开始时间',`count` varchar(128) DEFAULT NULL COMMENT '线程循环次数',`lasttime` varchar(1000) DEFAULT NULL COMMENT '最后使用时间lasttime', PRIMARY KEY (`id`)) ");

			// 以上为创建数据库的过程
			System.out.println("存在了");
			rs.close();
			close_conn();
			return "数据库存在";
		}

	}

}
