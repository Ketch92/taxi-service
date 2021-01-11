package core.service;

import core.model.Manufacturer;
import java.util.List;

public interface ManufacturerService {
    Manufacturer add(Manufacturer manufacturer);
    
    Manufacturer get(Long id);
    
    List<Manufacturer> getAll();
    
    Manufacturer update(Long id, Manufacturer manufacturer);
    
    Manufacturer delete(Long id);
    
    Manufacturer delete(Manufacturer manufacturer);
}
