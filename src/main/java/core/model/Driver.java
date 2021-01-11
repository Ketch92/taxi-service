package core.model;

import java.util.Objects;

public class Driver {
    private Long id;
    private String name;
    private String licenceNumber;
    
    public Driver(String name, String licenceNumber) {
        this.name = name;
        this.licenceNumber = licenceNumber;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLicenceNumber() {
        return licenceNumber;
    }
    
    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id)
               && Objects.equals(name, driver.name)
               && Objects.equals(licenceNumber, driver.licenceNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, licenceNumber);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Driver{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", licenceNumber='").append(licenceNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
