package Model;

public class Vehicle extends AbstractModel {
    private String licensePlate;
    private String model;
    private String brand;
    private boolean available;

    public Vehicle(String licensePlate, String model, String brand, boolean available) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
        this.available = available;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
