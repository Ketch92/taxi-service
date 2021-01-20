package core.controller.car;

import core.lib.Injector;
import core.model.Car;
import core.model.Manufacturer;
import core.service.car.CarService;
import core.service.manufacturer.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCarController extends HttpServlet {
    private static final Injector injector
            = Injector.getInstance("core");
    private final CarService carService = (CarService) injector.getInstance(CarService.class);
    private final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/add.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String model = req.getParameter("model");
        String manufacturerName = req.getParameter("manufacturerName");
        String manufacturerCountry = req.getParameter("manufacturerCountry");
        Manufacturer manufacturer
                = manufacturerService.add(new Manufacturer(manufacturerName,
                manufacturerCountry));
        carService.add(new Car(model, manufacturer));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
