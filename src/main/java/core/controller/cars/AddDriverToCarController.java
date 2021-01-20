package core.controller.cars;

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
    private CarService carService = (CarService) injector.getInstance(CarService.class);
    private DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/add_driver_to_car.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String carId = req.getParameter("carId");
        String driverId = req.getParameter("driverId");
        if (carId.isEmpty() || driverId.isEmpty()) {
            errorInput(req, resp, "Empty input provided");
        }
        Driver driver = driverService.get(Long.valueOf(driverId));
        Car car = carService.get(Long.valueOf(carId));
        if (car == null || driver == null) {
            errorInput(req, resp, "The car or drivers with provided ids doesn't exist");
        }
        carService.addDriverToCar(driver, car);
        resp.sendRedirect(req.getContextPath() + "/");
    }
    
    private void errorInput(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("errorMessage", message);
        req.getRequestDispatcher("/WEB-INF/views/cars/add_driver_to_car.jsp")
                .forward(req, resp);
    }
}
