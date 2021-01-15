package core;

import core.lib.Injector;
import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import core.service.car.CarService;
import core.service.driver.DriverService;
import core.service.manufacturer.ManufacturerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final Injector injector = Injector.getInstance(Main.class.getPackageName());
    
    public static void main(String[] args) {
        ManufacturerService manufacturerService
                = (ManufacturerService) injector.getInstance(ManufacturerService.class);
        
        CarService carService = (CarService) injector.getInstance(CarService.class);
        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        
        /**
        testManufacturerService(manufacturerService);
        testDriverService(driverService);
        testCarService(carService, manufacturerService.getAll(), driverService.getAll());
        */
        List<Driver> list = driverService.getAll();
        Manufacturer manufacturer = manufacturerService.get(17L);
        Car car = carService.get(6L);
        System.out.println(car);
        car.getDriverList().forEach(System.out::println);
        System.out.println(car.getDriverList().size());
//        car.setDriverList(list);
    }
    
    private static void testManufacturerService(ManufacturerService manufacturerService) {
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
        
        System.out.println(manufacturerService.get(5L).toString());
        System.out.println("it was the data of 3rd manufacturer");
        
        Manufacturer updated = new Manufacturer("FGT", "Volyn");
        updated.setId(0L);
        Manufacturer old = manufacturerService.update(updated);
        System.out.println("the manufacturer and 0 was updated, the old one " + old.toString());
        manufacturerService.getAll().forEach(System.out::println);
        
        manufacturerService.delete(2L);
        manufacturerService.delete(3L);
        
        System.out.println("Two manufacturers were removed");
        manufacturerService.getAll().forEach(System.out::println);
    }
    
    private static void testCarService(CarService carService,
                                       List<Manufacturer> manufacturers,
                                       List<Driver> drivers) {
        List<Car> cars = new ArrayList<>();
        for (Manufacturer manufacturer : manufacturers) {
            cars.add(new Car("BMV" + new Random().nextInt(250), manufacturer));
            cars.add(new Car("BMV" + new Random().nextInt(100), manufacturer));
        }
        
        cars.get(cars.size() - 1).setDriverList(drivers);
        
        for (Car car : cars) {
            carService.add(car);
        }
        
        System.out.println("\nPrint all cars from storage");
        carService.getAll().forEach(System.out::println);
        
        System.out.println("\nPrint car by index");
        System.out.println(cars.get(cars.size() - 1).toString());
        
        Car update = new Car("Some", new Manufacturer("Empty", "Poland"));
        update.setId(0L);
        Car old = carService.update(update);
        System.out.println("\nUpdate the 0 index car");
        System.out.println("Was " + old.toString());
        System.out.println("Now " + carService.get(0L).toString());
        
        System.out.println("\nremove car at with id = 1");
        carService.delete(1L);
        carService.getAll().forEach(System.out::println);
        
        System.out.println("\nNow we add driver to car with id 0");
        carService.addDriverToCar(new Driver("Oleh", "fhdajkh"), carService.get(0L));
        carService.getAll().forEach(System.out::println);
        
        System.out.println("\nRemove driver from car");
        System.out.println(carService.get((long) (cars.size() - 1)).toString() + " - car");
        System.out.println(drivers.get(0) + " - driver");
        carService.removeDriverFromCar(drivers.get(0), carService.get((long) (cars.size() - 1)));
        carService.getAll().forEach(System.out::println);
        
        System.out.println("\nGet cars by driver");
        carService.getAllByDriver(1L).forEach(System.out::println);
    }
    
    private static void testDriverService(DriverService driverService) {
        Driver bob = new Driver("Bob", "BobTheBest0001");
        Driver alice = new Driver("Alice", "AliceIsC00l");
        Driver john = new Driver("John", "JohnCosplayBohdan");
        Driver ironMan = new Driver("I'm Iron Man", "I love you 3000");
        Driver theHeroWhoDiesFirst = new Driver("Not important", "666");
        Driver actorWhoHasLeft = new Driver("Bob Johnson", "4134867");
        
        Driver[] drivers = new Driver[]{bob, alice, john, ironMan,
                theHeroWhoDiesFirst, actorWhoHasLeft};
        
        for (int i = 0; i < drivers.length; i++) {
            driverService.add(drivers[i]);
        }
        
        System.out.println("Print all added drivers");
        driverService.getAll().forEach(System.out::println);
        
        System.out.println("\nGet by id");
        System.out.println(driverService.get(theHeroWhoDiesFirst.getId()).toString());
        
        Driver actorChangedJonson = new Driver("John Bobson", "4154454");
        System.out.println("\nwe update " + actorWhoHasLeft.toString() + " by ");
        actorChangedJonson.setId(actorWhoHasLeft.getId());
        driverService.update(actorChangedJonson);
        System.out.println(driverService.get(actorWhoHasLeft.getId()).toString());
        
        System.out.println("\nNow we kill secondary hero");
        driverService.delete(theHeroWhoDiesFirst.getId());
        driverService.getAll().forEach(System.out::println);
        System.out.println("as we can see " + theHeroWhoDiesFirst.toString() + "isn't in the list");
    }
}
