package core.dao.car;

import core.dao.Dao;
import core.model.Car;

import java.util.List;

public interface CarDao extends Dao<Car> {
    List<Car> getAllByDriver(Long driverId);
}
