package core.service.driver;

import core.lib.Service;
import core.model.Driver;
import java.util.List;

@Service
public interface DriverService {
    Driver create(Driver driver);
    
    Driver get(Long id);
    
    List<Driver> getAll();

    Driver update(Driver driver);
    
    boolean delete(Long id);
}
