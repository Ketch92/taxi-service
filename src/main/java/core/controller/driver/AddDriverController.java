package core.controller.driver;

import core.lib.Injector;
import core.model.Driver;
import core.service.driver.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDriverController extends HttpServlet {
    private static final Injector injector
            = Injector.getInstance("core");
    private final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/driver/add.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String driverName = req.getParameter("name");
        String driverLicence = req.getParameter("licence");
        String driverLogin = req.getParameter("login");
        String driverPassword = req.getParameter("password");
        driverService.add(new Driver(driverName, driverLicence, driverLogin, driverPassword));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
