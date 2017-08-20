package ksmart.coder.db;

import ksmart.coder.model.EnumKV;
import ksmart.coder.model.FieldBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
    private static String className = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/ksmart";
    private static String user = "root";
    private static String password = "root";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(className);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void realseConn(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static List<Map<String, Object>> queryAll(String sql, Map<Integer, Object> conditionMap) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            if (conditionMap != null && conditionMap.size() != 0) {

                int paramNum = conditionMap.size();
                for (int i = 1; i <= paramNum; i++) {

                    Object paramValue = conditionMap.get(i);

                    if ("java.lang.Integer".equalsIgnoreCase(paramValue.getClass().getName())) {
                        pstmt.setInt(i, Integer.parseInt(paramValue.toString()));
                    } else if ("java.lang.String".equalsIgnoreCase(paramValue.getClass().getName())) {
                        pstmt.setString(i, paramValue.toString());
                    }
                }
            }

            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNum = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<String, Object>(0);
                for (int i = 1; i <= columnNum; i++) {
                    dataMap.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                resultList.add(dataMap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            realseConn(conn, pstmt, rs);
        }

        return resultList;
    }

    public static List<FieldBean> queryFiledBeanList(String tableSchema, String tableName) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE from information_schema.columns\n" +
                "where table_schema = '" + tableSchema + "' \n" +
                "and table_name = '" + tableName + "' ";
        List<FieldBean> resultList = new ArrayList<FieldBean>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNum = rsmd.getColumnCount();
            String cname;
            String comment;
            String dataType;
            String arr[];
            FieldBean fieldBean;
            while (rs.next()) {
                fieldBean = new FieldBean();
                cname = rs.getObject(1).toString();
                comment = rs.getObject(2).toString();
                dataType = rs.getObject(3).toString();
                fieldBean.setFieldName(cname);
                fieldBean.setProName(cname);
                fieldBean.setProType(dataType);
                if (comment.contains("#")) {
                    arr = comment.split("\\#");
                    if (arr.length > 1) {

//                        EU 编辑但不列表展示
//                        UU 不编辑不列表展示
//                        UL 不编辑不列表展示
//                        ML 枚举编辑并列表
//                        MU枚举编辑不列表
//                        TU TextArea编辑，不列表展示
                        if ("EU".equals(arr[1])) {
                            fieldBean.setInputType("text");
                            fieldBean.setTextName(arr[0]);
                            fieldBean.setIsEdit(1);
                            fieldBean.setShowList(0);
                        } else if ("UU".equals(arr[1])) {
                            fieldBean.setInputType("text");
                            fieldBean.setTextName(arr[0]);
                            fieldBean.setIsEdit(0);
                            fieldBean.setShowList(0);
                        } else if ("UL".equals(arr[1])) {
                            fieldBean.setInputType("text");
                            fieldBean.setTextName(arr[0]);
                            fieldBean.setIsEdit(0);
                            fieldBean.setShowList(1);
                        } else if ("ML".equals(arr[1]) && arr.length > 2) {
                            fieldBean.setInputType("select");
                            fieldBean.setTextName(arr[0]);
                            fieldBean.setIsEdit(1);
                            fieldBean.setShowList(1);
                            fieldBean.setEnums(getEnum(arr[2]));
                        } else if ("MU".equals(arr[1]) && arr.length > 2) {
                            fieldBean.setInputType("select");
                            fieldBean.setTextName(arr[0]);
                            fieldBean.setIsEdit(1);
                            fieldBean.setShowList(0);
                            fieldBean.setEnums(getEnum(arr[2]));
                        } else if ("TU".equals(arr[1])) {
                            fieldBean.setInputType("textarea");
                            fieldBean.setTextName(arr[0]);
                            fieldBean.setIsEdit(0);
                            fieldBean.setShowList(0);
                        } else {
                            fieldBean.setInputType("text");
                            fieldBean.setTextName(comment);
                            fieldBean.setShowList(1);
                            fieldBean.setIsEdit(1);
                            System.err.println("字段备注不符合规则");
                        }
                    } else {
                        System.err.println("字段备注不符合规则");
                    }
                } else {//默认类型
                    fieldBean.setInputType("text");
                    fieldBean.setTextName(comment);
                    fieldBean.setShowList(1);
                    fieldBean.setIsEdit(1);
                }
                resultList.add(fieldBean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            realseConn(conn, pstmt, rs);
        }

        return resultList;
    }

    private static List<EnumKV> getEnum(String enumstr) {
        List<EnumKV> enumKVs = new ArrayList<EnumKV>();
        enumstr = enumstr.replaceAll("，", ",");
        enumstr = enumstr.replaceAll("：", ":");
        String[] arr = enumstr.split(",");
        String[] temp;
        EnumKV ekv;
        for (int i = 0; i < arr.length; i++) {
            temp = arr[i].split(":");
            ekv = new EnumKV();
            ekv.setText(temp[1]);
            ekv.setValue(temp[0]);
            enumKVs.add(ekv);
        }
        return enumKVs;
    }


    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    /**
     * 根据表名获取所有的列信息
     *
     * @param tableName
     * @return
     */
    public static List<FieldBean> getAllColums(String tableName) {

        ArrayList<FieldBean> returnList = new ArrayList<FieldBean>();
        DbConn dbConn = new DbConn();
        Connection conn = getConnection();
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sqlstr = null;
        try {

            conn = dbConn.getConnection();
            sqlstr = "select * from " + tableName;
            st = conn.createStatement();
            rs = st.executeQuery(sqlstr);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
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
        } finally {
            dbConn.closeALL(conn, st, rs, pst);
        }
        return returnList;

    }

    /**
     * 根据表名获取所有的列信息
     *
     * @param tableName
     * @return
     */
    public static List<Map<String, Object>> getAllColumsMapL(String tableName) {

        ArrayList<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        DbConn dbConn = new DbConn();
        Connection conn = getConnection();
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sqlstr = null;
        try {

            conn = getConnection();
            sqlstr = "select * from " + tableName;
            st = conn.createStatement();
            rs = st.executeQuery(sqlstr);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                String columName = resultSetMetaData.getColumnName(i);
                String proName = convertField(columName);
                String dataType = getTypeName(resultSetMetaData.getColumnType(i));
                Map<String, Object> fieldBean = new HashMap<String, Object>();
                fieldBean.put("fieldName", columName.toLowerCase());
                fieldBean.put("proName", proName);
                fieldBean.put("dataType", dataType);
                returnList.add(fieldBean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeALL(conn, st, rs, pst);
        }
        return returnList;

    }

    /**
     * 把数据库中的字段转换为变量类型
     * 如（user_id ----> userId）
     *
     * @param field
     * @return
     */
    public static String convertField(String field) {
        //分隔符
        char separator = '_';
        //转化为小写
        String variable = field.toLowerCase();

        if (variable.indexOf(separator) > -1) {
            char[] varArray = variable.toCharArray();
            for (int i = 0; i < varArray.length; i++) {
                if (varArray[i] == separator && i < varArray.length - 1) {
                    varArray[i + 1] = Character.toUpperCase(varArray[i + 1]);
                }
            }
            variable = new String(varArray).replaceAll("_", "");
        }

        return variable;

    }

    /**
     * 获取字符串型的类型名
     *
     * @param type
     * @return
     */
    private static String getTypeName(int type) {

        String typeName = String.class.getSimpleName();

        switch (type) {
            case Types.VARCHAR:
            case Types.BLOB:
                break;
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.NUMERIC:
                typeName = Integer.class.getSimpleName();
                break;
            case Types.DATE:
            case Types.TIMESTAMP:
                typeName = Timestamp.class.getSimpleName();
                break;
            case Types.BOOLEAN:
                typeName = Boolean.class.getSimpleName();
                break;
            case Types.FLOAT:
                typeName = Float.class.getSimpleName();
                break;
            default:
                break;
        }

        return typeName;

    }

    public static void test1() {
        String sql = "select * from smt_template where id=? and name=?";
        Map<Integer, Object> conditionMap = new HashMap<Integer, Object>();
        conditionMap.put(1, 5);
        conditionMap.put(2, "e");
        List<Map<String, Object>> resultList = DBUtil.queryAll(sql, conditionMap);
        System.out.println(resultList);
    }

    public static void test2() {
        String sql = "select * from smt_template where id=? and name=?";
        Map<Integer, Object> conditionMap = new HashMap<Integer, Object>();
        conditionMap.put(1, 1);
        conditionMap.put(2, "admin");
        List<Map<String, Object>> resultList = DBUtil.queryAll(sql, conditionMap);
        System.out.println(resultList);
    }

    public static void test3() {
        String sql = "select * from smt_template";
        List<Map<String, Object>> resultList = DBUtil.queryAll(sql, null);
        System.out.println(resultList);
    }
}