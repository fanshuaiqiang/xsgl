<%--
  Created by IntelliJ IDEA.
  User: brmgc
  Date: 2025/11/6
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎界面</title>
    <style>
        /* 基础样式重置 */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            background: linear-gradient(135deg, #f5f7fa 0%, #e4edf9 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        /* 链接容器 */
        .link-container {
            text-align: center;
            padding: 40px;
            border-radius: 16px;
            background: rgba(255, 255, 255, 0.85);
            box-shadow: 0 10px 30px rgba(0, 0, 150, 0.1);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.3);
            transition: transform 0.3s ease;
        }

        /* 链接主样式 */
        .enter-link {
            display: inline-block;
            padding: 18px 45px;
            font-size: 1.8rem;
            font-weight: 600;
            color: #2c6fbb;
            text-decoration: none;
            background: transparent;
            border-radius: 50px;
            border: 3px solid #4a90e2;
            position: relative;
            overflow: hidden;
            transition: all 0.4s ease;
        }

        /* 链接悬停动画效果 */
        .enter-link:hover {
            color: white;
            background: linear-gradient(90deg, #4a90e2 0%, #2c6fbb 100%);
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(74, 144, 226, 0.4);
        }

        /* 链接点击效果 */
        .enter-link:active {
            transform: translateY(2px);
            box-shadow: 0 5px 15px rgba(74, 144, 226, 0.3);
        }

        /* 链接底部装饰线 */
        .enter-link::after {
            content: '';
            position: absolute;
            bottom: -8px;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 3px;
            background: #4a90e2;
            border-radius: 3px;
            transition: all 0.3s ease;
        }
        .enter-link:hover::after {
            width: 120px;
            background: white;
        }
    </style>
</head>
<body>
<div class="link-container">
    <a href="${pagecontext.request.contextPath}login.jsp" class="enter-link">欢迎进入学生系统管理系统</a>
</div>
</body>
</html>