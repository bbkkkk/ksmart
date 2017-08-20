package com.ksmart.pms.ctl;

import com.alibaba.fastjson.JSONObject;
import com.ksmart.base.ctl.BaseCtl;
import com.ksmart.base.utils.StringUtils;
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
@RequestMapping(value = "/")
public class LoginCtl extends BaseCtl {
    private static final Log log = LogFactory.getLog(LoginCtl.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request) {
        Map<String, Object> params = req2Map(request);
        log.info("入参：" + params);
        String username=request.getParameter("username")==null?"":request.getParameter("username").toString();
        String password=request.getParameter("password")==null?"":request.getParameter("password").toString();
        JSONObject json = new JSONObject();
        if("".equals(username)||"".equals(password)){
            json.put("statusCode", 300);
            json.put("msg", "非法用户名密码");
            log.info("出参：" + json.toString());
            return json;
        }
        System.out.println("检查用户名密码是否正确");
        if(username.equals("checkfail")){
            json.put("statusCode", 300);
            json.put("msg", "非法用户名密码");
            log.info("出参：" + json.toString());

            return json;
        }else {
            request.getSession().setAttribute("SE_UNAME", username);
            request.getSession().setAttribute("SE_UID", "1");
            json.put("statusCode", 200);
            log.info("出参：" + json.toString());
            return json;
        }

    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("SE_UNAME");
        request.getSession().removeAttribute("SE_UID");
        JSONObject json = new JSONObject();
        json.put("statusCode", 200);
//        int cnt = userService.update(params);
//        json.put("cnt", cnt);
        log.info("出参：" + json.toString());
        return json;
    }

    @RequestMapping(value = "/getuser")
    @ResponseBody
    public Map<String, Object> getuser(HttpServletRequest request) {
        Map<String, Object> params = req2Map(request);
        log.info("入参：" + params);
        JSONObject json = new JSONObject();
        json.put("statusCode", 200);
//        int cnt = userService.update(params);
        json.put("SE_UNAME", request.getSession().getAttribute("SE_UNAME"));
        json.put("SE_UID", request.getSession().getAttribute("SE_UID"));
        log.info("出参：" + json.toString());
        return json;
    }

}
