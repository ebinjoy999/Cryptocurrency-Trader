package com.robotrader.ebinjoy999.robotrader.service;

import android.content.Context;

import com.robotrader.ebinjoy999.robotrader.model.Symbol;

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
       intializeValues();
       this.ct = ct;
    }

    private void intializeValues() {

        if(sharedPreferenceManagerC.checkIsSymbolDetailsInLocalValid()){
            List<Symbol> symbols = sharedPreferenceManagerC.getSymbolsDetailsSharedPref(ct);
            int d = 1;
            int g =1;
        }else apiManager.getResponseAsJavaModel(APIManager.REQUEST_GET_SYMBOLS,null);
    }

    @Override
    public void onAPILoadSuccess(String REQUEST_TYPE, String message, Object jsonResult) {
       switch (REQUEST_TYPE){
           case APIManager.REQUEST_GET_SYMBOLS:
                 if( (jsonResult instanceof List) && ((List<Symbol>) jsonResult).size()> 0 && ((List<Symbol>) jsonResult).get(0) instanceof Symbol) {
                     sharedPreferenceManagerC.saveSymbolDetailsSharedPref(ct,((List<Symbol>) jsonResult));
                 }else {

                 }
               break;
       }
    }

    @Override
    public void onAPILoadFailed(String REQUEST_TYPE, String message, Object jsonResult) {

    }
}
