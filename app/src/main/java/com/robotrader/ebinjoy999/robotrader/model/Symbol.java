package com.robotrader.ebinjoy999.robotrader.model;

/**
 * Created by ebinjoy999 on 20/12/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Symbol {

    @SerializedName("pair")
    @Expose
    private String pair;
    @SerializedName("price_precision")
    @Expose
    private Integer pricePrecision;
    @SerializedName("initial_margin")
    @Expose
    private String initialMargin;
    @SerializedName("minimum_margin")
    @Expose
    private String minimumMargin;
    @SerializedName("maximum_order_size")
    @Expose
    private String maximumOrderSize;
    @SerializedName("minimum_order_size")
    @Expose
    private String minimumOrderSize;
    @SerializedName("expiration")
    @Expose
    private String expiration;

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Integer getPricePrecision() {
        return pricePrecision;
    }

    public void setPricePrecision(Integer pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    public String getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(String initialMargin) {
        this.initialMargin = initialMargin;
    }

    public String getMinimumMargin() {
        return minimumMargin;
    }

    public void setMinimumMargin(String minimumMargin) {
        this.minimumMargin = minimumMargin;
    }

    public String getMaximumOrderSize() {
        return maximumOrderSize;
    }

    public void setMaximumOrderSize(String maximumOrderSize) {
        this.maximumOrderSize = maximumOrderSize;
    }

    public String getMinimumOrderSize() {
        return minimumOrderSize;
    }

    public void setMinimumOrderSize(String minimumOrderSize) {
        this.minimumOrderSize = minimumOrderSize;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

}
