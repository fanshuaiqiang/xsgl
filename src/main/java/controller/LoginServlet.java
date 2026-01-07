package controller;

import Dao.UserDao;
import Dao.impl.UserDaoImpl;
import Dao.StudentDao;
import Dao.impl.StudentDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("=== LoginServlet 开始处理登录请求 ===");
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        logger.info("登录请求 - 用户名: {}, 密码长度: {}", username, password != null ? password.length() : 0);
        
        UserDao userDao = new UserDaoImpl();
        User loginUser = userDao.login(username, password);
        
        if (loginUser != null) {
            logger.info("登录成功 - 用户: {}, 角色: {}", loginUser.getUsername(), loginUser.getRole());
            
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);
            session.setAttribute("loginFlag", true);
            session.setAttribute("successMsg", "登录成功");
            
            if ("student".equals(loginUser.getRole())) {
                StudentDao studentDao = new StudentDaoImpl();
                Student student = studentDao.getStudentById(loginUser.getUsername());
                if (student != null) {
                    session.setAttribute("loginUserName", student.getStuName());
                }
            } else {
                session.setAttribute("loginUserName", loginUser.getUsername());
            }
            
            logger.info("Session信息已设置 - loginUser: {}, loginFlag: true", loginUser.getUsername());
            
            if ("admin".equals(loginUser.getRole()) || "teacher".equals(loginUser.getRole())) {
                logger.info("{}登录，跳转到管理员首页", "admin".equals(loginUser.getRole()) ? "管理员" : "教师");
                response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
            } else {
                    logger.info("学生登录，跳转到学生首页Servlet");
                    response.sendRedirect(request.getContextPath() + "/student/home");
                }
        } else {
            logger.warn("登录失败 - 用户名: {}", username);
            
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute("successMsg");
                logger.info("已清除session中的successMsg");
            }
             logger.warn("登录失败 - 用户名: {}", username);
            request.setAttribute("error", "请检查用户名和密码是否输入正确");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        
        logger.info("=== LoginServlet 处理完成 ===");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
