package Model;

public enum OrderState {
    DELIVERED("Delivered"),
    IN_DELIVERY("In Delivery"),
    PENDING("Pending");

    private final String text;

    OrderState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
