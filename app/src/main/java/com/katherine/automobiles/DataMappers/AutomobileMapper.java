package com.katherine.automobiles.DataMappers;

import android.database.sqlite.SQLiteException;

import com.katherine.automobiles.Entities.Automobile;
import com.katherine.automobiles.Entities.CommonEntity;
import java.util.ArrayList;

public class AutomobileMapper extends DataMapper {
    


    public AutomobileMapper() { }

    @Override
    public ArrayList find(String criteria, String search){
        ArrayList<CommonEntity> automobiles = new ArrayList<>();

        switch (criteria){
            case NAME:
                try{
                    dbadapter = dbadapter.open();
                    automobiles = dbadapter.getAutoes(search);
                } catch (SQLiteException ex){

                }
                return automobiles;
            case ALL:
                try{
                    dbadapter = dbadapter.open();
                    automobiles = dbadapter.getAutoes();
                } catch (SQLiteException ex){

                }
                return automobiles;

            case BRAND:
                try {
                    dbadapter = dbadapter.open();
                    automobiles = dbadapter.getAutoesOfBrand(search);
                } catch (SQLiteException ex){

                }
                return automobiles;

            case MANUFACTURER:
                try {
                    dbadapter = dbadapter.open();
                    automobiles = dbadapter.getAutoesOfManufacturer(search);
                } catch (SQLiteException ex){

                }
                return automobiles;
            case PRICE:
                try {
                    dbadapter = dbadapter.open();
                    automobiles = dbadapter.getPriceSorted();
                } catch (SQLiteException ex){

                }
                return automobiles;

                default:
                    return null;
        }
    }

    @Override
    public CommonEntity findById(String id) {
        CommonEntity automobile = null;
        try{
            dbadapter = dbadapter.open();
            automobile = dbadapter.getAuto(id);
        } catch (SQLiteException ex){

        }
        return automobile;
    }

    @Override
    public void insert(CommonEntity entity) {
        Automobile automobile = (Automobile) entity;
        try{
            dbadapter = dbadapter.open();
            dbadapter.addAuto(automobile);
        } catch (SQLiteException ex){

        }
    }


    @Override
    public void update(String criteria, CommonEntity entity) {
        switch (criteria){

        }
    }

    @Override
    public void delete(String id) {

        try{
            dbadapter = dbadapter.open();
            dbadapter.deleteAuto(id);
        } catch (SQLiteException ex){

        }

    }


}
