package core.controller.manufacturer;

import core.lib.Injector;
import core.service.manufacturer.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteManufacturerController extends HttpServlet {
    private static final Injector injector
            = Injector.getInstance("core");
    private final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        manufacturerService.delete(Long.valueOf(id));
        resp.sendRedirect(req.getContextPath() + "/manufacturers");
    }
}
