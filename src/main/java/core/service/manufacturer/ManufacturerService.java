package core.service.manufacturer;

import core.model.Manufacturer;
import core.service.Service;
import java.util.List;

public interface ManufacturerService extends Service<Manufacturer, Long> {
    Manufacturer add(Manufacturer manufacturer);
    
    Manufacturer get(Long id);
    
    List<Manufacturer> getAll();
    
    Manufacturer update(Manufacturer manufacturer);
    
    boolean delete(Long id);
}
