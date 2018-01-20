package com.robotrader.ebinjoy999.robotrader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robotrader.ebinjoy999.robotrader.R;
import com.robotrader.ebinjoy999.robotrader.model.activeorders.ActiveOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebinjoy999 on 20/01/18.
 */

public class AdapterActiveOrder  extends RecyclerView.Adapter<AdapterActiveOrder.CustomViewHolder> {


    Context context;
    public AdapterActiveOrder( Context context){
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.active_order_item, parent, false);
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
            ActiveOrder order = orders.get(position-1);
            if(order!=null){
                try {
                    holder.textViewSymbol.setText(order.getSymbol());
                    holder.textViewSide.setText(order.getSide());
                    holder.textViewOrginalAmount.setText(
                            String.valueOf(round(Float.parseFloat(order.getOriginalAmount()), 2))
                    );
                    holder.textViewRemainingAmount.setText(
                            String.valueOf(round(Float.parseFloat(order.getRemainingAmount()), 2))
                    );
                    holder.textViewExecutedAmount.setText(
                            String.valueOf(round(Float.parseFloat(order.getExecutedAmount()), 2))
                    );

                }catch (Exception exc){
                    exc.printStackTrace();
                }
            }
        }else if (position==0){
            holder.textViewSymbol.setText("Symbol");
            holder.textViewSide.setText("Side");
            holder.textViewOrginalAmount.setText("Org.A");
            holder.textViewRemainingAmount.setText("Rem.A");
            holder.textViewExecutedAmount.setText("Exe.A");
        }
    }

    @Override
    public int getItemCount() {
        return (orders==null || orders.size()<0)? 0 : (orders.size()+1);
    }

    List<ActiveOrder> orders = new ArrayList<>();
    public void setWalletItems(List<ActiveOrder> orders) {
        this.orders = null;
        this.orders= orders;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textViewSymbol, textViewSide, textViewOrginalAmount, textViewRemainingAmount,textViewExecutedAmount;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textViewSymbol = itemView.findViewById(R.id.textViewSymbol);
            textViewSide = itemView.findViewById(R.id.textViewSide);
            textViewOrginalAmount = itemView.findViewById(R.id.textViewOrginalAmount);
            textViewRemainingAmount = itemView.findViewById(R.id.textViewRemainingAmount);
            textViewExecutedAmount = itemView.findViewById(R.id.textViewExecutedAmount);
        }
    }
}
