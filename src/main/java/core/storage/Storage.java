package core.storage;

import core.model.Manufacturer;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<Long, Manufacturer> storage = new HashMap<>();
    private static long id;
    
    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(id);
        storage.put(id++, manufacturer);
    }
}
