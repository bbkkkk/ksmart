package com.platform.tool.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ksmart.coder.model.FieldBean;


public class DbServiceImpl implements DbService {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	String sqlstr = null;

	/**根据表名获取所有的列信息
	 * @param tableName
	 * @return
	 */
	public List<FieldBean> getAllColums(String tableName){

		ArrayList<FieldBean> returnList = new ArrayList<FieldBean>();
		DbConn dbConn = new DbConn();

		try {
			this.conn = dbConn.getConnection();
			this.sqlstr = "select * from " + tableName;
			this.st = conn.createStatement();
			this.rs = this.st.executeQuery(sqlstr);
			ResultSetMetaData resultSetMetaData = rs.getMetaData();

			for(int i=1;i<=resultSetMetaData.getColumnCount();i++){
				String columName = resultSetMetaData.getColumnName(i);
				String proName = convertField(columName);
				String dataType = getTypeName(resultSetMetaData.getColumnType(i));
				FieldBean fieldBean = new FieldBean();
				fieldBean.setFieldName(columName.toLowerCase());
				fieldBean.setProName(proName);
				fieldBean.setProType(dataType);

				returnList.add(fieldBean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			dbConn.closeALL(conn, st, rs, pst);
		}
		return returnList;

	}

	/**
	 * 把数据库中的字段转换为变量类型
	 * 如（user_id ----> userId）
	 * @param field
	 * @return
	 */
	public String convertField(String field) {
		//分隔符
		char separator = '_';
		//转化为小写
		String variable = field.toLowerCase();

		if (variable.indexOf(separator)>-1) {
			char[] varArray = variable.toCharArray();
			for(int i=0;i<varArray.length;i++){
				if (varArray[i]==separator&&i<varArray.length-1) {
					varArray[i+1]=Character.toUpperCase(varArray[i+1]);
				}
			}
			variable = new String(varArray).replaceAll("_", "");
		}

		return variable;

	}

	/**获取字符串型的类型名
	 * @param type
	 * @return
	 */
	private String getTypeName(int type){

		String typeName = String.class.getSimpleName();

		switch (type) {
			case Types.VARCHAR:
			case Types.BLOB:
				break;
			case Types.INTEGER:
			case Types.SMALLINT:
			case Types.NUMERIC:
				typeName=Integer.class.getSimpleName();
				break;
			case Types.DATE:
			case Types.TIMESTAMP:
				typeName=Timestamp.class.getSimpleName();
				break;
			case Types.BOOLEAN:
				typeName=Boolean.class.getSimpleName();
				break;
			case Types.FLOAT:
				typeName=Float.class.getSimpleName();
				break;
			default:
				break;
		}

		return typeName;

	}


}
