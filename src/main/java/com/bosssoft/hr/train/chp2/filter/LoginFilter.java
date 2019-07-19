package com.bosssoft.hr.train.chp2.filter;

import com.bosssoft.hr.train.util.Constants;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录拦截器
 * @author likang
 */
public class LoginFilter implements Filter {

    private static Logger logger = Logger.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("登录拦截器，正在拦截...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute(Constants.SESSION_USER) == null) {
                String goURL = request.getServletPath();
                if (request.getQueryString() != null) {
                    goURL += "?" + request.getQueryString();
                }
                String lastStr = null;
                if (goURL.contains(Constants.POINT)){
                    lastStr = goURL.substring(goURL.lastIndexOf(Constants.POINT));
                }
                if (Constants.URL_LOGIN.equals(goURL) ||
                        Constants.LAST_STR_JS.equals(lastStr) ||
                        Constants.LAST_STR_CSS.equals(lastStr) ||
                        Constants.LAST_STR_ICO.equals(lastStr) ||
                        Constants.LAST_STR_PNG.equals(lastStr) ||
                        Constants.LAST_STR_JPG.equals(lastStr)
                ) {
                    logger.info("登录拦截器，登录或者静态文件的请求，放行！");
                    filterChain.doFilter(request, response);
                } else {
                    session.setAttribute(Constants.SESSION_GO_URL, goURL);
                    request.setAttribute(Constants.REQUEST_MESSAGE, "请先登录！");
                    logger.info("登录拦截器，非法请求，请求地址为：" + goURL);
                    request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request, response);
                }
            } else {
                logger.info("登录拦截器，已登录，继续访问！");
                filterChain.doFilter(request, response);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
