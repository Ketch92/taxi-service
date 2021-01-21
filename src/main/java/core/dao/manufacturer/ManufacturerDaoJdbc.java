package core.dao.manufacturer;

import core.dao.DaoUtils;
import core.lib.Dao;
import core.model.exception.DataProcessingException;
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
                    .format(ErrorMessages.ADD.getMessage(), manufacturer), e);
        }
        return manufacturer;
    }
    
    @Override
    public Optional<Manufacturer> get(Long id) {
        String select = "SELECT id as manufacturer_id, "
                        + "name as manufacturer_name, country as manufacturer_country"
                        + " FROM manufacturers"
                        + " WHERE (id = ? AND deleted = false);";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(select)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(DaoUtils.parseToManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET.getMessage(),
                            Manufacturer.class.getSimpleName(), id), e);
        }
    }
    
    @Override
    public List<Manufacturer> getAll() {
        String select = "SELECT id as manufacturer_id, "
                        + "name as manufacturer_name, country as manufacturer_country"
                        + " FROM manufacturers"
                        + " WHERE deleted = false;";
        List<Manufacturer> resultList = new ArrayList<>();
        try (Connection con = ConnectionUtils.getConnection();
                PreparedStatement getAllStatement = con.prepareStatement(select)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(DaoUtils.parseToManufacturer(resultSet));
            }
            return resultList;
        } catch (SQLException e) {
            throw new DataProcessingException(String
                    .format(ErrorMessages.GET_ALL.getMessage(),
                            Manufacturer.class.getSimpleName()), e);
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
            throw new DataProcessingException(String
                    .format(ErrorMessages.UPDATE.getMessage(),
                            manufacturer), exception);
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
                    .format(ErrorMessages.DELETE.getMessage(),
                            Manufacturer.class.getSimpleName(), id), e);
        }
        return updated > 0;
    }
}
