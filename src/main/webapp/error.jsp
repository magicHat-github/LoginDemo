<%--
  Created by IntelliJ IDEA.
  User: kangd
  Date: 2019/7/17
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>错误</title>
</head>
<body>
    <h2 style="color: red;">错误页面！！！</h2>
    <%
        if (request.getAttribute("message")!=null){
            out.print(request.getAttribute("message"));
        }
    %>
</body>
</html>
