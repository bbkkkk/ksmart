package ksmart.coder.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMakerUtil {

    /**
     * ��ȡģ���ļ�
     *
     * @param name
     * @return
     */
    public static Template getTemplate(String name) {
        try {
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(FreeMakerUtil.class.getClass(), "/ftl");
            Template template = cfg.getTemplate(name);
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ����̨���
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
     * �����ļ�
     *
     * @param 'name:ģ����
     * @param root������ԭ��
     * @param 'outFile�����·��(ȫ·����)
     */
    public static void generateFile(String templateName, Map<String, Object> root, String outFilePath) {
        outFilePath=outFilePath.replaceAll("\\.",File.separator);
        FileWriter out = null;
        try {

            // ͨ��һ���ļ���������Ϳ���д����Ӧ���ļ��У��˴��õ��Ǿ���·��
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
     * �����ļ�
     *
     * @param 'name:ģ����
     * @param root������ԭ��
     * @param 'outFile�����·��(ȫ·����)
     */
    public static void generateFile(String templateName, Map<String, Object> root,String packageName, String outFilePath) {

        FileWriter out = null;
        try {

            // ͨ��һ���ļ���������Ϳ���д����Ӧ���ļ��У��˴��õ��Ǿ���·��
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
     * �½�Ŀ¼.
     *
     * @param path �ļ�·��
     * @throws Exception
     */
    public static void createDirectory(String path) throws Exception {
        if ("".equals(path)) {
            return;
        }
        try {
            // ����ļ�����
            File f = new File(path);
            if (!f.exists()) {
                // ���·��������,�򴴽�
                f.mkdirs();
            }
        } catch (Exception e) {
           System.err.println(e.getMessage());
            throw e;
        }
    }
}
