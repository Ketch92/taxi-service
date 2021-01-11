package core.service;

import core.dao.ManufacturerDaoIntf;
import core.lib.Inject;
import core.lib.Service;
import core.model.Manufacturer;
import java.util.List;

@Service
public class ManufacturerService implements ManufacturerServiceIntf {
    
    @Inject
    private ManufacturerDaoIntf dao;
    
    @Override
    public Manufacturer add(Manufacturer manufacturer) {
        return dao.add(manufacturer);
    }
    
    @Override
    public Manufacturer get(Long id) {
        return dao.get(id).get();
    }
    
    @Override
    public List<Manufacturer> getAll() {
        return dao.getAll();
    }
    
    @Override
    public Manufacturer update(Long id, Manufacturer manufacturer) {
        return dao.update(id, manufacturer);
    }
    
    @Override
    public Manufacturer delete(Long id) {
        return dao.delete(id);
    }
    
    @Override
    public Manufacturer delete(Manufacturer manufacturer) {
        return dao.delete(manufacturer);
    }
}
