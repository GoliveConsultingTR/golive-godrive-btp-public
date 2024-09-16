package com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone;

import com.google.gson.annotations.SerializedName;

public class PhoneDModel {
    @SerializedName("d")
    private PhoneResultsModel d;

    public PhoneResultsModel getD() {
        return d;
    }

    public void setD(PhoneResultsModel d) {
        this.d = d;
    }
}
