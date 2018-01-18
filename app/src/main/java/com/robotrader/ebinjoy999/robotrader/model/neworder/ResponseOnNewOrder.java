package com.robotrader.ebinjoy999.robotrader.model.neworder;

/**
 * Created by ebinjoy999 on 19/01/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseOnNewOrder {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("exchange")
    @Expose
    private String exchange;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("avg_execution_price")
    @Expose
    private String avgExecutionPrice;
    @SerializedName("side")
    @Expose
    private String side;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("is_live")
    @Expose
    private Boolean isLive;
    @SerializedName("is_cancelled")
    @Expose
    private Boolean isCancelled;
    @SerializedName("is_hidden")
    @Expose
    private Boolean isHidden;
    @SerializedName("was_forced")
    @Expose
    private Boolean wasForced;
    @SerializedName("original_amount")
    @Expose
    private String originalAmount;
    @SerializedName("remaining_amount")
    @Expose
    private String remainingAmount;
    @SerializedName("executed_amount")
    @Expose
    private String executedAmount;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvgExecutionPrice() {
        return avgExecutionPrice;
    }

    public void setAvgExecutionPrice(String avgExecutionPrice) {
        this.avgExecutionPrice = avgExecutionPrice;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Boolean getWasForced() {
        return wasForced;
    }

    public void setWasForced(Boolean wasForced) {
        this.wasForced = wasForced;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getExecutedAmount() {
        return executedAmount;
    }

    public void setExecutedAmount(String executedAmount) {
        this.executedAmount = executedAmount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}
