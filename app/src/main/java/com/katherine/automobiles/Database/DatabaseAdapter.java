package com.katherine.automobiles.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.katherine.automobiles.Entities.Automobile;
import com.katherine.automobiles.Entities.BodyStyle;
import com.katherine.automobiles.Entities.Brand;
import com.katherine.automobiles.Entities.CommonEntity;
import com.katherine.automobiles.Entities.Manufacturer;

import java.util.ArrayList;

/**
 * Класс для работы с БД.
 * Открывает и закрывает соединение с БД.
 * Осуществляет запросы на получение, загрузку и изменение данных.
 * **/

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private static DatabaseAdapter instance = null;


    private DatabaseAdapter(){ }

    public void setDbHelper(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public static DatabaseAdapter getInstance(){
        if(instance == null){
            instance = new DatabaseAdapter();
        }
        return instance;
    }

    public DatabaseAdapter open(){
        database = dbHelper.getReadableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
        database.close();
    }


                        // АВТОМОБИЛИ
    public void addAuto(Automobile newAuto){

        ContentValues cv = new ContentValues();
        cv.put("AutoModel", newAuto.getName());
        cv.put("_idBrand", newAuto.getBrand().getId());
        cv.put("Photo", newAuto.getPhoto());
        cv.put("ProductYear", newAuto.getProductYear());
        cv.put("Seats", newAuto.getSeats());
        cv.put("_idBodyStyle", newAuto.getBodyStyle().getId());
        cv.put("FuelType", newAuto.getFuelType());
        cv.put("Transmission", newAuto.getTransmission());
        cv.put("Price", newAuto.getPrice());

        database.insert("Automobiles",null, cv);
    }

    public ArrayList<CommonEntity> getAutoes() {
        ArrayList<CommonEntity> automobiles = new ArrayList<>();

        Cursor cursor = database.rawQuery("select Automobiles._id, AutoModel, Photo, ProductYear, " +
                "Seats, FuelType, Transmission, Price, Brands._id, Brand, Manufacturers._id, Manufacturer, " +
                "BodyStyles._id, BodyStyle " +
                "from Automobiles join Brands on _idBrand = Brands._id " +
                "join Manufacturers on Brands._idManufacturer = Manufacturers._id " +
                "join BodyStyles on _idBodyStyle = BodyStyles._id " +
                "order by Brand", new String[]{});
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7));
                automobile.setBodyStyle(new BodyStyle(cursor.getInt(12), cursor.getString(13)));
                automobile.setBrand(new Brand(cursor.getInt(8), cursor.getString(9)));
                automobile.getBrand().setManufacturer(new Manufacturer(cursor.getInt(10), cursor.getString(11)));
                automobiles.add(automobile);
                cursor.moveToNext();
            }
            cursor.close();
            return automobiles;
        } else {
            cursor.close();
            return null;
        }
    }

    public ArrayList<CommonEntity> getAutoes(String search){
        ArrayList<CommonEntity> automobiles = new ArrayList<>();

        Cursor cursor = database.rawQuery("select Automobiles._id, AutoModel, Photo, ProductYear, " +
                "Seats, FuelType, Transmission, Price, Brands._id, Brand, Manufacturers._id, Manufacturer, " +
                "BodyStyles._id, BodyStyle " +
                "from Automobiles join Brands on _idBrand = Brands._id " +
                "join Manufacturers on Brands._idManufacturer = Manufacturers._id " +
                "join BodyStyles on _idBodyStyle = BodyStyles._id " +
                "where Brand || ' ' || Automodel like ?" +
                "order by Brand", new String[]{"%" + search + "%"});
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7));
                automobile.setBodyStyle(new BodyStyle(cursor.getInt(12), cursor.getString(13)));
                automobile.setBrand(new Brand(cursor.getInt(8), cursor.getString(9)));
                automobile.getBrand().setManufacturer(new Manufacturer(cursor.getInt(10), cursor.getString(11)));
                automobiles.add(automobile);
                cursor.moveToNext();
            }
            cursor.close();
            return automobiles;
        } else {
            cursor.close();
            return null;
        }
    }

    public ArrayList<CommonEntity> getPriceSorted(){
        ArrayList<CommonEntity> automobiles = new ArrayList<>();
        Cursor cursor = database.query("Automobiles",
                new String[]{"_id", "AutoModel", "_idBrand", "Photo", "ProductYear", "Seats", "_idBodyStyle", "FuelType", "Transmission", "Price"},
                null,null,null,null,"Price");
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {


                Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(7), cursor.getString(8), cursor.getDouble(9));

                String idBrand = cursor.getString(2);
                String idBodyStyle = cursor.getString(6);

                Cursor smallCursor = database.rawQuery("select Brands._id, Brand, Manufacturers._id, Manufacturer " +
                        "from Brands join Manufacturers on Brands._idManufacturer = Manufacturers._id " +
                        "where Brands._id = ? ", new String[]{idBrand});
                smallCursor.moveToFirst();
                automobile.setBrand(new Brand(smallCursor.getInt(0), smallCursor.getString(1), new Manufacturer(smallCursor.getInt(2), smallCursor.getString(3))));

                smallCursor = database.rawQuery("select _id, BodyStyle  " +
                        "from BodyStyles " +
                        "where _id = ? ", new String[]{idBodyStyle});

                smallCursor.moveToFirst();
                automobile.setBodyStyle(new BodyStyle(smallCursor.getInt(0), smallCursor.getString(1)));

                automobiles.add(automobile);
                smallCursor.close();

                cursor.moveToNext();
            }
            cursor.close();
            return automobiles;
        } else{
            cursor.close();
            return null;
        }
    }

    public ArrayList<CommonEntity> getAutoesOfBrand(String id){
        ArrayList<CommonEntity> automobiles = new ArrayList<>();

        Cursor cursor = database.rawQuery("select Automobiles._id, AutoModel, Photo, ProductYear, " +
                "Seats, FuelType, Transmission, Price, Brands._id, Brand, Manufacturers._id, Manufacturer, " +
                "BodyStyles._id, BodyStyle " +
                "from Automobiles join Brands on _idBrand = Brands._id " +
                "join Manufacturers on Brands._idManufacturer = Manufacturers._id " +
                "join BodyStyles on _idBodyStyle = BodyStyles._id " +
                "where _idBrand in (" + id + ")", new String[]{});

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7));
                automobile.setBodyStyle(new BodyStyle(cursor.getInt(12), cursor.getString(13)));
                automobile.setBrand(new Brand(cursor.getInt(8), cursor.getString(9)));
                automobile.getBrand().setManufacturer(new Manufacturer(cursor.getInt(10), cursor.getString(11)));
                automobiles.add(automobile);
                cursor.moveToNext();
            }
            cursor.close();
            return automobiles;
        } else {
            cursor.close();
            return null;
        }

    }

    public ArrayList<CommonEntity> getAutoesOfManufacturer(String id){
        ArrayList<CommonEntity> automobiles = new ArrayList<>();

        Cursor cursor = database.rawQuery("select Automobiles._id, AutoModel, Photo, ProductYear, " +
                "Seats, FuelType, Transmission, Price, Brands._id, Brand, Manufacturers._id, Manufacturer, " +
                "BodyStyles._id, BodyStyle " +
                "from Automobiles join Brands on _idBrand = Brands._id " +
                "join Manufacturers on Brands._idManufacturer = Manufacturers._id " +
                "join BodyStyles on _idBodyStyle = BodyStyles._id " +
                "where _idManufacturer in (" + id + ")", new String[]{});

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7));
                automobile.setBodyStyle(new BodyStyle(cursor.getInt(12), cursor.getString(13)));
                automobile.setBrand(new Brand(cursor.getInt(8), cursor.getString(9)));
                automobile.getBrand().setManufacturer(new Manufacturer(cursor.getInt(10), cursor.getString(11)));
                automobiles.add(automobile);
                cursor.moveToNext();
            }
            cursor.close();
            return automobiles;
        } else {
            cursor.close();
            return null;
        }

    }

    public CommonEntity getAuto(String id) {

        Cursor cursor = database.rawQuery("select Automobiles._id, AutoModel, Photo, ProductYear, " +
                "Seats, FuelType, Transmission, Price, Brands._id, Brand, Manufacturers._id, Manufacturer, " +
                "BodyStyles._id, BodyStyle " +
                "from Automobiles join Brands on _idBrand = Brands._id " +
                "join Manufacturers on Brands._idManufacturer = Manufacturers._id " +
                "join BodyStyles on _idBodyStyle = BodyStyles._id " +
                "where Automobiles._id = ? " +
                "order by Brand", new String[]{id});
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7));
            automobile.setBodyStyle(new BodyStyle(cursor.getInt(12), cursor.getString(13)));
            automobile.setBrand(new Brand(cursor.getInt(8), cursor.getString(9)));
            automobile.getBrand().setManufacturer(new Manufacturer(cursor.getInt(10), cursor.getString(11)));
            cursor.close();
            return automobile;
        } else {
            cursor.close();
            return null;
        }
    }

    public void deleteAuto(String id){
        database.delete("Automobiles", "_id = ?", new String[]{id});
    }

    public void updateAuto(Automobile auto){
        //auto.setPhoto("");

        ContentValues cv = new ContentValues();
        cv.put("AutoModel", auto.getName());
        cv.put("_idBrand", auto.getBrand().getId());
        cv.put("Photo", auto.getPhoto());
        cv.put("ProductYear", auto.getProductYear());
        cv.put("Seats", auto.getSeats());
        cv.put("_idBodyStyle", auto.getBodyStyle().getId());
        cv.put("FuelType", auto.getFuelType());
        cv.put("Transmission", auto.getTransmission());
        cv.put("Price", auto.getPrice());

        database.update("Automobiles", cv, "_id = ?", new String[]{String.valueOf(auto.getId())});
    }


                        // ПРОИЗВОДИТЕЛИ
    public ArrayList<CommonEntity> getManufacturers(){
        ArrayList<CommonEntity> manufacturers = new ArrayList<>();

        Cursor cursor = database.query("Manufacturers",
                new String[]{"_id", "Manufacturer"},
                null,null,null,null,"Manufacturer");
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            manufacturers.add(new Manufacturer(cursor.getInt(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return manufacturers;
    }


                        // МАРКИ АВТОМОБИЛЕЙ
    public ArrayList<CommonEntity> getBrandsOfManufacturer(String id) {
        ArrayList<CommonEntity> brands = new ArrayList<>();

        Cursor cursor = database.rawQuery("select Brands._id, Brand, Manufacturers._id, Manufacturer  " +
                "from Brands join Manufacturers on Brands._idManufacturer = Manufacturers._id " +
                "where _idManufacturer in (" + id + ")", new String[]{});

        cursor.moveToFirst();
        for(int i = 0; i<cursor.getCount(); i++){
            brands.add(new Brand(cursor.getInt(0), cursor.getString(1), new Manufacturer(cursor.getInt(2), cursor.getString(3))));
            cursor.moveToNext();
        }
        cursor.close();

        return brands;
    }

    public ArrayList<CommonEntity> getBrands(){
        ArrayList<CommonEntity> brands = new ArrayList<>();

        Cursor cursor = database.query("Brands",
                new String[]{"_id", "Brand", "_idManufacturer"},
                null,null,null,null,"Brand");
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            brands.add(new Brand(cursor.getInt(0), cursor.getString(1), new Manufacturer(cursor.getInt(2))));
            cursor.moveToNext();
        }
        cursor.close();
        return brands;
    }


                        // ТИПЫ КОРПУСА
    public ArrayList<CommonEntity> getBodyStyles(){
        ArrayList<CommonEntity> bodyStyles = new ArrayList<>();

        Cursor cursor = database.query("BodyStyles",
                new String[]{"_id", "BodyStyle"},
                null,null,null,null,"BodyStyle");
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            bodyStyles.add(new BodyStyle(cursor.getInt(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return bodyStyles;
    }


}
