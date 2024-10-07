package com.golive.godrive.btppublic.model.serviceModel.getSalesOrder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesOrderDModel {
    @SerializedName("d")
    private SalesOrderResults d;

    public SalesOrderResults getD() {
        return d;
    }

    public void setD(SalesOrderResults d) {
        this.d = d;
    }
}
