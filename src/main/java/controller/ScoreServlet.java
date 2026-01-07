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
import jakarta.servlet.http.Part;
import model.Score;
import model.Student;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

@WebServlet("/scoreManage/*")
public class ScoreServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(ScoreServlet.class);
    
    private boolean checkAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user != null && ("admin".equals(user.getRole()) || "teacher".equals(user.getRole()));
    }
    
    private void addScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req)) {
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        String stuId = req.getParameter("stuId");
        String courseId = req.getParameter("courseId");
        String courseName = req.getParameter("courseName");
        String scoreStr = req.getParameter("score");
        String semester = req.getParameter("semester");
        String academicYear = req.getParameter("academicYear");
        
        if (stuId == null || stuId.trim().isEmpty() || 
            courseId == null || courseId.trim().isEmpty() || 
            courseName == null || courseName.trim().isEmpty() || 
            scoreStr == null || scoreStr.trim().isEmpty()) {
            req.setAttribute("msg", "请填写完整信息");
            req.getRequestDispatcher("/admin/score/add.jsp").forward(req, resp);
            return;
        }
        
        int score;
        try {
            score = Integer.parseInt(scoreStr);
            if (score < 0 || score > 100) {
                req.setAttribute("msg", "成绩必须在0-100之间");
                req.getRequestDispatcher("/admin/score/add.jsp").forward(req, resp);
                return;
            }
        } catch (NumberFormatException e) {
            req.setAttribute("msg", "成绩必须是数字");
            req.getRequestDispatcher("/admin/score/add.jsp").forward(req, resp);
            return;
        }
        
        ScoreDao scoreDao = new ScoreDaoImpl();
        Score scoreObj = new Score();
        scoreObj.setScoreId(UUID.randomUUID().toString().replace("-", ""));
        scoreObj.setStuId(stuId);
        scoreObj.setCourseId(courseId);
        scoreObj.setCourseName(courseName);
        scoreObj.setScore(score);
        scoreObj.setSemester(semester);
        scoreObj.setAcademicYear(academicYear);
        
        if (scoreDao.addScore(scoreObj)) {
            resp.sendRedirect(req.getContextPath() + "/scoreManage/list");
        } else {
            req.setAttribute("msg", "添加成绩失败");
            req.getRequestDispatcher("/admin/score/add.jsp").forward(req, resp);
        }
    }
    
    private void deleteScore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!checkAdmin(req)) {
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        String scoreId = req.getParameter("id");
        ScoreDao scoreDao = new ScoreDaoImpl();
        
        if (scoreDao.deleteScore(scoreId)) {
            resp.sendRedirect(req.getContextPath() + "/scoreManage/list");
        } else {
            resp.getWriter().write("删除失败！");
        }
    }
    
    private void editScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req)) {
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        String scoreId = req.getParameter("id");
        ScoreDao scoreDao = new ScoreDaoImpl();
        Score score = scoreDao.getScoreById(scoreId);
        
        if (score != null) {
            req.setAttribute("score", score);
            req.getRequestDispatcher("/admin/score/edit.jsp").forward(req, resp);
        } else {
            req.setAttribute("msg", "成绩不存在");
            resp.sendRedirect(req.getContextPath() + "/scoreManage/list");
        }
    }
    
    private void updateScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req)) {
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        String scoreId = req.getParameter("scoreId");
        String newScoreStr = req.getParameter("score");
        
        if (newScoreStr == null || newScoreStr.trim().isEmpty()) {
            req.setAttribute("msg", "成绩不能为空");
            req.getRequestDispatcher("/admin/score/edit.jsp").forward(req, resp);
            return;
        }
        
        int newScore;
        try {
            newScore = Integer.parseInt(newScoreStr);
            if (newScore < 0 || newScore > 100) {
                req.setAttribute("msg", "成绩必须在0-100之间");
                req.getRequestDispatcher("/admin/score/edit.jsp").forward(req, resp);
                return;
            }
        } catch (NumberFormatException e) {
            req.setAttribute("msg", "成绩必须是数字");
            req.getRequestDispatcher("/admin/score/edit.jsp").forward(req, resp);
            return;
        }
        
        ScoreDao scoreDao = new ScoreDaoImpl();
        
        if (scoreDao.updateScore(scoreId, newScore)) {
            resp.sendRedirect(req.getContextPath() + "/scoreManage/list");
        } else {
            req.setAttribute("msg", "修改失败");
            req.getRequestDispatcher("/admin/score/edit.jsp").forward(req, resp);
        }
    }
    
    private void listScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        
        // 请求计数，用于检测循环
        Integer requestCount = (Integer) session.getAttribute("scoreListRequestCount");
        if (requestCount == null) {
            requestCount = 0;
        }
        requestCount++;
        session.setAttribute("scoreListRequestCount", requestCount);
        
        logger.info("=== 开始查询成绩列表 (第{}次请求) ===", requestCount);
        
        // 如果请求次数过多，可能存在循环，打印警告
        if (requestCount > 5) {
            logger.error("检测到可能的循环查询！请求次数: {}，用户: {}", requestCount, user != null ? user.getUsername() : "未登录");
        }
        
        logger.info("请求URL: {}，查询参数: page={}, pageSize={}, keyword={}, semester={}, academicYear={}", 
            req.getRequestURL().toString(), 
            req.getParameter("page"), 
            req.getParameter("pageSize"),
            req.getParameter("keyword"),
            req.getParameter("semester"),
            req.getParameter("academicYear"));
        
        if (user == null) {
            logger.warn("查询成绩列表失败：用户未登录");
            session.removeAttribute("scoreListRequestCount");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        
        logger.info("当前用户: {}, 角色: {}", user.getUsername(), user.getRole());
        
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        
        int page = 1;
        int pageSize = 10;
        
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                logger.warn("页码格式错误: {}", pageStr);
                page = 1;
            }
        }
        
        if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSizeStr);
                if (pageSize < 1) {
                    pageSize = 10;
                }
            } catch (NumberFormatException e) {
                logger.warn("每页条数格式错误: {}", pageSizeStr);
                pageSize = 10;
            }
        }
        
        logger.info("查询参数 - page: {}, pageSize: {}", page, pageSize);
        
        String keyword = req.getParameter("keyword");
        String semester = req.getParameter("semester");
        String academicYear = req.getParameter("academicYear");
        
        logger.info("搜索参数 - keyword: {}, semester: {}, academicYear: {}", keyword, semester, academicYear);
        
        ScoreDao scoreDao = new ScoreDaoImpl();
        List<Score> scoreList;
        int totalCount;
        int totalPages;
        
        if ("student".equals(user.getRole())) {
            logger.info("学生查询个人成绩");
            StudentDao studentDao = new StudentDaoImpl();
            Student student = studentDao.getStudentById(user.getUsername());
            if (student == null) {
                logger.info("listScore: 通过username(学号)未查到学生，尝试通过userId查询: {}", user.getUserId());
                student = studentDao.getStudentById(user.getUserId());
            }
            
            if (student != null) {
                scoreList = scoreDao.getScoresByStudentIdByPage(student.getStuId(), page, pageSize);
                totalCount = scoreDao.getScoreCountByStudentId(student.getStuId());
                logger.info("学生学号: {}, 成绩数量: {}", student.getStuId(), totalCount);
            } else {
                logger.warn("listScore: 未找到学生信息，username: {}", user.getUsername());
                scoreList = new java.util.ArrayList<>();
                totalCount = 0;
            }
        } else {
            logger.info("管理员/教师查询所有成绩");
            boolean hasSearchParams = (keyword != null && !keyword.trim().isEmpty()) || 
                                      (semester != null && !semester.isEmpty()) || 
                                      (academicYear != null && !academicYear.isEmpty());
            
            if (hasSearchParams) {
                logger.info("执行搜索查询");
                scoreList = scoreDao.searchScores(keyword, semester, academicYear, page, pageSize);
                totalCount = scoreDao.getSearchScoreCount(keyword, semester, academicYear);
                logger.info("搜索结果数: {}", totalCount);
            } else {
                logger.info("查询全部成绩");
                scoreList = scoreDao.getAllScoresByPage(page, pageSize);
                totalCount = scoreDao.getTotalScoreCount();
                logger.info("成绩总数: {}", totalCount);
            }
        }
        
        totalPages = (int) Math.ceil((double) totalCount / pageSize);
        
        logger.info("查询结果 - 总记录数: {}, 总页数: {}, 当前页数据条数: {}", totalCount, totalPages, scoreList.size());
        
        req.setAttribute("scores", scoreList);
        req.setAttribute("currentPage", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("keyword", keyword);
        req.setAttribute("semester", semester);
        req.setAttribute("academicYear", academicYear);
        
        logger.info("查询完成，准备转发到JSP页面");
        // 重置请求计数器
        session.removeAttribute("scoreListRequestCount");
        
        // 统一转发到共用的列表页面
        req.getRequestDispatcher("/score/list.jsp").forward(req, resp);
        
        logger.info("JSP页面转发完成");
    }
    
    private void exportScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        
        ScoreDao scoreDao = new ScoreDaoImpl();
        List<Score> scoreList;
        
        if ("student".equals(user.getRole())) {
            StudentDao studentDao = new StudentDaoImpl();
            Student student = studentDao.getStudentById(user.getUsername());
        
            if (student != null) {
                scoreList = scoreDao.getScoresByStudentId(student.getStuId());
            } else {
                logger.warn("exportScore: 未找到学生信息，userId: {}", user.getUserId());
                scoreList = new java.util.ArrayList<>();
            }
        } else {
            scoreList = scoreDao.getAllScores();
        }
        
        resp.setContentType("text/csv;charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=scores.csv");
        
        java.io.PrintWriter out = resp.getWriter();
        
        if ("student".equals(user.getRole())) {
            out.println("课程ID,课程名称,成绩,学期,学年");
            
            for (Score score : scoreList) {
                out.println(score.getCourseId() + "," + score.getCourseName() + "," + 
                           score.getScore() + "," + score.getSemester() + "," + 
                           score.getAcademicYear());
            }
        } else {
            out.println("学号,姓名,课程ID,课程名称,成绩,学期,学年");
            
            for (Score score : scoreList) {
                out.println(score.getStuId() + "," + score.getStuName() + "," + 
                           score.getCourseId() + "," + score.getCourseName() + "," + 
                           score.getScore() + "," + score.getSemester() + "," + 
                           score.getAcademicYear());
            }
        }
        
        out.flush();
        out.close();
    }
    
    private void importScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req)) {
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        Part filePart = req.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            req.setAttribute("msg", "请选择文件");
            req.setAttribute("msgType", "error");
            req.getRequestDispatcher("/admin/score/import.jsp").forward(req, resp);
            return;
        }
        
        String fileName = filePart.getSubmittedFileName();
        if (!fileName.toLowerCase().endsWith(".csv")) {
            req.setAttribute("msg", "请上传CSV格式的文件");
            req.setAttribute("msgType", "error");
            req.getRequestDispatcher("/admin/score/import.jsp").forward(req, resp);
            return;
        }
        
        ScoreDao scoreDao = new ScoreDaoImpl();
        int successCount = 0;
        int failCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), "UTF-8"))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                if (line.isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length < 6) {
                    failCount++;
                    continue;
                }
                
                try {
                    String stuId = parts[0].trim();
                    String courseId = parts[1].trim();
                    String courseName = parts[2].trim();
                    int score = Integer.parseInt(parts[3].trim());
                    String semester = parts[4].trim();
                    String academicYear = parts[5].trim();
                    
                    if (score < 0 || score > 100) {
                        failCount++;
                        continue;
                    }
                    
                    Score scoreObj = new Score();
                    scoreObj.setScoreId(UUID.randomUUID().toString().replace("-", ""));
                    scoreObj.setStuId(stuId);
                    scoreObj.setCourseId(courseId);
                    scoreObj.setCourseName(courseName);
                    scoreObj.setScore(score);
                    scoreObj.setSemester(semester);
                    scoreObj.setAcademicYear(academicYear);
                    
                    if (scoreDao.addScore(scoreObj)) {
                        successCount++;
                    } else {
                        failCount++;
                    }
                } catch (Exception e) {
                    failCount++;
                }
            }
        } catch (Exception e) {
            req.setAttribute("msg", "文件读取失败：" + e.getMessage());
            req.setAttribute("msgType", "error");
            req.getRequestDispatcher("/admin/score/import.jsp").forward(req, resp);
            return;
        }
        
        req.setAttribute("msg", "导入完成！成功：" + successCount + " 条，失败：" + failCount + " 条");
        req.setAttribute("msgType", "success");
        req.getRequestDispatcher("/admin/score/import.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String path = req.getPathInfo();
        
        logger.info("=== ScoreServlet 开始处理请求 ===");
        logger.info("请求路径: {}", path);
        logger.info("请求方法: {}", req.getMethod());
        
        if (path == null || path.equals("/")) {
            path = "/list";
        }
        
        // 处理直接访问.jsp文件的情况，重定向到正确的Servlet路径
        if (path != null && path.endsWith(".jsp")) {
            logger.warn("检测到直接访问JSP文件: {}，重定向到正确路径", path);
            String redirectPath = path.replace(".jsp", "");
            resp.sendRedirect(req.getContextPath() + redirectPath);
            return;
        }
        
        logger.info("处理路径: {}", path);
        
        try {
            switch (path) {
                case "/add":
                    addScore(req, resp);
                    break;
                case "/delete":
                    deleteScore(req, resp);
                    break;
                case "/edit":
                    editScore(req, resp);
                    break;
                case "/update":
                    updateScore(req, resp);
                    break;
                case "/list":
                case "/myScore":
                    listScore(req, resp);
                    break;
                case "/export":
                    exportScore(req, resp);
                    break;
                case "/import":
                    importScore(req, resp);
                    break;
                default:
                    logger.warn("无效请求路径: {}", path);
                    resp.getWriter().write("无效请求");
            }
        } catch (Exception e) {
            logger.error("处理请求异常: " + path, e);
            resp.getWriter().write("处理请求时发生错误：" + e.getMessage());
        }
        
        logger.info("=== ScoreServlet 请求处理完成 ===");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
