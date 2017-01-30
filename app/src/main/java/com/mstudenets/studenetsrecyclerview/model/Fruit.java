package com.mstudenets.studenetsrecyclerview.model;


public class Fruit
{
    private String name;
    private String country;
    private double price;


    public Fruit() {
        this.name = "";
        this.country = "";
        this.price = 0.0;
    }

    public Fruit(String name, String country, double price) {
        this.name = name;
        this.country = country;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
