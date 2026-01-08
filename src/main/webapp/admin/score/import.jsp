<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>批量导入 - 学生成绩管理系统</title>
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
        
        .form-group input[type="file"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        
        .form-group input[type="file"]:focus {
            outline: none;
            border-color: #667eea;
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
        
        .msg-box.success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .msg-box.error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .info-box {
            background: #e7f3ff;
            border: 1px solid #b8daff;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 20px;
        }
        
        .info-box h3 {
            color: #004085;
            margin-bottom: 10px;
            font-size: 16px;
        }
        
        .info-box p {
            color: #004085;
            line-height: 1.6;
            font-size: 14px;
        }
        
        .info-box code {
            background: #f8f9fa;
            padding: 2px 6px;
            border-radius: 3px;
            font-family: 'Courier New', monospace;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>批量导入</h1>
            <a href="${pageContext.request.contextPath}/admin/index.jsp" class="btn btn-primary">返回首页</a>
        </div>
        
        <div class="content">
            <c:if test="${not empty msg}">
                <div class="msg-box ${msgType}">${msg}</div>
            </c:if>
            
            <div class="info-box">
                <h3>导入说明</h3>
                <p>请上传CSV格式的文件，文件格式如下：</p>
                <p><code>学号,课程ID,课程名称,成绩,学期,学年</code></p>
                <p>例如：<code>2024001,C001,Java程序设计,85,第一学期,2024-2025</code></p>
            </div>
            
            <form action="${pageContext.request.contextPath}/scoreManage/import" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="file">选择CSV文件 *</label>
                    <input type="file" id="file" name="file" accept=".csv" required>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-success">导入</button>
                    <a href="${pageContext.request.contextPath}/scoreManage/list" class="btn btn-primary">取消</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
