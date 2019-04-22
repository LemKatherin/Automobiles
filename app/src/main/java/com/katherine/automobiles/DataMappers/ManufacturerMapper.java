package com.katherine.automobiles.DataMappers;

import android.database.sqlite.SQLiteException;

import com.katherine.automobiles.Entities.CommonEntity;

import java.util.ArrayList;

public class ManufacturerMapper extends DataMapper {

    @Override
    public ArrayList<CommonEntity> find(CRITERIA criteria, String search) {
        ArrayList<CommonEntity> manufacturers = new ArrayList<>();

        switch (criteria) {
            case ALL:
                try {
                    dbadapter = dbadapter.open();
                    manufacturers = dbadapter.getManufacturers();
                } catch (SQLiteException ex) {

                }
                return manufacturers;
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
