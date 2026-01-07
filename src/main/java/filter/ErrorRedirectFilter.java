package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ErrorRedirectFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        chain.doFilter(request, response);

        int status = resp.getStatus();
        if (status == 404 || status == 500) {
            Integer errorCount = (Integer) session.getAttribute("errorCount");
            if (errorCount == null) {
                errorCount = 0;
            }
            errorCount++;
            session.setAttribute("errorCount", errorCount);

            if (errorCount >= 2) {
                session.removeAttribute("errorCount");
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }
        }
    }

    @Override
    public void destroy() {
    }
}
