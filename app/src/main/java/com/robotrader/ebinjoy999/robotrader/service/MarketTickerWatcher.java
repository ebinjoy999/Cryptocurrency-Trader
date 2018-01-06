package com.robotrader.ebinjoy999.robotrader.service;

import android.content.Context;

import com.robotrader.ebinjoy999.robotrader.model.Symbol;

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
               if( (jsonResult instanceof List) && ((List<Object>) jsonResult).size()> 0 && ((List<Symbol>) jsonResult).get(0) instanceof List) {

               }else {

               }
               break;
       }
    }

    @Override
    public void onAPILoadFailed(String REQUEST_TYPE, String message, Object jsonResult) {

    }
}
