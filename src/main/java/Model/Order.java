package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Order extends AbstractModel {

    private long oid;
    private long date;
    private HashMap<Deliverable, Integer> deliverables = new HashMap<>();
    private User orderer;
    private OrderState orderState;

    public Order(long oid, User orderer, long date, ArrayList<Deliverable> deliverables, OrderState orderState) {
        this.oid = oid;
        this.orderer = orderer;
        this.date = date;
        this.orderState = orderState;
        for (Deliverable deliverable : deliverables) {
            if (!this.deliverables.containsKey(deliverable))
                this.deliverables.put(deliverable, 0);
            this.deliverables.put(deliverable, this.deliverables.get(deliverable) + 1);
        }
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

    public User getOrderer() {
        return orderer;
    }

    public void setOrderer(User orderer) {
        this.orderer = orderer;
    }

    public String orderedItemsText() {
        String st = "";
        for (Deliverable deliverable : deliverables.keySet()) {
            st += deliverable.toString() + " x" + deliverables.get(deliverable) + "\n";
        }
        return st;
    }

    public double getTotalPrice() {
        return getTotalPriceWithoutDiscount() * (1 - orderer.getMembershipType().getDiscountRate());
    }

    public double getTotalPriceWithoutDiscount() {
        double price = 0;
        for (Deliverable deliverable : deliverables.keySet()) {
            price += deliverable.getPrice() * deliverables.get(deliverable);
        }
        return price;
    }

    public Integer getUniqueItemCount() {
        return deliverables.keySet().size();
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}
