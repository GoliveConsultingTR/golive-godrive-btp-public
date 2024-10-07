package com.golive.godrive.btppublic.model.serviceModel.getReference;

import com.google.gson.annotations.SerializedName;

public class ReferenceResponse {
    @SerializedName("ReferenceSDDocument")
    private String ReferenceSDDocument;

    @SerializedName("DeliveryDocument")
    private String DeliveryDocument;

    public String getReferenceSDDocument() {
        return ReferenceSDDocument;
    }

    public void setReferenceSDDocument(String referenceSDDocument) {
        ReferenceSDDocument = referenceSDDocument;
    }

    public String getDeliveryDocument() {
        return DeliveryDocument;
    }

    public void setDeliveryDocument(String deliveryDocument) {
        DeliveryDocument = deliveryDocument;
    }
}
