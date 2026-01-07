package controller;

import Dao.UserDao;
import Dao.impl.UserDaoImpl;
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
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("=== RegisterServlet 开始处理注册请求 ===");
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        
        logger.info("注册请求 - 用户名: {}, 角色: {}, 邮箱: {}", username, role, email);
        
        UserDao userDao = new UserDaoImpl();
        
        if (userDao.findUserByName(username) != null) {
            logger.warn("注册失败 - 用户名已存在: {}", username);
            request.setAttribute("msg", "注册失败，用户名已存在");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        User user = new User();
        user.setUserId(UUID.randomUUID().toString().replace("-", ""));
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEmail(email);
        
        logger.info("开始注册用户: {}", username);
        
        if (userDao.register(user)) {
            logger.info("注册成功 - 用户: {}, 角色: {}", username, role);
            HttpSession session = request.getSession(true);
            session.setAttribute("successMsg", "注册成功，请登录");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            logger.error("注册失败 - 用户: {}", username);
            request.setAttribute("msg", "注册失败");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
        
        logger.info("=== RegisterServlet 处理完成 ===");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("RegisterServlet 收到GET请求，转发到POST处理");
        doPost(request, response);
    }
}
