package com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhoneResultsModel {
    @SerializedName("results")
    private List<PhoneResponse> results;

    public List<PhoneResponse> getResults() {
        return results;
    }

    public void setResults(List<PhoneResponse> results) {
        this.results = results;
    }
}
