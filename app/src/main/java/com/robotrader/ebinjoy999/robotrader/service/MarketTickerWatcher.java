package com.robotrader.ebinjoy999.robotrader.service;

import android.content.Context;

import retrofit.APIManager;
import retrofit.ApiClient;
import retrofit.ApiInterface;

/**
 * Created by macadmin on 12/19/17.
 */

public class MarketTickerWatcher {
  APIManager apiManager;
    MarketTickerWatcher(Context ct){
       apiManager = new APIManager(null,ct);
       intializeValues();
    }

    private void intializeValues() {
        apiManager.getResponseAsJavaModel(APIManager.REQUEST_GET_SYMBOLS,null);
    }
}
