<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>学生列表 - 学生成绩管理系统</title>
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
            max-width: 1200px;
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
        
        .header-actions {
            display: flex;
            gap: 10px;
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
        
        .btn-danger {
            background: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background: #c82333;
        }
        
        .btn-warning {
            background: #ffc107;
            color: #212529;
        }
        
        .btn-warning:hover {
            background: #e0a800;
        }
        
        .content {
            padding: 30px;
        }
        
        .search-box {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
        }
        
        .search-box input {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        
        .search-box button {
            padding: 10px 20px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        
        .search-box button:hover {
            background: #5568d3;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        table th, table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        table th {
            background: #f8f9fa;
            font-weight: 600;
            color: #333;
        }
        
        table tr:hover {
            background: #f5f5f5;
        }
        
        .pagination {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            gap: 5px;
        }
        
        .pagination a, .pagination span {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 3px;
            text-decoration: none;
            color: #333;
        }
        
        .pagination a:hover {
            background: #667eea;
            color: white;
        }
        
        .pagination .active {
            background: #667eea;
            color: white;
        }
        
        .msg-box {
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            padding: 10px 20px;
            border-radius: 5px;
            z-index: 9999;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            font-size: 14px;
            transition: all 0.5s ease;
        }
        
        .msg-box.success {
            background: #28a745;
            color: white;
        }
        
        .msg-box.error {
            background: #dc3545;
            color: white;
        }
        
        .msg-box.hidden {
            opacity: 0;
            transform: translateX(-50%) translateY(-20px);
            pointer-events: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>学生列表</h1>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/admin/index.jsp" class="btn btn-primary">返回首页</a>
                <a href="${pageContext.request.contextPath}/studentManage/add" class="btn btn-success">添加学生</a>
            </div>
        </div>
        
        <div class="content">
            <c:if test="${not empty successMsg}">
                <div class="msg-box success" id="successMsg">${successMsg}</div>
                <c:remove var="successMsg" scope="session"/>
            </c:if>
            
            <c:if test="${not empty msg}">
                <div class="msg-box ${msgType}" id="msgBox">${msg}</div>
            </c:if>
            
            <div class="search-box">
                <form action="${pageContext.request.contextPath}/studentManage/list" method="get" style="display: flex; gap: 10px; flex: 1;">
                    <input type="text" name="keyword" placeholder="搜索学号或姓名" value="${keyword}">
                    <button type="submit">搜索</button>
                </form>
            </div>
            
            <table>
                <thead>
                    <tr>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>联系电话</th>
                        <th>班级</th>
                        <th>邮箱</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${students}" var="student">
                        <tr>
                            <td>${student.stuId}</td>
                            <td>${student.stuName}</td>
                            <td>${student.gender}</td>
                            <td>${student.age}</td>
                            <td>${student.phone}</td>
                            <td>${student.className}</td>
                            <td>${student.email}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/studentManage/edit?id=${student.stuId}" class="btn btn-warning">编辑</a>
                                <a href="${pageContext.request.contextPath}/studentManage/delete?id=${student.stuId}" class="btn btn-danger" onclick="return confirm('确定要删除该学生吗？')">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="${pageContext.request.contextPath}/studentManage/list?page=1&pageSize=${pageSize}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">首页</a>
                        <a href="${pageContext.request.contextPath}/studentManage/list?page=${currentPage - 1}&pageSize=${pageSize}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">上一页</a>
                    </c:if>
                    
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="active">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/studentManage/list?page=${i}&pageSize=${pageSize}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    
                    <c:if test="${currentPage < totalPages}">
                        <a href="${pageContext.request.contextPath}/studentManage/list?page=${currentPage + 1}&pageSize=${pageSize}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">下一页</a>
                        <a href="${pageContext.request.contextPath}/studentManage/list?page=${totalPages}&pageSize=${pageSize}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">末页</a>
                    </c:if>
                    
                    <span style="margin-left: 20px; line-height: 35px;">
                        共 ${totalCount} 条记录，第 ${currentPage}/${totalPages} 页
                    </span>
                </div>
            </c:if>
        </div>
    </div>
    
    <script>
        setTimeout(function() {
            var successMsg = document.getElementById('successMsg');
            var msgBox = document.getElementById('msgBox');
            
            if (successMsg) {
                successMsg.classList.add('hidden');
            }
            if (msgBox) {
                msgBox.classList.add('hidden');
            }
        }, 1000);
    </script>
</body>
</html>