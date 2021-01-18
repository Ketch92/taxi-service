package core.dao;

import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        Long manufacturerID = resultSet.getObject("manufacturer_id", Long.class);
        String manufacturerName = resultSet.getObject("manufacturer_name", String.class);
        String manufacturerCountry = resultSet.getObject("manufacturer_country", String.class);
    
        Long carID = resultSet.getObject("car_id", Long.class);
        String carModel = resultSet.getObject("car_model", String.class);
        Car car = new Car(carID, carModel, new Manufacturer(manufacturerID, manufacturerName, manufacturerCountry));
    
        List<Driver> drivers = new ArrayList<>();
        Driver driver = DaoUtils.parseToDriver(resultSet);
        if (driver.getId() != null) {
            drivers.add(driver);
            while (resultSet.next()) {
                if (resultSet.getObject("car_id", Long.class) != carID) {
                    break;
                }
                drivers.add(DaoUtils.parseToDriver(resultSet));
            }
        }
        car.setDriverList(drivers);
        return car;
    }
}
