<%--
  Created by IntelliJ IDEA.
  User: kangd
  Date: 2019/7/17
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="af" uri="myTag" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主页</title>
</head>
<body>
    <h2>Hello World!你好！欢迎您!</h2>
    <h1><af:userInfo /></h1>
    <hr />
    <a href="${pageContext.request.contextPath}/clearSession">清空Session，模拟退出</a>
    <hr />
    <h1>当前在线人数：<%=session.getServletContext().getAttribute("count") %></h1>
    <hr />
    <a href="${pageContext.request.contextPath}/login" >返回登录</a>
</body>
</html>
