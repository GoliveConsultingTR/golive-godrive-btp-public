package com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone;

import com.google.gson.annotations.SerializedName;

public class PhoneResponse {
    @SerializedName("PhoneNumber")
    private String PhoneNumber;

    @SerializedName("BusinessPartner")
    private String BusinessPartner;

    @SerializedName("BusinessPartnerFullName")
    private String BusinessPartnerFullName;

    @SerializedName("FullName")
    private String FullName;

    @SerializedName("StreetName")
    private String StreetName;

    @SerializedName("Language")
    private String Language;

    @SerializedName("PostalCode")
    private String PostalCode;

    @SerializedName("CityName")
    private String CityName;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getBusinessPartner() {
        return BusinessPartner;
    }

    public void setBusinessPartner(String businessPartner) {
        BusinessPartner = businessPartner;
    }

    public String getBusinessPartnerFullName() {
        return BusinessPartnerFullName;
    }

    public void setBusinessPartnerFullName(String businessPartnerFullName) {
        BusinessPartnerFullName = businessPartnerFullName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }
}
