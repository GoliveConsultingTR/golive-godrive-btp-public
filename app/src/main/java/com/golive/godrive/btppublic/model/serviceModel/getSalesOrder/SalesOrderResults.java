package com.golive.godrive.btppublic.model.serviceModel.getSalesOrder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesOrderResults {
    @SerializedName("results")
    private List<SalesOrderResponse> results;

    public List<SalesOrderResponse> getResults() {
        return results;
    }

    public void setResults(List<SalesOrderResponse> results) {
        this.results = results;
    }
}
