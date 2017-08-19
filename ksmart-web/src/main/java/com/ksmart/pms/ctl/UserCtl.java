package com.ksmart.pms.ctl;

import com.alibaba.fastjson.JSONObject;
import com.ksmart.base.ctl.BaseCtl;
import com.ksmart.base.utils.StringUtils;
import com.ksmart.demo.service.DemoService;
import com.ksmart.pms.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KF on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/pms/user")
public class UserCtl extends BaseCtl {
    private static final Log log = LogFactory.getLog(UserCtl.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request) {
        Map<String,Object> params=req2Map(request);
        log.info("入参："+params);
        JSONObject json = new JSONObject();
        json.put("statusCode", 200);
        int cnt = userService.insert(params);
        json.put("cnt", cnt);
        log.info("出参："+json.toString());
        return json;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String, Object> update(HttpServletRequest request) {
        Map<String,Object> params=req2Map(request);
        log.info("入参："+params);
        JSONObject json = new JSONObject();
        json.put("statusCode", 200);
        int cnt = userService.update(params);
        json.put("cnt", cnt);
        log.info("出参："+json.toString());
        return json;
    }
    @RequestMapping(value = "/show")
    @ResponseBody
    public Map<String, Object> show(HttpServletRequest request) {
        String id = request.getParameter("id");
        log.info("id ：" + id);
        List<Map<String, Object>> list = userService.queryListById(Long.parseLong(id));
        Map<String, Object> result = list.get(0);
        result.put("statusCode", 200);
        return result;
    }
//    @RequestMapping(value = "/del")
//    @ResponseBody
//    public Map<String, Object> delete(HttpServletRequest request) {
//        String id = request.getParameter("id");
//        log.error("系统关键操作日志：" + request.getSession().getAttribute("erp_pin") + "调用了group delete id=" + id);
//
//        Map<String, Object> result = new HashMap<String, Object>();
//        userService.delById(Long.parseLong(id));
//        result.put("statusCode", 200);
//        result.put("message", "系统编码ok");
//        return result;
//    }
    @RequestMapping(value = "/lgdel")
    @ResponseBody
    public Map<String, Object> lgdel(HttpServletRequest request) {
        String id = request.getParameter("id");
        log.error("系统关键操作日志：" + request.getSession().getAttribute("userid") + "调用了smt_user delete id=" + id);
        Map<String, Object> result = new HashMap<String, Object>();
        userService.lgDelById(Long.parseLong(id));
        result.put("statusCode", 200);
        result.put("message", "系统编码ok");
        return result;
    }

    @RequestMapping(value = "/page")
    @ResponseBody
    public JSONObject page(HttpServletRequest request, Model model) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String search_text = request.getParameter("search_text");
        String search_text2 = request.getParameter("search_text2");
        String sortname = request.getParameter("sortname");
        String sortorder = request.getParameter("sortorder");
        log.info("page  :  " + page);
        log.info("rows  :  " + rows);
        log.info("search_text  :  " + search_text);
        log.info("search_text2  :  " + search_text2);
        log.info("sortname  :  " + sortname);
        log.info("sortorder  :  " + sortorder);
        Map<String, Object> params = null;
        if (StringUtils.isNotBlank(search_text)) {
            params = new HashMap<String, Object>();
            params.put("uname", search_text);
        }
        JSONObject json = userService.page(page, rows, params, sortname, sortorder);

        return json;
    }


}
