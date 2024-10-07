package com.golive.godrive.btppublic.model.serviceModel.getDocumentOrder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentOrderResponse {

    @SerializedName("TransportationOrderDocRefUUID")
    private String TransportationOrderDocRefUUID;

    @SerializedName("TransportationOrderUUID")
    private String TransportationOrderUUID;

    @SerializedName("TranspOrdDocReferenceID")
    private String TranspOrdDocReferenceID;

    @SerializedName("TranspOrdDocReferenceType")
    private String TranspOrdDocReferenceType;

    @SerializedName("TranspOrdDocReferenceItmID")
    private String TranspOrdDocReferenceItmID;

    @SerializedName("TranspOrdDocReferenceItmType")
    private String TranspOrdDocReferenceItmType;

    @SerializedName("TranspOrdDocumentReferenceDate")
    private String TranspOrdDocumentReferenceDate;

    @SerializedName("TranspOrdDocRefIssuerName")
    private String TranspOrdDocRefIssuerName;

    public String getOdataEtag() {
        return odataEtag;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    @SerializedName("@odata.etag")
    private String odataEtag;


    public String getTransportationOrderDocRefUUID() {
        return TransportationOrderDocRefUUID;
    }

    public void setTransportationOrderDocRefUUID(String transportationOrderDocRefUUID) {
        TransportationOrderDocRefUUID = transportationOrderDocRefUUID;
    }

    public String getTransportationOrderUUID() {
        return TransportationOrderUUID;
    }

    public void setTransportationOrderUUID(String transportationOrderUUID) {
        TransportationOrderUUID = transportationOrderUUID;
    }

    public String getTranspOrdDocReferenceID() {
        return TranspOrdDocReferenceID;
    }

    public void setTranspOrdDocReferenceID(String transpOrdDocReferenceID) {
        TranspOrdDocReferenceID = transpOrdDocReferenceID;
    }

    public String getTranspOrdDocReferenceType() {
        return TranspOrdDocReferenceType;
    }

    public void setTranspOrdDocReferenceType(String transpOrdDocReferenceType) {
        TranspOrdDocReferenceType = transpOrdDocReferenceType;
    }

    public String getTranspOrdDocReferenceItmID() {
        return TranspOrdDocReferenceItmID;
    }

    public void setTranspOrdDocReferenceItmID(String transpOrdDocReferenceItmID) {
        TranspOrdDocReferenceItmID = transpOrdDocReferenceItmID;
    }

    public String getTranspOrdDocReferenceItmType() {
        return TranspOrdDocReferenceItmType;
    }

    public void setTranspOrdDocReferenceItmType(String transpOrdDocReferenceItmType) {
        TranspOrdDocReferenceItmType = transpOrdDocReferenceItmType;
    }

    public String getTranspOrdDocumentReferenceDate() {
        return TranspOrdDocumentReferenceDate;
    }

    public void setTranspOrdDocumentReferenceDate(String transpOrdDocumentReferenceDate) {
        TranspOrdDocumentReferenceDate = transpOrdDocumentReferenceDate;
    }

    public String getTranspOrdDocRefIssuerName() {
        return TranspOrdDocRefIssuerName;
    }

    public void setTranspOrdDocRefIssuerName(String transpOrdDocRefIssuerName) {
        TranspOrdDocRefIssuerName = transpOrdDocRefIssuerName;
    }
}
