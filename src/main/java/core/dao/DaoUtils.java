package core.dao;

import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtils {
    public static Driver parseToDriver(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String licence = resultSet.getObject("licence_number", String.class);
        Driver driver = new Driver(name, licence);
        driver.setId(id);
        return driver;
    }
    
    public static Manufacturer parseToManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String country = resultSet.getObject("country", String.class);
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(id);
        return manufacturer;
    }
    
    public static Car parseToCar(ResultSet resultSet) throws SQLException {
        Long carId = resultSet.getObject("carId", Long.class);
        String model = resultSet.getObject("model", String.class);
        Long manufacturerId = resultSet.getObject("mfId", Long.class);
        String mfName = resultSet.getObject("name", String.class);
        String country = resultSet.getObject("country", String.class);
        Car car = new Car(carId, model, new Manufacturer(manufacturerId, mfName, country));
        return car;
    }
}
