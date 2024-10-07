package com.golive.godrive.btppublic.model.serviceModel.getSalesOrder;

import com.google.gson.annotations.SerializedName;

public class SalesOrderResponse {

    @SerializedName("SalesOrder")
    private String SalesOrder;

    @SerializedName("SalesOrderItem")
    private String SalesOrderItem;

    @SerializedName("SalesOrderItemText")
    private String SalesOrderItemText;

    @SerializedName("OrderQuantityUnit")
    private String OrderQuantityUnit;

    @SerializedName("ItemGrossWeight")
    private String ItemGrossWeight;

    @SerializedName("ItemNetWeight")
    private String ItemNetWeight;

    @SerializedName("NetAmount")
    private String NetAmount;

    @SerializedName("RequestedQuantity")
    private String RequestedQuantity;

    @SerializedName("RequestedQuantityUnit")
    private String RequestedQuantityUnit;

    public String getRequestedQuantityUnit() {
        return RequestedQuantityUnit;
    }

    public void setRequestedQuantityUnit(String requestedQuantityUnit) {
        RequestedQuantityUnit = requestedQuantityUnit;
    }

    public String getRequestedQuantity() {
        return RequestedQuantity;
    }

    public void setRequestedQuantity(String requestedQuantity) {
        RequestedQuantity = requestedQuantity;
    }

    public String getSalesOrder() {
        return SalesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        SalesOrder = salesOrder;
    }

    public String getSalesOrderItem() {
        return SalesOrderItem;
    }

    public void setSalesOrderItem(String salesOrderItem) {
        SalesOrderItem = salesOrderItem;
    }

    public String getSalesOrderItemText() {
        return SalesOrderItemText;
    }

    public void setSalesOrderItemText(String salesOrderItemText) {
        SalesOrderItemText = salesOrderItemText;
    }

    public String getOrderQuantityUnit() {
        return OrderQuantityUnit;
    }

    public void setOrderQuantityUnit(String orderQuantityUnit) {
        OrderQuantityUnit = orderQuantityUnit;
    }

    public String getItemGrossWeight() {
        return ItemGrossWeight;
    }

    public void setItemGrossWeight(String itemGrossWeight) {
        ItemGrossWeight = itemGrossWeight;
    }

    public String getItemNetWeight() {
        return ItemNetWeight;
    }

    public void setItemNetWeight(String itemNetWeight) {
        ItemNetWeight = itemNetWeight;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }
}
