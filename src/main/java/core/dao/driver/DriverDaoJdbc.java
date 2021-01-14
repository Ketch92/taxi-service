package core.dao.driver;

import core.lib.Dao;
import core.model.DataProcessingException;
import core.model.Driver;
import core.model.Manufacturer;
import core.utils.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        return Optional.empty();
    }
    
    @Override
    public List<Driver> getAll() {
        return null;
    }
    
    @Override
    public Driver update(Driver value) {
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return false;
    }
    
    @Override
    public boolean delete(Driver value) {
        return false;
    }
    
    private Manufacturer parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String licence = resultSet.getObject("licence", String.class);
        Manufacturer manufacturer = new Manufacturer(name, licence);
        manufacturer.setId(id);
        return manufacturer;
    }
}
