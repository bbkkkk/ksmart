package ${ packageName }.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ksmart.base.utils.StringUtils;
import com.ksmart.${moudelName}.dao.${classPre}Dao;
import com.ksmart.${moudelName}.service.${classPre}Service;
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
public class ${classPre}ServiceImpl implements ${classPre}Service {
private static final Logger log = LoggerFactory.getLogger(${classPre}ServiceImpl.class);

@Autowired
${classPre}Dao ${classPreLow}Dao;
@Override
public int insert(Map<String, Object> params) {
return ${classPreLow}Dao.insert(params);

}

@Override
public int update(Map<String, Object> params){
return ${classPreLow}Dao.update(params);
}

@Override
public List<Map<String, Object>> queryListById(long id) {
return ${classPreLow}Dao.queryListById(id);
}

@Override
public int delById(long id) {
return ${classPreLow}Dao.delById(id);
}

@Override
public int lgDelById(Map<String, Object> params) {
return ${classPreLow}Dao.lgDelById( params);
}

@Override
public JSONObject page(String page, String rows, Map<String, Object> params, String sortname, String sortorder) {
log.info("分页查询条件：page,rows,search_text,sortname,sortorder");
log.info("分页查询参数：" +params);
JSONObject json = new JSONObject();
// 取得每页显示行数，,注意这是jqgrid自身的参数
int total_record = ${classPreLow}Dao.queryCount(params); // 总记录数(应根据数据库取得，在此只是模拟)
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
List<Map<String, Object>> list = ${classPreLow}Dao.queryPage(limit_start, limit_end,params, sortname, sortorder);
for (int i = 0; i < list.size(); i++) {
rows_jsonarr.add(list.get(i));
}
json.put("rows", rows_jsonarr);
log.info("查询返回结果：" + json.toString());
return json;
}
}
