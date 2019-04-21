package com.katherine.automobiles.Entities;

import java.util.ArrayList;

public class CommonEntity {

    protected int id;
    protected String name = "";

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

    public String[] toStringArray(){
        return new String[]{String.valueOf(id), name};
    }

    public static String[] toNameArray(ArrayList<CommonEntity> entities){
        String[] names = new String[entities.size()];
        int i = 0;
        for (CommonEntity entity: entities){
            names[i] = entity.getName();
            i++;
        }
        return names;
    }

}
