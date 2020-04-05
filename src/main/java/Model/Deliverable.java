package Model;

public class Deliverable extends AbstractModel {
    private final int did;
    private String name;
    private Double price;
    private Long bid;

    public Deliverable(Integer did, String name, Double price, Long bid) {
        this.did = did;
        this.name = name;
        this.price = price;
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDid() {
        return did;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String toString() {
        return name;
    }
}
