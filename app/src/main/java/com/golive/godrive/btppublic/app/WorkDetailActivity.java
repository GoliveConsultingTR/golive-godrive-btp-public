package com.golive.godrive.btppublic.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.golive.godrive.btppublic.R;
import com.golive.godrive.btppublic.adapter.ListViewAdapter;
import com.golive.godrive.btppublic.model.dataModel.Work;
import com.golive.godrive.btppublic.model.serviceModel.getBilling.GetBillingDModel;
import com.golive.godrive.btppublic.model.serviceModel.getBilling.GetBillingResponse;
import com.golive.godrive.btppublic.model.serviceModel.getFreightUnit.FreightUnitResponse;
import com.golive.godrive.btppublic.model.serviceModel.getFreightUnit.FreightUnitValueModel;
import com.golive.godrive.btppublic.model.serviceModel.getSalesOrder.SalesOrderDModel;
import com.golive.godrive.btppublic.model.serviceModel.getSalesOrder.SalesOrderResponse;
import com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery.CreateDeliveryDModel;
import com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery.CreateDeliveryRequestBody;
import com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery.CreateDeliveryRequestItem;
import com.golive.godrive.btppublic.model.serviceModel.postCreateDelivery.CreateDeliveryRequestResults;
import com.golive.godrive.btppublic.model.serviceModel.postGoodIssues.GoodsIssuesDModel;
import com.golive.godrive.btppublic.model.serviceModel.postGoodIssues.GoodsIssuesRequest;
import com.golive.godrive.btppublic.service.SAPServiceManager;
import com.golive.godrive.btppublic.service.Service;
import com.golive.godrive.btppublic.service.ServiceInterface;
import com.golive.godrive.btppublic.util.Session;
import com.golive.godrive.btppublic.util.Util;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkDetailActivity extends BaseActivity{
    private LinearLayout llDetailHeader;
    private TextView tvDetailSdSiparisNo;
    private TextView tvDetailNavlunBirimiNo;
    private TextView tvDetailMusteriKodu;
    private TextView tvDetailMusteriAdi;

    private ListView lvWorklist;
    private Button btnTeslimat;
    private Button btnFatura;

    private SAPServiceManager sapServiceManager = null;

    private Fragment currentFragment = null;
    private ListViewAdapter adapterWorklist = null;
    private Context context = this;
    private Activity activity = this;
    private List<Pair<Integer, String>> editTextValuesList = new ArrayList<>();
    private long mLastClickTimeBtn = 0;
    private String torId = "";
    private String evColorCode = "";

    String sdSiparis = "",musteriKodu = "",musteriAdi = "",navlumBirimi = "",quantityUnit = "", quantity = "";
    private List<String > referenceItemList = new ArrayList<>();
    String deliveryDocument = "", teslimat = "";
    List<CreateDeliveryRequestBody> deliveryRequestList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_work_detail);
        llDetailHeader = findViewById(R.id.ll_detail_header);
        tvDetailSdSiparisNo = findViewById(R.id.tv_detail_sd_siparis_no);
        tvDetailNavlunBirimiNo = findViewById(R.id.tv_detail_navlun_birimi_no);
        tvDetailMusteriKodu = findViewById(R.id.tv_detail_musteri_kodu);
        tvDetailMusteriAdi = findViewById(R.id.tv_detail_musteri_adi);
        lvWorklist = findViewById(R.id.listView);
        btnTeslimat = findViewById(R.id.btn_teslimat_yarat);
        btnFatura = findViewById(R.id.btn_fatura_yarat);

        setupView();

    }
    private void setupView() {
        Toolbar toolbar = findViewById(R.id.toolbar_worklist_detail);
        setSupportActionBar(toolbar);

        int titleColor = ContextCompat.getColor(this, R.color.white);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.order_detail));
            toolbar.setTitleTextColor(titleColor);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        fillHeaderInfo();
        //getDeliveryDetail();
        getSalesOrder();
        //createOrRefreshAdapter();
        butonaBas();
        getBilling();
    }



    private void fillHeaderInfo() {

        llDetailHeader.setVisibility(View.VISIBLE);
        int pos = Session.Instance().pozisyon;
        Work work = Session.Instance().worklistTm.get(pos);
        sdSiparis = work.getSalesOrder() != null ? work.getSalesOrder().trim() : "";
        navlumBirimi = work.getFreightUnit() != null ? work.getFreightUnit().trim() : "";
        musteriAdi = work.getConsigneeName() != null ? work.getConsigneeName().trim() : "";
        musteriKodu = work.getConsigneeID() != null ? work.getConsigneeID().trim() : "";
        teslimat = work.getTeslimatKontrol() != null ? work.getTeslimatKontrol().trim() : "";

        if(teslimat.equals("True")){
            btnTeslimat.setEnabled(false);
            btnTeslimat.setBackgroundColor(getResources().getColor(R.color.grey_300));
            btnFatura.setEnabled(true);
            btnFatura.setBackgroundColor(getResources().getColor(R.color.app_color_green));
        }else{
            btnFatura.setEnabled(false);
            btnFatura.setBackgroundColor(getResources().getColor(R.color.grey_300));
            btnTeslimat.setEnabled(true);
            btnTeslimat.setBackgroundColor(getResources().getColor(R.color.app_color_green));
        }

        tvDetailSdSiparisNo.setText(sdSiparis);
        tvDetailNavlunBirimiNo.setText(navlumBirimi);
        tvDetailMusteriKodu.setText(musteriKodu);
        tvDetailMusteriAdi.setText(musteriAdi);

        Session.Instance().worklistTm.clear();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Handle back button press
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return false; // Disable back button functionality
        }
        return super.onKeyDown(keyCode, event);
    }

    private void passToWorklistActivity() {
        // Navigate to WorklistActivity
        Session.Instance().worklistTmAdapter.clear();
        Intent intent = new Intent(getApplicationContext(), WorklistActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle options menu item clicks
        if (item.getItemId() == android.R.id.home) {
            passToWorklistActivity(); // If home button is clicked, pass to WorklistActivity
        }
        return super.onOptionsItemSelected(item);
    }

    private void createOrRefreshAdapter() {
        if (adapterWorklist == null) {
            adapterWorklist = new ListViewAdapter((WorkDetailActivity) activity, Session.Instance().worklistTm);
            lvWorklist.setAdapter(adapterWorklist);
        } else {
            adapterWorklist.notifyDataSetChanged();
        }
    }

    private void butonaBas() {
        btnTeslimat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterWorklist != null) {
                    boolean allValuesEntered = true;
                    for (int position = 0; position < adapterWorklist.getCount(); position++) {
                        View childView = lvWorklist.getChildAt(position);
                        if (childView != null) {
                            EditText numberEditText = childView.findViewById(R.id.number);
                            if (numberEditText != null) {
                                String value = numberEditText.getText().toString();
                                if (value.isEmpty()) {
                                    allValuesEntered = false;
                                    String msg = getString(R.string.enter_delivery);
                                    ((WorkDetailActivity) activity).error(msg, null);
                                    return;
                                } else {
                                    editTextValuesList.add(new Pair<>(position, value));
                                }
                            }
                        }
                    }
                    if (allValuesEntered) {
                        createItem();
                    } else {
                        String msg = getString(R.string.enter_delivery);
                        ((WorkDetailActivity) activity).error(msg, null);
                    }
                }
            }
        });
    }

    private void createItem() {
        boolean isHigh = false;

        for (int position = 0; position < editTextValuesList.size(); position++) {
            String value = editTextValuesList.get(position).second;
            BigDecimal quantity = new BigDecimal(value.isEmpty() ? "0" : value);

            Work work = Session.Instance().worklistTmAdapter.get(position);
            String miktar2 = work.getPlanned() != null ? work.getPlanned().trim() : "";
            BigDecimal miktar = new BigDecimal(miktar2);


            if (miktar != null && quantity.compareTo(miktar) > 0) {
                isHigh = true;
                editTextValuesList.clear();
                break;
            }
        }

        if (isHigh) {
            String msg = getString(R.string.more_enter_delivery);
            ((WorkDetailActivity) activity).error(msg, null);
            editTextValuesList.clear();
        } else {
            for (int position = 0; position < editTextValuesList.size(); position++) {
                String value = editTextValuesList.get(position).second;
                BigDecimal quantity = new BigDecimal(value.isEmpty() ? "0" : value);

                Work work = Session.Instance().worklistTmAdapter.get(position);
                String item = work.getItem() != null ? work.getItem().trim() : "";
                String pC   = work.getPc() != null ? work.getPc().trim() : "";
                /*
                DeliveryModel deliveryModel = new DeliveryModel();
                deliveryModel.setDeliveryItem(value);
                deliveryModel.setDeliveryQuantityUnit(pC);
                deliveryModel.setReferenceDocument(sdSiparis);
                deliveryModel.setReferenceDocumentItem(item);
                Session.Instance().deliveryModel.add(deliveryModel);
                */

                CreateDeliveryRequestBody deliveryRequest = new CreateDeliveryRequestBody();
                deliveryRequest.setActualDeliveryQuantity(value);
                deliveryRequest.setDeliveryQuantityUnit(pC);
                deliveryRequest.setReferenceSDDocument("135");
                deliveryRequest.setReferenceSDDocumentItem(item);

                deliveryRequestList.add(deliveryRequest);

            }
            postCreateDelivery();
            //pickAllItems("80000079");
        }
    }
    private void getDeliveryDetail(){


        KProgressHUD hud = KProgressHUD.create(WorkDetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.please_wait))
                .setDetailsLabel(getString(R.string.data_get))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();



        String deliveryUrl = "https://my407038-api.s4hana.cloud.sap/sap/opu/odata4/sap/api_freightunit/srvd_a2x/sap/freightunit/0001/FreightUnit(076cf98f-030a-1ede-bef8-cc4107a90171)/_FreightUnitItem";

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<FreightUnitValueModel> call = service.getDelivety(deliveryUrl);
        call.enqueue(new Callback<FreightUnitValueModel>() {
            @Override
            public void onResponse(Call<FreightUnitValueModel> call, Response<FreightUnitValueModel> response) {
                hud.dismiss();
                int responseCode = response.code();
                Work work = Session.Instance().worklistTm.get(0);
                if(200 <= responseCode && responseCode < 300) {
                    FreightUnitValueModel deliveryValueModel = response.body();
                    if (deliveryValueModel != null) {
                        List<FreightUnitResponse> deliveryList = response.body().getValue();
                        if (deliveryList.size() > 0) {
                            for(int i=0; i < deliveryList.size(); i++) {
                                String product = deliveryList.get(i).getTranspOrdItemDesc();
                                if (!product.isEmpty()) {
                                    quantity = deliveryList.get(0).getTranspOrdItemQuantity();
                                    quantityUnit = deliveryList.get(0).getTranspOrdItemQuantityUnit();
                                    String weight = deliveryList.get(0).getTranspOrdItemGrossWeight();
                                    BigDecimal grossWeight = new BigDecimal(weight);
                                    grossWeight = grossWeight.setScale(0, RoundingMode.DOWN);
                                    String weightUnit = deliveryList.get(0).getTranspOrdItemGrossWeightUnit();

                                    String planned = quantity + " " + quantityUnit + " - "+grossWeight + " " + weightUnit;

                                    work.setProduct(product);
                                    work.setPlanned(planned);
                                    break;
                                }
                            }
                        }
                        else{
                            Util.displayErrorDialog(WorkDetailActivity.this,getString(R.string.error),getString(R.string.delivery_error));
                            hud.dismiss();
                        }
                        fillHeaderInfo();
                        //Session.Instance().worklistTm.add(work);

                        adapterWorklist = new ListViewAdapter(WorkDetailActivity.this, Session.Instance().worklistTm);
                        lvWorklist.setAdapter(adapterWorklist);
                    }else{
                        Util.displayErrorDialog(WorkDetailActivity.this,getString(R.string.error),getString(R.string.delivery_error));
                        hud.dismiss();
                    }

                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        Util.displayErrorDialog(WorkDetailActivity.this,getString(R.string.error),getString(R.string.found_error));
                        hud.dismiss();
                    }
                }
                else{
                    Util.displayErrorDialog(WorkDetailActivity.this,getString(R.string.error),getString(R.string.found_error));
                    hud.dismiss();
                }
            }

            @Override
            public void onFailure(Call<FreightUnitValueModel> call, Throwable t) {
                Util.displayErrorDialog(WorkDetailActivity.this, getString(R.string.error), getString(R.string.found_error));
                hud.dismiss();
            }
        });
    }
    private void postCreateDelivery(){
        KProgressHUD hud = KProgressHUD.create(WorkDetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.please_wait))
                .setDetailsLabel(getString(R.string.data_get))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        /*
        CreateDeliveryRequestBody deliveryRequest = new CreateDeliveryRequestBody();
        deliveryRequest.setActualDeliveryQuantity("1");
        deliveryRequest.setDeliveryQuantityUnit("PC");
        deliveryRequest.setReferenceSDDocument("136");
        deliveryRequest.setReferenceSDDocumentItem("10");

        deliveryRequestList.add(deliveryRequest);

         */
        CreateDeliveryRequestResults createDeliveryRequestResults = new CreateDeliveryRequestResults();
        createDeliveryRequestResults.setResults(deliveryRequestList);

        CreateDeliveryRequestItem createDeliveryRequestItem = new CreateDeliveryRequestItem();
        createDeliveryRequestItem.setTo_DeliveryDocumentItem(createDeliveryRequestResults);

        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");
        map.put("Accept-Encoding", "application/json");
        map.put("Content-Type", "application/json");


        String createDelivryUrl = "https://goliveintegration.apimanagement.ap10.hana.ondemand.com:443/TM/Delivery/A_OutbDeliveryHeader";

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<CreateDeliveryDModel> call = service.postCreateDelivery(createDelivryUrl, map, createDeliveryRequestItem);
        call.enqueue(new Callback<CreateDeliveryDModel>() {
            @Override
            public void onResponse(Call<CreateDeliveryDModel> call, Response<CreateDeliveryDModel> response) {

                hud.dismiss();
                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    CreateDeliveryDModel createDeliveryDModel = response.body();
                    deliveryDocument = createDeliveryDModel.getD().getDeliveryDocument();
                    pickAllItems(deliveryDocument);


                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorkDetailActivity) activity).error(msg, null);
                        hud.dismiss();
                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorkDetailActivity) activity).error(msg, null);
                    hud.dismiss();
                }
            }

            @Override
            public void onFailure(Call<CreateDeliveryDModel> call, Throwable t) {
                String msg = getString(R.string.service_error);
                ((WorkDetailActivity) activity).error(msg, null);
                hud.dismiss();
            }
        });
    }

    private void getSalesOrder(){
/*
        KProgressHUD hud = KProgressHUD.create(WorkDetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.please_wait))
                .setDetailsLabel(getString(R.string.data_get))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

 */
        String salesOrderUrl = "https://goliveintegration.apimanagement.ap10.hana.ondemand.com:443/SalesOrder/A_SalesOrder('{sdSiparis}')/to_Item";
        salesOrderUrl = salesOrderUrl.replace("{sdSiparis}", sdSiparis);

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<SalesOrderDModel> call = service.getSalesOrder(salesOrderUrl);
        call.enqueue(new Callback<SalesOrderDModel>() {
            @Override
            public void onResponse(Call<SalesOrderDModel> call, Response<SalesOrderDModel> response) {
                //hud.dismiss();
                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    SalesOrderDModel salesOrderDModel = response.body();
                    if (salesOrderDModel != null) {
                        List<SalesOrderResponse> deliveryList = response.body().getD().getResults();
                        if (deliveryList.size() > 0) {
                            for(int i=0; i < deliveryList.size(); i++) {
                                String product = deliveryList.get(i).getSalesOrderItemText();

                                sdSiparis = deliveryList.get(i).getSalesOrder();
                                String item = deliveryList.get(i).getSalesOrderItem();
                                quantity = deliveryList.get(i).getRequestedQuantity();
                                String prod = deliveryList.get(i).getOrderQuantityUnit();
                                //quantityUnit = deliveryList.get(0).getRequestedQuantityUnit();
                                //String planned = quantity + " " + quantityUnit;
                                Work work = new Work();
                                work.setSalesOrder(sdSiparis);
                                work.setProduct(product);
                                work.setPlanned(quantity);
                                work.setItem(item);
                                work.setPc(prod);
                                Session.Instance().worklistTmAdapter.add(work);
                                }

                            }
                        }
                        else{
                        String msg = getString(R.string.service_error);
                        ((WorkDetailActivity) activity).error(msg, null);
                       // hud.dismiss();
                        }
                    adapterWorklist = new ListViewAdapter(WorkDetailActivity.this, Session.Instance().worklistTmAdapter);
                    lvWorklist.setAdapter(adapterWorklist);
                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorkDetailActivity) activity).error(msg, null);
                        //hud.dismiss();
                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorkDetailActivity) activity).error(msg, null);
                  // hud.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SalesOrderDModel> call, Throwable t) {
                String msg = getString(R.string.service_error);
                ((WorkDetailActivity) activity).error(msg, null);
                //hud.dismiss();
            }
        });
    }
    private void pickAllItems(String Document){
/*
        KProgressHUD hud = KProgressHUD.create(WorkDetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.please_wait))
                .setDetailsLabel(getString(R.string.data_get))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


 */

        GoodsIssuesRequest goodsIssuesRequest = new GoodsIssuesRequest();
        goodsIssuesRequest.setDeliveryDocument(Document);

        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");
        map.put("Accept-Encoding", "application/json");
        map.put("Content-Type", "application/json");



        String pickAll = "https://goliveintegration.apimanagement.ap10.hana.ondemand.com:443/TM/Delivery/PickAllItems?DeliveryDocument='{document}'";
        pickAll = pickAll.replace("{document}", Document);

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<GoodsIssuesDModel> call = service.postGoodIssues(pickAll, map, goodsIssuesRequest);
        call.enqueue(new Callback<GoodsIssuesDModel>() {
            @Override
            public void onResponse(Call<GoodsIssuesDModel> call, Response<GoodsIssuesDModel> response) {
                //hud.dismiss();
                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    GoodsIssuesDModel salesOrderDModel = response.body();
                    postGoodIssues(Document);

                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorkDetailActivity) activity).error(msg, null);
                      //  hud.dismiss();
                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorkDetailActivity) activity).error(msg, null);
                   // hud.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GoodsIssuesDModel> call, Throwable t) {
                String msg = getString(R.string.service_error);
                ((WorkDetailActivity) activity).error(msg, null);
              //  hud.dismiss();
            }
        });
    }

    private void postGoodIssues(String Document){




        GoodsIssuesRequest goodsIssuesRequest = new GoodsIssuesRequest();
        goodsIssuesRequest.setDeliveryDocument(Document);

        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");
        map.put("Accept-Encoding", "application/json");
        map.put("Content-Type", "application/json");



        String goodsIssues = "https://goliveintegration.apimanagement.ap10.hana.ondemand.com:443/TM/Delivery/PostGoodsIssue?DeliveryDocument='{document}'";
        goodsIssues = goodsIssues.replace("{document}", Document);

        ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
        Call<GoodsIssuesDModel> call = service.postGoodIssues(goodsIssues, map, goodsIssuesRequest);
        call.enqueue(new Callback<GoodsIssuesDModel>() {
            @Override
            public void onResponse(Call<GoodsIssuesDModel> call, Response<GoodsIssuesDModel> response) {
                int responseCode = response.code();

                if(200 <= responseCode && responseCode < 300) {
                    GoodsIssuesDModel salesOrderDModel = response.body();
                    String mesaj = "Başarılı";
                    String msg = getString(R.string.delivery_successful);
                    ((WorkDetailActivity) activity).success(msg, mesaj, new Runnable() {
                        @Override
                        public void run() {
                            btnTeslimat.setEnabled(false);
                            btnTeslimat.setBackgroundColor(getResources().getColor(R.color.grey_300));
                            btnFatura.setEnabled(true);
                            btnFatura.setBackgroundColor(getResources().getColor(R.color.app_color_green));
                        }
                    });
                }
                else if(responseCode == 400){
                    if(response.errorBody() != null) {
                        String msg = getString(R.string.service_error);
                        ((WorkDetailActivity) activity).error(msg, null);
                    }
                }
                else{
                    String msg = getString(R.string.service_error);
                    ((WorkDetailActivity) activity).error(msg, null);
                }
            }

            @Override
            public void onFailure(Call<GoodsIssuesDModel> call, Throwable t) {
                String msg = getString(R.string.service_error);
                ((WorkDetailActivity) activity).error(msg, null);
            }
        });
    }
    private void getBilling(){
        btnFatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                KProgressHUD hud = KProgressHUD.create(WorkDetailActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(getString(R.string.please_wait))
                        .setDetailsLabel(getString(R.string.data_get))
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                */


                String billUrl = "https://my407038-api.s4hana.cloud.sap/sap/opu/odata/sap/API_BILLING_DOCUMENT_SRV/A_BillingDocumentItem?$filter=ReferenceSDDocument eq '{document}' and ReferenceSDDocumentCategory eq 'J'&$top=1";
                billUrl = billUrl.replace("{document}", "80000073");
                ServiceInterface service = Service.Instance().retrofit.create(ServiceInterface.class);
                Call<GetBillingDModel> call = service.getBill(billUrl);
                call.enqueue(new Callback<GetBillingDModel>() {
                    @Override
                    public void onResponse(Call<GetBillingDModel> call, Response<GetBillingDModel> response) {

                        //hud.dismiss();
                        int responseCode = response.code();

                        if(200 <= responseCode && responseCode < 300) {
                            GetBillingDModel billDModel = response.body();
                            if (billDModel != null) {
                                List<GetBillingResponse> documentlist = response.body().getD().getResults();
                                if (documentlist.size() > 0) {
                                    String billingDocument = documentlist.get(0).getBillingDocument();
                                    String mesaj = "Başarılı";
                                    String msg = getString(R.string.invoice_success);
                                    ((WorkDetailActivity) activity).success(msg, mesaj, new Runnable() {
                                        @Override
                                        public void run() {
                                            passToWorklistActivity();
                                        }
                                    });
                                }
                                else{
                                    String msg = getString(R.string.service_error);
                                    ((WorkDetailActivity) activity).error(msg, null);
                                  //  hud.dismiss();
                                      }
                            }else{
                                String msg = getString(R.string.service_error);
                                ((WorkDetailActivity) activity).error(msg, null);
                                //hud.dismiss();
                                }
                        }
                        else if(responseCode == 400){
                            if(response.errorBody() != null) {
                                String msg = getString(R.string.service_error);
                                ((WorkDetailActivity) activity).error(msg, null);
                                //hud.dismiss();
                            }
                        }
                        else{
                            String msg = getString(R.string.service_error);
                            ((WorkDetailActivity) activity).error(msg, null);
                            //hud.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetBillingDModel> call, Throwable t) {
                        String msg = getString(R.string.service_error);
                        ((WorkDetailActivity) activity).error(msg, null);
                        //hud.dismiss();
                    }
                });
            }
        });

    }
}
