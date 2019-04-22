package com.katherine.automobiles.DataMappers;

import android.database.sqlite.SQLiteException;

import com.katherine.automobiles.Entities.CommonEntity;

import java.util.ArrayList;

public class BodyStyleMapper extends DataMapper {
    @Override
    public ArrayList<CommonEntity> find(CRITERIA criteria, String search) {
        ArrayList<CommonEntity> bodyStyles = new ArrayList<>();

        switch (criteria) {
            case ALL:
                try {
                    dbadapter = dbadapter.open();
                    bodyStyles = dbadapter.getBodyStyles();
                } catch (SQLiteException ex) {

                }
                return bodyStyles;
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
