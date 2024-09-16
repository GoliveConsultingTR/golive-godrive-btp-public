package com.golive.godrive.btppublic.model.serviceModel.getDocumentOrder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentOrderValueModel {
    public List<DocumentOrderResponse> getValue() {
        return value;
    }

    public void setValue(List<DocumentOrderResponse> value) {
        this.value = value;
    }

    @SerializedName("value")
    private List<DocumentOrderResponse> value;
}
