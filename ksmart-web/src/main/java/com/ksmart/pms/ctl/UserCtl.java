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
        String sysid = request.getParameter("sysid");
        log.info("sysid ：" + sysid);
        String msg_type = request.getParameter("msg_type");
        log.info("msg_type ：" + msg_type);
        String emails = request.getParameter("emails");
        log.info("emails ：" + emails);
        String groupid = request.getParameter("groupid");
        log.info("groupid ：" + groupid);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sysid", sysid);
        params.put("msg_type", msg_type);
        params.put("emails", emails);
        params.put("groupid", groupid);
        params.put("status", 20);
        params.put("applyer", request.getSession().getAttribute("erp_pin"));
        Map<String, Object> map1 = req2Map(request);
        JSONObject json = new JSONObject();
        json.put("statusCode", 200);
        int cnt = userService.insert(params);
        json.put("cnt", cnt);
        log.info(json.toString());
        return json;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String, Object> update(HttpServletRequest request) {
        String sysid = request.getParameter("sysid");
        log.info("sysid ：" + sysid);
        String msg_type = request.getParameter("msg_type");
        log.info("msg_type ：" + msg_type);
        String emails = request.getParameter("emails");
        log.info("emails ：" + emails);
        String groupid = request.getParameter("groupid");
        log.info("groupid ：" + groupid);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sysid", sysid);
        params.put("msg_type", msg_type);
        params.put("emails", emails);
        params.put("groupid", groupid);
        params.put("status", 20);
        params.put("applyer", request.getSession().getAttribute("erp_pin"));
        JSONObject json = new JSONObject();
        json.put("statusCode", 200);
        int cnt = userService.insert(params);
        json.put("cnt", cnt);
        log.info(json.toString());
        return json;
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
