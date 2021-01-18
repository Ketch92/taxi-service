package core.dao;

import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtils {
    public static Driver parseToDriver(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("driver_id", Long.class);
        String name = resultSet.getObject("driver_name", String.class);
        String licence = resultSet.getObject("driver_licence", String.class);
        Driver driver = new Driver(name, licence);
        driver.setId(id);
        return driver;
    }
    
    public static Manufacturer parseToManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("manufacturer_id", Long.class);
        String name = resultSet.getObject("manufacturer_name", String.class);
        String country = resultSet.getObject("manufacturer_country", String.class);
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(id);
        return manufacturer;
    }
    
    public static Car parseToCar(ResultSet resultSet) throws SQLException {
        Long carId = resultSet.getObject("carId", Long.class);
        String model = resultSet.getObject("model", String.class);
        Car car = new Car(carId, model, parseToManufacturer(resultSet));
        return car;
    }
}
