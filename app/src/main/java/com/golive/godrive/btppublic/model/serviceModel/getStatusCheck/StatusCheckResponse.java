package com.golive.godrive.btppublic.model.serviceModel.getStatusCheck;

import com.google.gson.annotations.SerializedName;

public class StatusCheckResponse {
    @SerializedName("DeliveryDocument")
    private String DeliveryDocument;

    @SerializedName("DeliveryRelatedBillingStatus")
    private String DeliveryRelatedBillingStatus;

    public String getDeliveryDocument() {
        return DeliveryDocument;
    }

    public void setDeliveryDocument(String deliveryDocument) {
        DeliveryDocument = deliveryDocument;
    }

    public String getDeliveryRelatedBillingStatus() {
        return DeliveryRelatedBillingStatus;
    }

    public void setDeliveryRelatedBillingStatus(String deliveryRelatedBillingStatus) {
        DeliveryRelatedBillingStatus = deliveryRelatedBillingStatus;
    }
}
