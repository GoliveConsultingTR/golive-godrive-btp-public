package com.golive.godrive.btppublic.model.serviceModel.postGoodIssues;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoodsIssuesResults {
    public List<GoodsIssuesResponse> getResults() {
        return results;
    }

    public void setResults(List<GoodsIssuesResponse> results) {
        this.results = results;
    }

    @SerializedName("results")
    private List<GoodsIssuesResponse> results;
}
