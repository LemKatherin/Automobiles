package com.katherine.automobiles;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import java.util.ArrayList;

public class CardsTextCheckAdapter extends CardAdapter{

    private CheckBox checkBox;

    public CardsTextCheckAdapter(ArrayList<String[]> contents) {
        this.contents = contents;

    }


    @Override
    public CardsTextCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_text_check, parent, false);
        return new CardsTextCheckAdapter.ViewHolder(cv);
    }
    @Override
    public void onBindViewHolder(CardsTextCheckAdapter.ViewHolder holder, final int position){
        CardView cardView = holder.cardView;
        checkBox = (CheckBox) cardView.findViewById(R.id.checkBox);
        checkBox.setText(contents.get(position)[1]);


      /*  for(Category cat: checkedCategories){
            if(cat.getId() == Integer.valueOf(contents.get(position)[0]) && !checkBox.isChecked())
                checkBox.setChecked(true);
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    checkedCategories.add(new Category(Integer.valueOf(contents.get(position)[0]), contents.get(position)[1]));
                else if( checkedCategories != null && checkedCategories.contains(buttonView.getText().toString()))
                    checkedCategories.remove(buttonView.getText().toString());
            }
        });*/

    }
    @Override
    public int getItemCount(){
        return contents.size();
    }
}

