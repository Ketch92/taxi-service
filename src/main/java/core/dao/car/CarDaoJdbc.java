package core.dao.car;

import core.dao.DaoUtils;
import core.lib.Dao;
import core.model.Car;
import core.model.DataProcessingException;
import core.model.Driver;
import core.model.ErrorMessages;
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
    private static final String GET_CARS_BY_DRIVER_EXCEPTION =
            "An error has occurred while retrieving data for driver id %d";
    
    @Override
    public Car add(Car car) {
        String insertCar = "INSERT INTO cars(model, manufacturer)"
                        + " VALUES(?, ?);";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement insertCarStatement = connection.prepareStatement(insertCar,
                         Statement.RETURN_GENERATED_KEYS)) {
            insertCarStatement.setString(1, car.getModel());
            insertCarStatement.setLong(2, car.getManufacturer().getId());
            insertCarStatement.executeUpdate();
            ResultSet resultSet = insertCarStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject("id", Long.class));
            }
            insertDrivers(car, connection);
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.ADD.getMessage(),
                            Car.class.getSimpleName(), car), e);
        }
        
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
                car.setDriverList(getDrivers(car, connection));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET.getMessage(),
                            Car.class.getSimpleName(), id), e);
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
                Car car = DaoUtils.parseToCar(resultSet);
                car.setDriverList(getDrivers(car, connection));
                returnList.add(car);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET_ALL.getMessage(),
                            Car.class.getSimpleName()), e);
        }
        return returnList;
    }
    
    @Override
    public Car update(Car car) {
        Manufacturer manufacturer = car.getManufacturer();
        String update = "UPDATE cars SET model = ?, manufacturer = ?"
                        + " WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(update)) {
            updateStatement.setString(1, car.getModel());
            updateStatement.setLong(2, car.getManufacturer().getId());
            updateStatement.setLong(3, car.getId());
            updateStatement.executeUpdate();
            removeDrivers(car, connection);
            insertDrivers(car, connection);
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
        List<Car> returnList = new ArrayList<>();
        String getAllByDriver = "SELECT cd.\"car_Id\" as carId, cars.model,"
                                + " cars.manufacturer as mfId, name, country"
                                + " FROM cars_drivers cd"
                                + " INNER JOIN cars ON cars.id = cd.\"car_Id\""
                                + " INNER JOIN manufacturers"
                                + " ON cars.manufacturer = manufacturers.id"
                                + " WHERE cd.\"driver_Id\" = ? AND cars.deleted = false";
        try (Connection connection = ConnectionUtils.getConnection();
                 PreparedStatement getAllStatement = connection.prepareStatement(getAllByDriver)) {
            getAllStatement.setLong(1, driverId);
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                Car car = DaoUtils.parseToCar(resultSet);
                car.setDriverList(getDrivers(car, connection));
                returnList.add(car);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(GET_CARS_BY_DRIVER_EXCEPTION, driverId), e);
        }
        return returnList;
    }
    
    private void insertDrivers(Car car, Connection connection) {
        String insert = "INSERT INTO cars_drivers(\"car_Id\", \"driver_Id\") VALUES ("
                        + car.getId() + ", ?);";
        try (PreparedStatement insertStatement = connection.prepareStatement(insert)) {
            for (Driver driver : car.getDriverList()) {
                insertStatement.setLong(1, driver.getId());
                int wasAdded = insertStatement.executeUpdate();
                if (wasAdded == 0) {
                    throw new DataProcessingException(String.format(INSERT_DRIVER_EXCEPTION,
                            driver.getId()));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String.format(INSERT_DRIVER_EXCEPTION,
                    car.getId()), e);
        }
    }
    
    private void removeDrivers(Car car, Connection connection) {
        String remove = "DELETE FROM cars_drivers WHERE \"car_Id\" = " + car.getId()
                + " AND EXISTS(SELECT id WHERE \"car_Id\" = ?)";
        try (PreparedStatement removeStatement = connection.prepareStatement(remove)) {
            removeStatement.setLong(1, car.getId());
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(REMOVE_DRIVER_EXCEPTION, car.getId()), e);
        }
    }
    
    private List<Driver> getDrivers(Car car, Connection connection) {
        String select = "SELECT DISTINCT drivers.id, drivers.name, drivers.licence_number"
                        + " FROM drivers"
                        + " INNER JOIN cars_drivers cd ON drivers.id = cd.\"driver_Id\""
                        + " WHERE deleted = false AND cd.\"car_Id\" = " + car.getId();
        List<Driver> list = new ArrayList<>();
        try (PreparedStatement selectDrivers = connection.prepareStatement(select)) {
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
