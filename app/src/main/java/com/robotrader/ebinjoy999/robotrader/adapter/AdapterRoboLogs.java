package com.robotrader.ebinjoy999.robotrader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robotrader.ebinjoy999.robotrader.R;

import java.util.ArrayList;

/**
 * Created by macadmin on 1/15/18.
 */

public class AdapterRoboLogs extends RecyclerView.Adapter<AdapterRoboLogs.PlanetViewHolder> {

    public void setLogList(ArrayList<String> logList) {
        this.logList = logList;
    }


    ArrayList<String> logList = new ArrayList<>();
   Context context;
     public AdapterRoboLogs(Context context) {
        this.context = context;
        }

@Override
public AdapterRoboLogs.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_log_rv_item,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(AdapterRoboLogs.PlanetViewHolder holder, int position) {
        holder.text.setText(logList.get(position).toString());
        }

@Override
public int getItemCount() {
        return logList==null? 0 : logList.size();
        }

public static class PlanetViewHolder extends RecyclerView.ViewHolder{
    protected TextView text;
    public PlanetViewHolder(View itemView) {
        super(itemView);
        text= (TextView) itemView.findViewById(R.id.textViewLog);
    }
}
}
