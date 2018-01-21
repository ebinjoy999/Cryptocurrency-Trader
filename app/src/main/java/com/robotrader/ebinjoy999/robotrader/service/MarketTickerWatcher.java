package com.robotrader.ebinjoy999.robotrader.service;

import android.content.Context;
import android.content.Intent;

import com.robotrader.ebinjoy999.robotrader.MainActivity;
import com.robotrader.ebinjoy999.robotrader.model.Symbol;
import com.robotrader.ebinjoy999.robotrader.model.SymbolDetails;
import com.robotrader.ebinjoy999.robotrader.model.WalletItem;
import com.robotrader.ebinjoy999.robotrader.model.activeorders.ActiveOrder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.APIManager;
import retrofit.InterfaceAPIManager;
import tools.SharedPreferenceManagerC;

/**
 * Created by macadmin on 12/19/17.
 */

public class MarketTickerWatcher implements InterfaceAPIManager{
  APIManager apiManager;
  SharedPreferenceManagerC sharedPreferenceManagerC;
  Context ct;
  final static int MAX_RESPOMSE_TO_START_ALGORITHM = 4;

  Boolean runningForLivePrice  = false;
  ArrayList<String> logList;
  HashMap<String, Object> responsesFromServer = new HashMap<>();

    MarketTickerWatcher(Context ct){
        logList = new ArrayList<>();
       apiManager = new APIManager(MarketTickerWatcher.this,ct);
       sharedPreferenceManagerC = new SharedPreferenceManagerC(ct);
       this.ct = ct;
    }

    public void intializeSymbolDetailsAndGetLivePrice() {
        runningForLivePrice = true;
        responsesFromServer = new HashMap<>();
        sentBrodcast("------------------------------------------------------------", MainActivity.TRADE_RECEIVER_LOGS,KEY_LOGS);
        if(sharedPreferenceManagerC.checkIsSymbolDetailsInLocalValid()){
            List<Symbol> symbols = sharedPreferenceManagerC.getSymbolsDetailsSharedPref(ct);
            getLivePrice(symbols);
            addToResponse(APIManager.REQUEST_GET_SYMBOLS,symbols);
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
            apiManager.getResponseAsJavaModel(APIManager.REQUEST_POST_WALLET,null);
            apiManager.getResponseAsJavaModel(APIManager.REQUEST_ACTIVE_ORDERS,null);

        }else {

        }
    }


    String[] monitorCryptos = {"xrp"};
    Float XRP_ACTION_PRICE = 1.40f;
    Float xrp_hit_price = 0.0f;
    private void initiateAlgorithm(HashMap<String, Object> responsesFromServer) {
        List<Symbol> symbols = (List<Symbol>) responsesFromServer.get(APIManager.REQUEST_GET_SYMBOLS);
        List<ActiveOrder> orders = (List<ActiveOrder>) responsesFromServer.get(APIManager.REQUEST_ACTIVE_ORDERS);
        List<WalletItem> walletItems = (List<WalletItem>) responsesFromServer.get(APIManager.REQUEST_POST_WALLET);
        HashMap<String, SymbolDetails> symbolDetails = (HashMap<String, SymbolDetails>) responsesFromServer.get(APIManager.REQUEST_GET_TICKERS);


        Map<String,WalletItem> walletsMap = new HashMap<String,WalletItem>();
        // Only deal with EXChange==============
        for (WalletItem walletItem : walletItems)
            if(walletItem.getType().equalsIgnoreCase("exchange"))walletsMap.put(walletItem.getCurrency(),walletItem);

        for(String currency : monitorCryptos){
            sentBrodcast("Monitoring currency "+currency, MainActivity.TRADE_RECEIVER_LOGS,KEY_LOGS);
            SymbolDetails symbolDetailsNew = symbolDetails.get("t"+currency.toUpperCase()+"USD");
            WalletItem walletItem = walletsMap.get(currency);
            if(walletItem!=null && symbolDetailsNew!=null){
                boolean haveCryptoCurrency =  DecideCellOrBuy.decideHaveCryptoCurrency(Float.parseFloat(walletItem.getAvailable()),
                        symbolDetailsNew.getBID());
                float priceMid  =  (symbolDetailsNew.getHIGH()+symbolDetailsNew.getLOW())/2;

                float smallIntegralforCheck =  (symbolDetailsNew.getHIGH()-symbolDetailsNew.getLOW())/10;
                float PROFIT_MARGIN = .03f;
                Float previosSellPrice = sharedPreferenceManagerC.getKeyToSharedPreferencFloat(currency+"sell_price");
                Float previosBuylPrice = sharedPreferenceManagerC.getKeyToSharedPreferencFloat(currency+"buy_price");

                if(   (haveCryptoCurrency &&(previosSellPrice==0.0f) && (priceMid<symbolDetailsNew.getBID()))
                          || ((previosSellPrice-PROFIT_MARGIN)>= symbolDetailsNew.getBID()) ){
                    //Decision about sell
                    sentBrodcast("Monitoring currency M:"+priceMid+" C:"+symbolDetailsNew.getBID()+" "+currency +" Decision about sell", MainActivity.TRADE_RECEIVER_LOGS,KEY_LOGS);

                } else if( !haveCryptoCurrency && (priceMid>=(symbolDetailsNew.getBID()-smallIntegralforCheck)) ){
                    //Decision about buy
                    sentBrodcast("Monitoring currency M:"+priceMid+" C:"+symbolDetailsNew.getBID()+" "+currency +" Decision about buy", MainActivity.TRADE_RECEIVER_LOGS,KEY_LOGS);

                    Float triggerPrice = (XRP_ACTION_PRICE==0)? priceMid : XRP_ACTION_PRICE;
                    if(previosSellPrice!=0) triggerPrice = previosSellPrice; //Override

                    if(DecideCellOrBuy.decideIsNeedsBuy(smallIntegralforCheck,PROFIT_MARGIN,
                            priceMid,symbolDetailsNew.getBID()
                            )){


                    }else{

                    }
                }else  {
                    sentBrodcast("Monitoring currency M:"+priceMid+" C:"+symbolDetailsNew.getBID()+" "+currency +" Algo no action!", MainActivity.TRADE_RECEIVER_LOGS,KEY_LOGS);
                }

            }
        }

        cancelCurrentExecution();
    }


    public static final String KEY_SYMBOL_DETAILS = "KEY_SYMBOL_DETAILS";
    public static final String KEY_WALLET_DETAILS = "KEY_WALLET_DETAILS";
    public static final String KEY_ACTIVE_ORDERS = "KEY_ACTIVE_ORDERS";
    public static final String KEY_LOGS = "KEY_LOGS";
    HashMap<String, SymbolDetails> symbolDetails;
    @Override
    public void onAPILoadSuccess(String REQUEST_TYPE, String message, Object jsonResult) {
        sentBrodcast("API "+ (jsonResult==null? "JSON out null -":"Success -")+REQUEST_TYPE, MainActivity.TRADE_RECEIVER_LOGS,KEY_LOGS);
       switch (REQUEST_TYPE){

           case APIManager.REQUEST_ACTIVE_ORDERS:
               if( (jsonResult instanceof List) && ((List<ActiveOrder>) jsonResult).size() >= 0) {
                   List<ActiveOrder> orders  = (List<ActiveOrder>) jsonResult;
                   sentBrodcast(orders, MainActivity.TRADE_ACTIVE_ORDERS,KEY_ACTIVE_ORDERS);
                   addToResponse(APIManager.REQUEST_ACTIVE_ORDERS,orders);
               }else {
                   cancelCurrentExecution();
               }
                   break;


           case APIManager.REQUEST_GET_SYMBOLS:
                 if( (jsonResult instanceof List) && ((List<Symbol>) jsonResult).size()> 0 && ((List<Symbol>) jsonResult).get(0) instanceof Symbol) {
                     sharedPreferenceManagerC.saveSymbolDetailsSharedPref(ct,((List<Symbol>) jsonResult));
                     getLivePrice(((List<Symbol>) jsonResult));
                     addToResponse(APIManager.REQUEST_GET_SYMBOLS,jsonResult);
                 }else {
                     cancelCurrentExecution();
                 }
               break;

           case APIManager.REQUEST_GET_TICKERS:  //Got live price
               if( (jsonResult instanceof List) && ((List<Object>) jsonResult).size()> 0 && ((List<ArrayList>) jsonResult).get(0) instanceof List) {
                  symbolDetails = new HashMap<>();
                   for(ArrayList arrayListDetail :  ((List<ArrayList>) jsonResult)){
                       if(arrayListDetail.get(0).toString().contains("USD"))
                       symbolDetails.put(arrayListDetail.get(0).toString(),new SymbolDetails(arrayListDetail.get(0).toString(),
                               Float.parseFloat(arrayListDetail.get(1).toString()),Float.parseFloat(arrayListDetail.get(2).toString()),Float.parseFloat(arrayListDetail.get(3).toString()),
                               Float.parseFloat(arrayListDetail.get(4).toString()),Float.parseFloat(arrayListDetail.get(5).toString()),Float.parseFloat(arrayListDetail.get(6).toString()),
                               Float.parseFloat(arrayListDetail.get(7).toString()),Float.parseFloat(arrayListDetail.get(8).toString()),Float.parseFloat(arrayListDetail.get(9).toString()),
                               Float.parseFloat(arrayListDetail.get(10).toString())) );
                   }
                   sentBrodcast(symbolDetails, MainActivity.TRADE_RECEIVER_PRICE,KEY_SYMBOL_DETAILS);
                   addToResponse(APIManager.REQUEST_GET_TICKERS,symbolDetails);
               }else {
                   cancelCurrentExecution();
               }
               break;

           case APIManager.REQUEST_POST_WALLET:
               List<WalletItem> walletItems = new ArrayList<>();
               if( (jsonResult instanceof List) && ((List<Object>) jsonResult).size()> 0 && ((List<Object>) jsonResult).get(0) instanceof WalletItem) {
                   walletItems = (List<WalletItem>) jsonResult;
                   sentBrodcast(walletItems, MainActivity.TRADE_WALLET_PRICE,KEY_WALLET_DETAILS);
                   addToResponse(APIManager.REQUEST_POST_WALLET,walletItems);
               }else {
                   cancelCurrentExecution();
               }


               break;
       }

       if(responsesFromServer.size()==MAX_RESPOMSE_TO_START_ALGORITHM)
           initiateAlgorithm(responsesFromServer);
    }

    private void cancelCurrentExecution() {
        this.runningForLivePrice = false; //responsesFromServer size  never got MAX_RESPOMSE_TO_START_ALGORITHM so cancelling
    }


    private void addToResponse(String key,Object symbolDetails) {
        this.responsesFromServer.put(key,symbolDetails);
    }


    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
    @Override
    public void onAPILoadFailed(String REQUEST_TYPE, String message, Object jsonResult) {
       //Add logs
       sentBrodcast("API failed -"+REQUEST_TYPE, MainActivity.TRADE_RECEIVER_LOGS,KEY_LOGS);

        switch (REQUEST_TYPE) {
            case APIManager.REQUEST_GET_SYMBOLS: cancelCurrentExecution();
                break;
            case APIManager.REQUEST_GET_TICKERS: cancelCurrentExecution();
                break;
            case APIManager.REQUEST_POST_WALLET:  cancelCurrentExecution();
                break;
            case APIManager.REQUEST_ACTIVE_ORDERS:  cancelCurrentExecution();
                break;
        }
    }


    private void sentBrodcast(Object data, String action, String KEY_EXTRAS) {
        if(data instanceof String && action==MainActivity.TRADE_RECEIVER_LOGS) {
            Calendar c = Calendar.getInstance();
            logList.add(sdf.format(c.getTime()) + " :: " + data);
        }
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(KEY_EXTRAS,(action==MainActivity.TRADE_RECEIVER_LOGS)? logList :  (Serializable) data);
        ct.sendBroadcast(intent);
    }

    public boolean isRunningForLivePrice() {
        return runningForLivePrice;
    }
}
