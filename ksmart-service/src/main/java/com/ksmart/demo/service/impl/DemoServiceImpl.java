package com.ksmart.demo.service.impl;


import com.ksmart.demo.dao.DemoDao;
import com.ksmart.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by KF on 2017/8/16.
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    DemoDao demoDao;
    @Override
    public int insert(Map<String, Object> params) {
        return demoDao.insert(params);

    }

    @Override
    public int update(Long id, String groupid, String emails, String modify_user) {
        return demoDao.update(id,groupid, emails,modify_user);
    }

}
