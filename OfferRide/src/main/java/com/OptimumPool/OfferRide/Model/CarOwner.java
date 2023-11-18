package com.OptimumPool.OfferRide.Model;


public class CarOwner {
    private int ownerId;
    private String name ;

    public int getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarOwner(int ownerId, String name) {
        this.ownerId = ownerId;
        this.name = name;
    }

    public CarOwner() {
    }
}
