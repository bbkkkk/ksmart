package ksmart.coder.model;

import java.util.List;

/**
 * 列对象
 *
 * @author Xiaobo
 */
public class FieldBean {

    private String fieldName; //数据库原字段名
    private String proName;   //变量名
    private String textName;//中文名称
    private String proType;   //变量类型
    private String inputType;
    private boolean showList;
    private boolean isEdit;
    private List<EnumKV> enums;//只有枚举类型有

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public boolean isShowList() {
        return showList;
    }

    public void setShowList(boolean showList) {
        this.showList = showList;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public List<EnumKV> getEnums() {
        return enums;
    }

    public void setEnums(List<EnumKV> enums) {
        this.enums = enums;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
