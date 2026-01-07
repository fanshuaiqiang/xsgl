<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑成绩 - 学生成绩管理系统</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: #f5f5f5;
            padding: 20px;
        }
        
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .header h1 {
            font-size: 24px;
        }
        
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background: white;
            color: #667eea;
        }
        
        .btn-primary:hover {
            background: #f0f0f0;
        }
        
        .btn-success {
            background: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background: #218838;
        }
        
        .content {
            padding: 30px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        
        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .form-group input[readonly] {
            background: #f5f5f5;
            cursor: not-allowed;
        }
        
        .form-group .error {
            color: #dc3545;
            font-size: 12px;
            margin-top: 5px;
        }
        
        .form-actions {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        
        .msg-box {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        
        .msg-box.error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>编辑成绩</h1>
            <a href="${pageContext.request.contextPath}/scoreManage/list" class="btn btn-primary">返回列表</a>
        </div>
        
        <div class="content">
            <c:if test="${not empty msg}">
                <div class="msg-box error">${msg}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/scoreManage/update" method="post">
                <input type="hidden" name="scoreId" value="${score.scoreId}">
                
                <div class="form-group">
                    <label for="stuId">学号</label>
                    <input type="text" id="stuId" name="stuId" value="${score.stuId}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="stuName">姓名</label>
                    <input type="text" id="stuName" name="stuName" value="${score.stuName}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="courseId">课程ID</label>
                    <input type="text" id="courseId" name="courseId" value="${score.courseId}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="courseName">课程名称</label>
                    <input type="text" id="courseName" name="courseName" value="${score.courseName}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="score">成绩 *</label>
                    <input type="number" id="score" name="score" value="${score.score}" min="0" max="100" required>
                </div>
                
                <div class="form-group">
                    <label for="semester">学期</label>
                    <input type="text" id="semester" name="semester" value="${score.semester}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="academicYear">学年</label>
                    <input type="text" id="academicYear" name="academicYear" value="${score.academicYear}" readonly>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-success">保存</button>
                    <a href="${pageContext.request.contextPath}/scoreManage/list" class="btn btn-primary">取消</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
