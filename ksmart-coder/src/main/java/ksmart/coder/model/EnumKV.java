package ksmart.coder.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/20.
 */
public class EnumKV implements Serializable {

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String text;
    private String value;

}
