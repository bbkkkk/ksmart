package com.platform.tool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConn {

	//定义一个连接对象
	private Connection conn= null;
	//定义连接数据库的url资源
	private String url = "jdbc:oracle:thin:@10.2.6.80:1521:orcl";
	//定义连接数据库的用户名和密码
	private String userName = "xiaobo";
	private String passWord = "xiaobo";

	//加载数据库连接驱动
	public Connection getConnection(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, userName, passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	//关闭数据库
	public void closeALL(Connection conn, Statement st, ResultSet rs, PreparedStatement pst) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
			if (pst != null)
				pst.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
