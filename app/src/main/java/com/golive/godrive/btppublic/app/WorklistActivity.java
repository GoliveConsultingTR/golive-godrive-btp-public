package com.golive.godrive.btppublic.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.golive.godrive.btppublic.R;
import com.golive.godrive.btppublic.adapter.ListViewAdapter;
import com.golive.godrive.btppublic.adapter.WorklistAdapter;
import com.golive.godrive.btppublic.helper.Constant;
import com.golive.godrive.btppublic.model.dataModel.Work;
import com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone.PhoneDModel;
import com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone.PhoneResponse;
import com.golive.godrive.btppublic.model.serviceModel.getDocumentOrder.DocumentOrderResponse;
import com.golive.godrive.btppublic.model.serviceModel.getDocumentOrder.DocumentOrderValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getFreightOrder.FreightOrderResponse;
import com.golive.godrive.btppublic.model.serviceModel.getFreightOrder.FreightOrderValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getFreightUnit.FreightUnitResponse;
import com.golive.godrive.btppublic.model.serviceModel.getFreightUnit.FreightUnitValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getReference.ReferenceDModel;
import com.golive.godrive.btppublic.model.serviceModel.getReference.ReferenceResponse;
import com.golive.godrive.btppublic.model.serviceModel.getStatusCheck.StatusCheckDModel;
import com.golive.godrive.btppublic.model.serviceModel.getStatusCheck.StatusCheckResponse;
import com.golive.godrive.btppublic.service.SAPServiceManager;
import com.golive.godrive.btppublic.service.Service;
import com.golive.godrive.btppublic.service.ServiceInterface;
import com.golive.godrive.btppublic.util.Session;
import com.golive.godrive.btppublic.util.Util;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nordan.dialog.NordanLoadingDialog;
import com.sap.cloud.mobile.flowv2.core.Flow;
import com.sap.cloud.mobile.flowv2.core.FlowContextRegistry;
import com.sap.cloud.mobile.flowv2.model.FlowType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorklistActivity extends BaseActivity {

    private SAPServiceManager sapServiceManager;

    private ListView lvWorklist;
    private TextView tvDriverId, tvDriverPlate, tvFreightNumber, tvDriver, tvOrderbar, tvOrderbarTitle;
    private long mLastClickTimeBtn = 0;

    private Context context = this;
    private Activity activity = this;

    private String driverId = "",driver_isim;
    private String numberOfCustomer = "";
    private String driverNo = "";
    private String driver_Name = "";

    private RoundCornerProgressBar rcpOrder;

    private FrameLayout btnBasla, btnBitir;
    private String departureBegin = "";
    private String departureEnd = "";
    private String torID = "";
    private Logger logger = LoggerFactory.getLogger(WorklistActivity.class);

    private String plaka = "", phoneNumber = "";
    private String order = "", transportationOrderUuid = "", docType = "", docRefId = "", address = "", freightNo = "";
    public WorklistAdapter adapter;
    String sdSiparis = "",musteriKodu = "",musteriAdi = "",navlumBirimi = "";
    private String odata = "", consignee = "", freightNumber = "";
    private String closed = "", teslimatKontrol = "", delDoc = "";
    private List<String > docRefList = new ArrayList<>();
    private List<String > navlumList = new ArrayList<>();
    private List<String > docList = new ArrayList<>();
    private List<String > freightNoList = new ArrayList<>();
    private List<String > teslimatList = new ArrayList<>();
    private WelcomeActivity welcomeActivity = new WelcomeActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

        setContentView(R.layout.activity_worklist);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.app_color_green));
        logger.info("start_worklist_screen", WorklistActivity.class.getSimpleName());

        lvWorklist = (ListView)           findViewById(R.id.lv_worklist);
        tvDriverId = findViewById(R.id.tv_driver_id);
        tvDriverPlate = findViewById(R.id.tv_driver_plate);
        tvFreightNumber = findViewById(R.id.tv_freight_number);
        tvDriver = findViewById(R.id.tv_surname);
        tvOrderbar = findViewById(R.id.tv_orderbar);
        tvOrderbarTitle = findViewById(R.id.tv_orderbar_title);

        rcpOrder = findViewById(R.id.rcp_order);
        btnBasla = findViewById(R.id.fl_basla);
        btnBitir = findViewById(R.id.fl_bitir);
        btnBitir.setEnabled(false);
        btnBitir.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_300));
        btnBasla.setEnabled(false);
        btnBasla.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_300));


        setupView();
        getFreightOrder();

    }
    private void passToWorkDetailActivity() {
        Intent intent = new Intent(WorklistActivity.this, WorkDetailActivity.class);
        startActivity(intent);
        finish();
    }


    public void removeUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putString("userId", "");
                editor.apply();
            }
        }
    }

    private void setupView() {

        lvWorklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (SystemClock.elapsedRealtime() - mLastClickTimeBtn < Session.Instance().btnThresholdTime) {
                    return;
                }
                mLastClickTimeBtn = SystemClock.elapsedRealtime();

                if (closed.equals("True")) {
                    String msg = getString(R.string.delivery_ok);
                    ((WorklistActivity) activity).error(msg, null);
                    return;
                }
                Session.Instance().pozisyon = position;
                passToWorkDetailActivity();
            }
        });

    }
    private void check(){
        for (int i = 0; i < docRefList.size(); i++) {
            String freightNoUUid = docRefList.get(i);
            checkReference(freightNoUUid);
        }
        freightUUid();

    }
    private void freightUUid(){
        for (int i = 0; i < docList.size(); i++) {
            String freightNoUUid = docList.get(i);
            getFreightUnit(transportationOrderUuid, freightNoUUid);
        }
        deliveryDetail();
    }

    private void deliveryDetail(){
        for (int i = 0; i < freightNoList.size(); i++) {
            String freightNoUUid = freightNoList.get(i);
            getDeliveryDetail(freightNoUUid);
        }
        freight();
    }
    private void freight(){

        fillScreenData();
        fillRcBarData();

    }
    private void fillScreenData() {
        tvDriverPlate.setText(plaka);
        tvFreightNumber.setText(order);
        tvDriverId.setText(driverNo);
        tvDriver.setText(driver_isim);
        for (int i = 0; i < docRefList.size(); i++) {

            Work work = new Work();
            work.setConsigneeAddress(address);
            work.setConsigneeTelNumber(phoneNumber);
            work.setConsigneeID(driverId);
            work.setSalesOrder(docRefList.get(i));
            work.setFreightUnit(navlumList.get(i));
            work.setConsigneeName(driver_Name);
            work.setClosed(closed);
            work.setTeslimatKontrol(teslimatList.get(i));
            Session.Instance().worklistTm.add(work);
        }
        lvWorklist = (ListView) findViewById(R.id.lv_worklist);
        adapter = new WorklistAdapter(WorklistActivity.this, Session.Instance().worklistTm);
        lvWorklist.setAdapter(adapter);
        lvWorklist.setVisibility(View.VISIBLE);
        docRefList.clear();
        navlumList.clear();
        freightNoList.clear();
        teslimatList.clear();
    }

    private void showHideWorklistMessage(String message) {
        lvWorklist.setVisibility(message.isEmpty() ? View.VISIBLE : View.GONE);
    }


    private void saveUserId(String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public void fillRcBarData() {
        int workCount = Session.Instance().worklistTm.size();
        int closedWorkCount = 0;

        for (Work work : Session.Instance().worklistTm) {
            if (work.getClosed().equals("True")) {
                closedWorkCount++;
            }
        }

        rcpOrder.setMax(workCount);
        rcpOrder.setProgress((float) closedWorkCount);

        if (workCount - closedWorkCount > 0) {
            rcpOrder.setProgressColor(getColor(R.color.app_color_green));
        } else {
            rcpOrder.setProgressColor(getColor(R.color.app_color_green));
        }

        String orderBar = closedWorkCount + "/" + workCount;
        String orderBarTitle = getString(R.string.amount_distributed);

        tvOrderbarTitle.setText(orderBarTitle);
        tvOrderbar.setText(orderBar);
    }





    private void getFreightOrder(){

/*
        KProgressHUD hud = KProgressHUD.create(WorklistActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.please_wait))
                .setDetailsLabel(getString(R.string.data_get))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


 */

        String freightUrlNew = Constant.baseUrl+"TM/FreightOrder/FreightOrder?$filter=TranspOrdLifeCycleStatus ne '05' and TranspOrdLifeCycleStatus ne '10' and TransportationOrderExecSts ne '04' and TransportationOrderExecSts ne '06'";

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);

        Call<FreightOrderValueModel> call = service.getFreightOrder(freightUrlNew);
        call.enqueue(new Callback<FreightOrderValueModel>() {
            @Override
            public void onResponse(Call<FreightOrderValueModel> call, Response<FreightOrderValueModel> response) {

                //hud.dismiss();

                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    FreightOrderValueModel freightOrderValueModel = response.body();
                    if (freightOrderValueModel != null) {
                        List<FreightOrderResponse> freightlist = response.body().getValue();
                        boolean found = false;
                        if (freightlist.size() > 0) {
                            for (int i = 0; i < freightlist.size(); i++) {
                                String driver_y =freightlist.get(i).getYY1_YY_Driver_TOR();
                                if(driver_y.equals("D_1012")) {
                                    found = true;
                                    order = freightlist.get(i).getTransportationOrder();
                                    plaka = freightlist.get(i).getYY1_Plaka_TOR();
                                    transportationOrderUuid = freightlist.get(i).getTransportationOrderUUID();
                                    odata = freightlist.get(i).getOdataEtag();
                                    consignee = freightlist.get(i).getConsignee();
                                    driver_isim = freightlist.get(i).getYY1_DriverDescription_TOR();
                                    driverNo =freightlist.get(i).getYY1_YY_Driver_TOR();

                                    //getFreightUnit(transportationOrderUuid);
                                    //getDocumentOrder(transportationOrderUuid);
                                    getDocumentOrder(transportationOrderUuid);
                                    //getFreightUnit("95bad49a-e63f-1edf-83a6-0d9fce30c1aa");
                                }
                            }
                            if (!found){
                                String msg = getString(R.string.delivery_error);
                                ((WorklistActivity) activity).warning(getString(R.string.warning),msg, null);
                                //hud.dismiss();
                            }

                        }else{
                            String msg = getString(R.string.service_error);
                            ((WorklistActivity) activity).error(msg, null);
                            // hud.dismiss();
                        }
                    }else{
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                        //hud.dismiss();
                    }

                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                        //hud.dismiss();

                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                    //  hud.dismiss();

                }
            }

            @Override
            public void onFailure(Call<FreightOrderValueModel> call, Throwable t) {
                //  hud.dismiss();
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);

            }
        });
    }

    /*
    private void getFreightUnit(String OrderUuid, String docNum){



        String freightUrl = Constant.baseUrl+"TM/FreightOrder/FreightOrder({UUID})/_FreightOrderItem";
        freightUrl = freightUrl.replace("{UUID}", OrderUuid);

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<FreightUnitValueModel> call = service.getDelivety(freightUrl);
        call.enqueue(new Callback<FreightUnitValueModel>() {
            @Override
            public void onResponse(Call<FreightUnitValueModel> call, Response<FreightUnitValueModel> response) {


                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    FreightUnitValueModel freightOrderValueModel = response.body();
                    if (freightOrderValueModel != null) {
                        List<FreightUnitResponse> freightlist = response.body().getValue();
                        if (freightlist.size() > 0) {

                            for (int i = 0; i < freightlist.size(); i++) {
                                String cosigne = freightlist.get(i).getConsignee();
                                String siparis = freightlist.get(i).getTranspBaseDocument();
                                String type = freightlist.get(i).getTranspBaseDocumentType();
                                if (consignee.equals(cosigne)) {

                                    if (delDoc.equals(siparis)) {
                                        freightNo = freightlist.get(i).getFreightUnitUUID();
                                        freightNoList.add(freightNo);
                                        break;
                                    }
                                }
                            }
                        }else{
                            String msg = getString(R.string.service_error);
                            ((WorklistActivity) activity).error(msg, null);
                        }
                    }else{
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }

                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }
                freight();

            }

            @Override
            public void onFailure(Call<FreightUnitValueModel> call, Throwable t) {
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);
            }
        });
    }

     */
    private void getFreightUnit(String OrderUuid, String docNum) {
        String freightUrl = Constant.baseUrl + "TM/FreightOrder/FreightOrder({UUID})/_FreightOrderItem";
        freightUrl = freightUrl.replace("{UUID}", OrderUuid);

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<FreightUnitValueModel> call = service.getDelivety(freightUrl);

        try {
            Response<FreightUnitValueModel> response = call.execute();
            int responseCode = response.code();

            if (200 <= responseCode && responseCode < 300) {
                FreightUnitValueModel freightOrderValueModel = response.body();
                if (freightOrderValueModel != null) {
                    List<FreightUnitResponse> freightlist = freightOrderValueModel.getValue();
                    if (freightlist != null && freightlist.size() > 0) {
                        for (FreightUnitResponse freightUnit : freightlist) {
                            String cosigne = freightUnit.getConsignee();
                            String transpBaseDocument = freightUnit.getTranspBaseDocument();
                            String type = freightUnit.getTranspBaseDocumentType();

                            if (cosigne != null && consignee.equals(cosigne) && docNum != null && docNum.equals(transpBaseDocument)) {
                                if(type.equals("73")){
                                    teslimatKontrol = "True";
                                    teslimatList.add(teslimatKontrol);
                                }else{
                                    teslimatKontrol = "False";
                                    teslimatList.add(teslimatKontrol);
                                }
                                freightNo = freightUnit.getFreightUnitUUID();
                                freightNoList.add(freightNo);
                                break;
                            }
                        }
                    } else {
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }
                } else {
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }
            } else {
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);
            }


        } catch (IOException e) {
            String msg = getString(R.string.service_error);
            ((WorklistActivity) activity).error(msg, null);
        }
    }


    private void getDocumentOrder(String OrderUuid){

        String documentUrl = Constant.baseUrl+"TM/FreightOrder/FreightOrder({UUID})/_FreightOrderDocumentReference";
        documentUrl = documentUrl.replace("{UUID}", OrderUuid);
        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<DocumentOrderValueModel> call = service.getDocumentOrder(documentUrl);
        call.enqueue(new Callback<DocumentOrderValueModel>() {
            @Override
            public void onResponse(Call<DocumentOrderValueModel> call, Response<DocumentOrderValueModel> response) {


                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    DocumentOrderValueModel documentOrderValueModel = response.body();
                    if (documentOrderValueModel != null) {
                        List<DocumentOrderResponse> documentlist = response.body().getValue();
                        if (documentlist.size() > 0) {
                            for(int i=0; i < documentlist.size(); i++) {
                                docType = documentlist.get(i).getTranspOrdDocReferenceType();
                                if(docType.equals("114")){
                                    docRefId = documentlist.get(i).getTranspOrdDocReferenceID();
                                    docRefList.add(docRefId);
                                    //checkReference(docRefId);
                                }
                                else if(docType.equals("73")){
                                    //teslimatKontrol = "True";
                                    String docRefIdCL = documentlist.get(i).getTranspOrdDocReferenceID();
                                    getStatus(docRefIdCL);
                                }
                            }
                        }else{
                            String msg = getString(R.string.service_error);
                            ((WorklistActivity) activity).error(msg, null);
                        }
                    }else{
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }
                    getPhoneNumber();
                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }
            }

            @Override
            public void onFailure(Call<DocumentOrderValueModel> call, Throwable t) {
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);
            }
        });
    }
    private void getPhoneNumber(){

        String phoneUrl = "https://my407038-api.s4hana.cloud.sap/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_BusinessPartnerAddress(BusinessPartner='36100001',AddressID='46')/to_PhoneNumber";

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<PhoneDModel> call = service.getPhone(phoneUrl);
        call.enqueue(new Callback<PhoneDModel>() {
            @Override
            public void onResponse(Call<PhoneDModel> call, Response<PhoneDModel> response) {


                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    PhoneDModel phoneDModel = response.body();
                    if (phoneDModel != null) {
                        List<PhoneResponse> documentlist = response.body().getD().getResults();
                        if (documentlist.size() > 0) {
                            phoneNumber = documentlist.get(0).getPhoneNumber();
                            getAddress();
                        }
                        else{
                            Util.displayErrorDialog(WorklistActivity.this,getString(R.string.error),getString(R.string.delivery_error));
                        }
                    }else{
                        Util.displayErrorDialog(WorklistActivity.this,getString(R.string.error),getString(R.string.delivery_error));
                    }
                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        Util.displayErrorDialog(WorklistActivity.this,getString(R.string.error),getString(R.string.found_error));

                    }
                }
                else{
                    Util.displayErrorDialog(WorklistActivity.this,getString(R.string.error),getString(R.string.found_error));

                }
            }

            @Override
            public void onFailure(Call<PhoneDModel> call, Throwable t) {
                Util.displayErrorDialog(WorklistActivity.this, getString(R.string.error), getString(R.string.found_error));

            }
        });
    }
    private void getAddress(){

        String addressUrl = "https://my407038-api.s4hana.cloud.sap/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_BusinessPartner('36100001')/to_BusinessPartnerAddress";
        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<PhoneDModel> call = service.getPhone(addressUrl);
        call.enqueue(new Callback<PhoneDModel>() {
            @Override
            public void onResponse(Call<PhoneDModel> call, Response<PhoneDModel> response) {


                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    PhoneDModel phoneDModel = response.body();
                    if (phoneDModel != null) {
                        List<PhoneResponse> documentlist = response.body().getD().getResults();
                        if (documentlist.size() > 0) {
                            driverId = documentlist.get(0).getBusinessPartner() != null ? documentlist.get(0).getBusinessPartner() : "";
                            //driver_Name = documentlist.get(0).getBusinessPartnerFullName() != null ? documentlist.get(0).getBusinessPartnerFullName() : "";
                            driver_Name = documentlist.get(0).getFullName() != null ? documentlist.get(0).getFullName() : "";
                            String streetName = documentlist.get(0).getStreetName() != null ? documentlist.get(0).getStreetName() : "";
                            String lan = documentlist.get(0).getLanguage() != null ? documentlist.get(0).getLanguage() : "";
                            String postalCode = documentlist.get(0).getPostalCode() != null ? documentlist.get(0).getPostalCode() : "";
                            String cityName = documentlist.get(0).getCityName() != null ? documentlist.get(0).getCityName() : "";

                            address = driver_Name + " / " + streetName + " /" + lan + "-" + postalCode + " " + cityName;

                        }
                        else{
                            String msg = getString(R.string.service_error);
                            ((WorklistActivity) activity).error(msg, null);                        }
                    }else{
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }
                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }
                check();
            }

            @Override
            public void onFailure(Call<PhoneDModel> call, Throwable t) {
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);
            }
        });
    }

    private void getStatus(String OrderUuid){


        String statusUrl = "https://goliveintegration.apimanagement.ap10.hana.ondemand.com:443/TM/Delivery/A_OutbDeliveryItem?$filter=DeliveryDocument eq '{Document}'";
        statusUrl = statusUrl.replace("{Document}", OrderUuid);

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<StatusCheckDModel> call = service.getStatus(statusUrl);
        call.enqueue(new Callback<StatusCheckDModel>() {
            @Override
            public void onResponse(Call<StatusCheckDModel> call, Response<StatusCheckDModel> response) {

                int responseCode = response.code();
                if(200 <= responseCode && responseCode < 300) {
                    StatusCheckDModel status = response.body();
                    if (status != null) {
                        List<StatusCheckResponse> statuslist = response.body().getD().getResults();
                        if (statuslist.size() > 0) {
                            for(int i=0; i < statuslist.size(); i++) {
                                String deliveryDocumet = statuslist.get(i).getDeliveryDocument();
                                String deliveryBillingStatus = statuslist.get(i).getDeliveryRelatedBillingStatus();
                                if(deliveryBillingStatus.equals("C")){
                                    closed = "False";
                                    break;
                                }
                                else if(deliveryBillingStatus.equals("A")){
                                    closed = "False";
                                    break;
                                }
                            }
                        }
                        else{
                            String msg = getString(R.string.service_error);
                            ((WorklistActivity) activity).error(msg, null);
                        }
                    }else{
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }

                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }

            }

            @Override
            public void onFailure(Call<StatusCheckDModel> call, Throwable t) {
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);
            }
        });
    }

    /*
        private void getReference(String OrderUuid){



            String freightUrl = Constant.baseUrl+"TM/FreightOrder/FreightOrder({UUID})/_FreightOrderItem";
            freightUrl = freightUrl.replace("{UUID}", OrderUuid);

            ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
            Call<FreightUnitValueModel> call = service.getDelivety(freightUrl);
            call.enqueue(new Callback<FreightUnitValueModel>() {
                @Override
                public void onResponse(Call<FreightUnitValueModel> call, Response<FreightUnitValueModel> response) {


                    int responseCode = response.code();

                    if(200 <= responseCode && responseCode < 300) {
                        FreightUnitValueModel freightOrderValueModel = response.body();
                        if (freightOrderValueModel != null) {
                            List<FreightUnitResponse> freightlist = response.body().getValue();
                            if (freightlist.size() > 0) {
                                for(int i=0; i < freightlist.size(); i++) {
                                    String cosigne = freightlist.get(i).getConsignee();
                                    if(consignee.equals(cosigne)){
                                        freightNo = freightlist.get(i).getFreightUnitUUID();
                                        getDeliveryDetail(freightNo);
                                        break;
                                    }

                                }
                            }else{
                                String msg = getString(R.string.service_error);
                                ((WorklistActivity) activity).error(msg, null);
                            }
                        }else{
                            String msg = getString(R.string.service_error);
                            ((WorklistActivity) activity).error(msg, null);
                        }

                    }
                    else if(responseCode == 400){
                        if(response.errorBody() != null) {
                            String msg = getString(R.string.service_error);
                            ((WorklistActivity) activity).error(msg, null);
                        }
                    }
                    else{
                        String msg = getString(R.string.service_error);
                        ((WorklistActivity) activity).error(msg, null);
                    }
                }

                @Override
                public void onFailure(Call<FreightUnitValueModel> call, Throwable t) {
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }
            });
        }

     */
    /*
private void getDeliveryDetail(String OrderUuid){


    String deliveryUrl = "https://my407038-api.s4hana.cloud.sap/sap/opu/odata4/sap/api_freightunit/srvd_a2x/sap/freightunit/0001/FreightUnit({UUID})";
    deliveryUrl = deliveryUrl.replace("{UUID}", OrderUuid);
    ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
    Call <FreightOrderResponse> call = service.getDelivery(deliveryUrl);
    call.enqueue(new Callback<FreightOrderResponse>() {
        @Override
        public void onResponse(Call<FreightOrderResponse> call, Response<FreightOrderResponse> response) {

            int responseCode = response.code();
            if(200 <= responseCode && responseCode < 300) {
                FreightOrderResponse deliveryValueModel = response.body();
                if (deliveryValueModel != null) {
                    freightNumber = deliveryValueModel.getTransportationOrder();
                    navlumList.add(freightNumber);
                }else{
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }

            }
            else if(responseCode == 400){
                if(response.errorBody() != null) {
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }
            }
            else{
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);
            }
        }

        @Override
        public void onFailure(Call<FreightOrderResponse> call, Throwable t) {
            String msg = getString(R.string.service_error);
            ((WorklistActivity) activity).error(msg, null);
        }
    });
}

*/
    private void getDeliveryDetail(String OrderUuid) {
        String deliveryUrl = "https://my407038-api.s4hana.cloud.sap/sap/opu/odata4/sap/api_freightunit/srvd_a2x/sap/freightunit/0001/FreightUnit({UUID})";
        deliveryUrl = deliveryUrl.replace("{UUID}", OrderUuid);

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<FreightOrderResponse> call = service.getDelivery(deliveryUrl);

        try {
            Response<FreightOrderResponse> response = call.execute();
            int responseCode = response.code();

            if (200 <= responseCode && responseCode < 300) {
                FreightOrderResponse deliveryValueModel = response.body();
                if (deliveryValueModel != null) {
                    freightNumber = deliveryValueModel.getTransportationOrder();
                    navlumList.add(freightNumber);
                } else {
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }
            } else if (responseCode == 400) {
                if (response.errorBody() != null) {
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                } else {
                    String msg = getString(R.string.service_error);
                    ((WorklistActivity) activity).error(msg, null);
                }
            } else {
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);
            }

        } catch (IOException e) {
            String msg = getString(R.string.service_error);
            ((WorklistActivity) activity).error(msg, null);
        }
    }


    private void checkReference(String OrderUuid) {
        String refUrl = "https://my407038-api.s4hana.cloud.sap/sap/opu/odata/sap/API_OUTBOUND_DELIVERY_SRV/A_OutbDeliveryItem?$filter=ReferenceSDDocument eq '{Reference}'";
        refUrl = refUrl.replace("{Reference}", OrderUuid);

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<ReferenceDModel> call = service.getReference(refUrl);

        try {
            Response<ReferenceDModel> response = call.execute();
            int responseCode = response.code();

            if (200 <= responseCode && responseCode < 300) {
                ReferenceDModel refModel = response.body();
                if (refModel != null) {
                    List<ReferenceResponse> freightlist = response.body().getD().getResults();
                    if (freightlist.size() > 0) {
                        delDoc = freightlist.get(0).getDeliveryDocument();
                        docList.add(delDoc);
                    } if (freightlist.size() == 0) {
                        docList.add(OrderUuid);
                    }
                }
            } else {
                String msg = getString(R.string.service_error);
                ((WorklistActivity) activity).error(msg, null);
            }


        } catch (IOException e) {
            String msg = getString(R.string.service_error);
            ((WorklistActivity) activity).error(msg, null);
        }
    }

}
