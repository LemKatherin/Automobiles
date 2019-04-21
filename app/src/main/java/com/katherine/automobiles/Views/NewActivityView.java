package com.katherine.automobiles.Views;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.katherine.automobiles.Entities.CommonEntity;


public abstract class NewActivityView extends AppCompatActivity {


    protected CommonEntity newEntity;

    public CommonEntity getNewEntity() {
        return newEntity;
    }

    public void setNewEntity(CommonEntity newEntity) {
        this.newEntity = newEntity;
    }

    public abstract void showToast(String text);


}
