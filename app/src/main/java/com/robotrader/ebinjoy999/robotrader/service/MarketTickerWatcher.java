package com.robotrader.ebinjoy999.robotrader.service;

import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;

import com.robotrader.ebinjoy999.robotrader.MainActivity;
import com.robotrader.ebinjoy999.robotrader.model.Symbol;
import com.robotrader.ebinjoy999.robotrader.model.SymbolDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.APIManager;
import retrofit.ApiClient;
import retrofit.ApiInterface;
import retrofit.InterfaceAPIManager;
import tools.SharedPreferenceManagerC;

/**
 * Created by macadmin on 12/19/17.
 */

public class MarketTickerWatcher implements InterfaceAPIManager{
  APIManager apiManager;
  SharedPreferenceManagerC sharedPreferenceManagerC;
  Context ct;
    MarketTickerWatcher(Context ct){
       apiManager = new APIManager(MarketTickerWatcher.this,ct);
       sharedPreferenceManagerC = new SharedPreferenceManagerC(ct);
        intializeSymbolDetails();
       this.ct = ct;
    }

    private void intializeSymbolDetails() {
        if(sharedPreferenceManagerC.checkIsSymbolDetailsInLocalValid()){
            List<Symbol> symbols = sharedPreferenceManagerC.getSymbolsDetailsSharedPref(ct);
            getLivePrice(symbols);
        }else apiManager.getResponseAsJavaModel(APIManager.REQUEST_GET_SYMBOLS,null);
    }

    private void getLivePrice(List<Symbol> symbols) {
        if(symbols!=null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Symbol symbol : symbols) {
                stringBuilder.append(",t" + symbol.getPair().toUpperCase());
            }
            final String query = stringBuilder.toString().substring(1, stringBuilder.toString().length());
            apiManager.getResponseAsJavaModel(APIManager.REQUEST_GET_TICKERS,
                    new HashMap<String, Object>() {{
                        put(APIManager.KEY_REQUEST_QUERY_PARAMS, query);
                    }});
        }else {

        }
    }

    public static final String KEY_SYMBOL_DETAILS = "KEY_SYMBOL_DETAILS";
    @Override
    public void onAPILoadSuccess(String REQUEST_TYPE, String message, Object jsonResult) {
       switch (REQUEST_TYPE){
           case APIManager.REQUEST_GET_SYMBOLS:
                 if( (jsonResult instanceof List) && ((List<Symbol>) jsonResult).size()> 0 && ((List<Symbol>) jsonResult).get(0) instanceof Symbol) {
                     sharedPreferenceManagerC.saveSymbolDetailsSharedPref(ct,((List<Symbol>) jsonResult));
                     getLivePrice(((List<Symbol>) jsonResult));
                 }else {

                 }
               break;

           case APIManager.REQUEST_GET_TICKERS:
               if( (jsonResult instanceof List) && ((List<Object>) jsonResult).size()> 0 && ((List<ArrayList>) jsonResult).get(0) instanceof List) {
                   HashMap<String, SymbolDetails> symbolDetails = new HashMap<>();
                   for(ArrayList arrayListDetail :  ((List<ArrayList>) jsonResult)){
                       if(arrayListDetail.get(0).toString().contains("USD"))
                       symbolDetails.put(arrayListDetail.get(0).toString(),new SymbolDetails(arrayListDetail.get(0).toString(),
                               Float.parseFloat(arrayListDetail.get(1).toString()),Float.parseFloat(arrayListDetail.get(2).toString()),Float.parseFloat(arrayListDetail.get(3).toString()),
                               Float.parseFloat(arrayListDetail.get(4).toString()),Float.parseFloat(arrayListDetail.get(5).toString()),Float.parseFloat(arrayListDetail.get(6).toString()),
                               Float.parseFloat(arrayListDetail.get(7).toString()),Float.parseFloat(arrayListDetail.get(8).toString()),Float.parseFloat(arrayListDetail.get(9).toString()),
                               Float.parseFloat(arrayListDetail.get(10).toString())) );
                   }
//                  int n =  symbolDetails.size();
                   Intent intent = new Intent();
                   intent.setAction(MainActivity.TRADE_RECEIVER_PRICE);
                   intent.putExtra(KEY_SYMBOL_DETAILS, symbolDetails);
                   ct.sendBroadcast(intent);
               }else {

               }
               break;
       }
    }

    @Override
    public void onAPILoadFailed(String REQUEST_TYPE, String message, Object jsonResult) {

    }
}
