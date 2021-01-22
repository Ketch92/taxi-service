package core.web.filters;

import core.lib.Injector;
import core.service.driver.DriverService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final Injector injector
            = Injector.getInstance("core");
    private static final String DRIVER_ID = "driverId";
    private final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
    
        String url = req.getServletPath();
        Long driverId = (Long) req.getSession().getAttribute(DRIVER_ID);
        if (url.equals(req.getContextPath() + "/login")
                || url.equals(req.getContextPath() + "/drivers/add")
                || driverId != null) {
            filterChain.doFilter(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
    
    @Override
    public void destroy() {
    }
}
