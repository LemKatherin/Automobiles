package com.katherine.automobiles.Presenters;

import android.widget.Spinner;

import com.katherine.automobiles.DataMappers.AutomobileMapper;
import com.katherine.automobiles.DataMappers.BodyStyleMapper;
import com.katherine.automobiles.DataMappers.BrandMapper;
import com.katherine.automobiles.DataMappers.DataMapper;
import com.katherine.automobiles.DataMappers.ManufacturerMapper;
import com.katherine.automobiles.Entities.CommonEntity;
import com.katherine.automobiles.Views.NewActivityView;


import java.util.ArrayList;


/**
 * Presenter - для связи БД и активности для создания новой сущности
 * (активность наследуется от абстрактного класса NewActivityView)
 */
public class NewEntityActivityPresenter {

    private NewActivityView newActivityView;
    private DataMapper dataModel;

    public enum MAPPERS{
        AUTOMOBILE,
        BODYSTYLE,
        BRAND,
        MANUFACTURER
    }

    public NewEntityActivityPresenter(NewActivityView newActivityView) { this.newActivityView = newActivityView; }

            // устанавливаем с чем конкретно будем работать
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

            // если активность удалена
    public void detachView() {
        newActivityView = null;
        dataModel = null;
    }

            // проверка, можно ли сохранить запись, когда только название обязательно
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

            // получает массив значений для Spinner и возвращет его активности
    public ArrayList<CommonEntity> setSpinnerList(Spinner spinner){
        ArrayList<CommonEntity> entities = dataModel.find(DataMapper.CRITERIA.ALL,"");
        String[] values = CommonEntity.toNameArray(entities);
        newActivityView.setSpinner(spinner, values);
        return entities;
    }

    public CommonEntity getEntity(String id){
        return dataModel.findById(id);
    }

            // добавление новой записи
    public void save(){ dataModel.insert(newActivityView.getNewEntity()); }

            // изменение существующей записи
    public void update(){
        dataModel.update(newActivityView.getNewEntity());
    }

            // нажатие на ImageView для загрузки изображения из галереи
    public void onImageClick(){
        newActivityView.openGallery();
    }

}
