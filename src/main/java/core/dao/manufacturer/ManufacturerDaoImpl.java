package core.dao.manufacturer;

import core.lib.Dao;
import core.model.Manufacturer;
import core.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        Storage.add(manufacturer);
        return manufacturer;
    }
    
    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.ofNullable(Storage.manufacturersStorage.get(id));
    }
    
    @Override
    public List<Manufacturer> getAll() {
        return new ArrayList<>(Storage.manufacturersStorage.values());
    }
    
    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer oldValue = Storage.manufacturersStorage.get(manufacturer.getId());
        Storage.manufacturersStorage.put(manufacturer.getId(), manufacturer);
        return oldValue;
    }
    
    @Override
    public boolean delete(Long id) {
        if (Storage.manufacturersStorage.containsKey(id)) {
            Storage.manufacturersStorage.remove(id);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean delete(Manufacturer manufacturer) {
        return delete(manufacturer.getId());
    }
}
