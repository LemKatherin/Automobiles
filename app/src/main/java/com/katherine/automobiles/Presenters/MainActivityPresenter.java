package com.katherine.automobiles.Presenters;

import android.view.View;

import com.katherine.automobiles.DataMappers.AutomobileMapper;
import com.katherine.automobiles.DataMappers.BrandMapper;
import com.katherine.automobiles.DataMappers.DataMapper;
import com.katherine.automobiles.DataMappers.ManufacturerMapper;
import com.katherine.automobiles.Database.DatabaseAdapter;
import com.katherine.automobiles.Views.ActivityView;

import java.util.ArrayList;

public class MainActivityPresenter {

    private ActivityView activityView;
    private DataMapper dataModel;

    public enum MAPPERS{
        AUTOMOBILE,
        BODYSTYLE,
        BRAND,
        MANUFACTURER
    }

    public enum FILTERS{
        PRICE,
        BRAND,
        MANUFACTURER,
        SEARCH
    }

   /* public MainActivityPresenter(DataMapper dataModel) {
        this.dataModel = dataModel;
    }*/

    public MainActivityPresenter(ActivityView activityView) {
        this.activityView = activityView;
    }

    public void setDataModel(DataMapper dataModel) {
        this.dataModel = dataModel;
    }

    public void attachView(ActivityView activityView) {
        this.activityView = activityView;
    }

    public void detachView() {
        activityView = null;
    }

    public void changeViewVisibility(View view){
        if(view.getVisibility() == View.GONE)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }

    public ArrayList<String[]> getCardContent(MAPPERS mapper){
        ArrayList<String[]> cardContent = new ArrayList<>();
        switch (mapper){
            case BRAND:
                dataModel = new BrandMapper();
                cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.ALL, ""));
                break;
            case MANUFACTURER:
                dataModel = new ManufacturerMapper();
                cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.ALL, ""));
                break;
            case AUTOMOBILE:
                dataModel = new AutomobileMapper();
                cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.ALL, ""));
                break;
        }
        return cardContent;
    }

    public ArrayList<String[]> getCardContentFilter(MAPPERS mapper, FILTERS filter){
        ArrayList<String[]> cardContent = new ArrayList<>();
        switch (mapper){
            case BRAND:
                dataModel = new BrandMapper();
                cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.ALL, ""));
                break;
            case MANUFACTURER:
                dataModel = new ManufacturerMapper();
                cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.ALL, ""));
                break;
            case AUTOMOBILE:
                dataModel = new AutomobileMapper();
                switch (filter){
                    case PRICE:
                        cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.PRICE, ""));
                        break;
                    case SEARCH:
                        cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.NAME, ""));
                        break;
                }
                break;
        }
        return cardContent;
    }

    public ArrayList<String[]> searchQuery(String query){
        ArrayList<String[]> cardContent = new ArrayList<>();
        if(!query.isEmpty()) {
            //cardContent = getCardContentFilter(MAPPERS.AUTOMOBILE, FILTERS.SEARCH);
            cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.NAME, query));
            if (cardContent.isEmpty()) {
                activityView.showToast("Ничего не найдено!");
            }
        } else {
            cardContent = getCardContent(MAPPERS.AUTOMOBILE);
        }
        return cardContent;
    }

    public void removeItem(String id){
        dataModel = new AutomobileMapper();
        dataModel.delete(id);
    }

}
