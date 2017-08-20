package com.ksmart.pms.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ksmart.base.utils.StringUtils;
import com.ksmart.pms.dao.UserDao;
import com.ksmart.pms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by KF on 2017/8/16.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;
    @Override
    public int insert(Map<String, Object> params) {
        return userDao.insert(params);

    }

    @Override
    public int update(Map<String, Object> params){
        return userDao.update(params);
    }

    @Override
    public List<Map<String, Object>> queryListById(long id) {
        return userDao.queryListById(id);
    }

    @Override
    public int delById(long id) {
        return userDao.delById(id);
    }

    @Override
    public int lgDelById(Map<String, Object> params) {
        return userDao.lgDelById( params);
    }

    @Override
    public JSONObject page(String page, String rows, Map<String, Object> params, String sortname, String sortorder) {
        log.info("分页查询条件：page,rows,search_text,sortname,sortorder");
        log.info("分页查询参数：" +params);
        JSONObject json = new JSONObject();
        // 取得每页显示行数，,注意这是jqgrid自身的参数
        int total_record = userDao.queryCount(params); // 总记录数(应根据数据库取得，在此只是模拟)
        int page_size = Integer.parseInt(rows);
        int current_page = Integer.parseInt(page);
        int total_page = total_record % page_size == 0 ? total_record / page_size : total_record / page_size + 1; // 计算总页数
        int limit_start = (current_page - 1) * page_size; // 开始记录数
        int limit_end = limit_start + page_size;
        json.put("total", total_page);
        json.put("page", page);
        json.put("records", total_record);
        JSONArray rows_jsonarr = new JSONArray();
        if (!StringUtils.isNotEmpty(sortname.trim())) {
            sortname="id";
        }
        if (!StringUtils.isNotEmpty(sortorder.trim())) {
            sortorder="desc";
        }
        List<Map<String, Object>> list = userDao.queryPage(limit_start, limit_end,params, sortname, sortorder);
        for (int i = 0; i < list.size(); i++) {
            rows_jsonarr.add(list.get(i));
        }
        json.put("rows", rows_jsonarr);
        log.info("查询返回结果：" + json.toString());
        return json;
    }
}
