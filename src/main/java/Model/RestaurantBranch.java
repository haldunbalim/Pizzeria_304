package Model;

public class RestaurantBranch extends AbstractModel {
    private long bid;
    private String name;
    private Address address;

    public RestaurantBranch(long bid, String name, Address address) {
        this.bid = bid;
        this.name = name;
        this.address = address;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
