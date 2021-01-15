package core.dao.car;

import core.dao.DaoUtils;
import core.model.ErrorMessages;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoJdbc implements CarDao {
    public static final String INSERT_DRIVER_EXCEPTION =
            "An error occurred while adding driver with id = %d";
    private static final String REMOVE_DRIVER_EXCEPTION =
            "An error occurred removing drivers for car with id = %d";
    private static final String GET_DRIVERS_EXCEPTION =
            "An error has occurred while retrieving data for car id %d";
    
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
                    .format(ErrorMessages.ADD.getMessage(),
                            Car.class.getSimpleName(), car), e);
        }
        removeDrivers(car);
        insertDrivers(car);
        return car;
    }
    
    @Override
    public Optional<Car> get(Long id) {
        String select = "SELECT cars.id, model, manufacturer, name, country"
                        + " FROM cars INNER JOIN manufacturers m on m.id = cars.manufacturer"
                        + " WHERE (cars.id = ? AND cars.deleted = false);";
        Car car;
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(select)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
//                return Optional.of(parseResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET.getMessage(),
                            Car.class.getSimpleName(), id), e);
        }
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
    public List<Car> getAllByDriver(Long driverId) {
        return null;
    }
    
    private void insertDrivers(Car car) {
        String insert = "INSERT INTO cars_drivers(\"carId\", \"driverId\") VALUES ("
                        + car.getId() + ", ?);";
        try (Connection connection = ConnectionUtils.getConnection();
                 PreparedStatement insertStatement = connection.prepareStatement(insert)) {
            for (Driver driver : car.getDriverList()) {
                insertStatement.setLong(1, driver.getId());
                int wasAdded = insertStatement.executeUpdate();
                if (wasAdded == 0) {
                    throw new DataProcessingException(String.format(INSERT_DRIVER_EXCEPTION,
                            driver.getId()));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String.format(INSERT_DRIVER_EXCEPTION, car.getId()), e);
        }
    }
    
    private void removeDrivers(Car car) {
        String remove = "DELETE FROM cars_drivers WHERE \"carId\" = " + car.getId()
                + " AND EXISTS(SELECT id WHERE \"carId\" = " + car.getId() + ")";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement removeStatement = connection.prepareStatement(remove)) {
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(REMOVE_DRIVER_EXCEPTION, car.getId()), e);
        }
    }
    
    private List<Driver> getDrivers(Long carId) {
        String select = "SELECT DISTINCT drivers.id, name, licence_number"
                        + " FROM drivers INNER JOIN cars_drivers cd ON cd.\"carId\" = "
                        + carId + " WHERE deleted = false";
        List<Driver> list = new ArrayList<>();
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement selectDrivers = connection.prepareStatement(select)) {
            ResultSet resultSet = selectDrivers.executeQuery();
            while (resultSet.next()) {
                list.add(DaoUtils.parseToDriver(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(GET_DRIVERS_EXCEPTION, carId), e);
        }
        return list;
    }
}
