package ksmart.coder;

import ksmart.coder.model.FieldBean;
import ksmart.coder.db.DBUtil;
import ksmart.coder.tools.FreeMakerUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App2 {
    public static void main(String[] args) {
        String sevicePath = "E:\\test\\service";//service 模块的绝对路径
        String webPath = "E:\\test\\web";//web 模块的绝对路径
        String tableSchema = "ksmart";
        String tableName = "smt_template";
        String tableTextName = "模板";
        String moudelName = "demo";
        String classPre = "Template";

        String packageName = "com.ksmart."+moudelName;
        String objectName = classPre.toLowerCase();
        List<FieldBean> columns = DBUtil.queryFiledBeanList(tableSchema, tableName);
        Map<String, Object> templateData = new HashMap<String, Object>();
        templateData.put("tableName", tableName);
        templateData.put("packagePath", packageName);
        templateData.put("classPre", classPre);
        templateData.put("classPreLow", classPre.toLowerCase());
        templateData.put("tableTextName", tableTextName);
        templateData.put("columns", columns);
        templateData.put("packageName", packageName);
        templateData.put("moudelName", moudelName);
        templateData.put("objectName", objectName);

        String ctlfilePath= webPath + "." + packageName +".ctl"+ ".";
        FreeMakerUtil.generateFile("templateCtl.ftl", templateData,ctlfilePath,   classPre +"Ctl.java");

        String daofilePath= sevicePath + "." + packageName +".dao"+ ".";
        FreeMakerUtil.generateFile("templateDao.ftl", templateData,daofilePath,   classPre +"Dao.java");

        String servicefilePath= sevicePath + "." + packageName +".service"+ ".";
        FreeMakerUtil.generateFile("templateService.ftl", templateData,servicefilePath,   classPre +"Service.java");

        String serviceImplfilePath= sevicePath + "." + packageName +".service"+ ".impl.";
        FreeMakerUtil.generateFile("templateServiceImpl.ftl", templateData,serviceImplfilePath,   classPre +"ServiceImpl.java");

        String mybatisfilePath= sevicePath + "." + packageName +".mybatis"+ ".";
        FreeMakerUtil.generateFile("templateMybatis.ftl", templateData,mybatisfilePath,   classPre +"Dao.xml");

        String htmlfilePath= webPath + ".html.ajax.content.";
        FreeMakerUtil.buildHtml("templateHtml.ftl", templateData,htmlfilePath,  moudelName+"-"+ objectName +"-list.html");


    }
}
