package com.robotrader.ebinjoy999.robotrader.model.cancelorder;

/**
 * Created by ebinjoy999 on 19/01/18.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCancelAllOrder {

    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}