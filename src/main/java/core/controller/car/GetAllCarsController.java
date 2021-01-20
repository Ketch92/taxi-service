package core.controller.car;

import core.lib.Injector;
import core.model.Car;
import core.service.car.CarService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllCarsController extends HttpServlet {
    private static final Injector injector
            = Injector.getInstance("core");
    private final CarService carService = (CarService) injector.getInstance(CarService.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Car> cars = carService.getAll();
        req.setAttribute("cars", cars);
        req.getRequestDispatcher("/WEB-INF/views/car/get_all.jsp").forward(req, resp);
    }
}
