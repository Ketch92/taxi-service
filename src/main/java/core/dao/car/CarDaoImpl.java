package core.dao.car;

import core.lib.Dao;
import core.model.Car;
import core.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car add(Car car) {
        Storage.add(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Optional.ofNullable(Storage.cars.get(id));
    }

    @Override
    public List<Car> getAll() {
        return new ArrayList<>(Storage.cars.values());
    }

    @Override
    public Car update(Car car) {
        Storage.cars.put(car.getId(), car);
        return car;
    }

    @Override
    public boolean delete(Long id) {
        if (Storage.cars.containsKey(id)) {
            Storage.cars.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Car car) {
        return delete(car.getId());
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return Storage.cars.values().stream()
                .filter(c -> c.getDriverList()
                        .stream()
                        .anyMatch(d -> d.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}
