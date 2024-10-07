package com.golive.godrive.btppublic.model.dataModel;

public class DeliveryModel {
    private String deliveryItem;
    private String deliveryQuantityUnit;
    private String referenceDocument;
    private String referenceDocumentItem;

    public String getDeliveryItem() {
        return deliveryItem;
    }

    public void setDeliveryItem(String deliveryItem) {
        this.deliveryItem = deliveryItem;
    }

    public String getDeliveryQuantityUnit() {
        return deliveryQuantityUnit;
    }

    public void setDeliveryQuantityUnit(String deliveryQuantityUnit) {
        this.deliveryQuantityUnit = deliveryQuantityUnit;
    }

    public String getReferenceDocument() {
        return referenceDocument;
    }

    public void setReferenceDocument(String referenceDocument) {
        this.referenceDocument = referenceDocument;
    }

    public String getReferenceDocumentItem() {
        return referenceDocumentItem;
    }

    public void setReferenceDocumentItem(String referenceDocumentItem) {
        this.referenceDocumentItem = referenceDocumentItem;
    }
}
