<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人信息 - 学生成绩管理系统</title>
    <c:if test="${empty sessionScope.loginUser or sessionScope.loginUser.role ne 'student'}">
        <c:redirect url="/login.jsp"/>
    </c:if>
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
            max-width: 800px;
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }
        
        .header h1 {
            font-size: 24px;
            margin-bottom: 10px;
        }
        
        .back-btn {
            position: absolute;
            top: 20px;
            left: 20px;
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: background 0.3s;
        }
        
        .back-btn:hover {
            background: rgba(255, 255, 255, 0.3);
        }
        
        .content {
            padding: 40px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            color: #333;
            font-size: 14px;
            margin-bottom: 8px;
            font-weight: 500;
        }
        
        .form-group input, .form-group select {
            width: 100%;
            height: 45px;
            padding: 0 15px;
            border: 1px solid #e2e8f0;
            border-radius: 6px;
            font-size: 15px;
            color: #333;
            transition: border-color 0.3s ease;
        }
        
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #11998e;
            box-shadow: 0 0 0 3px rgba(17, 153, 142, 0.1);
        }
        
        .form-group input:disabled {
            background-color: #f5f5f5;
            cursor: not-allowed;
        }
        
        .btn {
            width: 100%;
            height: 48px;
            background-color: #11998e;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        
        .btn:hover {
            background-color: #0e8076;
        }
        
        .msg-box {
            font-size: 13px;
            text-align: center;
            margin-bottom: 15px;
            padding: 8px;
            border-radius: 4px;
        }
        
        .error {
            color: #e53e3e;
            background-color: #fef2f2;
        }
        
        .success {
            color: #38a169;
            background-color: #f0fff4;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>个人信息</h1>
            <a href="${pageContext.request.contextPath}/student/index.jsp" class="back-btn">返回首页</a>
        </div>
        
        <div class="content">
            <c:if test="${not empty sessionScope.successMsg}">
                <div class="msg-box success">${sessionScope.successMsg}</div>
                <c:remove var="successMsg" scope="session"/>
            </c:if>
            
            <c:if test="${not empty msg}">
                <div class="msg-box ${msgType}">${msg}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/studentManage/update" method="post">
                <div class="form-group">
                    <label for="stuId">学号</label>
                    <input type="text" id="stuId" name="stuId" value="${student.stuId}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="stuName">姓名</label>
                    <input type="text" id="stuName" name="stuName" value="${student.stuName}" disabled>
                </div>
                
                <div class="form-group">
                    <label for="gender">性别</label>
                    <input type="text" id="gender" name="gender" value="${student.gender}" disabled>
                </div>
                
                <div class="form-group">
                    <label for="age">年龄</label>
                    <input type="text" id="age" name="age" value="${student.age}" disabled>
                </div>
                
                <div class="form-group">
                    <label for="className">班级</label>
                    <input type="text" id="className" name="className" value="${student.className}" disabled>
                </div>
                
                <div class="form-group">
                    <label for="phone">联系电话</label>
                    <input type="text" id="phone" name="phone" value="${student.phone}" required>
                </div>
                
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email" value="${student.email}" required>
                </div>
                
                <button type="submit" class="btn">保存修改</button>
            </form>
        </div>
    </div>
</body>
</html>
