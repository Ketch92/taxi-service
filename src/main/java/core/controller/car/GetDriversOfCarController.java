package core.controller.car;

import core.lib.Injector;
import core.model.Driver;
import core.service.car.CarService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetDriversOfCarController extends HttpServlet {
    private static final Injector injector
            = Injector.getInstance("core");
    private final CarService carService
            = (CarService) injector.getInstance(CarService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        List<Driver> drivers = carService.get(Long.valueOf(id)).getDriverList();
        req.setAttribute("drivers", drivers);
        req.getRequestDispatcher("/WEB-INF/views/car/drivers_of_car.jsp").forward(req, resp);
    }
}
