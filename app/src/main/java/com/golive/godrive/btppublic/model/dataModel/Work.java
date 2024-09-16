package com.golive.godrive.btppublic.model.dataModel;

import java.io.Serializable;

/**
 * Created by okanozturk on 25.12.2017.
 */

public class Work implements Serializable {

    private String ivTorid;
    private String departureBegin;
    private String torID;
    private String salesOrder;
    private String consigneeID;
    private String consigneeTelNumber;
    private String consigneeAddress;
    private String freightUnit;
    private String consigneeName;

    public String getIvTorid() {
        return ivTorid;
    }

    public void setIvTorid(String ivTorid) {
        this.ivTorid = ivTorid;
    }

    public String getDepartureBegin() {
        return departureBegin;
    }

    public void setDepartureBegin(String departureBegin) {
        this.departureBegin = departureBegin;
    }

    public String getTorID() {
        return torID;
    }

    public void setTorID(String torID) {
        this.torID = torID;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getConsigneeID() {
        return consigneeID;
    }

    public void setConsigneeID(String consigneeID) {
        this.consigneeID = consigneeID;
    }

    public String getConsigneeTelNumber() {
        return consigneeTelNumber;
    }

    public void setConsigneeTelNumber(String consigneeTelNumber) {
        this.consigneeTelNumber = consigneeTelNumber;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getFreightUnit() {
        return freightUnit;
    }

    public void setFreightUnit(String freightUnit) {
        this.freightUnit = freightUnit;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }
}

