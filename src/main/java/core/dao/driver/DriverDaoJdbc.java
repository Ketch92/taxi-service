package core.dao.driver;

import core.dao.DaoUtils;
import core.lib.Dao;
import core.model.DataProcessingException;
import core.model.Driver;
import core.model.ErrorMessages;
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
        String insert = "INSERT INTO drivers(name, licence_number, login, password)"
                        + " VALUES(?, ?, ?, ?);";
        try (Connection con = ConnectionUtils.getConnection();
                PreparedStatement insertStatement = con.prepareStatement(insert,
                            Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, driver.getName());
            insertStatement.setString(2, driver.getLicenceNumber());
            insertStatement.setString(3, driver.getLogin());
            insertStatement.setString(4, driver.getPassword());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject("id", Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.ADD.getMessage(),
                            Driver.class.getSimpleName(), driver), e);
        }
        return driver;
    }
    
    @Override
    public Optional<Driver> get(Long id) {
        String select = "SELECT id as driver_id, name as driver_name,"
                        + " licence_number as driver_licence, "
                        + " login as driver_login, "
                        + " password as driver_password"
                        + " FROM drivers"
                        + " WHERE (id = ? AND deleted = false);";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(select)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(DaoUtils.parseToDriver(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET.getMessage(), Driver.class.getSimpleName(), id), e);
        }
    }
    
    @Override
    public List<Driver> getAll() {
        String select = "SELECT id as driver_id, name as driver_name,"
                        + " licence_number as driver_licence,"
                        + " login as driver_login,"
                        + " password as driver_password"
                        + " FROM drivers"
                        + " WHERE deleted = false;";
        List<Driver> resultList = new ArrayList<>();
        try (Connection con = ConnectionUtils.getConnection();
                 PreparedStatement getAllStatement = con.prepareStatement(select)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(DaoUtils.parseToDriver(resultSet));
            }
            return resultList;
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET_ALL.getMessage(),
                    Driver.class.getSimpleName()), e);
        }
    }
    
    @Override
    public Driver update(Driver driver) {
        String update = "UPDATE drivers SET name = ?,"
                        + " licence_number = ?,"
                        + " login = ?,"
                        + " password = ?"
                        + " WHERE id = ? AND deleted = false";
        try (Connection con = ConnectionUtils.getConnection();
                 PreparedStatement updateStatement = con.prepareStatement(update)) {
            updateStatement.setString(1, driver.getName());
            updateStatement.setString(2, driver.getLicenceNumber());
            updateStatement.setString(3, driver.getLogin());
            updateStatement.setString(4, driver.getPassword());
            updateStatement.setLong(5, driver.getId());
            updateStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataProcessingException(String.format(ErrorMessages.UPDATE.getMessage(),
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
                    .format(ErrorMessages.DELETE.getMessage(),
                            Driver.class.getSimpleName(), id), e);
        }
        return updated > 0;
    }
}
