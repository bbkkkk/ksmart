package ksmart.coder.tools;

import java.io.*;
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
    public static void generateFile(String templateName, Map<String, Object> root, String outFilePath, String fileName) {
        outFilePath = outFilePath.replaceAll("\\.", "\\\\");
        FileWriter out = null;
        try {
            createDirectory(outFilePath);
            // ͨ��һ���ļ���������Ϳ���д����Ӧ���ļ��У��˴��õ��Ǿ���·��

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
     * �����ļ�
     *
     * @param 'name:ģ����
     * @param root������ԭ��
     * @param 'outFile�����·��(ȫ·����)
     */
    public static void generateFile(String templateName, Map<String, Object> root, String outFilePath) {

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

    /**
     * public static void generateFile(String templateName, Map<String, Object> root, String outFilePath,String fileName) {
     * outFilePath=outFilePath.replaceAll("\\.","\\\\");
     */
    public static boolean buildHtml(String templateName, Map root, String outFilePath, String fileName) {
        try {
            outFilePath = outFilePath.replaceAll("\\.", "\\\\");
            Template template = getTemplate(templateName);
            //���������ļ�Ŀ¼
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
