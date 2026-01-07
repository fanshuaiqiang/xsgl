<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>学生首页 - 学生成绩管理系统</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 1200px;
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }
        
        .header h1 {
            font-size: 28px;
            margin-bottom: 10px;
        }
        
        .header p {
            font-size: 14px;
            opacity: 0.9;
        }
        
        .content {
            padding: 40px;
        }
        
        .welcome-section {
            text-align: center;
            margin-bottom: 40px;
        }
        
        .welcome-section h2 {
            color: #333;
            margin-bottom: 10px;
        }
        
        .welcome-section p {
            color: #666;
            font-size: 16px;
        }
        
        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        
        .menu-item {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 30px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            border: 2px solid transparent;
        }
        
        .menu-item:hover {
            background: white;
            border-color: #11998e;
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(17, 153, 142, 0.3);
        }
        
        .menu-item .icon {
            font-size: 48px;
            margin-bottom: 15px;
        }
        
        .menu-item h3 {
            color: #333;
            margin-bottom: 10px;
            font-size: 18px;
        }
        
        .menu-item p {
            color: #666;
            font-size: 14px;
        }
        
        .footer {
            background: #f8f9fa;
            padding: 20px;
            text-align: center;
            color: #666;
            font-size: 14px;
        }
        
        .logout-btn {
            position: absolute;
            top: 20px;
            right: 20px;
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: background 0.3s;
        }
        
        .logout-btn:hover {
            background: rgba(255, 255, 255, 0.3);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>学生成绩管理系统</h1>
            <p>学生个人中心</p>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">退出登录</a>
        </div>
        
        <div class="content">
            <c:if test="${not empty sessionScope.successMsg}">
                <div style="text-align: center; color: #38a169; background-color: #f0fff4; padding: 10px; border-radius: 4px; margin-bottom: 20px;">
                    ${sessionScope.successMsg}
                </div>
                <c:remove var="successMsg" scope="session"/>
            </c:if>
            <div class="welcome-section">
                <h2>欢迎回来，${loginUserName}</h2>
                <p>请选择您要执行的操作</p>
            </div>
            
            <div class="menu-grid">
                <div class="menu-item" onclick="location.href='${pageContext.request.contextPath}/studentManage/myInfo'">
                    <div class="icon">👤</div>
                    <h3>个人信息</h3>
                    <p>查看和修改个人信息</p>
                </div>
                
                <div class="menu-item" onclick="location.href='${pageContext.request.contextPath}/scoreManage/myScore'">
                    <div class="icon">📊</div>
                    <h3>我的成绩</h3>
                    <p>查看我的成绩信息</p>
                </div>
                
                <div class="menu-item" onclick="location.href='${pageContext.request.contextPath}/scoreManage/export'">
                    <div class="icon">📥</div>
                    <h3>成绩导出</h3>
                    <p>导出我的成绩数据</p>
                </div>
            </div>
        </div>
        
        <div class="footer">
            <p>&copy; 2026 学生成绩管理系统. All rights reserved.</p>
        </div>
    </div>
</body>
</html>