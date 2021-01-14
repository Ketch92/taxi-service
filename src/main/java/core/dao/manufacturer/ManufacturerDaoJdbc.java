package core.dao.manufacturer;

import core.lib.Dao;
import core.model.DataProcessingException;
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
public class ManufacturerDaoJdbc implements ManufacturerDao {
    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        String insert = "INSERT INTO manufacturers(name, country)"
                        + " VALUES(?, ?);";
        try (Connection con = ConnectionUtils.getConnection();
                PreparedStatement insertStatement = con.prepareStatement(insert,
                Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject("id", Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format("Failed to insert the %s to database", manufacturer), e);
        }
        return manufacturer;
    }
    
    @Override
    public Optional<Manufacturer> get(Long id) {
        String select = "SELECT id, name, country"
                        + " FROM manufacturers"
                        + " WHERE (id = ? AND deleted = false);";
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement getByIdStatement = connection.prepareStatement(select)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturerFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format("Failed to get manufacturer by id = %d", id), e);
        }
    }
    
    @Override
    public List<Manufacturer> getAll() {
        String select = "SELECT id, name, country"
                        + " FROM manufacturers"
                        + " WHERE deleted = false;";
        List<Manufacturer> resultList = new ArrayList<>();
        try (Connection con = ConnectionUtils.getConnection();
             PreparedStatement getAllStatement = con.prepareStatement(select)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(getManufacturerFromResultSet(resultSet));
            }
            return resultList;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all manufacturers from database", e);
        }
    }
    
    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String update = "UPDATE manufacturers SET name = ?,"
                        + " country = ? WHERE id = ? AND deleted = false";
        try (Connection con = ConnectionUtils.getConnection();
             PreparedStatement updateStatement = con.prepareStatement(update)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataProcessingException(String.format("Failed to update the %s",
                    manufacturer.toString()), exception);
        }
        return manufacturer;
    }
    
    @Override
    public boolean delete(Long id) {
        String delete = "UPDATE manufacturers SET deleted = true WHERE id = ?";
        int updated;
        try (Connection con = ConnectionUtils.getConnection();
             PreparedStatement deleteStatement = con.prepareStatement(delete)) {
            deleteStatement.setLong(1, id);
            updated = deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format("Failed to delete the manufacturer with id = %s", id), e);
        }
        return updated > 0;
    }
    
    @Override
    public boolean delete(Manufacturer manufacturer) {
        return delete(manufacturer.getId());
    }
    
    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String country = resultSet.getObject("country", String.class);
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(id);
        return manufacturer;
    }
}
