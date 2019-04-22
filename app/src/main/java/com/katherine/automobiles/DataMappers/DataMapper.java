package com.katherine.automobiles.DataMappers;

import com.katherine.automobiles.Database.DatabaseAdapter;
import com.katherine.automobiles.Entities.CommonEntity;

import java.util.ArrayList;

public abstract class DataMapper {

    public enum CRITERIA{
        ALL,
        NAME,
        MANUFACTURER,
        BRAND,
        PRICE
    }


    DatabaseAdapter dbadapter = DatabaseAdapter.getInstance();

    public abstract ArrayList<CommonEntity> find(CRITERIA criteria, String search);

    public abstract CommonEntity findById(String id);
    public abstract void insert(CommonEntity entity);
    public abstract void update(CommonEntity entity);
    public abstract void delete(String id);

    public  ArrayList<CommonEntity> filter(CRITERIA criteria, String query){return  null;}

    public ArrayList<String[]> toStringArrayList(ArrayList<CommonEntity> listEntities){
        ArrayList<String[]> stringListEntities = new ArrayList<>();

        try {

            for (CommonEntity entity : listEntities) {
                stringListEntities.add(entity.toStringArray());
            }
        } catch (Exception ex){}

        return stringListEntities;
    }
}
