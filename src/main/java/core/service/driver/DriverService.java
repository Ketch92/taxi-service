package core.service.driver;

import core.model.Driver;
import core.service.Service;
import java.util.Optional;

public interface DriverService extends Service<Driver, Long> {
    Driver getByLogin(String login);
}
