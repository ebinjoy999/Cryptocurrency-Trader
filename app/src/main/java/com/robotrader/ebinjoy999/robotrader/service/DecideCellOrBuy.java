package com.robotrader.ebinjoy999.robotrader.service;

import java.util.List;

import tools.SharedPreferenceManagerC;

/**
 * Created by ebinjoy999 on 21/01/18.
 */

public class DecideCellOrBuy {

    public static boolean decideHaveCryptoCurrency(float amountCurrency, float currencyPrice) {
        return (amountCurrency>1)? true : false;
    }

    public static boolean decideIsNeedsBuy(Float small_integral, float PROFIT_MARGIN,
                                            float mid, float bid) {





        return false;
    }
}
