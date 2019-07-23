package com.bosssoft.hr.train.chp2.listener;

import com.bosssoft.hr.train.util.Constants;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 容器监听器
 * 安全销毁
 * @author likang
 */
public class ContextListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(UserListeners.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();
        application.setAttribute(Constants.USER_LOGIN_ONLINE_COUNT,0);
        application.setAttribute(Constants.USER_COUNT,0);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                logger.info(String.format("ContextFinalizer:Driver %s deregistered", d));
            } catch (SQLException ex) {
                logger.error(String.format("ContextFinalizer:Error deregistering driver %s", d) + ":" + ex , ex);
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
