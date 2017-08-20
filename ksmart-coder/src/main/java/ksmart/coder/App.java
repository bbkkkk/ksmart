package ksmart.coder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ksmart.coder.db.Config;
import ksmart.coder.model.FieldBean;
import ksmart.coder.db.DBUtil;
import ksmart.coder.tools.FreeMakerUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
//    static String sevicePath = "E:\\test\\service";//service 模块的绝对路径
//    static String webPath = "E:\\test\\web";//web 模块的绝对路径
//    static String tableSchema = "ksmart";
//    static String moudelName = "demo";

    public static void main(String[] args) {
        JsonParser parser=new JsonParser();
        try {
//            System.out.println(App.class.getResourceAsStream("/data.json"));
//            JsonArray jsonArray=(JsonArray ) parser.parse(new FileReader("data.json"));

            JsonArray jsonArray=(JsonArray ) parser.parse(new InputStreamReader(App.class.getResourceAsStream("/data.json")));

            if(jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JsonObject  job = (JsonObject) jsonArray.get(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    System.out.print("tableName="+job.get("tableName")) ;
                    System.out.print(",tableTextName="+job.get("tableTextName")) ;
                    System.out.println(",classPre="+job.get("classPre")) ;
                    gentOneTable(job.get("tableName").toString(),job.get("tableTextName").toString(),job.get("classPre").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//
    }


    public static void gentOneTable(String tableName,String tableTextName,String classPre) {

         tableName =tableName.replaceAll("\"","");
         tableTextName = tableTextName.replaceAll("\"","");
         classPre = classPre.replaceAll("\"","");

        String packageName = "com.ksmart." + Config.moudelName;
        String objectName = classPre.toLowerCase();
        List<FieldBean> columns = DBUtil.queryFiledBeanList(Config.tableSchema, tableName);
        Map<String, Object> templateData = new HashMap<String, Object>();
        templateData.put("tableName", tableName);
        templateData.put("packagePath", packageName);
        templateData.put("classPre", classPre);
        templateData.put("classPreLow", classPre.toLowerCase());
        templateData.put("tableTextName", tableTextName);
        templateData.put("columns", columns);
        templateData.put("packageName", packageName);
        templateData.put("moudelName", Config.moudelName);
        templateData.put("objectName", objectName);

        String ctlfilePath = Config.webPath + "." + packageName + ".ctl" + ".";
        FreeMakerUtil.generateFile("templateCtl.ftl", templateData, ctlfilePath, classPre + "Ctl.java");

        String daofilePath = Config.sevicePath + "." + packageName + ".dao" + ".";
        FreeMakerUtil.generateFile("templateDao.ftl", templateData, daofilePath, classPre + "Dao.java");

        String servicefilePath = Config.sevicePath + "." + packageName + ".service" + ".";
        FreeMakerUtil.generateFile("templateService.ftl", templateData, servicefilePath, classPre + "Service.java");

        String serviceImplfilePath = Config.sevicePath + "." + packageName + ".service" + ".impl.";
        FreeMakerUtil.generateFile("templateServiceImpl.ftl", templateData, serviceImplfilePath, classPre + "ServiceImpl.java");

        String mybatisfilePath = Config.sevicePath + "." + packageName + ".mybatis" + ".";
        FreeMakerUtil.generateFile("templateMybatis.ftl", templateData, mybatisfilePath, classPre + "Dao.xml");

        String htmlfilePath = Config.webPath + ".html.ajax.content.";
        FreeMakerUtil.buildHtml("templateHtml.ftl", templateData, htmlfilePath, Config.moudelName + "-" + objectName + "-list.html");


    }
}
