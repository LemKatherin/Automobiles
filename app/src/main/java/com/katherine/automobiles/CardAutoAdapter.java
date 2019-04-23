package com.katherine.automobiles;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Карточка для отображения данных об автомобиле.
 */
public class CardAutoAdapter extends CardAdapter {

    private TextView modelTextView;
    private ImageView photoImageView;
    private TextView yearValueTextView;
    private TextView seatsValueTextView;
    private TextView bodyValueTextView;
    private TextView fuelValueTextView;
    private TextView transmissionValueTextView;
    private TextView priceValueTextView;



    public CardAutoAdapter(ArrayList<String[]> contents) { this.contents = contents; }


    @Override
    public CardsTextCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_auto, parent, false);
        return new CardsTextCheckAdapter.ViewHolder(cv);
    }
    @Override
    public void onBindViewHolder(CardsTextCheckAdapter.ViewHolder holder, final int position){

        CardView cardView = holder.cardView;

        cardView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 1, 0, "Удалить запись");
                menu.add(0, 2, 0, "Изменить запись");
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(position);
                return false;
            }
        });

        modelTextView = cardView.findViewById(R.id.modelTextView);
        photoImageView = cardView.findViewById(R.id.photoImageView);
        yearValueTextView = cardView.findViewById(R.id.yearValueTextView);
        seatsValueTextView = cardView.findViewById(R.id.seatsValueTextView);
        bodyValueTextView = cardView.findViewById(R.id.bodyValueTextView);
        fuelValueTextView = cardView.findViewById(R.id.fuelValueTextView);
        transmissionValueTextView = cardView.findViewById(R.id.transmissionValueTextView);
        priceValueTextView = cardView.findViewById(R.id.priceValueTextView);

        modelTextView.setText(String.format("%s %s", contents.get(position)[1], contents.get(position)[2]));
        if(!contents.get(position)[4].equals("0"))
            yearValueTextView.setText(contents.get(position)[4]);
        if(!contents.get(position)[5].equals("0"))
            seatsValueTextView.setText(contents.get(position)[5]);
        bodyValueTextView.setText(contents.get(position)[6]);
        fuelValueTextView.setText(contents.get(position)[7]);
        transmissionValueTextView.setText(contents.get(position)[8]);
        if(!contents.get(position)[9].equals("0.0"))
            priceValueTextView.setText(contents.get(position)[9]);

        String filename =  contents.get(position)[3];
        InputStream inputStream = null;

        try {
            inputStream = cardView.getContext().getAssets().open(filename);
            Drawable d = Drawable.createFromStream(inputStream, null);
            photoImageView.setImageDrawable(d);
        } catch (IOException e) {
            if(!contents.get(position)[3].isEmpty()){
                Uri uri;
                File f = new File(contents.get(position)[3]);
                uri = Uri.fromFile(f);
                photoImageView.setImageURI(null);
                photoImageView.setImageURI(uri);
                photoImageView.setVisibility(View.VISIBLE);
            }else{
                photoImageView.setVisibility(View.GONE);
            }
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }



    }
    @Override
    public int getItemCount(){
        return contents.size();
    }

}
