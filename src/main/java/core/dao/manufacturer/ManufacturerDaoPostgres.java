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
        String insert = "INSERT INTO manufacturers(name,country)"
                        + " VALUES(?, ?)"
                        + " WHERE delete = false;";
        
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
                        + " WHERE id = ? AND delete = false;";
        try (Connection con = ConnectionUtils.getConnection()) {
            PreparedStatement getById = con.prepareStatement(select);
            getById.setLong(1, id);
            ResultSet resultSet = getById.executeQuery();
            List<Manufacturer> fromResultSet = getFromResultSet(resultSet);
            if (fromResultSet.size() == 1) {
                return Optional.ofNullable(fromResultSet.get(0));
            }
            resultSet.close();
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Fail to get manufacturer by id " + id, e);
        }
    }
    
    @Override
    public List<Manufacturer> getAll() {
        String select = "SELECT id, name, country"
                        + " FROM manufacturers"
                        + " WHERE delete == false;";
        try (Connection con = ConnectionUtils.getConnection()) {
            PreparedStatement getAll = con.prepareStatement(select);
            ResultSet resultSet = getAll.executeQuery();
            List<Manufacturer> all = getFromResultSet(resultSet);
            getAll.close();
            resultSet.close();
            return all;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all manufacturers from database", e);
        }
    }
    
    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return false;
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
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get values from resultSet", e);
        }
        return list;
    }
}
