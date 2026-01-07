<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>统计分析 - 学生成绩管理系统</title>
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
        
        .content {
            padding: 30px;
        }
        
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 25px;
            border-radius: 10px;
            text-align: center;
        }
        
        .stat-card h3 {
            font-size: 14px;
            margin-bottom: 10px;
            opacity: 0.9;
        }
        
        .stat-card .value {
            font-size: 36px;
            font-weight: bold;
        }
        
        .stat-card.success {
            background: linear-gradient(135deg, #48bb78 0%, #38a169 100%);
        }
        
        .stat-card.warning {
            background: linear-gradient(135deg, #ed8936 0%, #dd6b20 100%);
        }
        
        .stat-card.danger {
            background: linear-gradient(135deg, #f56565 0%, #e53e3e 100%);
        }
        
        .section {
            margin-bottom: 30px;
        }
        
        .section h2 {
            color: #333;
            margin-bottom: 20px;
            font-size: 20px;
            border-bottom: 2px solid #667eea;
            padding-bottom: 10px;
        }
        
        .table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }
        
        .table th {
            background: #667eea;
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 500;
        }
        
        .table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        
        .table tr:hover {
            background: #f8f9fa;
        }
        
        .table tr:last-child td {
            border-bottom: none;
        }
        
        .empty {
            text-align: center;
            padding: 40px;
            color: #999;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>统计分析</h1>
            <a href="${pageContext.request.contextPath}/admin/index.jsp" class="btn btn-primary">返回首页</a>
        </div>
        
        <div class="content">
            <div class="stats-grid">
                <div class="stat-card">
                    <h3>学生总数</h3>
                    <div class="value">${totalStudents}</div>
                </div>
                
                <div class="stat-card">
                    <h3>成绩记录数</h3>
                    <div class="value">${totalScores}</div>
                </div>
                
                <div class="stat-card">
                    <h3>平均分</h3>
                    <div class="value">${averageScore}</div>
                </div>
                
                <div class="stat-card">
                    <h3>最高分</h3>
                    <div class="value">${maxScore}</div>
                </div>
                
                <div class="stat-card">
                    <h3>最低分</h3>
                    <div class="value">${minScore}</div>
                </div>
                
                <div class="stat-card success">
                    <h3>及格人数</h3>
                    <div class="value">${passCount}</div>
                </div>
                
                <div class="stat-card danger">
                    <h3>不及格人数</h3>
                    <div class="value">${failCount}</div>
                </div>
                
                <div class="stat-card warning">
                    <h3>优秀人数</h3>
                    <div class="value">${excellentCount}</div>
                </div>
                
                <div class="stat-card success">
                    <h3>及格率</h3>
                    <div class="value">${passRate}%</div>
                </div>
                
                <div class="stat-card warning">
                    <h3>优秀率</h3>
                    <div class="value">${excellentRate}%</div>
                </div>
            </div>
            
            <div class="section">
                <h2>按课程统计</h2>
                <c:choose>
                    <c:when test="${not empty scoresByCourse}">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>课程名称</th>
                                    <th>成绩记录数</th>
                                    <th>平均分</th>
                                    <th>最高分</th>
                                    <th>最低分</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${scoresByCourse}" var="entry">
                                    <c:set var="courseScores" value="${entry.value}"/>
                                    <c:set var="count" value="${0}"/>
                                    <c:set var="sum" value="${0}"/>
                                    <c:set var="max" value="${0}"/>
                                    <c:set var="min" value="${100}"/>
                                    
                                    <c:forEach items="${courseScores}" var="score">
                                        <c:set var="count" value="${count + 1}"/>
                                        <c:set var="sum" value="${sum + score.score}"/>
                                        <c:if test="${score.score > max}">
                                            <c:set var="max" value="${score.score}"/>
                                        </c:if>
                                        <c:if test="${score.score < min}">
                                            <c:set var="min" value="${score.score}"/>
                                        </c:if>
                                    </c:forEach>
                                    
                                    <c:set var="avg" value="${sum / count}"/>
                                    
                                    <tr>
                                        <td>${entry.key}</td>
                                        <td>${count}</td>
                                        <td>${avg}</td>
                                        <td>${max}</td>
                                        <td>${min}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="empty">暂无数据</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>
