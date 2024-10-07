package com.golive.godrive.btppublic.model.serviceModel.getReference;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferenceResults {
    public List<ReferenceResponse> getResults() {
        return results;
    }

    public void setResults(List<ReferenceResponse> results) {
        this.results = results;
    }

    @SerializedName("results")
    private List<ReferenceResponse> results;
}
