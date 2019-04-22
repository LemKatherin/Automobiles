package com.katherine.automobiles;

import android.content.Entity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.katherine.automobiles.DataMappers.AutomobileMapper;
import com.katherine.automobiles.DataMappers.BodyStyleMapper;
import com.katherine.automobiles.DataMappers.BrandMapper;
import com.katherine.automobiles.Entities.Automobile;
import com.katherine.automobiles.Entities.BodyStyle;
import com.katherine.automobiles.Entities.Brand;
import com.katherine.automobiles.Entities.CommonEntity;
import com.katherine.automobiles.Presenters.NewEntityActivityPresenter;
import com.katherine.automobiles.Views.NewActivityView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class NewAutoActivity extends NewActivityView {
    static final int GALLERY_REQUEST = 1;
    public static final String AUTO_ID = "autoID";

    private Spinner brandSpinner;
    private TextView modelTextView;
    private ImageView photoImageView;
    private TextView yearValueTextView;
    private TextView seatsValueTextView;
    private Spinner bodyStyleSpinner;
    private TextView fuelValueTextView;
    private TextView transmissionValueTextView;
    private TextView priceValueTextView;

    private String imagePath;

    private NewEntityActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new NewEntityActivityPresenter(this);
        newEntity = new Automobile();

        initActivity();

        presenter.setDataModel(NewEntityActivityPresenter.MAPPERS.AUTOMOBILE);

        try {
            String autoID = getIntent().getExtras().get(AUTO_ID).toString();

            AutomobileMapper automobileMapper = new AutomobileMapper();
            newEntity = automobileMapper.findById(autoID);

            modelTextView.setText(newEntity.getName());
            try {
                yearValueTextView.setText(String.valueOf(((Automobile) newEntity).getProductYear()));
            }catch (Exception ex){}
            try {
                seatsValueTextView.setText(String.valueOf(((Automobile)newEntity).getSeats()));
            }catch (Exception ex){}
            try {
                fuelValueTextView.setText(((Automobile)newEntity).getFuelType());
            }catch (Exception ex){}
            try {
                transmissionValueTextView.setText(((Automobile)newEntity).getFuelType());
            }catch (Exception ex){}
            try {
                priceValueTextView.setText(String.valueOf(((Automobile)newEntity).getPrice()));
            }catch (Exception ex){}
            try {
                for(int i = 0; i < brandSpinner.getAdapter().getCount(); i++){
                    if(((Automobile)newEntity).getBrand().getName().equals(brandSpinner.getAdapter().getItem(i).toString())){
                        brandSpinner.setSelection(i);
                        break;
                    }
                }
            }catch (Exception ex){}

            try {
                for(int i = 0; i < bodyStyleSpinner.getAdapter().getCount(); i++){
                    if(((Automobile)newEntity).getBodyStyle().getName().equals(bodyStyleSpinner.getAdapter().getItem(i).toString())){
                        bodyStyleSpinner.setSelection(i);
                        break;
                    }
                }
            }catch (Exception ex){}



        } catch (Exception ex){}

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initActivity(){
        brandSpinner = (Spinner) findViewById(R.id.brandSpinner);
        modelTextView = (TextView) findViewById(R.id.modelTextView);
        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        yearValueTextView = (TextView) findViewById(R.id.yearValueTextView);
        seatsValueTextView = (TextView) findViewById(R.id.seatsValueTextView);
        bodyStyleSpinner = (Spinner) findViewById(R.id.bodyStyleSpinner);
        fuelValueTextView = (TextView) findViewById(R.id.fuelValueTextView);
        transmissionValueTextView = (TextView) findViewById(R.id.transmissionValueTextView);
        priceValueTextView = (TextView) findViewById(R.id.priceValueTextView);

        File extStore = Environment.getExternalStorageDirectory();

        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        presenter.setDataModel(NewEntityActivityPresenter.MAPPERS.BRAND);
        final ArrayList<CommonEntity> brandEntities = presenter.setSpinnerList();

        String[] names = CommonEntity.toNameArray(brandEntities);
        ArrayAdapter<String> arrayAdapterBrand = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        arrayAdapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(arrayAdapterBrand);

        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ((Automobile)newEntity).setBrand((Brand) brandEntities.get(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        presenter.setDataModel(NewEntityActivityPresenter.MAPPERS.BODYSTYLE);
        final ArrayList<CommonEntity> bodyEntities = presenter.setSpinnerList();

        names = CommonEntity.toNameArray(bodyEntities);
        ArrayAdapter<String> arrayAdapterBody = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        arrayAdapterBody.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyStyleSpinner.setAdapter(arrayAdapterBody);

        bodyStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ((Automobile)newEntity).setBodyStyle((BodyStyle) bodyEntities.get(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    private String getPath(Uri uri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id){
            case R.id.saveItem:

                if(presenter.checkToSave(modelTextView.getText().toString())) {
                    newEntity.setName(modelTextView.getText().toString());
                    try {
                        ((Automobile) newEntity).setProductYear(Integer.valueOf(yearValueTextView.getText().toString()));
                    }catch (Exception ex) {}
                    try {
                        ((Automobile)newEntity).setSeats(Integer.valueOf(seatsValueTextView.getText().toString()));
                    }catch (Exception ex) {}
                    try {
                        ((Automobile)newEntity).setFuelType(fuelValueTextView.getText().toString());
                    }catch (Exception ex) {}
                    try {
                        ((Automobile)newEntity).setTransmission(transmissionValueTextView.getText().toString());
                    }catch (Exception ex) {}
                    try {
                        ((Automobile)newEntity).setPrice(Double.valueOf(priceValueTextView.getText().toString()));
                    }catch (Exception ex) {}

                    try {
                        ((Automobile)newEntity).setPhoto(imagePath);
                    }catch (Exception ex) {}

                    if(newEntity.getId() == 0)
                        presenter.save();
                    else
                        presenter.update();

                    onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK && imageReturnedIntent != null) {
                    try {
                        Uri photoImageUri = imageReturnedIntent.getData();
                        imagePath = getPath(photoImageUri);
                        if (imagePath != null) {
                            File f = new File(imagePath);
                            photoImageUri = Uri.fromFile(f);
                        }

                        photoImageView.setImageURI(photoImageUri);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy(){
        presenter.detachView();
        super.onDestroy();
    }
}
