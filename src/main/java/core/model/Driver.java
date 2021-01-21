package core.model;

import java.util.Objects;

public class Driver {
    private Long id;
    private String name;
    private String licenceNumber;
    private String login;
    private String password;
    
    public Driver(String name, String licenceNumber) {
        this.name = name;
        this.licenceNumber = licenceNumber;
    }
    
    private Driver(Long id, String name, String licenceNumber) {
        this.id = id;
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
    
    private String getLogin() {
        return login;
    }
    
    private void setLogin(String login) {
        this.login = login;
    }
    
    private String getPassword() {
        return password;
    }
    
    private void setPassword(String password) {
        this.password = password;
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
               && Objects.equals(licenceNumber, driver.licenceNumber)
               && Objects.equals(login, driver.login)
               && Objects.equals(password, driver.password);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, licenceNumber, login, password);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Driver{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", licenceNumber='").append(licenceNumber).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
