package com.ksmart.demo.service;

import java.util.Map;

/**
 * Created by KF on 2017/8/16.
 */
public interface DemoService {

    int insert(Map<String, Object> params);

    int update(Long id, String groupid, String emails, String modify_user);
}
