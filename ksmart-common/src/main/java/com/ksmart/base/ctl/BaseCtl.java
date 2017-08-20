package com.ksmart.base.ctl;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KF on 2017/8/17.
 */
public  class BaseCtl {
    protected Map<String,Object> req2Map(HttpServletRequest request){
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    System.out.println("参数：" + paramName + "=" + paramValue);
                    map.put(paramName, paramValue);
                }
            }
        }
        map.put("SE_UNAME",request.getSession().getAttribute("SE_UNAME"));
        map.put("SE_UID",request.getSession().getAttribute("SE_UID"));
        return map;
    }
}
