package com.bosssoft.hr.train.chp2.controller;

import com.bosssoft.hr.train.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 其他控制层
 * 目前作用，用于清空Session
 * @author likang
 */
public class OtherController extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(OtherController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            logger.info("GET访问：/clearSession，正在清空Session里面的值...");
            request.getSession().removeAttribute(Constants.SESSION_USER);
            request.getSession().removeAttribute(Constants.SESSION_GO_URL);
            logger.info("清空Session成功！");
            request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request, response);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
