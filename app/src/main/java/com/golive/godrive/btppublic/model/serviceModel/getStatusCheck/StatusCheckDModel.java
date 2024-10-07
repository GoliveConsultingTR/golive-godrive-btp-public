package com.golive.godrive.btppublic.model.serviceModel.getStatusCheck;

import com.google.gson.annotations.SerializedName;

public class StatusCheckDModel {

    @SerializedName("d")
    private StatusCheckResults d;

    public StatusCheckResults getD() {
        return d;
    }

    public void setD(StatusCheckResults d) {
        this.d = d;
    }
}
