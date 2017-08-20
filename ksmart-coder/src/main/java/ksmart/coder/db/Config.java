package ksmart.coder.db;

import ksmart.coder.App;

import java.io.*;
import java.util.Properties;

/**
 * Created by Administrator on 2017/8/20.
 */
public class Config {

    public static String url;
    public static String user;
    public static String password;
    public static String sevicePath;//service 模块的绝对路径
    public static String webPath;//web 模块的绝对路径
    public static String tableSchema;
    public static String moudelName;

    static {
        Properties prop = new Properties();
        try {
//            InputStream in = new BufferedInputStream(new FileInputStream("/config.properties"));
            InputStream in = new BufferedInputStream(App.class.getResourceAsStream("/config.properties")) ;
            prop.load(in);     ///加载属性列表
            url = prop.getProperty("jdbc.url");
            user = prop.getProperty("jdbc.username");
            password = prop.getProperty("jdbc.password");
            sevicePath = prop.getProperty("sevicePath");

            webPath = prop.getProperty("webPath");
            tableSchema = prop.getProperty("tableSchema");
            moudelName = prop.getProperty("moudelName");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
