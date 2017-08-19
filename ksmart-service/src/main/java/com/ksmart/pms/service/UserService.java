package com.ksmart.pms.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by KF on 2017/8/16.
 */
public interface UserService {

    int insert(Map<String, Object> params);

    int update(Map<String, Object> params);

    public List<Map<String, Object>> queryListById(long id);

    public int delById(long id);

    public int lgDelById(long id);

    JSONObject page(String page, String rows, Map<String, Object> params, String sortname, String sortorder);
}
