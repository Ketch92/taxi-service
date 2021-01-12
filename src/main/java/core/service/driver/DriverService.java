package core.service.driver;

import core.model.Driver;
import java.util.List;

public interface DriverService {
    Driver add(Driver driver);
    
    Driver get(Long id);
    
    List<Driver> getAll();

    Driver update(Driver driver);
    
    boolean delete(Long id);
}
