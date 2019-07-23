package com.bosssoft.hr.train.chp2.listener;

import com.bosssoft.hr.train.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 用户在线人数的监听器
 * @author likang
 */
public class UserListeners implements HttpSessionListener {

    private static Logger logger = LoggerFactory.getLogger(UserListeners.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        ServletContext application = session.getServletContext();
        if (application.getAttribute(Constants.USER_COUNT)==null){
            application.setAttribute(Constants.USER_COUNT,1);
        }else{
            Integer count = (Integer) application.getAttribute(Constants.USER_COUNT);
            count++;
            application.setAttribute(Constants.USER_COUNT,count);
        }
        logger.info("新用户登录成功！新用户的Session ID为："+ session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        ServletContext application = session.getServletContext();
        if (application.getAttribute(Constants.USER_COUNT)==null){
            logger.info("出现不理智的错误！！");
        }else{
            Integer count = (Integer) application.getAttribute(Constants.USER_COUNT);
            count--;
            application.setAttribute(Constants.USER_COUNT,count);
        }
        logger.info("新用户注销成功！用户的Session ID为："+ session.getId());
    }
}
