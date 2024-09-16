package com.golive.godrive.btppublic.model.serviceModel.getSalesOrder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesOrderDModel {
    @SerializedName("d")
    private SalesOrderResponse d;

    public SalesOrderResponse getD() {
        return d;
    }

    public void setD(SalesOrderResponse d) {
        this.d = d;
    }
}
