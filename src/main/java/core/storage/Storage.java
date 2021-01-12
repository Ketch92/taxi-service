package core.storage;

import core.model.Manufacturer;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<Long, Manufacturer> manufacturersStorage = new HashMap<>();
    private static long manufacturerID;
    
    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(manufacturerID);
        manufacturersStorage.put(manufacturerID++, manufacturer);
    }
}
