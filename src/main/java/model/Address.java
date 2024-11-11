package model;

public class Address {

    private String name;
    private int id;
    private Address parentAddress;

    public Address(String name, int id, Address parentAddress) {
        this.name = name;
        this.id = id;
        this.parentAddress = parentAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Address getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(Address parentAddress) {
        this.parentAddress = parentAddress;
    }
}
