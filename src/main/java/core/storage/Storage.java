package core.storage;

import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<Long, Manufacturer> manufacturers = new HashMap<>();
    public static final Map<Long, Car> cars = new HashMap<>();
    public static final Map<Long, Driver> drivers = new HashMap<>();
    private static long manufacturerID;
    private static long carID;
    private static long driverID;
    
    public static void add(Manufacturer manufacturer) {
        manufacturer.setId(manufacturerID);
        manufacturers.put(manufacturerID++, manufacturer);
    }
    
    public static void add(Car car) {
        car.setId(carID);
        cars.put(carID++, car);
    }
    
    public static void add(Driver driver) {
        driver.setId(driverID);
        drivers.put(driverID++, driver);
    }
}
