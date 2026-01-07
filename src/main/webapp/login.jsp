<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
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

        .login-container {
            background-color: #ffffff;
            width: 100%;
            max-width: 420px;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 2px 15px rgba(0, 0, 0, 0.08);
        }

        .login-title {
            text-align: center;
            color: #2d3748;
            font-size: 22px;
            margin-bottom: 25px;
            font-weight: 600;
            position: relative;
        }

        .login-title::after {
            content: "";
            display: block;
            width: 50px;
            height: 3px;
            background-color: #4299e1;
            margin: 8px auto 0;
            border-radius: 3px;
        }

        .login-form {
            margin-top: 10px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            color: #4a5568;
            font-size: 14px;
            margin-bottom: 8px;
            font-weight: 500;
        }

        .form-group input {
            width: 100%;
            height: 45px;
            padding: 0 15px;
            border: 1px solid #e2e8f0;
            border-radius: 6px;
            font-size: 15px;
            color: #2d3748;
            transition: border-color 0.3s ease;
        }

        .form-group input:focus {
            outline: none;
            border-color: #4299e1;
            box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.1);
        }

        .login-btn {
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
        }

        .login-btn:hover {
            background-color: #3182ce;
        }

        .register-link {
            text-align: center;
            font-size: 14px;
        }

        .register-link a {
            color: #4299e1;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .register-link a:hover {
            color: #3182ce;
            text-decoration: underline;
        }

        .error-msg {
            color: #e53e3e;
            font-size: 13px;
            text-align: center;
            margin-bottom: 15px;
            padding: 8px;
            background-color: #fef2f2;
            border-radius: 4px;
        }

        .success-toast {
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #48bb78;
            color: white;
            padding: 12px 20px;
            border-radius: 4px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            z-index: 1000;
            animation: slideIn 0.3s ease-out;
        }

        @keyframes slideIn {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        @keyframes fadeOut {
            from {
                opacity: 1;
            }
            to {
                opacity: 0;
            }
        }
    </style>
    <script>
        window.onload = function() {
            var toast = document.querySelector('.success-toast');
            if (toast) {
                setTimeout(function() {
                    toast.style.animation = 'fadeOut 0.3s ease-out';
                    setTimeout(function() {
                        toast.style.display = 'none';
                    }, 300);
                }, 3000);
            }
        };
    </script>
</head>
<body>
<c:if test="${not empty sessionScope.successMsg}">
    <div class="success-toast">${sessionScope.successMsg}</div>
</c:if>
<div class="login-container">
    <h2 class="login-title">学生成绩管理系统</h2>
    <c:if test="${not empty error}">
        <div class="error-msg">${error}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/LoginServlet" method="post" class="login-form">
        <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" id="username" name="username" placeholder="请输入用户名" required>
        </div>
        <div class="form-group">
            <label for="password">密码</label>
            <input type="password" id="password" name="password" placeholder="请输入密码" required>
        </div>
        <button type="submit" class="login-btn">登录</button>
        <div class="register-link">
            还没有账号？<a href="${pageContext.request.contextPath}/register.jsp">立即注册</a>
        </div>
    </form>
</div>
</body>
</html>
