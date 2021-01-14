package core.dao.car;

import core.lib.Dao;
import core.model.Car;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoJdbc implements CarDao {
    @Override
    public Car add(Car value) {
        return null;
    }
    
    @Override
    public Optional<Car> get(Long id) {
        return Optional.empty();
    }
    
    @Override
    public List<Car> getAll() {
        return null;
    }
    
    @Override
    public Car update(Car value) {
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return false;
    }
    
    @Override
    public boolean delete(Car value) {
        return false;
    }
    
    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return null;
    }
}
