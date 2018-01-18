package com.robotrader.ebinjoy999.robotrader.model;

import java.io.Serializable;

/**
 * Created by ebinjoy999 on 08/01/18.
 */

public class SymbolDetails implements Serializable {
    String SYMBOL;

    float BID; //Price of last highest bid
    float BID_SIZE;
    float ASK;
    float ASK_SIZE;
    float DAILY_CHANGE;  //
    float DAILY_CHANGE_PERC; //
    float LAST_PRICE; //Price of the last trade
    float VOLUME;
    float HIGH; // Daily high
    float LOW;  //Daily low

    public SymbolDetails(String SYMBOL, float BID, float BID_SIZE, float ASK,
                         float ASK_SIZE, float DAILY_CHANGE, float DAILY_CHANGE_PERC, float LAST_PRICE,
                         float VOLUME, float HIGH, float LOW) {
        this.SYMBOL = SYMBOL;
        this.BID = BID;
        this.BID_SIZE = BID_SIZE;
        this.ASK = ASK;
        this.ASK_SIZE = ASK_SIZE;
        this.DAILY_CHANGE = DAILY_CHANGE;
        this.DAILY_CHANGE_PERC = (DAILY_CHANGE_PERC*100);
        this.LAST_PRICE = LAST_PRICE;
        this.VOLUME = VOLUME;
        this.HIGH = HIGH;
        this.LOW = LOW;
    }

    public void setSYMBOL(String SYMBOL) {
        this.SYMBOL = SYMBOL;
    }

    public void setBID(float BID) {
        this.BID = BID;
    }

    public void setBID_SIZE(float BID_SIZE) {
        this.BID_SIZE = BID_SIZE;
    }

    public void setASK(float ASK) {
        this.ASK = ASK;
    }

    public void setASK_SIZE(float ASK_SIZE) {
        this.ASK_SIZE = ASK_SIZE;
    }

    public void setDAILY_CHANGE(float DAILY_CHANGE) {
        this.DAILY_CHANGE = DAILY_CHANGE;
    }

    public void setDAILY_CHANGE_PERC(float DAILY_CHANGE_PERC) {
        this.DAILY_CHANGE_PERC = DAILY_CHANGE_PERC;
    }

    public void setLAST_PRICE(float LAST_PRICE) {
        this.LAST_PRICE = LAST_PRICE;
    }

    public void setVOLUME(float VOLUME) {
        this.VOLUME = VOLUME;
    }

    public void setHIGH(float HIGH) {
        this.HIGH = HIGH;
    }

    public void setLOW(float LOW) {
        this.LOW = LOW;
    }

    public String getSYMBOL() {
        return SYMBOL;
    }

    public float getBID() {
        return BID;
    }

    public float getBID_SIZE() {
        return BID_SIZE;
    }

    public float getASK() {
        return ASK;
    }

    public float getASK_SIZE() {
        return ASK_SIZE;
    }

    public float getDAILY_CHANGE() {
        return DAILY_CHANGE;
    }

    public float getDAILY_CHANGE_PERC() {
        return DAILY_CHANGE_PERC;
    }

    public float getLAST_PRICE() {
        return LAST_PRICE;
    }

    public float getVOLUME() {
        return VOLUME;
    }

    public float getHIGH() {
        return HIGH;
    }

    public float getLOW() {
        return LOW;
    }
}
