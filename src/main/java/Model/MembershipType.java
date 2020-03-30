package Model;

public enum MembershipType {
    BRONZE("Bronze", 0),
    SILVER("Silver", 0.05),
    GOLD("Gold", 0.1);

    private final String text;
    private final Double discountRate;

    MembershipType(final String text, final double discountRate) {
        this.text = text;
        this.discountRate = discountRate;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public Double getDiscountRate() {
        return discountRate;
    }
}
