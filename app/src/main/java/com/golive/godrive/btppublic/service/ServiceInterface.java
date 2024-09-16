package com.golive.godrive.btppublic.service;

import com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerAddress.AddressResultsModel;
import com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone.PhoneDModel;
import com.golive.godrive.btppublic.model.serviceModel.getDocumentOrder.DocumentOrderValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getFreightOrder.FreightOrderValueModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ServiceInterface {
    @GET
    Call<FreightOrderValueModel> getFreightOrder(@Url String url);
    @GET
    Call<DocumentOrderValueModel> getDocumentOrder(@Url String url);
    @GET
    Call<PhoneDModel> getPhone(@Url String url);

    @GET
    Call<AddressResultsModel> getAddress(@Url String url);
}
