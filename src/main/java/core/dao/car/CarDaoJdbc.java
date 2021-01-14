package core.dao.car;

import core.lib.Dao;
import core.model.Car;
import core.model.DataProcessingException;
import core.model.Driver;
import core.utils.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoJdbc implements CarDao {
    @Override
    public Car add(Car car) {
        String insertCar = "INSERT INTO cars(model, manufacturer)"
                        + " VALUES(?, ?);";
        try (Connection con = ConnectionUtils.getConnection();
             PreparedStatement insertCarStatement = con.prepareStatement(insertCar,
                     Statement.RETURN_GENERATED_KEYS)) {
            insertCarStatement.setString(1, car.getModel());
            insertCarStatement.setLong(2, car.getManufacturer().getId());
            insertCarStatement.executeUpdate();
    
            ResultSet resultSet = insertCarStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject("id", Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format("Failed to insert the %s to database", car), e);
        }
        removeDrivers(car);
        insertDrivers(car);
        return car;
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
    public Car update(Car car) {
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return false;
    }
    
    @Override
    public boolean delete(Car car) {
        return false;
    }
    
    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return null;
    }
    
    private void insertDrivers(Car car) {
        String exceptionMessage = "An error occurred while adding driver with id = %d";
        String insert = "INSERT INTO cars_drivers(\"carId\", \"driverId\") VALUES ("
                        + car.getId() + ", ?);";
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insert)) {
            for (Driver driver : car.getDriverList()) {
                insertStatement.setLong(1, driver.getId());
                int wasAdded = insertStatement.executeUpdate();
                if (wasAdded == 0) {
                    throw new DataProcessingException(String.format(exceptionMessage, driver.getId()));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String.format(exceptionMessage, car.getId()), e);
        }
    }
    
    private void removeDrivers(Car car) {
        String exceptionMessage = "An error occurred removing drivers for car with id = %d";
        String remove = "DELETE FROM cars_drivers WHERE \"carId\" = " + car.getId()
                + " AND EXISTS(SELECT id WHERE \"carId\" = " + car.getId() + ")";
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement removeStatement = connection.prepareStatement(remove)) {
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(String.format(exceptionMessage, car.getId()), e);
        }
    }
}
