package com.golive.godrive.btppublic.model.serviceModel.getBilling;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBillingResults {

    @SerializedName("results")
    private List<GetBillingResponse> results;

    public List<GetBillingResponse> getResults() {
        return results;
    }

    public void setResults(List<GetBillingResponse> results) {
        this.results = results;
    }
}
