package core.dao;

import core.model.Manufacturer;
import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {
    Manufacturer add(Manufacturer manufacturer);
    
    Optional<Manufacturer> get(Long id);
    
    List<Manufacturer> getAll();
    
    Manufacturer update(Long id, Manufacturer manufacturer);

    Manufacturer delete(Long id);
    
    Manufacturer delete(Manufacturer manufacturer);
}
