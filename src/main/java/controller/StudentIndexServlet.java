package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/student/home")
public class StudentIndexServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentIndexServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("=== StudentIndexServlet 开始处理请求 ===");
        logger.info("请求路径: {}", request.getRequestURI());
        logger.info("请求方法: {}", request.getMethod());
        
        HttpSession session = request.getSession(false);
        
        if (session == null) {
            logger.warn("Session为空，重定向到登录页面");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        User loginUser = (User) session.getAttribute("loginUser");
        
        if (loginUser == null) {
            logger.warn("Session中loginUser为空，重定向到登录页面");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        logger.info("当前登录用户: username={}, role={}, userId={}", 
            loginUser.getUsername(), loginUser.getRole(), loginUser.getUserId());
        
        if (!"student".equals(loginUser.getRole())) {
            logger.warn("用户角色不是student，当前角色: {}", loginUser.getRole());
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        logger.info("权限验证通过，转发到学生首页");
        request.getRequestDispatcher("/student/index.jsp").forward(request, response);
        logger.info("=== StudentIndexServlet 处理完成 ===");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}