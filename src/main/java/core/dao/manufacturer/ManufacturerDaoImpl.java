package core.dao.manufacturer;

import core.model.Manufacturer;
import core.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        Storage.add(manufacturer);
        return manufacturer;
    }
    
    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.ofNullable(Storage.manufacturers.get(id));
    }
    
    @Override
    public List<Manufacturer> getAll() {
        return new ArrayList<>(Storage.manufacturers.values());
    }
    
    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Storage.manufacturers.put(manufacturer.getId(), manufacturer);
        return manufacturer;
    }
    
    @Override
    public boolean delete(Long id) {
        if (Storage.manufacturers.containsKey(id)) {
            Storage.manufacturers.remove(id);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean delete(Manufacturer manufacturer) {
        return delete(manufacturer.getId());
    }
}
