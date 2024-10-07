package com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateDeliveryRequestResults {
    @SerializedName("results")
    private List<CreateDeliveryRequestBody> results;

    public List<CreateDeliveryRequestBody> getResults() {
        return results;
    }

    public void setResults(List<CreateDeliveryRequestBody> results) {
        this.results = results;
    }
}
