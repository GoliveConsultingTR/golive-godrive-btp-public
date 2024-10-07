package com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery;

import com.google.gson.annotations.SerializedName;

public class CreateDeliveryDModel {
    @SerializedName("d")
    private CreateDeliveryResponse d;

    public CreateDeliveryResponse getD() {
        return d;
    }

    public void setD(CreateDeliveryResponse d) {
        this.d = d;
    }
}
