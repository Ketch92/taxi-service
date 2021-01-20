package core.service.car;

import core.model.Car;
import core.model.Driver;
import core.service.Service;
import java.util.List;

public interface CarService extends Service<Car, Long> {
    Car add(Car car);
    
    Car get(Long id);
    
    List<Car> getAll();
    
    Car update(Car car);
    
    boolean delete(Long id);
    
    void addDriverToCar(Driver driver, Car car);
    
    void removeDriverFromCar(Driver driver, Car car);
    
    List<Car> getAllByDriver(Long driverId);
}
