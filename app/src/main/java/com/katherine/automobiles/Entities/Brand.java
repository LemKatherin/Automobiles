package com.katherine.automobiles.Entities;

public class Brand extends CommonEntity {

    private Manufacturer manufacturer;

    public Brand() { }

    public Brand(int id, String name) {
        super(id, name);
    }

    public Brand(int id, String name, Manufacturer manufacturer) {
        super(id, name);
        this.manufacturer = manufacturer;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
