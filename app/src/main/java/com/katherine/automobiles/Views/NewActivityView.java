package com.katherine.automobiles.Views;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.katherine.automobiles.Entities.CommonEntity;


public abstract class NewActivityView extends AppCompatActivity {

    protected CommonEntity newEntity;
    public static final int GALLERY_REQUEST = 1;

    public CommonEntity getNewEntity() { return newEntity; }

    public void setNewEntity(CommonEntity newEntity) {
        this.newEntity = newEntity;
    }

    public abstract void showToast(String text);

    public  void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    public abstract void setSpinner(Spinner spinner, String[] values);


}
