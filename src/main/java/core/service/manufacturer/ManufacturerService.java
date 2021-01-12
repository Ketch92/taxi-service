package core.service.manufacturer;

import core.model.Manufacturer;
import java.util.List;

public interface ManufacturerService {
    Manufacturer add(Manufacturer manufacturer);
    
    Manufacturer get(Long id);
    
    List<Manufacturer> getAll();
    
    Manufacturer update(Manufacturer manufacturer);
    
    boolean delete(Long id);
    
    boolean delete(Manufacturer manufacturer);
}
