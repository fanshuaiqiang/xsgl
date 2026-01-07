<%--
  Created by IntelliJ IDEA.
  User: brmgc
  Date: 2026/1/7
  Time: 1:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>学生成绩列表 - 成绩管理系统</title>
  <style>
    /* 全局样式重置，与整套系统风格统一 */
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: "Microsoft YaHei", Arial, sans-serif;
    }

    /* 页面背景 */
    body {
      background-color: #f5f7fa;
      padding: 20px;
    }

    /* 头部容器：标题 + 操作按钮 */
    .header-container {
      max-width: 1200px;
      margin: 0 auto 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      gap: 10px;
    }

    /* 标题样式 */
    .page-title {
      color: #2d3748;
      font-size: 24px;
      font-weight: 600;
      position: relative;
      padding-bottom: 10px;
    }

    .page-title::after {
      content: "";
      display: block;
      width: 60px;
      height: 3px;
      background-color: #4299e1;
      margin-top: 5px;
      border-radius: 3px;
    }

    /* 按钮组样式 */
    .btn-group {
      display: flex;
      gap: 10px;
    }

    /* 通用按钮样式 */
    .btn {
      padding: 8px 16px;
      border-radius: 6px;
      text-decoration: none;
      font-size: 14px;
      font-weight: 500;
      cursor: pointer;
      transition: all 0.3s ease;
      border: none;
      display: inline-block;
    }

    /* 新增按钮（主按钮） */
    .btn-primary {
      background-color: #4299e1;
      color: #ffffff;
    }

    .btn-primary:hover {
      background-color: #3182ce;
      box-shadow: 0 2px 8px rgba(66, 153, 225, 0.3);
    }

    /* 返回按钮（次要按钮） */
    .btn-secondary {
      background-color: #e2e8f0;
      color: #2d3748;
    }

    .btn-secondary:hover {
      background-color: #cbd5e1;
    }

    /* 操作按钮：修改/删除 */
    .btn-edit {
      background-color: #48bb78;
      color: #fff;
      padding: 4px 10px;
      font-size: 13px;
      margin-right: 5px;
    }

    .btn-edit:hover {
      background-color: #38a169;
    }

    .btn-delete {
      background-color: #e53e3e;
      color: #fff;
      padding: 4px 10px;
      font-size: 13px;
    }

    .btn-delete:hover {
      background-color: #c53030;
    }

    /* 表格容器：卡片式布局 + 滚动适配 */
    .table-container {
      max-width: 1200px;
      margin: 0 auto;
      background-color: #ffffff;
      border-radius: 10px;
      box-shadow: 0 2px 15px rgba(0, 0, 0, 0.08);
      overflow-x: auto;
    }

    /* 表格样式 */
    .score-table {
      width: 100%;
      border-collapse: collapse;
      font-size: 14px;
    }

    /* 表头样式 */
    .score-table thead tr {
      background-color: #f8fafc;
    }

    .score-table th {
      padding: 15px 12px;
      text-align: left;
      color: #2d3748;
      font-weight: 600;
      border-bottom: 2px solid #e2e8f0;
      white-space: nowrap;
    }

    /* 表体样式 */
    .score-table td {
      padding: 12px;
      color: #4a5568;
      border-bottom: 1px solid #e2e8f0;
      white-space: nowrap;
    }

    /* 行hover效果 */
    .score-table tbody tr:hover {
      background-color: #f8fafc;
      transition: background-color 0.2s ease;
    }

    /* 最后一行去掉下边框 */
    .score-table tbody tr:last-child td {
      border-bottom: none;
    }

    /* 普通用户查看文本样式 */
    .view-only {
      color: #718096;
      font-style: italic;
    }

    /* 权限控制：管理员才显示新增/修改/删除 */
    .admin-only {
      display: none;
    }
    <c:if test="${loginUser.role == 'admin'}">
    .admin-only {
      display: inline-block;
    }
    </c:if>

    /* 响应式适配 */
    @media (max-width: 768px) {
      .header-container {
        flex-direction: column;
        align-items: flex-start;
      }
      .page-title {
        font-size: 20px;
      }
      .score-table th, .score-table td {
        padding: 10px 8px;
        font-size: 13px;
      }
      .btn {
        padding: 6px 12px;
        font-size: 13px;
      }
    }

    /* 空数据提示 */
    .empty-tip {
      text-align: center;
      padding: 30px;
      color: #718096;
      font-size: 14px;
    }
  </style>
</head>
<body>
<!-- 头部：标题 + 操作按钮 -->
<div class="header-container">
  <h1 class="page-title">学生成绩列表</h1>
  <div class="btn-group">
    <!-- 仅管理员显示新增成绩按钮 -->
    <a href="${pageContext.request.contextPath}/jsp/add.jsp" class="btn btn-primary admin-only">新增成绩</a>
    <a href="${pageContext.request.contextPath}/jsp/main.jsp" class="btn btn-secondary">返回首页</a>
  </div>
</div>

<!-- 成绩表格 -->
<div class="table-container">
  <table class="score-table">
    <thead>
    <tr>
      <th>课程号</th>
      <th>科目</th>
      <th>成绩</th>
      <th>学号</th>
      <th>姓名</th>
      <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <!-- 循环展示成绩数据 -->
    <c:forEach items="${scoreList}" var="score">
      <tr>
        <!-- 修复EL表达式：变量名小写，且用循环变量score取值 -->
        <td>${score.courseId}</td>
        <td>${score.courseName}</td>
        <td>${score.score}</td>
        <td>${score.stuId}</td>
        <td>${score.stuName}</td>
        <td>
          <!-- 管理员显示修改/删除，普通用户显示查看 -->
          <a href="updateScore.jsp?scoreId=${score.scoreId}" class="btn btn-edit admin-only">修改</a>
          <a href="${pageContext.request.contextPath}/ScoreServlet?action=delete&scoreId=${score.scoreId}" class="btn btn-delete admin-only" onclick="return confirm('确定删除该成绩记录吗？')">删除</a>
          <c:if test="${loginUser.role == 'user'}">
            <span class="view-only">查看</span>
          </c:if>
        </td>
      </tr>
    </c:forEach>

    <!-- 无数据时显示空提示 -->
    <c:if test="${empty scoreList}">
      <tr>
        <td colspan="6" class="empty-tip">暂无成绩数据</td>
      </tr>
    </c:if>
    </tbody>
  </table>
</div>
</body>
</html>