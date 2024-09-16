package com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerAddress;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResultsModel {
    @SerializedName("results")
    private List<AddressResponse> results;

    public List<AddressResponse> getResults() {
        return results;
    }

    public void setResults(List<AddressResponse> results) {
        this.results = results;
    }
}
