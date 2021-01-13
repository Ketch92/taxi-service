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
public class ManufacturerDaoPostgres implements ManufacturerDao {
    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        String insert = "INSERT INTO manufacturers(name, country)"
                        + " VALUES(?, ?);";
        
        try (Connection con = ConnectionUtils.getConnection()) {
            PreparedStatement insertStatement = con.prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject("id", Long.class));
            }
            insertStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Failed to insert the %s manufacturer to database",
                            manufacturer.toString()),
                    e);
        }
        return manufacturer;
    }
    
    @Override
    public Optional<Manufacturer> get(Long id) {
        String select = "SELECT id, name, country"
                        + " FROM manufacturers"
                        + " WHERE (id = ? AND deleted = false);";
        try (Connection con = ConnectionUtils.getConnection()) {
            PreparedStatement getById = con.prepareStatement(select);
            getById.setLong(1, id);
            ResultSet resultSet = getById.executeQuery();
            getById.close();
            
            List<Manufacturer> fromResultSet = getFromResultSet(resultSet);
            if (fromResultSet.size() == 1) {
                return Optional.ofNullable(fromResultSet.get(0));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Fail to get manufacturer by id " + id, e);
        }
    }
    
    @Override
    public List<Manufacturer> getAll() {
        String select = "SELECT id, name, country"
                        + " FROM manufacturers"
                        + " WHERE deleted = false;";
        try (Connection con = ConnectionUtils.getConnection()) {
            PreparedStatement getAll = con.prepareStatement(select);
            ResultSet resultSet = getAll.executeQuery();
            getAll.close();
    
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all manufacturers from database", e);
        }
    }
    
    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String update = "UPDATE manufacturers SET name = ?,"
                        + " country = ? WHERE id = ? AND deleted = false";
        try (Connection con = ConnectionUtils.getConnection()) {
            PreparedStatement updateStatement = con.prepareStatement(update);
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException exception) {
            throw new DataProcessingException(
                    String.format("Failed to update the %s",
                            manufacturer.toString()
                    ),
                    exception);
        }
        return manufacturer;
    }
    
    @Override
    public boolean delete(Long id) {
        String delete = "UPDATE manufacturers SET deleted = true WHERE id = ?";
        int updated;
        try (Connection con = ConnectionUtils.getConnection()) {
            PreparedStatement deleteStatement = con.prepareStatement(delete);
            deleteStatement.setLong(1, id);
            updated = deleteStatement.executeUpdate();
            deleteStatement.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete the manufacturer at id = " + id, e);
        }
        return updated > 0;
    }
    
    @Override
    public boolean delete(Manufacturer manufacturer) {
        return delete(manufacturer.getId());
    }
    
    private List<Manufacturer> getFromResultSet(ResultSet resultSet) {
        List<Manufacturer> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(name, country);
                manufacturer.setId(id);
                list.add(manufacturer);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get values from resultSet", e);
        }
        return list;
    }
}
