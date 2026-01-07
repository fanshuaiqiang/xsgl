<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑学生 - 学生成绩管理系统</title>
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
        
        .form-actions {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>编辑学生信息</h1>
            <a href="${pageContext.request.contextPath}/studentManage/list" class="btn btn-primary">返回列表</a>
        </div>
        
        <div class="content">
            <form action="${pageContext.request.contextPath}/studentManage/adminUpdate" method="post">
                <div class="form-group">
                    <label for="stuId">学号 (不可修改)</label>
                    <input type="text" id="stuId" name="stuId" value="${student.stuId}" readonly style="background-color: #eee;">
                </div>
                
                <div class="form-group">
                    <label for="stuName">姓名</label>
                    <input type="text" id="stuName" name="stuName" value="${student.stuName}" readonly style="background-color: #eee;">
                </div>
                
                <div class="form-group">
                    <label for="gender">性别</label>
                    <select id="gender" name="gender">
                        <option value="男" ${student.gender == '男' ? 'selected' : ''}>男</option>
                        <option value="女" ${student.gender == '女' ? 'selected' : ''}>女</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="age">年龄</label>
                    <input type="number" id="age" name="age" value="${student.age}" min="1" max="100">
                </div>
                
                <div class="form-group">
                    <label for="className">班级</label>
                    <input type="text" id="className" name="className" value="${student.className}">
                </div>
                
                <div class="form-group">
                    <label for="phone">联系电话</label>
                    <input type="tel" id="phone" name="phone" value="${student.phone}" required>
                </div>
                
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email" value="${student.email}" required>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-success">保存修改</button>
                    <a href="${pageContext.request.contextPath}/studentManage/list" class="btn btn-primary">取消</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
