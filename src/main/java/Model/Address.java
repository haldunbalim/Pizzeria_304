package Model;

public class Address {
    private String city;
    private String streetName;
    private String postalCode;
    private int houseNumber;

    public Address(String city, String streetName, String postalCode, int houseNumber) {
        this.city = city;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
    }

    public Address() {
        this.city = "Not Known";
        this.streetName = "Not Known";
        this.postalCode = "Not Known";
        this.houseNumber = -1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
}
