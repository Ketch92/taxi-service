package core.controller.car;

import core.lib.Injector;
import core.model.Car;
import core.model.Driver;
import core.service.car.CarService;
import core.service.driver.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector
            = Injector.getInstance("core");
    private final CarService carService = (CarService) injector.getInstance(CarService.class);
    private final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/add_driver_to_car.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String carId = req.getParameter("carId");
        String driverId = req.getParameter("driverId");
        Driver driver = driverService.get(Long.valueOf(driverId));
        Car car = carService.get(Long.valueOf(carId));
        carService.addDriverToCar(driver, car);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
