package com.golive.godrive.btppublic.model.serviceModel.BusinessPartner;

import com.google.gson.annotations.SerializedName;

public class BusinessPartnerDModel {
    @SerializedName("d")
    private BusinessPartnerResponse d;

    public BusinessPartnerResponse getD() {
        return d;
    }

    public void setD(BusinessPartnerResponse d) {
        this.d = d;
    }
}
