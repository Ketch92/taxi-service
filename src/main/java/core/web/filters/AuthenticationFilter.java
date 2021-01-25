package core.web.filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final String DRIVER_ID = "driverId";
    private static final Set<String> allowedURLs = new HashSet<>();
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedURLs.add("/login");
        allowedURLs.add("/drivers/add");
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
    
        String url = req.getServletPath();
        Long driverId = (Long) req.getSession().getAttribute(DRIVER_ID);
        if (allowedURLs.contains(url) || driverId != null) {
            filterChain.doFilter(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
    
    @Override
    public void destroy() {
        allowedURLs.clear();
    }
}
