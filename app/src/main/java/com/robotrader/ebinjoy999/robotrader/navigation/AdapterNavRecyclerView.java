package com.robotrader.ebinjoy999.robotrader.navigation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robotrader.ebinjoy999.robotrader.R;
import com.robotrader.ebinjoy999.robotrader.model.Symbol;
import com.robotrader.ebinjoy999.robotrader.model.SymbolDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by ebinjoy999 on 14/01/18.
 */

public class AdapterNavRecyclerView extends RecyclerView.Adapter<AdapterNavRecyclerView.CustomViewHolder> {


    Context context;
    public AdapterNavRecyclerView( Context context){
        this.context = context;
    }

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

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if(position!=0){
            SymbolDetails symbolDetails = (new ArrayList<SymbolDetails> (symbolsList.values()) ).get(position-1);
            if(symbolDetails!=null){
                holder.textViewPair.setText(symbolDetails.getSYMBOL().substring(1,(symbolDetails.getSYMBOL().length()-3)));
                holder.textViewBid.setText(  String.valueOf(round(symbolDetails.getBID(),2)));
                holder.textViewVolume.setText(  String.valueOf(round(symbolDetails.getVOLUME(),1)));
                holder.textViewHigh.setText(  String.valueOf(round(symbolDetails.getHIGH(),2)));
                holder.textViewLow.setText(  String.valueOf(round(symbolDetails.getLOW(),2)));

                holder.textViewPercentage.setText(String.valueOf(round(symbolDetails.getDAILY_CHANGE_PERC(),1)));
                if(symbolDetails.getDAILY_CHANGE_PERC() >0){
                    holder.textViewPercentage.setTextColor(ContextCompat.getColor(context,R.color.text_green));
                }else {
                    holder.textViewPercentage.setTextColor(ContextCompat.getColor(context,R.color.text_red));
                }
            }
        }else if (position==0){
            holder.textViewPercentage.setTextColor(ContextCompat.getColor(context,R.color.text_green));
            holder.textViewPair.setText("Pair(USD)");
            holder.textViewBid.setText("Bid");
            holder.textViewVolume.setText("Volume");
            holder.textViewHigh.setText("High");
            holder.textViewLow.setText("Low");
            holder.textViewPercentage.setText("%");

        }
    }

    @Override
    public int getItemCount() {
        return (symbolsList==null || symbolsList.size()<0)? 0 : (symbolsList.size()+1);
    }

    LinkedHashMap<String,SymbolDetails> symbolsList = new LinkedHashMap<>();
    public void setSymbolsList(HashMap<String,SymbolDetails> symbolsList) {

//        this.symbolsList = symbolsList;
        this.symbolsList = null;
        this.symbolsList= new LinkedHashMap<String,SymbolDetails>(symbolsList);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textViewPair, textViewBid, textViewPercentage, textViewVolume, textViewHigh, textViewLow;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textViewPair = itemView.findViewById(R.id.textViewPair);
            textViewBid = itemView.findViewById(R.id.textViewBid);
            textViewPercentage = itemView.findViewById(R.id.textViewPercentage);
            textViewVolume = itemView.findViewById(R.id.textViewVolume);
            textViewHigh = itemView.findViewById(R.id.textViewHigh);
            textViewLow = itemView.findViewById(R.id.textViewLow);
        }
    }
}
