<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${sessionScope.loginUser.role eq 'student' ? '我的成绩' : '成绩管理'} - 学生成绩管理系统</title>
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
            /* 根据角色显示不同的背景色 */
            background: ${sessionScope.loginUser.role eq 'student' ? 'linear-gradient(135deg, #11998e 0%, #38ef7d 100%)' : 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'};
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
            color: ${sessionScope.loginUser.role eq 'student' ? '#11998e' : '#667eea'};
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
        
        .search-box input, .search-box select {
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
        
        .score-excellent {
            color: #28a745;
            font-weight: bold;
        }
        
        .score-good {
            color: #17a2b8;
        }
        
        .score-pass {
            color: #ffc107;
        }
        
        .score-fail {
            color: #dc3545;
            font-weight: bold;
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
        
        .pagination {
            margin-top: 20px;
            display: flex;
            align-items: center;
            gap: 5px;
        }
        
        .pagination a, .pagination span {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-decoration: none;
            color: #667eea;
            display: inline-block;
            min-width: 35px;
            text-align: center;
        }
        
        .pagination a:hover {
            background: #667eea;
            color: white;
        }
        
        .pagination span.active {
            background: #667eea;
            color: white;
            border-color: #667eea;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>${sessionScope.loginUser.role eq 'student' ? '我的成绩' : '成绩列表'}</h1>
            <div class="header-actions">
                <c:choose>
                    <c:when test="${sessionScope.loginUser.role eq 'student'}">
                        <a href="${pageContext.request.contextPath}/student/home" class="btn btn-primary">返回首页</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/admin/index.jsp" class="btn btn-primary">返回首页</a>
                        <a href="${pageContext.request.contextPath}/scoreManage/add" class="btn btn-success">添加成绩</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
        <div class="content">
            <c:if test="${not empty msg}">
                <div class="msg-box ${msgType}">${msg}</div>
            </c:if>
            
            <%-- 搜索框仅对管理角色显示，或者根据需要对学生也显示 --%>
            <c:if test="${sessionScope.loginUser.role ne 'student'}">
                <div class="search-box">
                    <form action="${pageContext.request.contextPath}/scoreManage/list" method="get" style="display: flex; gap: 10px; flex: 1;">
                        <input type="text" name="keyword" placeholder="搜索学号、姓名或课程名称" value="${keyword}" style="flex: 1;">
                        <select name="semester">
                            <option value="">全部学期</option>
                            <option value="第一学期" ${semester == '第一学期' ? 'selected' : ''}>第一学期</option>
                            <option value="第二学期" ${semester == '第二学期' ? 'selected' : ''}>第二学期</option>
                        </select>
                        <select name="academicYear">
                            <option value="">全部学年</option>
                            <option value="2024-2025" ${academicYear == '2024-2025' ? 'selected' : ''}>2024-2025</option>
                            <option value="2023-2024" ${academicYear == '2023-2024' ? 'selected' : ''}>2023-2024</option>
                            <option value="2022-2023" ${academicYear == '2022-2023' ? 'selected' : ''}>2022-2023</option>
                        </select>
                        <button type="submit">搜索</button>
                    </form>
                </div>
            </c:if>
            
            <table>
                <thead>
                    <tr>
                        <c:if test="${sessionScope.loginUser.role ne 'student'}">
                            <th>学号</th>
                            <th>姓名</th>
                        </c:if>
                        <th>课程名称</th>
                        <th>成绩</th>
                        <th>学期</th>
                        <th>学年</th>
                        <c:if test="${sessionScope.loginUser.role ne 'student'}">
                            <th>操作</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${scores}" var="score">
                        <tr>
                            <c:if test="${sessionScope.loginUser.role ne 'student'}">
                                <td>${score.stuId}</td>
                                <td>${score.stuName}</td>
                            </c:if>
                            <td>${score.courseName}</td>
                            <td class="${score.score >= 90 ? 'score-excellent' : score.score >= 80 ? 'score-good' : score.score >= 60 ? 'score-pass' : 'score-fail'}">${score.score}</td>
                            <td>${score.semester}</td>
                            <td>${score.academicYear}</td>
                            <c:if test="${sessionScope.loginUser.role ne 'student'}">
                                <td>
                                    <a href="${pageContext.request.contextPath}/scoreManage/edit?id=${score.scoreId}" class="btn btn-warning">编辑</a>
                                    <a href="${pageContext.request.contextPath}/scoreManage/delete?id=${score.scoreId}" class="btn btn-danger" onclick="return confirm('确定要删除该成绩吗？')">删除</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="${pageContext.request.contextPath}/scoreManage/list?page=1&pageSize=${pageSize}">首页</a>
                        <a href="${pageContext.request.contextPath}/scoreManage/list?page=${currentPage - 1}&pageSize=${pageSize}">上一页</a>
                    </c:if>
                    
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="active">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/scoreManage/list?page=${i}&pageSize=${pageSize}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    
                    <c:if test="${currentPage < totalPages}">
                        <a href="${pageContext.request.contextPath}/scoreManage/list?page=${currentPage + 1}&pageSize=${pageSize}">下一页</a>
                        <a href="${pageContext.request.contextPath}/scoreManage/list?page=${totalPages}&pageSize=${pageSize}">尾页</a>
                    </c:if>
                    
                    <span style="margin-left: 20px; line-height: 35px;">
                        共 ${totalCount} 条记录，第 ${currentPage}/${totalPages} 页
                    </span>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>