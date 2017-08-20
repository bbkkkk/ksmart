package com.ksmart.demo.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* Created by KF on 2017/8/16.
*/
public interface TemplateService {

int insert(Map<String, Object> params);

int update(Map<String, Object> params);

public List<Map<String, Object>> queryListById(long id);

public int delById(long id);

public int lgDelById(Map<String, Object> params);

JSONObject page(String page, String rows, Map<String, Object> params, String sortname, String sortorder);
}
