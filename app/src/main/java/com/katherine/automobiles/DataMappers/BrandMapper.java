package com.katherine.automobiles.DataMappers;

import android.database.sqlite.SQLiteException;

import com.katherine.automobiles.Entities.CommonEntity;

import java.util.ArrayList;

public class BrandMapper extends DataMapper {
    @Override
    public ArrayList<CommonEntity> find(String criteria, String search) {

        ArrayList<CommonEntity> brands = new ArrayList<>();

        switch (criteria){
            case ALL:
                try {
                    dbadapter = dbadapter.open();
                    brands = dbadapter.getBrands();
                } catch (SQLiteException ex){

                }
                return brands;

                default:
                    return null;
        }
    }

    @Override
    public ArrayList<CommonEntity> filter(String criteria, String query) {
        ArrayList<CommonEntity> brands = new ArrayList<>();

        switch (criteria){
            case MANUFACTURER:
                try {
                    dbadapter = dbadapter.open();
                    brands = dbadapter.getBrandsOfManufacturer(query);
                } catch (SQLiteException ex){

                }
                return brands;
            default:
                return null;
        }
    }

    @Override
    public CommonEntity findById(String id) {
        return null;
    }

    @Override
    public void insert(CommonEntity entity) {

    }

    @Override
    public void update(CommonEntity entity) {

    }

    @Override
    public void delete(String id) {

    }
}
