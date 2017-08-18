package com.ksmart.pms.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by KF on 2017/8/16.
 */
public interface UserService {

    int insert(Map<String, Object> params);

    int update(Map<String, Object> params);

    JSONObject page(String page, String rows, Map<String, Object> params, String sortname, String sortorder);
}
