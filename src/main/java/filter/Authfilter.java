package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({"/student/*", "/studentManage/*", "/scoreManage/*", "/statistics/*"})
public class Authfilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        
        // 要判断有没有登录
        HttpSession session = request.getSession(false);
        boolean loginFlag = false;
        String role = null;
        
        if (session != null) {
            Boolean sessionLoginFlag = (Boolean) session.getAttribute("loginFlag");
            if (sessionLoginFlag != null) {
                loginFlag = sessionLoginFlag;
            }
            
            // 从loginUser对象获取角色
            Object loginUser = session.getAttribute("loginUser");
            if (loginUser != null) {
                try {
                    // 使用反射获取role属性
                    java.lang.reflect.Field roleField = loginUser.getClass().getDeclaredField("role");
                    roleField.setAccessible(true);
                    role = (String) roleField.get(loginUser);
                } catch (Exception e) {
                    // 忽略反射异常
                }
            }
        }
        
        if (!loginFlag) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 管理员专属URL（增删改操作）
        boolean isAdminOnly = uri.contains("/add") || uri.contains("/delete")
                || uri.contains("/edit") || uri.contains("/update")
                || uri.contains("/import") || uri.contains("/statistics");

        // 普通用户访问管理员接口 → 拦截并提示
        if ("student".equals(role) && isAdminOnly) {
            // 排除学生自己的信息修改和成绩查询
            if (!uri.contains("studentManage/update") && !uri.contains("scoreManage/myScore")) {
                request.setAttribute("msg", "权限不足！仅管理员可执行该操作");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
        }
        
        // 只有通过所有验证才继续处理请求
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
