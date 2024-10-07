package com.golive.godrive.btppublic.model.serviceModel.getBilling;

import com.google.gson.annotations.SerializedName;

public class GetBillingDModel {
    @SerializedName("d")
    private GetBillingResults d;

    public GetBillingResults getD() {
        return d;
    }

    public void setD(GetBillingResults d) {
        this.d = d;
    }
}
