package Model;

import java.util.ArrayList;

public class Order {

    private long oid;
    private long date;
    private ArrayList<Deliverable> deliverables;
    private User orderer;
    private OrderState orderState;


    // TODO: Make order deliverables a counter dct
    public Order(long oid, User orderer, long date, ArrayList<Deliverable> deliverables, OrderState orderState) {
        this.oid = oid;
        this.orderer = orderer;
        this.date = date;
        this.deliverables = deliverables;
        this.orderState = orderState;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public ArrayList<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(ArrayList<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    public User getOrderer() {
        return orderer;
    }

    public void setOrderer(User orderer) {
        this.orderer = orderer;
    }

    public String orderedItemsText() {
        String st = "";
        for (Deliverable deliverable : deliverables) {
            st += deliverable.toString() + "\n";
        }
        return st;
    }

    public double getTotalPrice() {
        double price = 0;
        for (Deliverable deliverable : deliverables) {
            price += deliverable.getPrice();
        }
        price *= (1 - orderer.getMembershipType().getDiscountRate());
        return price;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}
