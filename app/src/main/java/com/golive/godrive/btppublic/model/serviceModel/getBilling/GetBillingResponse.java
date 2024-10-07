package com.golive.godrive.btppublic.model.serviceModel.getBilling;

import com.google.gson.annotations.SerializedName;

public class GetBillingResponse {

    @SerializedName("BillingDocument")
    private String BillingDocument;

    @SerializedName("BillingDocumentItem")
    private String BillingDocumentItem;

    @SerializedName("CreationTime")
    private String CreationTime;

    public String getBillingDocument() {
        return BillingDocument;
    }

    public void setBillingDocument(String billingDocument) {
        BillingDocument = billingDocument;
    }

    public String getBillingDocumentItem() {
        return BillingDocumentItem;
    }

    public void setBillingDocumentItem(String billingDocumentItem) {
        BillingDocumentItem = billingDocumentItem;
    }

    public String getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(String creationTime) {
        CreationTime = creationTime;
    }
}
