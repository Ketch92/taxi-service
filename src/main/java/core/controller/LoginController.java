package core.controller;

import core.lib.Injector;
import core.model.Driver;
import core.model.exception.AuthenticationException;
import core.security.AuthenticationDriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController extends HttpServlet {
    private static final Injector injector
            = Injector.getInstance("core");
    private final AuthenticationDriverService authenticationDriverService
            = (AuthenticationDriverService) injector.getInstance(AuthenticationDriverService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/sign_in.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
    
        try {
            Driver driver = authenticationDriverService.login(login, password);
        } catch (AuthenticationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/sign_in.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
