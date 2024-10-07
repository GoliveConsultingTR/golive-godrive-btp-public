package com.golive.godrive.btppublic.model.serviceModel.getFreightUnit;

import com.golive.godrive.btppublic.model.serviceModel.getFreightOrder.FreightOrderResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FreightUnitValueModel {
    @SerializedName("value")
    private List<FreightUnitResponse> value;

    public List<FreightUnitResponse> getValue() {
        return value;
    }

    public void setValue(List<FreightUnitResponse> value) {
        this.value = value;
    }
}
