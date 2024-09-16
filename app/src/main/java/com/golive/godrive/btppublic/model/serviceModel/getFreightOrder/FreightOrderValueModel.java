package com.golive.godrive.btppublic.model.serviceModel.getFreightOrder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FreightOrderValueModel {
    @SerializedName("value")
    private List<FreightOrderResponse> value;

    public List<FreightOrderResponse> getValue() {
        return value;
    }

    public void setValue(List<FreightOrderResponse> value) {
        this.value = value;
    }
}
