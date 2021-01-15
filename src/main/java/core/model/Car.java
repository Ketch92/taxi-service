package core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Car {
    private Long id;
    private String model;
    private Manufacturer manufacturer;
    private List<Driver> driverList;
    
    public Car(String model, Manufacturer manufacturer) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.driverList = new ArrayList<>();
    }
    
    public Car(Long id, String model, Manufacturer manufacturer) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.driverList = new ArrayList<>();
    }
    
    public void addDriver(Driver driver) {
        driverList.add(driver);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Manufacturer getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public List<Driver> getDriverList() {
        return driverList;
    }
    
    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(id, car.id)
               && Objects.equals(model, car.model)
               && Objects.equals(manufacturer, car.manufacturer)
               && Objects.equals(driverList, car.driverList);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, model, manufacturer, driverList);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Car{");
        sb.append("id=").append(id);
        sb.append(", model='").append(model).append('\'');
        sb.append(", manufacturer=").append(manufacturer);
        sb.append(", driverList=").append(driverList);
        sb.append('}');
        return sb.toString();
    }
}
