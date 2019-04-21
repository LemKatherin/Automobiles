package com.katherine.automobiles.DataMappers;

import com.katherine.automobiles.Database.DatabaseAdapter;
import com.katherine.automobiles.Entities.CommonEntity;

import java.util.ArrayList;

public abstract class DataMapper {
    public final static String ALL = "ALL";
    public final static String NAME = "NAME";
    public final static String ID = "ID";
    public final static String MANUFACTURER = "MANUFACTURER";
    public final static String BRAND = "BRAND";
    public final static String PRICE = "PRICE";

    DatabaseAdapter dbadapter = DatabaseAdapter.getInstance();

    public abstract ArrayList<CommonEntity> find(String criteria, String search);

    public abstract CommonEntity findById(String id);
    public abstract void insert(CommonEntity entity);
    public abstract void update(String criteria, CommonEntity entity);
    public abstract void delete(String id);

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
