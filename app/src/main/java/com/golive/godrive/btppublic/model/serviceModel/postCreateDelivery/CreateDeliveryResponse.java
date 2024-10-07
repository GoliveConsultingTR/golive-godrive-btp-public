package com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery;

import com.google.gson.annotations.SerializedName;

public class CreateDeliveryResponse {
    @SerializedName("ActualDeliveryRoute")
    private String ActualDeliveryRoute;

    @SerializedName("DeliveryDate")
    private String DeliveryDate;

    @SerializedName("DeliveryTime")
    private String DeliveryTime;

    @SerializedName("DeliveryDocument")
    private String DeliveryDocument;

    public String getActualDeliveryRoute() {
        return ActualDeliveryRoute;
    }

    public void setActualDeliveryRoute(String actualDeliveryRoute) {
        ActualDeliveryRoute = actualDeliveryRoute;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public String getDeliveryDocument() {
        return DeliveryDocument;
    }

    public void setDeliveryDocument(String deliveryDocument) {
        DeliveryDocument = deliveryDocument;
    }
}
