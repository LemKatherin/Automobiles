package com.katherine.automobiles;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    ArrayList<String[]> contents = new ArrayList<>();

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public ArrayList<String[]> getContents() {
        return contents;
    }

    public void setContents(ArrayList<String[]> contents) {
        this.contents = contents;
    }

    protected Listener listener;

    public static interface Listener {
        public void onClick(int position);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public abstract CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int position);

    @Override
    public abstract int getItemCount();
}
