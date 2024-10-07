package com.golive.godrive.btppublic.model.serviceModel.getFreightOrder;

import com.google.gson.annotations.SerializedName;

public class FreightOrderResponse {
    @SerializedName("TransportationOrderUUID")
    private String TransportationOrderUUID;

    @SerializedName("TransportationOrder")
    private String TransportationOrder;

    @SerializedName("TransportationOrderType")
    private String TransportationOrderType;

    @SerializedName("TransportationOrderCategory")
    private String TransportationOrderCategory;

    @SerializedName("TransportationShippingType")
    private String TransportationShippingType;

    @SerializedName("TransportationMode")
    private String TransportationMode;

    @SerializedName("TransportationModeCategory")
    private String TransportationModeCategory;

    @SerializedName("CarrierUUID")
    private String CarrierUUID;

    @SerializedName("Carrier")
    private String Carrier;

    @SerializedName("StandardCarrierAlphaCode")
    private String StandardCarrierAlphaCode;

    @SerializedName("TranspOrdExecutingCarrierUUID")
    private String TranspOrdExecutingCarrierUUID;

    @SerializedName("TranspOrdExecutingCarrier")
    private String TranspOrdExecutingCarrier;

    @SerializedName("ShipperUUID")
    private String ShipperUUID;

    @SerializedName("Shipper")
    private String Shipper;

    @SerializedName("ShipperAddressID")
    private String ShipperAddressID;

    @SerializedName("ConsigneeUUID")
    private String ConsigneeUUID;

    @SerializedName("Consignee")
    private String Consignee;

    @SerializedName("ConsigneeAddressID")
    private String ConsigneeAddressID;

    @SerializedName("TranspPurgOrg")
    private String TranspPurgOrg;

    @SerializedName("TranspPurgOrgExtID")
    private String TranspPurgOrgExtID;

    @SerializedName("TranspPurgGroup")
    private String TranspPurgGroup;

    @SerializedName("TranspPurgGroupExtID")
    private String TranspPurgGroupExtID;

    @SerializedName("PurgOrgCompanyCode")
    private String PurgOrgCompanyCode;

    @SerializedName("CarrierAccountNumber")
    private String CarrierAccountNumber;

    @SerializedName("TranspMeansOfTransport")
    private String TranspMeansOfTransport;

    @SerializedName("TranspOrdPartnerReference")
    private String TranspOrdPartnerReference;

    @SerializedName("TranspOrdResponsiblePerson")
    private String TranspOrdResponsiblePerson;

    @SerializedName("TranspOrdHasMltplExectgPties")
    private String TranspOrdHasMltplExectgPties;

    @SerializedName("TranspOrdInvoicingCarrierLevel")
    private String TranspOrdInvoicingCarrierLevel;

    @SerializedName("TranspOrdLifeCycleStatus")
    private String TranspOrdLifeCycleStatus;

    @SerializedName("TranspOrderSubcontrgSts")
    private String TranspOrderSubcontrgSts;

    @SerializedName("TransportationOrderConfSts")
    private String TransportationOrderConfSts;

    @SerializedName("TransportationOrderExecSts")
    private String TransportationOrderExecSts;

    @SerializedName("TranspOrdGoodsMovementStatus")
    private String TranspOrdGoodsMovementStatus;

    @SerializedName("TranspOrdWhseProcessingStatus")
    private String TranspOrdWhseProcessingStatus;

    @SerializedName("TranspOrderDngrsGdsSts")
    private String TranspOrderDngrsGdsSts;

    @SerializedName("TranspOrdExecutionIsBlocked")
    private String TranspOrdExecutionIsBlocked;

    @SerializedName("TransportationOrderCrtnType")
    private String TransportationOrderCrtnType;

    @SerializedName("CreatedByUser")
    private String CreatedByUser;

    @SerializedName("CreationDateTime")
    private String CreationDateTime;

    @SerializedName("LastChangedByUser")
    private String LastChangedByUser;

    @SerializedName("ChangedDateTime")
    private String ChangedDateTime;

    @SerializedName("YY1_Plaka_TOR")
    private String YY1_Plaka_TOR;

    @SerializedName("@odata.etag")
    private String odataEtag;

    @SerializedName("YY1_YY_Driver_TOR")
    private String YY1_YY_Driver_TOR;

    @SerializedName("YY1_DriverDescription_TOR")
    private String YY1_DriverDescription_TOR;

    public String getYY1_DriverDescription_TOR() {
        return YY1_DriverDescription_TOR;
    }

    public void setYY1_DriverDescription_TOR(String YY1_DriverDescription_TOR) {
        this.YY1_DriverDescription_TOR = YY1_DriverDescription_TOR;
    }

    public String getYY1_YY_Driver_TOR() {
        return YY1_YY_Driver_TOR;
    }

    public void setYY1_YY_Driver_TOR(String YY1_YY_Driver_TOR) {
        this.YY1_YY_Driver_TOR = YY1_YY_Driver_TOR;
    }

    public String getOdataEtag() {
        return odataEtag;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    public String getTransportationOrderUUID() {
        return TransportationOrderUUID;
    }

    public void setTransportationOrderUUID(String transportationOrderUUID) {
        TransportationOrderUUID = transportationOrderUUID;
    }

    public String getTransportationOrder() {
        return TransportationOrder;
    }

    public void setTransportationOrder(String transportationOrder) {
        TransportationOrder = transportationOrder;
    }

    public String getTransportationOrderType() {
        return TransportationOrderType;
    }

    public void setTransportationOrderType(String transportationOrderType) {
        TransportationOrderType = transportationOrderType;
    }

    public String getTransportationOrderCategory() {
        return TransportationOrderCategory;
    }

    public void setTransportationOrderCategory(String transportationOrderCategory) {
        TransportationOrderCategory = transportationOrderCategory;
    }

    public String getTransportationShippingType() {
        return TransportationShippingType;
    }

    public void setTransportationShippingType(String transportationShippingType) {
        TransportationShippingType = transportationShippingType;
    }

    public String getTransportationMode() {
        return TransportationMode;
    }

    public void setTransportationMode(String transportationMode) {
        TransportationMode = transportationMode;
    }

    public String getTransportationModeCategory() {
        return TransportationModeCategory;
    }

    public void setTransportationModeCategory(String transportationModeCategory) {
        TransportationModeCategory = transportationModeCategory;
    }

    public String getCarrierUUID() {
        return CarrierUUID;
    }

    public void setCarrierUUID(String carrierUUID) {
        CarrierUUID = carrierUUID;
    }

    public String getCarrier() {
        return Carrier;
    }

    public void setCarrier(String carrier) {
        Carrier = carrier;
    }

    public String getStandardCarrierAlphaCode() {
        return StandardCarrierAlphaCode;
    }

    public void setStandardCarrierAlphaCode(String standardCarrierAlphaCode) {
        StandardCarrierAlphaCode = standardCarrierAlphaCode;
    }

    public String getTranspOrdExecutingCarrierUUID() {
        return TranspOrdExecutingCarrierUUID;
    }

    public void setTranspOrdExecutingCarrierUUID(String transpOrdExecutingCarrierUUID) {
        TranspOrdExecutingCarrierUUID = transpOrdExecutingCarrierUUID;
    }

    public String getTranspOrdExecutingCarrier() {
        return TranspOrdExecutingCarrier;
    }

    public void setTranspOrdExecutingCarrier(String transpOrdExecutingCarrier) {
        TranspOrdExecutingCarrier = transpOrdExecutingCarrier;
    }

    public String getShipperUUID() {
        return ShipperUUID;
    }

    public void setShipperUUID(String shipperUUID) {
        ShipperUUID = shipperUUID;
    }

    public String getShipper() {
        return Shipper;
    }

    public void setShipper(String shipper) {
        Shipper = shipper;
    }

    public String getShipperAddressID() {
        return ShipperAddressID;
    }

    public void setShipperAddressID(String shipperAddressID) {
        ShipperAddressID = shipperAddressID;
    }

    public String getConsigneeUUID() {
        return ConsigneeUUID;
    }

    public void setConsigneeUUID(String consigneeUUID) {
        ConsigneeUUID = consigneeUUID;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public String getConsigneeAddressID() {
        return ConsigneeAddressID;
    }

    public void setConsigneeAddressID(String consigneeAddressID) {
        ConsigneeAddressID = consigneeAddressID;
    }

    public String getTranspPurgOrg() {
        return TranspPurgOrg;
    }

    public void setTranspPurgOrg(String transpPurgOrg) {
        TranspPurgOrg = transpPurgOrg;
    }

    public String getTranspPurgOrgExtID() {
        return TranspPurgOrgExtID;
    }

    public void setTranspPurgOrgExtID(String transpPurgOrgExtID) {
        TranspPurgOrgExtID = transpPurgOrgExtID;
    }

    public String getTranspPurgGroup() {
        return TranspPurgGroup;
    }

    public void setTranspPurgGroup(String transpPurgGroup) {
        TranspPurgGroup = transpPurgGroup;
    }

    public String getTranspPurgGroupExtID() {
        return TranspPurgGroupExtID;
    }

    public void setTranspPurgGroupExtID(String transpPurgGroupExtID) {
        TranspPurgGroupExtID = transpPurgGroupExtID;
    }

    public String getPurgOrgCompanyCode() {
        return PurgOrgCompanyCode;
    }

    public void setPurgOrgCompanyCode(String purgOrgCompanyCode) {
        PurgOrgCompanyCode = purgOrgCompanyCode;
    }

    public String getCarrierAccountNumber() {
        return CarrierAccountNumber;
    }

    public void setCarrierAccountNumber(String carrierAccountNumber) {
        CarrierAccountNumber = carrierAccountNumber;
    }

    public String getTranspMeansOfTransport() {
        return TranspMeansOfTransport;
    }

    public void setTranspMeansOfTransport(String transpMeansOfTransport) {
        TranspMeansOfTransport = transpMeansOfTransport;
    }

    public String getTranspOrdPartnerReference() {
        return TranspOrdPartnerReference;
    }

    public void setTranspOrdPartnerReference(String transpOrdPartnerReference) {
        TranspOrdPartnerReference = transpOrdPartnerReference;
    }

    public String getTranspOrdResponsiblePerson() {
        return TranspOrdResponsiblePerson;
    }

    public void setTranspOrdResponsiblePerson(String transpOrdResponsiblePerson) {
        TranspOrdResponsiblePerson = transpOrdResponsiblePerson;
    }

    public String getTranspOrdHasMltplExectgPties() {
        return TranspOrdHasMltplExectgPties;
    }

    public void setTranspOrdHasMltplExectgPties(String transpOrdHasMltplExectgPties) {
        TranspOrdHasMltplExectgPties = transpOrdHasMltplExectgPties;
    }

    public String getTranspOrdInvoicingCarrierLevel() {
        return TranspOrdInvoicingCarrierLevel;
    }

    public void setTranspOrdInvoicingCarrierLevel(String transpOrdInvoicingCarrierLevel) {
        TranspOrdInvoicingCarrierLevel = transpOrdInvoicingCarrierLevel;
    }

    public String getTranspOrdLifeCycleStatus() {
        return TranspOrdLifeCycleStatus;
    }

    public void setTranspOrdLifeCycleStatus(String transpOrdLifeCycleStatus) {
        TranspOrdLifeCycleStatus = transpOrdLifeCycleStatus;
    }

    public String getTranspOrderSubcontrgSts() {
        return TranspOrderSubcontrgSts;
    }

    public void setTranspOrderSubcontrgSts(String transpOrderSubcontrgSts) {
        TranspOrderSubcontrgSts = transpOrderSubcontrgSts;
    }

    public String getTransportationOrderConfSts() {
        return TransportationOrderConfSts;
    }

    public void setTransportationOrderConfSts(String transportationOrderConfSts) {
        TransportationOrderConfSts = transportationOrderConfSts;
    }

    public String getTransportationOrderExecSts() {
        return TransportationOrderExecSts;
    }

    public void setTransportationOrderExecSts(String transportationOrderExecSts) {
        TransportationOrderExecSts = transportationOrderExecSts;
    }

    public String getTranspOrdGoodsMovementStatus() {
        return TranspOrdGoodsMovementStatus;
    }

    public void setTranspOrdGoodsMovementStatus(String transpOrdGoodsMovementStatus) {
        TranspOrdGoodsMovementStatus = transpOrdGoodsMovementStatus;
    }

    public String getTranspOrdWhseProcessingStatus() {
        return TranspOrdWhseProcessingStatus;
    }

    public void setTranspOrdWhseProcessingStatus(String transpOrdWhseProcessingStatus) {
        TranspOrdWhseProcessingStatus = transpOrdWhseProcessingStatus;
    }

    public String getTranspOrderDngrsGdsSts() {
        return TranspOrderDngrsGdsSts;
    }

    public void setTranspOrderDngrsGdsSts(String transpOrderDngrsGdsSts) {
        TranspOrderDngrsGdsSts = transpOrderDngrsGdsSts;
    }

    public String getTranspOrdExecutionIsBlocked() {
        return TranspOrdExecutionIsBlocked;
    }

    public void setTranspOrdExecutionIsBlocked(String transpOrdExecutionIsBlocked) {
        TranspOrdExecutionIsBlocked = transpOrdExecutionIsBlocked;
    }

    public String getTransportationOrderCrtnType() {
        return TransportationOrderCrtnType;
    }

    public void setTransportationOrderCrtnType(String transportationOrderCrtnType) {
        TransportationOrderCrtnType = transportationOrderCrtnType;
    }

    public String getCreatedByUser() {
        return CreatedByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        CreatedByUser = createdByUser;
    }

    public String getCreationDateTime() {
        return CreationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        CreationDateTime = creationDateTime;
    }

    public String getLastChangedByUser() {
        return LastChangedByUser;
    }

    public void setLastChangedByUser(String lastChangedByUser) {
        LastChangedByUser = lastChangedByUser;
    }

    public String getChangedDateTime() {
        return ChangedDateTime;
    }

    public void setChangedDateTime(String changedDateTime) {
        ChangedDateTime = changedDateTime;
    }

    public String getYY1_Plaka_TOR() {
        return YY1_Plaka_TOR;
    }

    public void setYY1_Plaka_TOR(String YY1_Plaka_TOR) {
        this.YY1_Plaka_TOR = YY1_Plaka_TOR;
    }

}
