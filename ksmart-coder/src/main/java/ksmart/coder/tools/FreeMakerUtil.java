package ksmart.coder.tools;

import java.io.*;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMakerUtil {

    /**
     * 获取模板文件
     *
     * @param name
     * @return
     */
    public static Template getTemplate(String name) {
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(FreeMakerUtil.class.getClass(), "/ftl");
            Template template = cfg.getTemplate(name);
            template.setEncoding("UTF-8");
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 控制台输出
     *
     * @param 'name
     * @param root
     */
    public void print(String templateName, Map<String, Object> root) {

        try {
            Template template = this.getTemplate(templateName);
            template.process(root, new PrintWriter(System.out));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成文件
     *
     * @param 'name:模板名
     * @param root：数据原型
     * @param 'outFile：输出路径(全路径名)
     */
    public static void generateFile(String templateName, Map<String, Object> root, String outFilePath, String fileName) {
        outFilePath = outFilePath.replaceAll("\\.", "\\\\");
        FileWriter out = null;
        try {
            createDirectory(outFilePath);
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径

            out = new FileWriter(new File(outFilePath + fileName));
            Template temp = getTemplate(templateName);
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成文件
     *
     * @param 'name:模板名
     * @param root：数据原型
     * @param 'outFile：输出路径(全路径名)
     */
    public static void generateFile(String templateName, Map<String, Object> root, String outFilePath) {

        FileWriter out = null;
        try {

            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            out = new FileWriter(new File(outFilePath));
            Template temp = getTemplate(templateName);
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 新建目录.
     *
     * @param path 文件路径
     * @throws Exception
     */
    public static void createDirectory(String path) throws Exception {
        if ("".equals(path)) {
            return;
        }
        try {
            // 获得文件对象
            File f = new File(path);
            if (!f.exists()) {
                // 如果路径不存在,则创建
                f.mkdirs();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * public static void generateFile(String templateName, Map<String, Object> root, String outFilePath,String fileName) {
     * outFilePath=outFilePath.replaceAll("\\.","\\\\");
     */
    public static boolean buildHtml(String templateName, Map root, String outFilePath, String fileName) {
        try {
            outFilePath = outFilePath.replaceAll("\\.", "\\\\");
            Template template = getTemplate(templateName);
            //创建生成文件目录
            createDirectory(outFilePath);

            File htmlFile = new File(outFilePath + fileName);
//            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8"));
            template.process(root, out);
            out.flush();
            return true;
        } catch (TemplateException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
