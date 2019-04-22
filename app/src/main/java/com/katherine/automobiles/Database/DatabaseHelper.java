package com.katherine.automobiles.Database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

/**
 * Класс для создания БД.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH; // полный путь к базе данных
    static final String DB_NAME = "AutomobilesDB.db";
    private static final int myVersion = 1; // версия базы данных
    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, myVersion);
        this.myContext = context;
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArrayList<String> tables = getSQLTables();
        for (String table: tables){
            db.execSQL(table);
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        ArrayList<String> tables = getSQLTables();
        for (String table: tables){
            db.execSQL(table);
        }
    }

    private ArrayList<String> getSQLTables() {

        ArrayList<String> tables = new ArrayList<>();

        AssetManager assetManager = myContext.getAssets();

        try {
            ArrayList<String> files = new ArrayList<>();
            String startPath = "tables";

            String[] listFiles = assetManager.list(startPath);
            for (String file : listFiles) {
                files.add(file);
            }

            BufferedReader bufferedReader;
            String query;
            String line;

            for (String file : files) {
                Log.d(TAG, "file db is " + file);
                bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(startPath + "/" + file)));
                query = "";
                while ((line = bufferedReader.readLine()) != null) {
                    query = query + line;
                }
                bufferedReader.close();
                tables.add(query);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ArrayList<String> files = new ArrayList<>();
            String startPath = "data";
            String[] listFiles = assetManager.list(startPath);
            for (String file : listFiles) {
                files.add(file);
            }

            BufferedReader bufferedReader;
            String query;
            String line;

            for (String file : files) {
                Log.d(TAG, "file db is " + file);
                bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(startPath + "/" + file)));
                query = "";
                while ((line = bufferedReader.readLine()) != null) {
                    query = query + line;
                }
                bufferedReader.close();
                tables.add(query);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tables;
    }
}
