package com.katherine.automobiles.Entities;

public class CommonEntity {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonEntity() { }

    public CommonEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CommonEntity(String name) {
        this.name = name;
    }

    public CommonEntity(int id) {
        this.id = id;
    }
}
