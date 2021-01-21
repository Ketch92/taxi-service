package core.dao.driver;

import core.dao.Dao;
import core.model.Driver;
import java.util.Optional;

public interface DriverDao extends Dao<Driver, Long> {
    Optional<Driver> getByLogin(String login);
}
