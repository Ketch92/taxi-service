package core.dao.car;

import core.dao.DaoUtils;
import core.model.ErrorMessages;
import core.lib.Dao;
import core.model.Car;
import core.model.DataProcessingException;
import core.model.Driver;
import core.model.Manufacturer;
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
    private static final String INSERT_DRIVER_EXCEPTION =
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
        String select = "SELECT cars.id as carId, model, manufacturer as mfId, name, country"
                        + " FROM cars INNER JOIN manufacturers mf on mf.id = cars.manufacturer"
                        + " WHERE (cars.id = ? AND cars.deleted = false);";
        Car car = null;
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(select)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                car = DaoUtils.parseToCar(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET.getMessage(),
                            Car.class.getSimpleName(), id), e);
        }
        if (car != null) {
            car.setDriverList(getDrivers(car));
        }
        return Optional.ofNullable(car);
    }
    
    @Override
    public List<Car> getAll() {
        List<Car> returnList = new ArrayList<>();
        String getAll = "SELECT cars.id as carId, model, manufacturer as mfId, name, country"
                        + " FROM cars INNER JOIN manufacturers mf on mf.id = cars.manufacturer"
                        + " WHERE cars.deleted = false";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(getAll)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                returnList.add(DaoUtils.parseToCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET_ALL.getMessage(),
                            Car.class.getSimpleName()), e);
        }
        for (Car car : returnList) {
            car.setDriverList(getDrivers(car));
        }
        return returnList;
    }
    
    @Override
    public Car update(Car car) {
        removeDrivers(car);
        insertDrivers(car);
        Manufacturer manufacturer = car.getManufacturer();
        String update = "UPDATE cars SET model = ?, manufacturer = ?"
                        + " WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(update)) {
            updateStatement.setString(1, car.getModel());
            updateStatement.setLong(2, car.getManufacturer().getId());
            updateStatement.setLong(3, car.getId());
            updateStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.UPDATE.getMessage(), car), exception);
        }
        return car;
    }
    
    @Override
    public boolean delete(Long id) {
        String delete = "UPDATE cars SET deleted = true WHERE id = ?";
        int updated;
        try (Connection con = ConnectionUtils.getConnection();
             PreparedStatement deleteStatement = con.prepareStatement(delete)) {
            deleteStatement.setLong(1, id);
            updated = deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.DELETE.getMessage(), Car.class.getSimpleName(), id), e);
        }
        return updated > 0;
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
                + " AND EXISTS(SELECT id WHERE \"carId\" = ?)";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement removeStatement = connection.prepareStatement(remove)) {
            removeStatement.setLong(1, car.getId());
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(REMOVE_DRIVER_EXCEPTION, car.getId()), e);
        }
    }
    
    private List<Driver> getDrivers(Car car) {
        String select = "SELECT DISTINCT drivers.id, name, licence_number"
                        + " FROM drivers INNER JOIN cars_drivers cd ON cd.\"carId\" = "
                        + car.getId() + " WHERE deleted = false";
        List<Driver> list = new ArrayList<>();
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement selectDrivers = connection.prepareStatement(select)) {
            ResultSet resultSet = selectDrivers.executeQuery();
            while (resultSet.next()) {
                list.add(DaoUtils.parseToDriver(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(GET_DRIVERS_EXCEPTION, car.getId()), e);
        }
        return list;
    }
}
