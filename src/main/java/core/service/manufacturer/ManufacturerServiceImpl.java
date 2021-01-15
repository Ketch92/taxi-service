package core.service.manufacturer;

import core.dao.manufacturer.ManufacturerDao;
import core.lib.Inject;
import core.lib.Service;
import core.model.Manufacturer;
import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    
    @Inject
    private ManufacturerDao dao;
    
    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        return dao.add(manufacturer);
    }
    
    @Override
    public Manufacturer get(Long id) {
        return dao.get(id).orElseThrow();
    }
    
    @Override
    public List<Manufacturer> getAll() {
        return dao.getAll();
    }
    
    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return dao.update(manufacturer);
    }
    
    @Override
    public boolean delete(Long id) {
        return dao.delete(id);
    }
}
