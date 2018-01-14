package com.robotrader.ebinjoy999.robotrader.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robotrader.ebinjoy999.robotrader.R;
import com.robotrader.ebinjoy999.robotrader.model.SymbolDetails;

import java.util.HashMap;

/**
 * Created by ebinjoy999 on 14/01/18.
 */

public class AdapterNavRecyclerView extends RecyclerView.Adapter<AdapterNavRecyclerView.CustomViewHolder> {

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.navigation_drawer_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (symbolsList==null || symbolsList.size()<0)? 0 : symbolsList.size();
    }

    HashMap<String,SymbolDetails> symbolsList = new HashMap<>();
    public void setSymbolsList(HashMap<String,SymbolDetails> symbolsList) {
        this.symbolsList = symbolsList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView tex;
        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }
}
