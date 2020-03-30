package Model;

public class User {

    private long UID;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private Address address;
    private UserType userType;
    private MembershipType membershipType;
    private int membershipPoints;
    private RestaurantBranch affiliatedBranch;

    public User(long UID, String username, String password, String name, String surname, String phoneNumber, Address address, UserType userType, int membershipPoints, RestaurantBranch affiliatedBranch) {
        this.UID = UID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userType = userType;
        this.membershipPoints = membershipPoints;
        this.affiliatedBranch = affiliatedBranch;
        setMembershipType();
    }

    private void setMembershipType() {
        if (membershipPoints < 5000) {
            membershipType = MembershipType.BRONZE;
        } else if (membershipPoints < 10000) {
            membershipType = MembershipType.SILVER;
        } else {
            membershipType = MembershipType.GOLD;
        }
    }

    public int getMembershipPoints() {
        return membershipPoints;
    }

    public void setMembershipPoints(int membershipPoints) {
        this.membershipPoints = membershipPoints;
        setMembershipType();
    }

    public long getUID() {
        return UID;
    }

    public void setUID(long UID) {
        this.UID = UID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RestaurantBranch getAffiliatedBranch() {
        return affiliatedBranch;
    }

    public void setAffiliatedBranch(RestaurantBranch affiliatedBranch) {
        this.affiliatedBranch = affiliatedBranch;
    }
}
