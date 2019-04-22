package com.katherine.automobiles;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.katherine.automobiles.Presenters.MainActivityPresenter;

import java.util.ArrayList;

public class CardsTextCheckAdapter extends CardAdapter{

    private RecyclerView parent;
    private CheckBox checkBox;
    private ArrayList<String> checked = new ArrayList<>();

    public CardsTextCheckAdapter(ArrayList<String[]> contents) {
        this.contents = contents;

    }

    public void setParent(RecyclerView parent) {
        this.parent = parent;
    }

    public ArrayList<String> getChecked() {
        return checked;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    @Override
    public CardsTextCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_text_check, parent, false);
        return new CardsTextCheckAdapter.ViewHolder(cv);
    }
    @Override
    public void onBindViewHolder(final CardsTextCheckAdapter.ViewHolder holder, final int position){
        final CardView cardView = holder.cardView;
        checkBox = (CheckBox) cardView.findViewById(R.id.checkBox);
        checkBox.setText(contents.get(position)[1]);



       // MainActivity activity = (MainActivity)cardView.getContext();
       // activity.setChecks(contents.get(position)[0]);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity activity = (MainActivity)cardView.getContext();
                if(isChecked)
                    checked.add(contents.get(position)[0]);
                else
                    checked.remove(contents.get(position)[0]);
                activity.setChecks(parent);
            }
        });


        for(String check: checked){
            if(check.equals(contents.get(position)[0]) && !checkBox.isChecked())
                checkBox.setChecked(true);
        }

        if(checked.isEmpty() && checkBox.isChecked()) checkBox.setChecked(false);



    }
    @Override
    public int getItemCount(){
        return contents.size();
    }

}

