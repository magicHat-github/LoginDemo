package com.bosssoft.hr.train.chp2.controller;

import com.bosssoft.hr.train.chp2.pojo.User;
import com.bosssoft.hr.train.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * 自定义标签
 * @author likang
 */
public class UserTag extends SimpleTagSupport {

    private static Logger logger = LoggerFactory.getLogger(UserTag.class);

    @Override
    public void doTag() throws IOException {
        logger.info("自定义标签处理器，正在处理信息...");
        PageContext pageContext = (PageContext) getJspContext();
        User user = (User) pageContext.getSession().getAttribute(Constants.SESSION_USER);
        JspWriter out = getJspContext().getOut();
        String message =user !=null?"欢迎你， " + user.getName() + "!!!":"错误的标签信息！";
        logger.info("自定义标签处理器，处理成功，输出信息为：" + message);
        out.println(message);
    }
}
