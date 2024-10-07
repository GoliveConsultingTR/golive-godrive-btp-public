package com.golive.godrive.btppublic.model.serviceModel.postGoodIssues;

import com.google.gson.annotations.SerializedName;

public class GoodsIssuesDModel {

    @SerializedName("d")
    private GoodsIssuesResults d;

    public GoodsIssuesResults getD() {
        return d;
    }

    public void setD(GoodsIssuesResults d) {
        this.d = d;
    }
}
