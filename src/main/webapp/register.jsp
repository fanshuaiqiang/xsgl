<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Microsoft YaHei", Arial, sans-serif;
        }

        body {
            background-color: #f5f7fa;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .register-container {
            background-color: #ffffff;
            width: 100%;
            max-width: 450px;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 2px 15px rgba(0, 0, 0, 0.08);
        }

        .register-title {
            text-align: center;
            color: #2d3748;
            font-size: 22px;
            margin-bottom: 25px;
            font-weight: 600;
            position: relative;
        }

        .register-title::after {
            content: "";
            display: block;
            width: 50px;
            height: 3px;
            background-color: #4299e1;
            margin: 8px auto 0;
            border-radius: 3px;
        }

        .register-form {
            margin-top: 10px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            color: #4a5568;
            font-size: 14px;
            margin-bottom: 8px;
            font-weight: 500;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            height: 45px;
            padding: 0 15px;
            border: 1px solid #e2e8f0;
            border-radius: 6px;
            font-size: 15px;
            color: #2d3748;
            transition: border-color 0.3s ease;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #4299e1;
            box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.1);
        }

        .register-btn {
            width: 100%;
            height: 48px;
            background-color: #4299e1;
            color: #ffffff;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-bottom: 15px;
            margin-top: 10px;
        }

        .register-btn:hover {
            background-color: #3182ce;
        }

        .login-link {
            text-align: center;
            font-size: 14px;
        }

        .login-link a {
            color: #4299e1;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .login-link a:hover {
            color: #3182ce;
            text-decoration: underline;
        }

        .msg-box {
            font-size: 13px;
            text-align: center;
            margin-bottom: 15px;
            padding: 8px;
            border-radius: 4px;
        }

        .error-msg {
            color: #e53e3e;
            background-color: #fef2f2;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2 class="register-title">学生成绩管理系统</h2>
    <c:if test="${not empty msg}">
        <div class="msg-box error-msg">${msg}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post" class="register-form">
        <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" id="username" name="username" placeholder="请输入用户名" required>
        </div>
        <div class="form-group">
            <label for="password">密码</label>
            <input type="password" id="password" name="password" placeholder="请设置登录密码" required>
        </div>
        <div class="form-group">
            <label for="role">角色</label>
            <select id="role" name="role" required>
                <option value="student">普通用户</option>
                <option value="admin">管理员</option>
            </select>
        </div>
        <div class="form-group">
            <label for="email">邮箱</label>
            <input type="email" id="email" name="email" placeholder="请输入邮箱">
        </div>
        <button type="submit" class="register-btn">注册</button>
        <div class="login-link">
            已有账号？<a href="${pageContext.request.contextPath}/login.jsp">立即登录</a>
        </div>
    </form>
</div>
</body>
</html>
