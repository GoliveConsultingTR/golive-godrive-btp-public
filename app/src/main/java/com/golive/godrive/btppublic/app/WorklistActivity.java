package com.golive.godrive.btppublic.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import com.golive.godrive.btppublic.R;
import com.golive.godrive.btppublic.adapter.WorklistAdapter;
import com.golive.godrive.btppublic.helper.BasicAuthInterceptor;
import com.golive.godrive.btppublic.helper.Constant;
import com.golive.godrive.btppublic.model.dataModel.Work;
import com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone.PhoneDModel;
import com.golive.godrive.btppublic.model.serviceModel.BusinessPartnerPhone.PhoneResponse;
import com.golive.godrive.btppublic.model.serviceModel.getDocumentOrder.DocumentOrderResponse;
import com.golive.godrive.btppublic.model.serviceModel.getDocumentOrder.DocumentOrderValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getFreightOrder.FreightOrderResponse;
import com.golive.godrive.btppublic.model.serviceModel.getFreightOrder.FreightOrderValueModel;
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
import com.sap.cloud.mobile.foundation.mobileservices.ServiceListener;
import com.sap.cloud.mobile.foundation.mobileservices.ServiceResult;
import com.sap.cloud.mobile.foundation.user.User;
import com.sap.cloud.mobile.foundation.user.UserService;
import com.sap.cloud.mobile.odata.DataQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorklistActivity extends BaseActivity {

    private SAPServiceManager sapServiceManager;

    private ListView lvWorklist;
    private TextView tvDriverId, tvDriverPlate, tvFreightNumber, tvDriver, tvOrderbar, tvOrderbarTitle;
    private long mLastClickTimeBtn = 0;

    private Context context = this;
    private Activity activity = this;

    private String driverId = "";
    private String numberOfCustomer = "";
    private String driver = "";
    private String driver_Name = "";
    private String driver_Surname = "";
    private RoundCornerProgressBar rcpOrder;

    private FrameLayout btnBasla, btnBitir;
    private String departureBegin = "";
    private String departureEnd = "";
    private String torID = "";
    private Logger logger = LoggerFactory.getLogger(WorklistActivity.class);

    private String plaka = "", phoneNumber = "";
    private String order = "", transportationOrderUuid = "", docType = "", docRefId = "", address = "";
    public WorklistAdapter adapter;
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
        getFreightOrder();

    }

    private void fillScreenData() {
        tvDriverPlate.setText(plaka);
        tvFreightNumber.setText(docRefId);

        Work work = new Work();
        work.setConsigneeAddress(address);
        work.setConsigneeTelNumber(phoneNumber);
        work.setConsigneeID(driverId);
        work.setSalesOrder(docRefId);
        work.setFreightUnit("");
        work.setConsigneeName(driver_Name);


        Session.Instance().worklistTm.add(work);

        lvWorklist = (ListView) findViewById(R.id.lv_worklist);
        adapter = new WorklistAdapter(WorklistActivity.this, Session.Instance().worklistTm);
        lvWorklist.setAdapter(adapter);
        lvWorklist.setVisibility(View.VISIBLE);

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
        if (Session.Instance().worklistTm != null) {

            int workCount = Session.Instance().worklistTm.size();
            int closedWorkCount = 0;

            rcpOrder.setMax(workCount);
            rcpOrder.setProgress(closedWorkCount);

            if (workCount - closedWorkCount > 0) {
                rcpOrder.setProgressColor(getColor(R.color.app_color_green));
            } else {
                rcpOrder.setProgressColor(getColor(R.color.app_color_green));
            }

            String orderBar = closedWorkCount + "/" + numberOfCustomer;
            String orderBarTitle = getString(R.string.amount_distributed);

            tvOrderbarTitle.setText(orderBarTitle);
            tvOrderbar.setText(orderBar);
        }
    }




    private void getFreightOrder(){


        KProgressHUD hud = KProgressHUD.create(WorklistActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.please_wait))
                .setDetailsLabel(getString(R.string.data_get))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        /*
        String freightUrl = "https://goliveintegration.apimanagement.ap10.hana.ondemand.com:443/";
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(Constant.httpUsername, Constant.httpPassword))
                .connectTimeout(360, TimeUnit.SECONDS)
                .writeTimeout(360, TimeUnit.SECONDS)
                .readTimeout(360, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(freightUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

*/

        String freightUrlNew = Constant.baseUrl+"TM/FreightOrder/FreightOrder?$filter=TranspOrdLifeCycleStatus ne '05' and TranspOrdLifeCycleStatus ne '10' and TransportationOrderExecSts ne '04' and TransportationOrderExecSts ne '06'";

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);

        Call<FreightOrderValueModel> call = service.getFreightOrder(freightUrlNew);
        call.enqueue(new Callback<FreightOrderValueModel>() {
            @Override
            public void onResponse(Call<FreightOrderValueModel> call, Response<FreightOrderValueModel> response) {

                hud.dismiss();

                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    FreightOrderValueModel freightOrderValueModel = response.body();
                    if (freightOrderValueModel != null) {
                        List<FreightOrderResponse> freightlist = response.body().getValue();
                        if (freightlist.size() > 0) {
                            order = freightlist.get(0).getTransportationOrder();
                            plaka = freightlist.get(0).getYY1_Plaka_TOR();
                            transportationOrderUuid = freightlist.get(0).getTransportationOrderUUID();
                            getDocumentOrder(transportationOrderUuid);

                        }else{
                            Util.displayErrorDialog(WorklistActivity.this,getString(R.string.error),getString(R.string.delivery_error));
                            hud.dismiss();
                        }
                    }else{
                        Util.displayErrorDialog(WorklistActivity.this,getString(R.string.error),getString(R.string.delivery_error));
                        hud.dismiss();
                    }

                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        Util.displayErrorDialog(WorklistActivity.this,getString(R.string.error),getString(R.string.found_error));
                        hud.dismiss();

                    }
                }
                else{
                    Util.displayErrorDialog(WorklistActivity.this,getString(R.string.error),getString(R.string.found_error));
                    hud.dismiss();

                }
            }

            @Override
            public void onFailure(Call<FreightOrderValueModel> call, Throwable t) {
                hud.dismiss();
                Util.displayErrorDialog(WorklistActivity.this, getString(R.string.error), getString(R.string.found_error));

            }
        });
    }

    private void getDocumentOrder(String OrderUuid){

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

        String documentUrl = Constant.baseUrl+"TM/FreightOrder/FreightOrder(076cf98f-030a-1ede-bef8-dc3bcbc34171)/_FreightOrderDocumentReference";

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
                                    getPhoneNumber();
                                }
                            }
                        }else{
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
            public void onFailure(Call<DocumentOrderValueModel> call, Throwable t) {
                Util.displayErrorDialog(WorklistActivity.this, getString(R.string.error), getString(R.string.found_error));

            }
        });
    }
    private void getPhoneNumber(){

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
                            fillScreenData();
                            fillRcBarData();

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


}
