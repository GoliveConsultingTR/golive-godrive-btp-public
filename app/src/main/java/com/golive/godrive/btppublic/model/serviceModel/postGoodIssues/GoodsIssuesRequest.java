package com.golive.godrive.btppublic.model.serviceModel.postGoodIssues;

import com.google.gson.annotations.SerializedName;

public class GoodsIssuesRequest {

    @SerializedName("DeliveryDocument")
    private String DeliveryDocument;

    public String getDeliveryDocument() {
        return DeliveryDocument;
    }

    public void setDeliveryDocument(String deliveryDocument) {
        DeliveryDocument = deliveryDocument;
    }
}
