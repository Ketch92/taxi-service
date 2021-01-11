package core;

import core.lib.Injector;
import core.model.Manufacturer;
import core.service.ManufacturerServiceIntf;

public class Main {
    private static final Injector injector = Injector.getInstance(Main.class.getPackageName());
    
    public static void main(String[] args) {
        ManufacturerServiceIntf manufacturerService
                = (ManufacturerServiceIntf) injector.getInstance(ManufacturerServiceIntf.class);
    
        Manufacturer first = new Manufacturer("AMW", "Ukraine");
        Manufacturer second = new Manufacturer("BMW", "Ukraine");
        Manufacturer third = new Manufacturer("CMW", "Germany");
        Manufacturer nextAfterThird = new Manufacturer("GT", "Germany");
        
        manufacturerService.add(first);
        manufacturerService.add(second);
        manufacturerService.add(third);
        manufacturerService.add(nextAfterThird);
        System.out.println("4 manufacturers were added\n");
    
        manufacturerService.getAll().forEach(System.out::println);
        System.out.println("printed all manufacturers\n");
    
        System.out.println(manufacturerService.get(3L).toString());
        System.out.println("it was the data of 3rd manufacturer");
    
        Manufacturer old = manufacturerService.update(0L, new Manufacturer("FGT", "Volyn"));
        System.out.println("the manufacturer and 0 was updated, the old one " + old.toString());
        manufacturerService.getAll().forEach(System.out::println);
        
        manufacturerService.delete(2L);
        manufacturerService.delete(3L);
    
        System.out.println("Two manufacturers were removed");
        manufacturerService.getAll().forEach(System.out::println);
    }
}
