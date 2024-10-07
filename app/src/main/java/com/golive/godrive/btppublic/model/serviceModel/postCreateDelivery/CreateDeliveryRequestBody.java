package com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery;

import com.google.gson.annotations.SerializedName;

public class CreateDeliveryRequestBody {
    @SerializedName("ActualDeliveryQuantity")
    private String ActualDeliveryQuantity;

    @SerializedName("DeliveryQuantityUnit")
    private String DeliveryQuantityUnit;

    @SerializedName("ReferenceSDDocument")
    private String ReferenceSDDocument;

    @SerializedName("ReferenceSDDocumentItem")
    private String ReferenceSDDocumentItem;

    public String getActualDeliveryQuantity() {
        return ActualDeliveryQuantity;
    }

    public void setActualDeliveryQuantity(String actualDeliveryQuantity) {
        ActualDeliveryQuantity = actualDeliveryQuantity;
    }

    public String getDeliveryQuantityUnit() {
        return DeliveryQuantityUnit;
    }

    public void setDeliveryQuantityUnit(String deliveryQuantityUnit) {
        DeliveryQuantityUnit = deliveryQuantityUnit;
    }

    public String getReferenceSDDocument() {
        return ReferenceSDDocument;
    }

    public void setReferenceSDDocument(String referenceSDDocument) {
        ReferenceSDDocument = referenceSDDocument;
    }

    public String getReferenceSDDocumentItem() {
        return ReferenceSDDocumentItem;
    }

    public void setReferenceSDDocumentItem(String referenceSDDocumentItem) {
        ReferenceSDDocumentItem = referenceSDDocumentItem;
    }
}
