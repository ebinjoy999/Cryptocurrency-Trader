package com.robotrader.ebinjoy999.robotrader.service;

import java.util.List;

/**
 * Created by ebinjoy999 on 21/01/18.
 */

public class DecideCellOrBuy {

    public static boolean decideHaveCryptoCurrency(float amountCurrency, float currencyPrice) {
        return (amountCurrency>1)? true : false;
    }

    public static boolean decideIsNeedsBuy(Float small_integral, float PROFIT_MARGIN,
                                           Float XRP_ACTION_PRICE, float mid, List<Float> xrpFloats, float bid) {
        Float triggerPrice = (XRP_ACTION_PRICE==0)? mid : XRP_ACTION_PRICE;
        if(xrpFloats.size()<2){ //
            return false; //Watig for future values
        }else {

        }

        return false;
    }
}
