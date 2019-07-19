package com.bosssoft.hr.train.util;

import javafx.util.StringConverter;

/**
 * 存放静态变量
 * @author likang
 */
public class Constants {

    /**
     * 总数居大小
     */
    public static final Integer DATA_SIZE = 1000;

    /**
     * 存放登录前的请求地址和GET参数
     */
    public static final String SESSION_GO_URL = "goURL";

    /**
     * 存放登录成功之后，放在session中的标志
     */
    public static final String SESSION_USER = "user";

    /**
     * 放在request中的消息标志位
     */
    public static final String REQUEST_MESSAGE = "message";

    /**
     * 登录页面的跳转地址
     */
    public static final String PAGE_LOGIN = "/login.jsp";

    /**
     * 首页的跳转地址
     */
    public static final String PAGE_INDEX = "/index.jsp";

    /**
     * 登录地址，用于拦截器放行地址
     */
    public static final String URL_LOGIN = "/login";

    /**
     * 错误页面的跳转地址
     */
    public static final String PAGE_ERROR = "/error.jsp";

    /**
     * 系统在线访问用户人数标志位
     */
    public static final String USER_COUNT = "count";

    /**
     * 系统在线已登录用户人数标志位
     */
    public static final String USER_LOGIN_ONLINE_COUNT = "online";

    /**
     * 保存Cookie的标志位
     */
    public static final String SAVE_PASSWORD = "checkbox";

    /**
     * 过滤静态JS文件
     */
    public static final String LAST_STR_JS = ".js";

    /**
     * 过滤静态CSS文件
     */
    public static final String LAST_STR_CSS = ".css";

    /**
     * 过滤静态ICO文件
     */
    public static final String LAST_STR_ICO = ".ico";

    /**
     * 过滤静态PNG文件
     */
    public static final String LAST_STR_PNG = ".png";

    /**
     * 过滤静态JPG文件
     */
    public static final String LAST_STR_JPG = ".jpg";

    /**
     * 点
     */
    public static final String POINT = ".";
}
