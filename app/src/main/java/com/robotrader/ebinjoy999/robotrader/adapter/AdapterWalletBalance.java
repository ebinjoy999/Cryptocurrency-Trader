package com.robotrader.ebinjoy999.robotrader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robotrader.ebinjoy999.robotrader.R;
import com.robotrader.ebinjoy999.robotrader.model.WalletItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebinjoy999 on 20/01/18.
 */

public class AdapterWalletBalance  extends RecyclerView.Adapter<AdapterWalletBalance.CustomViewHolder> {


    Context context;
    public AdapterWalletBalance( Context context){
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.wallet_item, parent, false);
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
            WalletItem walletItem = walletItems.get(position-1);
            if(walletItem!=null){
                try {
                    holder.textViewCurrency.setText(
                            walletItem.getCurrency()
                    );
                    holder.textViewAmount.setText(
                            String.valueOf(round(Float.parseFloat(walletItem.getAmount()), 2))
                    );
                    holder.textViewAvailable.setText(
                            String.valueOf(round(Float.parseFloat(walletItem.getAvailable()), 2))
                    );
                    holder.textViewType.setText(walletItem.getType());
                }catch (Exception exc){
                    exc.printStackTrace();
                }
            }
        }else if (position==0){
            holder.textViewCurrency.setText("Currency");
            holder.textViewType.setText("Type");
            holder.textViewAmount.setText("Amount");
            holder.textViewAvailable.setText("Available");

        }
    }

    @Override
    public int getItemCount() {
        return (walletItems==null || walletItems.size()<0)? 0 : (walletItems.size()+1);
    }

    List<WalletItem> walletItems = new ArrayList<>();
    public void setWalletItems(List<WalletItem> walletItems) {
        this.walletItems = null;
        this.walletItems= walletItems;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCurrency, textViewType, textViewAmount, textViewAvailable;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textViewCurrency = itemView.findViewById(R.id.textViewCurrency);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewAvailable = itemView.findViewById(R.id.textViewAvailable);
        }
    }
}
