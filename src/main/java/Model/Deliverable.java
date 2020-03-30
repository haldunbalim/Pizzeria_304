package Model;

public class Deliverable extends AbstractModel {
    private final int did;
    private String name;
    private Double price;

    public Deliverable(Integer did, String name, Double price) {
        this.did = did;
        this.name = name;
        this.price = price;
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

    public String toString() {
        return name;
    }
}
