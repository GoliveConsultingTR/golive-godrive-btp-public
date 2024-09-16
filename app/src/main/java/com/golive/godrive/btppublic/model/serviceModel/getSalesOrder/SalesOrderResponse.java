package com.golive.godrive.btppublic.model.serviceModel.getSalesOrder;

import com.google.gson.annotations.SerializedName;

public class SalesOrderResponse {

    @SerializedName("SalesOrder")
    private String SalesOrder;

    @SerializedName("SalesOrderType")
    private String SalesOrderType;

    @SerializedName("SalesOrderTypeInternalCode")
    private String SalesOrderTypeInternalCode;

    @SerializedName("SalesOrganization")
    private String SalesOrganization;

    @SerializedName("DistributionChannel")
    private String DistributionChannel;

    @SerializedName("OrganizationDivision")
    private String OrganizationDivision;

    @SerializedName("SalesGroup")
    private String SalesGroup;

    @SerializedName("SalesOffice")
    private String SalesOffice;

    @SerializedName("SalesDistrict")
    private String SalesDistrict;

    @SerializedName("SoldToParty")
    private String SoldToParty;

    @SerializedName("CreationDate")
    private String CreationDate;

    @SerializedName("CreatedByUser")
    private String CreatedByUser;

    @SerializedName("LastChangeDate")
    private String LastChangeDate;

    @SerializedName("SenderBusinessSystemName")
    private String SenderBusinessSystemName;

    @SerializedName("ExternalDocumentID")
    private String ExternalDocumentID;

    @SerializedName("LastChangeDateTime")
    private String LastChangeDateTime;

    public String getSalesOrder() {
        return SalesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        SalesOrder = salesOrder;
    }

    public String getSalesOrderType() {
        return SalesOrderType;
    }

    public void setSalesOrderType(String salesOrderType) {
        SalesOrderType = salesOrderType;
    }

    public String getSalesOrderTypeInternalCode() {
        return SalesOrderTypeInternalCode;
    }

    public void setSalesOrderTypeInternalCode(String salesOrderTypeInternalCode) {
        SalesOrderTypeInternalCode = salesOrderTypeInternalCode;
    }

    public String getSalesOrganization() {
        return SalesOrganization;
    }

    public void setSalesOrganization(String salesOrganization) {
        SalesOrganization = salesOrganization;
    }

    public String getDistributionChannel() {
        return DistributionChannel;
    }

    public void setDistributionChannel(String distributionChannel) {
        DistributionChannel = distributionChannel;
    }

    public String getOrganizationDivision() {
        return OrganizationDivision;
    }

    public void setOrganizationDivision(String organizationDivision) {
        OrganizationDivision = organizationDivision;
    }

    public String getSalesGroup() {
        return SalesGroup;
    }

    public void setSalesGroup(String salesGroup) {
        SalesGroup = salesGroup;
    }

    public String getSalesOffice() {
        return SalesOffice;
    }

    public void setSalesOffice(String salesOffice) {
        SalesOffice = salesOffice;
    }

    public String getSalesDistrict() {
        return SalesDistrict;
    }

    public void setSalesDistrict(String salesDistrict) {
        SalesDistrict = salesDistrict;
    }

    public String getSoldToParty() {
        return SoldToParty;
    }

    public void setSoldToParty(String soldToParty) {
        SoldToParty = soldToParty;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getCreatedByUser() {
        return CreatedByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        CreatedByUser = createdByUser;
    }

    public String getLastChangeDate() {
        return LastChangeDate;
    }

    public void setLastChangeDate(String lastChangeDate) {
        LastChangeDate = lastChangeDate;
    }

    public String getSenderBusinessSystemName() {
        return SenderBusinessSystemName;
    }

    public void setSenderBusinessSystemName(String senderBusinessSystemName) {
        SenderBusinessSystemName = senderBusinessSystemName;
    }

    public String getExternalDocumentID() {
        return ExternalDocumentID;
    }

    public void setExternalDocumentID(String externalDocumentID) {
        ExternalDocumentID = externalDocumentID;
    }

    public String getLastChangeDateTime() {
        return LastChangeDateTime;
    }

    public void setLastChangeDateTime(String lastChangeDateTime) {
        LastChangeDateTime = lastChangeDateTime;
    }
}
