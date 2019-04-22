package com.katherine.automobiles.Presenters;

import android.view.View;

import com.katherine.automobiles.DataMappers.AutomobileMapper;
import com.katherine.automobiles.DataMappers.BodyStyleMapper;
import com.katherine.automobiles.DataMappers.BrandMapper;
import com.katherine.automobiles.DataMappers.DataMapper;
import com.katherine.automobiles.DataMappers.ManufacturerMapper;
import com.katherine.automobiles.Views.ActivityView;

import java.util.ArrayList;

/**
 * Presenter - для связи БД и главной активности MainActivity
 */

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
        AUTOMOBILE,
    }


    public MainActivityPresenter(ActivityView activityView) {
        this.activityView = activityView;
    }

    public void setDataModel(MAPPERS mapper) {
        switch (mapper){
            case AUTOMOBILE:
                dataModel = new AutomobileMapper();
                break;
            case BRAND:
                dataModel = new BrandMapper();
                break;
            case MANUFACTURER:
                dataModel = new ManufacturerMapper();
            case BODYSTYLE:
                dataModel = new BodyStyleMapper();
                break;
        }
    }

    public void detachView() {
        activityView = null;
    }

    public ArrayList<String[]> getCardContent(MAPPERS mapper){
        ArrayList<String[]> cardContent = new ArrayList<>();
        switch (mapper){
            case BRAND:
                dataModel = new BrandMapper();
                cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.CRITERIA.ALL, ""));
                break;
            case MANUFACTURER:
                dataModel = new ManufacturerMapper();
                cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.CRITERIA.ALL, ""));
                break;
            case AUTOMOBILE:
                dataModel = new AutomobileMapper();
                cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.CRITERIA.ALL, ""));
                break;
        }
        return cardContent;
    }

    public ArrayList<String[]> getCardContentSorted(MAPPERS mapper, FILTERS filter){
        ArrayList<String[]> cardContent = new ArrayList<>();
        switch (mapper){
            case AUTOMOBILE:
                dataModel = new AutomobileMapper();
                switch (filter){
                    case PRICE:
                        cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.CRITERIA.PRICE, ""));
                        break;
                }
                break;
        }
        return cardContent;
    }


    public ArrayList<String[]> searchQuery(FILTERS filter,String query){
        ArrayList<String[]> cardContent = new ArrayList<>();
        if(!query.isEmpty()) {
            switch (filter) {
                case AUTOMOBILE:
                    cardContent = dataModel.toStringArrayList(dataModel.find(DataMapper.CRITERIA.NAME, query));
                    break;
            }
            if (cardContent.isEmpty()) {
                activityView.showToast("Ничего не найдено!");
            }
        } else {
            cardContent = getCardContent(MAPPERS.AUTOMOBILE);
        }
        return cardContent;
    }

    public ArrayList<String[]> filter(FILTERS filter, ArrayList<String> query){
        ArrayList<String[]> cardContent = new ArrayList<>();

        if(!query.isEmpty()) {

            String strings = "";
            for(String string: query){
                if(!strings.isEmpty()) strings += ",";
                strings = strings + "'" + string + "'";
            }

            switch (filter) {
                case BRAND:
                    cardContent = dataModel.toStringArrayList(dataModel.filter(DataMapper.CRITERIA.BRAND, strings));
                    break;
                case MANUFACTURER:
                    cardContent = dataModel.toStringArrayList(dataModel.filter(DataMapper.CRITERIA.MANUFACTURER, strings));
                    break;
            }
            if (cardContent.isEmpty()) {
                activityView.showToast("Ничего не найдено!");
            }

        } else {
            if(dataModel.getClass() == BrandMapper.class)
                cardContent = getCardContent(MAPPERS.BRAND);
            if(dataModel.getClass() == AutomobileMapper.class)
                cardContent = getCardContent(MAPPERS.AUTOMOBILE);
        }
        return cardContent;
    }

    public void removeItem(String id){
        dataModel = new AutomobileMapper();
        dataModel.delete(id);
    }



}
