package core.dao.car;

import core.dao.DaoUtils;
import core.lib.Dao;
import core.model.Car;
import core.model.exception.DataProcessingException;
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
    private static final String GET_CARS_BY_DRIVER_EXCEPTION =
            "An error has occurred while retrieving data for driver id %d";
    
    @Override
    public Car add(Car car) {
        String insertCar = "INSERT INTO cars(model, manufacturer)"
                        + " VALUES(?, ?);";
        try (Connection connection = ConnectionUtils.getConnection()) {
            PreparedStatement insertCarStatement = connection.prepareStatement(insertCar,
                    Statement.RETURN_GENERATED_KEYS);
            insertCarStatement.setString(1, car.getModel());
            insertCarStatement.setLong(2, car.getManufacturer().getId());
            insertCarStatement.executeUpdate();
            ResultSet resultSet = insertCarStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject("id", Long.class));
            }
            insertCarStatement.close();
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
        String select = "SELECT cars.id as car_id, cars.model as car_model,"
                        + " cars.manufacturer as manufacturer_id,"
                        + " m.name as manufacturer_name, m.country as manufacturer_country,"
                        + " d.id as driver_id, d.name as driver_name,"
                        + " d.licence_number as driver_licence,"
                        + " d.login as driver_login,"
                        + " d.password as driver_password"
                        + " FROM cars"
                        + " LEFT JOIN manufacturers m ON m.id = cars.manufacturer"
                        + " LEFT JOIN cars_drivers cd ON cd.car_id = cars.id"
                        + " LEFT JOIN drivers d ON cd.driver_id = d.id"
                        + " WHERE cars.id = ? AND cars.deleted = false "
                        + "AND (d.deleted = false OR d IS NULL)";
        Car car = null;
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(select,
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)) {
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
        return Optional.ofNullable(car);
    }
    
    @Override
    public List<Car> getAll() {
        List<Car> returnList = new ArrayList<>();
        String getAll = "SELECT cars.id as car_id, cars.model as car_model,"
                        + " cars.manufacturer as manufacturer_id,"
                        + " m.name as manufacturer_name, m.country as manufacturer_country,"
                        + " d.id as driver_id, d.name as driver_name,"
                        + " d.licence_number as driver_licence,"
                        + " d.login as driver_login,"
                        + " d.password as driver_password"
                        + " FROM cars"
                        + " FULL JOIN manufacturers m ON m.id = cars.manufacturer"
                        + " FULL JOIN cars_drivers cd ON cd.car_id = cars.id"
                        + " FULL JOIN drivers d ON cd.driver_id = d.id"
                        + " WHERE cars.deleted = false AND (d.deleted = false OR d IS NULL)"
                        + " ORDER BY car_id";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(getAll,
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            if (resultSet.next()) {
                returnList = getListCarParser(resultSet);
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
        try (Connection connection = ConnectionUtils.getConnection()) {
            PreparedStatement updateStatement = connection.prepareStatement(update);
            updateStatement.setString(1, car.getModel());
            updateStatement.setLong(2, car.getManufacturer().getId());
            updateStatement.setLong(3, car.getId());
            updateStatement.executeUpdate();
            updateStatement.close();
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
        String getAllByDriver = "SELECT cars.id as car_id, cars.model as car_model,"
                                + " cars.manufacturer as manufacturer_id,"
                                + " m.name as manufacturer_name, m.country as manufacturer_country,"
                                + " d.id as driver_id, d.name as driver_name,"
                                + " d.licence_number as driver_licence,"
                                + " d.login as driver_login,"
                                + " d.password as driver_password"
                                + " FROM cars"
                                + " LEFT JOIN cars_drivers cd ON cd.car_id = cars.id"
                                + " LEFT JOIN drivers d on cd.driver_id = d.id"
                                + " LEFT JOIN manufacturers m ON m.id = cars.manufacturer"
                                + " WHERE cars.deleted = false AND d.deleted = false"
                                + " AND cars.id in (select car_id FROM cars_drivers "
                                + " WHERE driver_id = ?)"
                                + " ORDER BY cars.id";
        try (Connection connection = ConnectionUtils.getConnection();
                 PreparedStatement getAllStatement = connection.prepareStatement(getAllByDriver,
                         ResultSet.TYPE_SCROLL_SENSITIVE,
                         ResultSet.CONCUR_READ_ONLY)) {
            getAllStatement.setLong(1, driverId);
            ResultSet resultSet = getAllStatement.executeQuery();
            if (resultSet.next()) {
                returnList = getListCarParser(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(GET_CARS_BY_DRIVER_EXCEPTION, driverId), e);
        }
        return returnList;
    }
    
    private void insertDrivers(Car car, Connection connection) {
        String insert = "INSERT INTO cars_drivers(car_id, driver_id)"
                        + " VALUES (?, ?);";
        try (PreparedStatement insertStatement = connection.prepareStatement(insert)) {
            for (Driver driver : car.getDriverList()) {
                insertStatement.setLong(1, car.getId());
                insertStatement.setLong(2, driver.getId());
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
        String remove = "DELETE FROM cars_drivers WHERE car_id = ?;";
        try (PreparedStatement removeStatement = connection.prepareStatement(remove)) {
            removeStatement.setLong(1, car.getId());
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(REMOVE_DRIVER_EXCEPTION, car.getId()), e);
        }
    }
    
    private List<Car> getListCarParser(ResultSet resultSet) throws SQLException {
        List<Car> cars = new ArrayList<>();
        cars.add(DaoUtils.parseToCar(resultSet));
        while (resultSet.next()) {
            cars.add(DaoUtils.parseToCar(resultSet));
        }
        return cars;
    }
}
