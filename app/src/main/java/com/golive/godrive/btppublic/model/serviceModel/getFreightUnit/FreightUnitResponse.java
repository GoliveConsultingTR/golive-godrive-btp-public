package com.golive.godrive.btppublic.model.serviceModel.getFreightUnit;

import com.google.gson.annotations.SerializedName;

public class FreightUnitResponse {
    @SerializedName("TransportationOrderItemUUID")
    private String TransportationOrderItemUUID;

    @SerializedName("TransportationOrderUUID")
    private String TransportationOrderUUID;

    @SerializedName("TranspOrdItem")
    private String TranspOrdItem;

    @SerializedName("TranspOrdItemType")
    private String TranspOrdItemType;

    @SerializedName("TranspOrdItemCategory")
    private String TranspOrdItemCategory;

    @SerializedName("TranspOrdItemParentItemUUID")
    private String TranspOrdItemParentItemUUID;

    @SerializedName("TranspOrdItemDesc")
    private String TranspOrdItemDesc;

    @SerializedName("IsMainCargoItem")
    private String IsMainCargoItem;

    @SerializedName("TranspOrdItemSorting")
    private String TranspOrdItemSorting;

    @SerializedName("SourceStopUUID")
    private String SourceStopUUID;

    @SerializedName("DestinationStopUUID")
    private String DestinationStopUUID;

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

    @SerializedName("FreightUnitUUID")
    private String FreightUnitUUID;

    @SerializedName("TranspBaseDocument")
    private String TranspBaseDocument;

    @SerializedName("TranspBaseDocumentType")
    private String TranspBaseDocumentType;

    @SerializedName("TranspBaseDocumentItem")
    private String TranspBaseDocumentItem;

    @SerializedName("TranspBaseDocumentItemType")
    private String TranspBaseDocumentItemType;

    @SerializedName("TranspOrdItemPackageID")
    private String TranspOrdItemPackageID;

    @SerializedName("ProductUUID")
    private String ProductUUID;

    @SerializedName("ProductID")
    private String ProductID;

    @SerializedName("MaterialFreightGroup")
    private String MaterialFreightGroup;

    @SerializedName("TransportationGroup")
    private String TransportationGroup;

    @SerializedName("TranspOrdItmMinTemp")
    private String TranspOrdItmMinTemp;

    @SerializedName("TranspOrdItmMaxTemp")
    private String TranspOrdItmMaxTemp;

    @SerializedName("TranspOrdItemTemperatureUnit")
    private String TranspOrdItemTemperatureUnit;

    @SerializedName("TranspOrdItemQuantity")
    private String TranspOrdItemQuantity;

    @SerializedName("TranspOrdItemQuantityUnit")
    private String TranspOrdItemQuantityUnit;

    @SerializedName("TranspOrdItemGrossWeight")
    private String TranspOrdItemGrossWeight;

    @SerializedName("TranspOrdItemGrossWeightUnit")
    private String TranspOrdItemGrossWeightUnit;

    @SerializedName("TranspOrdItemGrossVolume")
    private String TranspOrdItemGrossVolume;

    @SerializedName("TranspOrdItemGrossVolumeUnit")
    private String TranspOrdItemGrossVolumeUnit;

    @SerializedName("TranspOrdItemNetWeight")
    private String TranspOrdItemNetWeight;

    @SerializedName("TranspOrdItemNetWeightUnit")
    private String TranspOrdItemNetWeightUnit;

    @SerializedName("TranspOrdItemDngrsGdsSts")
    private String TranspOrdItemDngrsGdsSts;

    @SerializedName("@odata.etag")
    private String odataEtag;

    public String getOdataEtag() {
        return odataEtag;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    public String getTransportationOrderItemUUID() {
        return TransportationOrderItemUUID;
    }

    public void setTransportationOrderItemUUID(String transportationOrderItemUUID) {
        TransportationOrderItemUUID = transportationOrderItemUUID;
    }

    public String getTransportationOrderUUID() {
        return TransportationOrderUUID;
    }

    public void setTransportationOrderUUID(String transportationOrderUUID) {
        TransportationOrderUUID = transportationOrderUUID;
    }

    public String getTranspOrdItem() {
        return TranspOrdItem;
    }

    public void setTranspOrdItem(String transpOrdItem) {
        TranspOrdItem = transpOrdItem;
    }

    public String getTranspOrdItemType() {
        return TranspOrdItemType;
    }

    public void setTranspOrdItemType(String transpOrdItemType) {
        TranspOrdItemType = transpOrdItemType;
    }

    public String getTranspOrdItemCategory() {
        return TranspOrdItemCategory;
    }

    public void setTranspOrdItemCategory(String transpOrdItemCategory) {
        TranspOrdItemCategory = transpOrdItemCategory;
    }

    public String getTranspOrdItemParentItemUUID() {
        return TranspOrdItemParentItemUUID;
    }

    public void setTranspOrdItemParentItemUUID(String transpOrdItemParentItemUUID) {
        TranspOrdItemParentItemUUID = transpOrdItemParentItemUUID;
    }

    public String getTranspOrdItemDesc() {
        return TranspOrdItemDesc;
    }

    public void setTranspOrdItemDesc(String transpOrdItemDesc) {
        TranspOrdItemDesc = transpOrdItemDesc;
    }

    public String getIsMainCargoItem() {
        return IsMainCargoItem;
    }

    public void setIsMainCargoItem(String isMainCargoItem) {
        IsMainCargoItem = isMainCargoItem;
    }

    public String getTranspOrdItemSorting() {
        return TranspOrdItemSorting;
    }

    public void setTranspOrdItemSorting(String transpOrdItemSorting) {
        TranspOrdItemSorting = transpOrdItemSorting;
    }

    public String getSourceStopUUID() {
        return SourceStopUUID;
    }

    public void setSourceStopUUID(String sourceStopUUID) {
        SourceStopUUID = sourceStopUUID;
    }

    public String getDestinationStopUUID() {
        return DestinationStopUUID;
    }

    public void setDestinationStopUUID(String destinationStopUUID) {
        DestinationStopUUID = destinationStopUUID;
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

    public String getFreightUnitUUID() {
        return FreightUnitUUID;
    }

    public void setFreightUnitUUID(String freightUnitUUID) {
        FreightUnitUUID = freightUnitUUID;
    }

    public String getTranspBaseDocument() {
        return TranspBaseDocument;
    }

    public void setTranspBaseDocument(String transpBaseDocument) {
        TranspBaseDocument = transpBaseDocument;
    }

    public String getTranspBaseDocumentType() {
        return TranspBaseDocumentType;
    }

    public void setTranspBaseDocumentType(String transpBaseDocumentType) {
        TranspBaseDocumentType = transpBaseDocumentType;
    }

    public String getTranspBaseDocumentItem() {
        return TranspBaseDocumentItem;
    }

    public void setTranspBaseDocumentItem(String transpBaseDocumentItem) {
        TranspBaseDocumentItem = transpBaseDocumentItem;
    }

    public String getTranspBaseDocumentItemType() {
        return TranspBaseDocumentItemType;
    }

    public void setTranspBaseDocumentItemType(String transpBaseDocumentItemType) {
        TranspBaseDocumentItemType = transpBaseDocumentItemType;
    }

    public String getTranspOrdItemPackageID() {
        return TranspOrdItemPackageID;
    }

    public void setTranspOrdItemPackageID(String transpOrdItemPackageID) {
        TranspOrdItemPackageID = transpOrdItemPackageID;
    }

    public String getProductUUID() {
        return ProductUUID;
    }

    public void setProductUUID(String productUUID) {
        ProductUUID = productUUID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getMaterialFreightGroup() {
        return MaterialFreightGroup;
    }

    public void setMaterialFreightGroup(String materialFreightGroup) {
        MaterialFreightGroup = materialFreightGroup;
    }

    public String getTransportationGroup() {
        return TransportationGroup;
    }

    public void setTransportationGroup(String transportationGroup) {
        TransportationGroup = transportationGroup;
    }

    public String getTranspOrdItmMinTemp() {
        return TranspOrdItmMinTemp;
    }

    public void setTranspOrdItmMinTemp(String transpOrdItmMinTemp) {
        TranspOrdItmMinTemp = transpOrdItmMinTemp;
    }

    public String getTranspOrdItmMaxTemp() {
        return TranspOrdItmMaxTemp;
    }

    public void setTranspOrdItmMaxTemp(String transpOrdItmMaxTemp) {
        TranspOrdItmMaxTemp = transpOrdItmMaxTemp;
    }

    public String getTranspOrdItemTemperatureUnit() {
        return TranspOrdItemTemperatureUnit;
    }

    public void setTranspOrdItemTemperatureUnit(String transpOrdItemTemperatureUnit) {
        TranspOrdItemTemperatureUnit = transpOrdItemTemperatureUnit;
    }

    public String getTranspOrdItemQuantity() {
        return TranspOrdItemQuantity;
    }

    public void setTranspOrdItemQuantity(String transpOrdItemQuantity) {
        TranspOrdItemQuantity = transpOrdItemQuantity;
    }

    public String getTranspOrdItemQuantityUnit() {
        return TranspOrdItemQuantityUnit;
    }

    public void setTranspOrdItemQuantityUnit(String transpOrdItemQuantityUnit) {
        TranspOrdItemQuantityUnit = transpOrdItemQuantityUnit;
    }

    public String getTranspOrdItemGrossWeight() {
        return TranspOrdItemGrossWeight;
    }

    public void setTranspOrdItemGrossWeight(String transpOrdItemGrossWeight) {
        TranspOrdItemGrossWeight = transpOrdItemGrossWeight;
    }

    public String getTranspOrdItemGrossWeightUnit() {
        return TranspOrdItemGrossWeightUnit;
    }

    public void setTranspOrdItemGrossWeightUnit(String transpOrdItemGrossWeightUnit) {
        TranspOrdItemGrossWeightUnit = transpOrdItemGrossWeightUnit;
    }

    public String getTranspOrdItemGrossVolume() {
        return TranspOrdItemGrossVolume;
    }

    public void setTranspOrdItemGrossVolume(String transpOrdItemGrossVolume) {
        TranspOrdItemGrossVolume = transpOrdItemGrossVolume;
    }

    public String getTranspOrdItemGrossVolumeUnit() {
        return TranspOrdItemGrossVolumeUnit;
    }

    public void setTranspOrdItemGrossVolumeUnit(String transpOrdItemGrossVolumeUnit) {
        TranspOrdItemGrossVolumeUnit = transpOrdItemGrossVolumeUnit;
    }

    public String getTranspOrdItemNetWeight() {
        return TranspOrdItemNetWeight;
    }

    public void setTranspOrdItemNetWeight(String transpOrdItemNetWeight) {
        TranspOrdItemNetWeight = transpOrdItemNetWeight;
    }

    public String getTranspOrdItemNetWeightUnit() {
        return TranspOrdItemNetWeightUnit;
    }

    public void setTranspOrdItemNetWeightUnit(String transpOrdItemNetWeightUnit) {
        TranspOrdItemNetWeightUnit = transpOrdItemNetWeightUnit;
    }

    public String getTranspOrdItemDngrsGdsSts() {
        return TranspOrdItemDngrsGdsSts;
    }

    public void setTranspOrdItemDngrsGdsSts(String transpOrdItemDngrsGdsSts) {
        TranspOrdItemDngrsGdsSts = transpOrdItemDngrsGdsSts;
    }
}
