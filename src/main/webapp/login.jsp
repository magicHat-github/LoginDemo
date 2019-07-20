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
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();
    String username = null;
    String password = null;
    for (Cookie cookie:cookies){
        if ("username".equals(cookie.getName())){
            username = cookie.getValue();
        }else if("password".equals(cookie.getName())){
            password = cookie.getValue();
        }
    }
%>
<div id="particles-js">
    <div class="login">
        <div class="login-top">
            <span style="text-shadow: 0 0 1px black;">登录</span>
            <span style="font-size: 14px;margin-left: 60px;text-shadow: 1px 1px 4px black;">当前在线人数：<span style="color: aqua"> <%=session.getServletContext().getAttribute("count") %> </span></span>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="${pageContext.request.contextPath}/images/name.png"  alt=""/></div>
            <div class="login-center-input">
                <input id="username" type="text" name="username" value="<%=username==null?"":username%>" placeholder="请输入您的用户名" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的用户名'" />
                <label for="username" class="login-center-input-text">用户名</label>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="${pageContext.request.contextPath}/images/password.png"  alt=""/></div>
            <div class="login-center-input">
                <input id="password" type="password" name="password" value="<%=password==null?"":password%>" placeholder="请输入您的密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的密码'" />
                <label for="password" class="login-center-input-text">密码</label>
            </div>
        </div>
        <div class="login-center clearfix">
            <input id="save-checkbox" style="width: 10%;height: 20px;" value="checkbox" type="checkbox" name="savePassword" <%=username==null?"":"checked"%>/>
            <span style="font-size: 14px;margin-left: 10px;"><label for="save-checkbox">记住密码</label></span>
        </div>
        <div class="login-button">
            登陆
        </div>
        <div class="login-center" style="text-align: center;margin-top: 20px;font-size: 18px;text-shadow: 2px 1px 8px black;">
            <span id="messageSpan" style="color: brown;">
                <%
                    if(request.getAttribute("message")!=null){
                        out.print(request.getAttribute("message"));
                    }
                %>
            </span>
        </div>
    </div>
    <div class="sk-rotating-plane"></div>
</div>


<script src="${pageContext.request.contextPath}/js/particles.min.js"></script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
    /**
     * 判断节点是否有样式
     * @param elem 节点
     * @param cls 样式
     * @returns {boolean}
     */
    function hasClass(elem, cls) {
        cls = cls || '';
        if (cls.replace(/\s/g, '').length === 0) return false;
        return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
    }

    /**
     * 给节点添加样式
     * @param ele 节点
     * @param cls 样式
     */
    function addClass(ele, cls) {
        if (!hasClass(ele, cls)) {
            ele.className = ele.className === '' ? cls : ele.className + ' ' + cls;
        }
    }

    /**
     * 删除样式
     * @param ele 节点
     * @param cls 样式
     */
    function removeClass(ele, cls) {
        if (hasClass(ele, cls)) {
            var newClass = ' ' + ele.className.replace(/[\t\r\n]/g, '') + ' ';
            while (newClass.indexOf(' ' + cls + ' ') >= 0) {
                newClass = newClass.replace(' ' + cls + ' ', ' ');
            }
            ele.className = newClass.replace(/^\s+|\s+$/g, '');
        }
    }

    /**
     * 响应提交按钮，发送Ajax请求
     */
    document.querySelector(".login-button").onclick = function(){
        var username = $("#username").val();
        var password = $("#password").val();
        var checkbox = $("#save-checkbox").is(":checked")?$("#save-checkbox").val():null;
        if (username === "" || password === ""){
            $("#messageSpan").html("用户名密码不能为空！");
            return
        }
        addClass(document.querySelector(".login"), "active")
        addClass(document.querySelector(".sk-rotating-plane"), "active")
        document.querySelector(".login").style.display = "none"
        var user = {
            username: username,
            password: password,
            savePassword: checkbox,
            ajax: true
        };
        $.ajax({
            url:'${pageContext.request.contextPath}/login',
            method:'POST',
            data: user,
            success:function (data){
                var result = JSON.parse(data);
                if (result.status){
                    window.location.href = result.message;
                }else{
                    removeClass(document.querySelector(".login"), "active");
                    removeClass(document.querySelector(".sk-rotating-plane"), "active");
                    document.querySelector(".login").style.display = "block";
                    $("#messageSpan").html("用户名或者密码错误")
                }
            }
        })
    };
</script>
</body>
</html>
