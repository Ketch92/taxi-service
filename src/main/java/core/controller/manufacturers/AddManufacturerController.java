package core.controller.manufacturers;

import core.Main;
import core.lib.Injector;
import core.model.Manufacturer;
import core.service.manufacturer.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance(Main.class.getPackageName());
    private ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/manufacturers/add.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String manufacturerName = req.getParameter("name");
        String manufacturerCountry = req.getParameter("country");
        manufacturerService.add(new Manufacturer(manufacturerName, manufacturerCountry));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
