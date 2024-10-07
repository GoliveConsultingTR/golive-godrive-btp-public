package com.golive.godrive.btppublic.model.serviceModel.getReference;

import com.google.gson.annotations.SerializedName;

public class ReferenceDModel {

    @SerializedName("d")
    private ReferenceResults d;

    public ReferenceResults getD() {
        return d;
    }

    public void setD(ReferenceResults d) {
        this.d = d;
    }
}
