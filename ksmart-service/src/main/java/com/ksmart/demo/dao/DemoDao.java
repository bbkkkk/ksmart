package com.ksmart.demo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by KF on 2017/8/16.
 */
@Repository
public interface DemoDao {
    public int insert(Map<String, Object> params);
    public int update(@Param(value = "id") Long id, @Param(value = "groupid") String groupid, @Param(value = "emails") String emails, @Param(value = "modify_user") String modify_user);
//    public List<Map<String, Object>> queryListById(@Param(value = "id")  Long id);

}
