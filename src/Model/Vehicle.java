package Model;

public class Vehicle {
    private String licensePlate;
    private String model;
    private String brand;

    public Vehicle(String licensePlate, String model, String brand) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
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
}
