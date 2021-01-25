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
        String login = resultSet.getObject("driver_login", String.class);
        String password = resultSet.getObject("driver_password", String.class);
        Driver driver = new Driver(name, licence, login, password);
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
        Long carId = resultSet.getObject("car_id", Long.class);
        String carModel = resultSet.getObject("car_model", String.class);
        Car car = new Car(carId, carModel,
                parseToManufacturer(resultSet));
    
        List<Driver> drivers = new ArrayList<>();
        resultSet.previous();
        while (resultSet.next()) {
            if (!resultSet.getObject("car_id", Long.class).equals(carId)) {
                resultSet.previous();
                break;
            }
            Driver driver = DaoUtils.parseToDriver(resultSet);
            if (driver.getId() != null) {
                drivers.add(driver);
            }
        }
        
        car.setDriverList(drivers);
        return car;
    }
}
