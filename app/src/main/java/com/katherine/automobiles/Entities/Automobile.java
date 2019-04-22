package com.katherine.automobiles.Entities;

public class Automobile extends CommonEntity {

    private Brand brand;
    private String photo;
    private int productYear;
    private int seats;
    private BodyStyle bodyStyle;
    private String fuelType;
    private String transmission;
    private double price;

    public Automobile() { }

    public Automobile(int id, String name) {
        super(id, name);
    }

    public Automobile(int id, String name, Brand brand, String photo, int productYear, int seats, BodyStyle bodyStyle, String fuelType, String transmission, double price) {
        super(id, name);
        this.brand = brand;
        this.photo = photo;
        this.productYear = productYear;
        this.seats = seats;
        this.bodyStyle = bodyStyle;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.price = price;
    }

    public Automobile(int id, String name, String photo, int productYear, int seats, String fuelType, String transmission, double price) {
        super(id, name);
        this.photo = photo;
        this.productYear = productYear;
        this.seats = seats;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.price = price;
    }

    public Automobile(String name) {
        super(name);
    }

    @Override
    public String[] toStringArray() {
        return new String[]{String.valueOf(id), brand.getName(), name, photo, String.valueOf(productYear), String.valueOf(seats),
        bodyStyle.getName(), fuelType, transmission, String.valueOf(price)};
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public BodyStyle getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(BodyStyle bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
