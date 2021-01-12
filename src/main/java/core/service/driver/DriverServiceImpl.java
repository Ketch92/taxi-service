package core.service.driver;

import core.dao.driver.DriverDao;
import core.lib.Inject;
import core.lib.Service;
import core.model.Driver;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    @Inject
    private DriverDao dao;
    
    @Override
    public Driver add(Driver driver) {
        return dao.add(driver);
    }
    
    @Override
    public Driver get(Long id) {
        return dao.get(id).orElseThrow();
    }
    
    @Override
    public List<Driver> getAll() {
        return dao.getAll();
    }
    
    @Override
    public Driver update(Driver driver) {
        return dao.update(driver);
    }
    
    @Override
    public boolean delete(Long id) {
        return dao.delete(id);
    }
}
