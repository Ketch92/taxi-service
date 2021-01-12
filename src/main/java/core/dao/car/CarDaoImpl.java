package core.dao.car;

import core.lib.Dao;
import core.model.Car;
import core.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car add(Car car) {
        Storage.add(car);
        return car;
    }
    
    @Override
    public Optional<Car> get(Long id) {
        return Optional.ofNullable(Storage.carStorage.get(id));
    }
    
    @Override
    public List<Car> getAll() {
        return new ArrayList<>(Storage.carStorage.values());
    }
    
    @Override
    public Car update(Car car) {
        Car oldCar = Storage.carStorage.get(car.getId());
        Storage.carStorage.put(car.getId(), car);
        return oldCar;
    }
    
    @Override
    public boolean delete(Long id) {
        if (Storage.carStorage.containsKey(id)) {
            Storage.carStorage.remove(id);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean delete(Car car) {
        return delete(car.getId());
    }
}
