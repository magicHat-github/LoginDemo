package com.bosssoft.hr.train.chp2.controller;

import com.alibaba.fastjson.JSONObject;
import com.bosssoft.hr.train.chp2.pojo.User;
import com.bosssoft.hr.train.chp2.service.UserService;
import com.bosssoft.hr.train.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 登录控制层
 * @author likang
 */
public class LoginController extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET访问：/login，请求到登录控制层!!!");
        request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST访问：/login，请求到登录控制层，正在处理登录...");
        request.setCharacterEncoding("UTF-8");
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String savePassword = request.getParameter("savePassword");
            String isAjax = request.getParameter("ajax");

            UserService userService = new UserService();
            User user = userService.checkUserLogin(username, password);
            if (isAjax == null) {
                // 这里处理非Ajax请求
                if (user == null) {
                    request.setAttribute(Constants.REQUEST_MESSAGE, "用户名或者密码错误");
                    logger.info("POST访问：/login，请求到登录控制层，登录失败，即将跳转至登录页面");
                    request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request, response);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute(Constants.SESSION_USER, user);
                    if (session.getAttribute(Constants.SESSION_GO_URL) != null) {
                        logger.info("POST访问：/login，请求到登录控制层，登录成功，即将跳转至之前请求的页面");
                        request.getRequestDispatcher((String) session.getAttribute(Constants.SESSION_GO_URL)).forward(request, response);
                    } else {
                        logger.info("POST访问：/login，请求到登录控制层，登录成功，即将跳转至首页");
                        request.getRequestDispatcher(Constants.PAGE_INDEX).forward(request, response);
                    }
                }
            } else {
                JSONObject jsonObject = new JSONObject();
                if (user == null) {
                    logger.info("POST-Ajax访问：/login，请求到登录控制层，登录失败！！");
                    String message = new String("用户名或者密码错误!".getBytes(), "UTF-8");
                    jsonObject.put("status", false);
                    jsonObject.put("message", message);
                } else {
                    jsonObject.put("status", true);
                    HttpSession session = request.getSession();
                    session.setAttribute(Constants.SESSION_USER, user);
                    String path = session.getAttribute(Constants.SESSION_GO_URL) != null ? (String) session.getAttribute(Constants.SESSION_GO_URL) : Constants.PAGE_INDEX;
                    logger.info("POST-Ajax访问：/login，请求到登录控制层，登录成功，返回的地址为：" + path);
                    jsonObject.put("message", request.getContextPath() + path);

                    setUserCookie(Constants.SAVE_PASSWORD.equals(savePassword),request,response,username,password);
                }
                response.getWriter().print(jsonObject.toJSONString());
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,response);
        }
    }

    /**
     * 给用户登录设置Cookie
     * @param isChecked 是否选中记住密码按钮
     * @param request request
     * @param response response
     * @param username 用户名 username
     * @param password 密码 password
     */
    private void setUserCookie(Boolean isChecked,HttpServletRequest request, HttpServletResponse response,String username,String password){
        if (isChecked){
            Cookie usernameCookie = new Cookie("username", username);
            Cookie passwordCookie = new Cookie("password", password);
            usernameCookie.setMaxAge(60 * 2);
            passwordCookie.setMaxAge(60 * 2);
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
        }else{
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie:cookies){
                if ("username".equals(cookie.getName())){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }else if("password".equals(cookie.getName())){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
}
