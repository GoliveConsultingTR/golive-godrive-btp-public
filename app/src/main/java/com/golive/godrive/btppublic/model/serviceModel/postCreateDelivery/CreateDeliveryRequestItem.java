package com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery;

import com.google.gson.annotations.SerializedName;

public class CreateDeliveryRequestItem {
    @SerializedName("to_DeliveryDocumentItem")
    private CreateDeliveryRequestResults to_DeliveryDocumentItem;

    public CreateDeliveryRequestResults getTo_DeliveryDocumentItem() {
        return to_DeliveryDocumentItem;
    }

    public void setTo_DeliveryDocumentItem(CreateDeliveryRequestResults to_DeliveryDocumentItem) {
        this.to_DeliveryDocumentItem = to_DeliveryDocumentItem;
    }
}
