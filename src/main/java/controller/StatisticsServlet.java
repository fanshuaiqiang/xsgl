package controller;

import Dao.ScoreDao;
import Dao.StudentDao;
import Dao.impl.ScoreDaoImpl;
import Dao.impl.StudentDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Score;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServlet.class);
    
    private boolean checkAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user != null && ("admin".equals(user.getRole()) || "teacher".equals(user.getRole()));
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("=== StatisticsServlet 开始处理统计请求 ===");
        logger.info("请求路径: {}", req.getRequestURI());
        
        if (!checkAdmin(req)) {
            logger.warn("统计请求被拒绝：无权限访问，用户角色不符合要求");
            resp.getWriter().write("无权限访问！");
            return;
        }
        
        logger.info("权限验证通过，开始统计数据");
        
        ScoreDao scoreDao = new ScoreDaoImpl();
        StudentDao studentDao = new StudentDaoImpl();
        
        List<Score> scoreList = scoreDao.getAllScores();
        int totalStudents = studentDao.getStudents().size();
        int totalScores = scoreList.size();
        
        logger.info("统计数据 - 学生总数: {}, 成绩记录数: {}", totalStudents, totalScores);
        
        double averageScore = 0;
        int maxScore = 0;
        int minScore = 100;
        int passCount = 0;
        int excellentCount = 0;
        int failCount = 0;
        
        if (!scoreList.isEmpty()) {
            int sum = 0;
            for (Score score : scoreList) {
                int scoreValue = score.getScore();
                sum += scoreValue;
                
                if (scoreValue > maxScore) {
                    maxScore = scoreValue;
                }
                if (scoreValue < minScore) {
                    minScore = scoreValue;
                }
                
                if (scoreValue >= 60) {
                    passCount++;
                } else {
                    failCount++;
                }
                
                if (scoreValue >= 85) {
                    excellentCount++;
                }
            }
            averageScore = (double) sum / totalScores;
        }
        
        double passRate = totalScores > 0 ? (double) passCount / totalScores * 100 : 0;
        double excellentRate = totalScores > 0 ? (double) excellentCount / totalScores * 100 : 0;
        
        Map<String, List<Score>> scoresByCourse = scoreList.stream()
            .collect(Collectors.groupingBy(Score::getCourseName));
        
        logger.info("统计结果 - 平均分: {}, 最高分: {}, 最低分: {}, 及格数: {}, 不及格数: {}, 优秀数: {}, 及格率: {}, 优秀率: {}",
            String.format("%.2f", averageScore), maxScore, minScore, passCount, failCount, excellentCount,
            String.format("%.2f", passRate), String.format("%.2f", excellentRate));
        
        req.setAttribute("totalStudents", totalStudents);
        req.setAttribute("totalScores", totalScores);
        req.setAttribute("averageScore", String.format("%.2f", averageScore));
        req.setAttribute("maxScore", maxScore);
        req.setAttribute("minScore", minScore);
        req.setAttribute("passCount", passCount);
        req.setAttribute("failCount", failCount);
        req.setAttribute("excellentCount", excellentCount);
        req.setAttribute("passRate", String.format("%.2f", passRate));
        req.setAttribute("excellentRate", String.format("%.2f", excellentRate));
        req.setAttribute("scoresByCourse", scoresByCourse);
        
        req.getRequestDispatcher("/admin/statistics.jsp").forward(req, resp);
        logger.info("=== StatisticsServlet 处理完成 ===");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("StatisticsServlet 收到POST请求，转发到GET处理");
        doGet(req, resp);
    }
}
