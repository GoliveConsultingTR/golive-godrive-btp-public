package com.golive.godrive.btppublic.model.serviceModel.getStatusCheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatusCheckResults {

    @SerializedName("results")
    private List<StatusCheckResponse> results;

    public List<StatusCheckResponse> getResults() {
        return results;
    }

    public void setResults(List<StatusCheckResponse> results) {
        this.results = results;
    }
}
