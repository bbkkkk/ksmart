package com.ksmart.base.utils;

/**
 * 包装自己的StringUtils工具类
 * 
 * @author winter
 * @date 2011-4-10 下午06:11:57
 * @company hbc.com
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * 截取hql中逗号
     * 
     * @param hql
     * @return
     */
    public static String hqlSubString(StringBuilder hql) {

        String html = hql.toString();

        if (html.startsWith(",")) {

            html = html.substring(1);
        }
        return html;
    }

    /**
     * 支持传入按指定分隔符 进行截取
     * 
     * @param hql
     * @param splitSeparator
     * @return
     */
    public static String hqlSubString(StringBuilder hql, String splitSeparator) throws Exception {

        if (StringUtils.isBlank(splitSeparator)) {

            throw new Exception("分隔符为空，截取失败");
        }

        String html = hql.toString().trim();

        if (html.startsWith(splitSeparator)) {

            html = html.substring(splitSeparator.length());
        }
        return html;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     */

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;

    }

}
