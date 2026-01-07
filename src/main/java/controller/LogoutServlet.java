package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("=== LogoutServlet 开始处理登出请求 ===");
        logger.info("请求路径: {}", request.getRequestURI());
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("loginUser");
            if (user != null) {
                logger.info("用户登出成功: {}", user);
            }
            session.invalidate();
            logger.info("Session已销毁");
        } else {
            logger.info("无活跃Session");
        }
        
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("successMsg", "登出成功");
        logger.info("登出消息已设置，重定向到登录页面");
        
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        logger.info("=== LogoutServlet 处理完成 ===");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("LogoutServlet 收到POST请求，转发到GET处理");
        doGet(request, response);
    }
}
