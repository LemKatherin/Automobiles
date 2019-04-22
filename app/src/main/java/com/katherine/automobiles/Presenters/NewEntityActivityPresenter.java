package com.katherine.automobiles.Presenters;

import com.katherine.automobiles.DataMappers.AutomobileMapper;
import com.katherine.automobiles.DataMappers.BodyStyleMapper;
import com.katherine.automobiles.DataMappers.BrandMapper;
import com.katherine.automobiles.DataMappers.DataMapper;
import com.katherine.automobiles.DataMappers.ManufacturerMapper;
import com.katherine.automobiles.Entities.CommonEntity;
import com.katherine.automobiles.Views.NewActivityView;


import java.util.ArrayList;

public class NewEntityActivityPresenter {

    private NewActivityView newActivityView;
    private DataMapper dataModel;

    public enum MAPPERS{
        AUTOMOBILE,
        BODYSTYLE,
        BRAND,
        MANUFACTURER
    }

    public NewEntityActivityPresenter(NewActivityView newActivityView) {
        this.newActivityView = newActivityView;
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
        newActivityView = null;
        dataModel = null;
    }

    public boolean checkToSave(String titleStr){

        try{
            if(titleStr.isEmpty())
                throw new Exception("Введите название!");
        }catch (Exception ex){
            newActivityView.showToast(ex.getMessage());
            return false;
        }
        return true;

    }

    public ArrayList<CommonEntity> setSpinnerList(){ return dataModel.find(DataMapper.CRITERIA.ALL,""); }

    public void save(){ dataModel.insert(newActivityView.getNewEntity()); }

    public void update(){
        dataModel.update(newActivityView.getNewEntity());
    }


}
