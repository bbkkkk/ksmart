package com.ksmart.pms.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by KF on 2017/8/17.
 */
@Repository
public interface UserDao {
    public int insert(Map<String, Object> params);

    public int update(Map<String, Object> params);

    public List<Map<String, Object>> queryListById(@Param(value = "id") long id);

    public int delById(@Param(value = "id") long id);

    public int lgDelById(@Param(value = "id") long id);

    public int queryCount(@Param(value = "params") Map<String, Object> params);

    public List<Map<String, Object>> queryPage(@Param(value = "limit_start") int limit_start, @Param(value = "limit_end") int limit_end, @Param(value = "params") Map<String, Object> params,
                                               @Param(value = "sortname") String sortname, @Param(value = "sortorder") String sortorder);
}
