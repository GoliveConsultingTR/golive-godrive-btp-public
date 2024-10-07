package com.golive.godrive.btppublic.service;

import com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerAddress.AddressResultsModel;
import com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone.PhoneDModel;
import com.golive.godrive.btppublic.model.serviceModel.getBilling.GetBillingDModel;
import com.golive.godrive.btppublic.model.serviceModel.getDocumentOrder.DocumentOrderValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getFreightOrder.FreightOrderResponse;
import com.golive.godrive.btppublic.model.serviceModel.getFreightOrder.FreightOrderValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getFreightUnit.FreightUnitValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getReference.ReferenceDModel;
import com.golive.godrive.btppublic.model.serviceModel.getSalesOrder.SalesOrderDModel;
import com.golive.godrive.btppublic.model.serviceModel.getStatusCheck.StatusCheckDModel;
import com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery.CreateDeliveryDModel;
import com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery.CreateDeliveryRequestItem;
import com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery.CreateDeliveryRequestResults;
import com.golive.godrive.btppublic.model.serviceModel.postGoodIssues.GoodsIssuesDModel;
import com.golive.godrive.btppublic.model.serviceModel.postGoodIssues.GoodsIssuesRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
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

    @GET
    Call<FreightUnitValueModel> getDelivety(@Url String url);

    @GET
    Call <FreightOrderResponse> getDelivery(@Url String url);

    @GET
    Call <SalesOrderDModel> getSalesOrder(@Url String url);

    @POST
    Call<CreateDeliveryDModel> postCreateDelivery(@Url String url, @HeaderMap Map<String, String> headers, @Body CreateDeliveryRequestItem createDeliveryRequestItem);

    @POST
    Call <GoodsIssuesDModel> postGoodIssues(@Url String url, @HeaderMap Map<String, String> headers, @Body GoodsIssuesRequest goodsIssuesRequest);

    @GET
    Call<GetBillingDModel> getBill(@Url String url);

    @GET
    Call<StatusCheckDModel> getStatus(@Url String url);

    @GET
    Call<ReferenceDModel> getReference(@Url String url);
}
