package com.ksmart.pms.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 登录认证的拦截器
 */
public class LoginInterceptor implements HandlerInterceptor{
	private static final Log log = LogFactory.getLog(LoginInterceptor.class);

	private String AJAX_NOT_LOGIN_MESSAGE = "{\"error\":\"NotLogin\"}";

	/**
	 * Handler执行完成之后调用这个方法
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exc)
			throws Exception {
		
	}

	/**
	 * Handler执行之后，ModelAndView返回之前调用这个方法
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * Handler执行之前调用这个方法
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		//获取请求的URL
		String url = request.getRequestURI();
		//URL:login.jsp是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制
		if(url.indexOf("login")>=0){
			return true;
		}
		//获取Session
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("SE_UNAME");
		
		if(username != null){
			return true;
		}else {
			if (request.getHeader("X-Requested-With") != null) {
				try {
					response.setHeader("sessionstatus", "timeout");
					response.sendError(518, "session timeout.");
					response.getWriter().write(AJAX_NOT_LOGIN_MESSAGE);
				} catch (IOException e) {
					log.error(e.getMessage() + "ajax not login response.getWriter error");
				}
			}
			//不符合条件的，跳转到登录界面
			request.getRequestDispatcher("/index.html").forward(request, response);

			return false;
		}
	}

}

