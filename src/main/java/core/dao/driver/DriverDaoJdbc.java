package core.dao.driver;

import core.lib.Dao;
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
public class DriverDaoJdbc implements DriverDao {
    @Override
    public Driver add(Driver driver) {
        String insert = "INSERT INTO drivers(name, licence_number)"
                        + " VALUES(?, ?);";
        try (Connection con = ConnectionUtils.getConnection();
                PreparedStatement insertStatement = con.prepareStatement(insert,
                     Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, driver.getName());
            insertStatement.setString(2, driver.getLicenceNumber());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject("id", Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format("Failed to insert the %s to database", driver), e);
        }
        return driver;
    }
    
    @Override
    public Optional<Driver> get(Long id) {
        String select = "SELECT id, name, licence_number"
                        + " FROM drivers"
                        + " WHERE (id = ? AND deleted = false);";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(select)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseToDriver(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format("Failed to get driver by id = %d", id), e);
        }
    }
    
    @Override
    public List<Driver> getAll() {
        String select = "SELECT id, name, licence_number"
                        + " FROM drivers"
                        + " WHERE deleted = false;";
        List<Driver> resultList = new ArrayList<>();
        try (Connection con = ConnectionUtils.getConnection();
                 PreparedStatement getAllStatement = con.prepareStatement(select)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(parseToDriver(resultSet));
            }
            return resultList;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all drivers from database", e);
        }
    }
    
    @Override
    public Driver update(Driver driver) {
        String update = "UPDATE drivers SET name = ?,"
                        + " licence_number = ? WHERE id = ? AND deleted = false";
        try (Connection con = ConnectionUtils.getConnection();
                 PreparedStatement updateStatement = con.prepareStatement(update)) {
            updateStatement.setString(1, driver.getName());
            updateStatement.setString(2, driver.getLicenceNumber());
            updateStatement.setLong(3, driver.getId());
            updateStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataProcessingException(String.format("Failed to update the %s",
                    driver), exception);
        }
        return driver;
    }
    
    @Override
    public boolean delete(Long id) {
        String delete = "UPDATE drivers SET deleted = true WHERE id = ?";
        int updated;
        try (Connection con = ConnectionUtils.getConnection();
                 PreparedStatement deleteStatement = con.prepareStatement(delete)) {
            deleteStatement.setLong(1, id);
            updated = deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format("Failed to delete the driver with id = %s", id), e);
        }
        return updated > 0;
    }
    
    @Override
    public boolean delete(Driver driver) {
        return delete(driver.getId());
    }
    
    public static Driver parseToDriver(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String licence = resultSet.getObject("licence_number", String.class);
        Driver driver = new Driver(name, licence);
        driver.setId(id);
        return driver;
    }
}
