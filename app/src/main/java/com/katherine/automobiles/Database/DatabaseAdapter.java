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
        Cursor cursor = database.query("Automobiles",
                new String[]{"_id", "AutoModel", "_idBrand", "Photo", "ProductYear", "Seats", "_idBodyStyle", "FuelType", "Transmission", "Price"},
                null,null,null,null,"_idBrand");
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {


                Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(7), cursor.getString(8), cursor.getDouble(9));

                String idBrand = cursor.getString(2);
                String idBodyStyle = cursor.getString(6);

                Cursor smallCursor = database.rawQuery("select Brands._id, Brand, Manufacturers._id, Manufacturer  " +
                        "from Brands join Manufacturers on Brands._idManufacturer = Manufacturer._id " +
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

        Cursor cursor = database.query("Automobiles",
                new String[]{"_id", "AutoModel", "_idBrand", "Photo", "ProductYear", "Seats", "_idBodyStyle", "FuelType", "Transmission", "Price"},
                "_idBrand = ?",new String[] {id},null,null,"_idBrand");

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {


                Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(7), cursor.getString(8), cursor.getDouble(9));

                String idBrand = cursor.getString(2);
                String idBodyStyle = cursor.getString(6);

                Cursor smallCursor = database.rawQuery("select Brands._id, Brand, Manufacturers._id, Manufacturer  " +
                        "from Brands join Manufacturers on Brands._idManufacturer = Manufacturer._id " +
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

    public ArrayList<CommonEntity> getAutoesOfManufacturer(String id){
        ArrayList<CommonEntity> automobiles = new ArrayList<>();


        Cursor cursor = database.rawQuery("select _id ,AutoModel, _idBrand, Photo, ProductYear, Seats, _idBodyStyle, FuelType, Transmission, Price, _idManufacturer " +
                "from Automobiles join Brands on _idBrand = Brands._id " +
                "join Manufacturers on  _idManufacturer = Manufacturers._id " +
                "where Manufacturers._id = ?", new String[]{id});

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(7), cursor.getString(8), cursor.getDouble(9));

                String idBrand = cursor.getString(2);
                String idBodyStyle = cursor.getString(6);

                Cursor smallCursor = database.rawQuery("select Brands._id, Brand, Manufacturers._id, Manufacturer  " +
                        "from Brands join Manufacturers on Brands._idManufacturer = Manufacturer._id " +
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

    public CommonEntity getAuto(String id) {

        Cursor cursor = database.rawQuery("select _id ,AutoModel, _idBrand, Photo, ProductYear, Seats, _idBodyStyle, FuelType, Transmission, Price " +
                "from Automobiles " +
                "where _id = ?", new String[]{id});

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();

            Automobile automobile = new Automobile(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(7), cursor.getString(8), cursor.getDouble(9));

            String idBrand = cursor.getString(2);
            String idBodyStyle = cursor.getString(6);

            cursor = database.rawQuery("select Brands._id, Brand, Manufacturers._id, Manufacturer  " +
                    "from Brands join Manufacturers on Brands._idManufacturer = Manufacturer._id " +
                    "where Brands._id = ? ", new String[]{idBrand});
            cursor.moveToFirst();
            automobile.setBrand(new Brand(cursor.getInt(0), cursor.getString(1), new Manufacturer(cursor.getInt(2), cursor.getString(3))));


            cursor = database.rawQuery("select _id, BodyStyle  " +
                    "from BodyStyles " +
                    "where _id = ? ", new String[]{idBodyStyle});

            cursor.moveToFirst();
            automobile.setBodyStyle(new BodyStyle(cursor.getInt(0), cursor.getString(1)));

            cursor.close();
            return automobile;
        } else{
            cursor.close();
            return null;
        }

    }

    public void deleteAuto(String id){
        database.delete("Automobiles", "_id = ?", new String[]{id});
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
                "from Brands join Manufacturers on Brands._idManufacturer = Manufacturer._id " +
                "where Manufacturers._id = ? ", new String[]{id});

        cursor.moveToFirst();
        for(int i = 0; i<cursor.getCount(); i++){
            brands.add(new Brand(cursor.getInt(0), cursor.getString(1), new Manufacturer(cursor.getInt(2), cursor.getString(3))));
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
