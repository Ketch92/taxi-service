package core.dao.driver;

import core.lib.Dao;
import core.model.Driver;
import core.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver add(Driver driver) {
        Storage.add(driver);
        return driver;
    }
    
    @Override
    public Optional<Driver> get(Long id) {
        return Optional.ofNullable(Storage.drivers.get(id));
    }
    
    @Override
    public List<Driver> getAll() {
        return new ArrayList<>(Storage.drivers.values());
    }
    
    @Override
    public Driver update(Driver driver) {
        Driver oldDriver = Storage.drivers.get(driver.getId());
        Storage.drivers.put(driver.getId(), driver);
        return oldDriver;
    }
    
    @Override
    public boolean delete(Long id) {
        if (Storage.drivers.containsKey(id)) {
            Storage.drivers.remove(id);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean delete(Driver driver) {
        return delete(driver.getId());
    }
}
