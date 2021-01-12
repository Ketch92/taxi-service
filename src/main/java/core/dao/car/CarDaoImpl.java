package core.dao.car;

import core.model.Car;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements CarDao {
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
}
