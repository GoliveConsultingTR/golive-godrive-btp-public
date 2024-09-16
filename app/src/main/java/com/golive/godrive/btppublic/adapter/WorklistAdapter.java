package com.golive.godrive.btppublic.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.golive.godrive.btppublic.R;
import com.golive.godrive.btppublic.model.dataModel.Work;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class WorklistAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Work> workList;
    private Activity currentActivity;

    public WorklistAdapter(Activity activity, List<Work> workList) {

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.workList = workList;
        currentActivity = activity;
    }

    @Override
    public int getCount() {
        return workList.size();
    }

    @Override
    public Object getItem(int position) {
        return workList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View rowView;

        rowView = inflater.inflate(R.layout.row_worklist, null);

        //MaterialCardView mcvFo = rowView.findViewById(R.id.mcv_fo);
        TextView tvSiparisNo = rowView.findViewById(R.id.tv_siparis_no);
        TextView tvNavlunBirimiNo = rowView.findViewById(R.id.tv_navlun_birimi_no);
        TextView tvMusteriAdi = rowView.findViewById(R.id.tv_musteri_adi);
        TextView tvMusteriKodu = rowView.findViewById(R.id.tv_musteri_kodu);
        TextView tvMusteriTelefonu = rowView.findViewById(R.id.tv_musteri_telefonu);
        TextView tvMusteriAdresi = rowView.findViewById(R.id.tv_musteri_adresi);
        LinearLayout llWarningMessage = rowView.findViewById(R.id.ll_warning_message);
        LinearLayout llWarningMessageSeperator = rowView.findViewById(R.id.ll_warning_message_seperator);
        LinearLayout llPrinterSeperator = rowView.findViewById(R.id.ll_printer_seperator);
        LinearLayout llPrinter = rowView.findViewById(R.id.ll_printer);

        llWarningMessage.setVisibility(View.GONE);
        llWarningMessageSeperator.setVisibility(View.GONE);
        llPrinterSeperator.setVisibility(View.GONE);
        llPrinter.setVisibility(View.GONE);
        final Work work = workList.get(0);

        String sdSiparis        = work.getSalesOrder()   != null ? work.getSalesOrder()   : "";
        String musteriKodu      = work.getConsigneeID()  != null ? work.getConsigneeID()  : "";
        String musteriTelefonu  = work.getConsigneeTelNumber()     != null ? work.getConsigneeTelNumber() : "";
        String musteriAdresi    = work.getConsigneeAddress()        != null ? work.getConsigneeAddress() : "";
        String navlumBirimi     = work.getFreightUnit()       != null ? work.getFreightUnit() : "";
        String musteriAdi       = work.getConsigneeName()      != null ? work.getConsigneeName() : "";



        tvSiparisNo.setText(sdSiparis);
        tvMusteriKodu.setText(musteriKodu);
        tvMusteriTelefonu.setText(musteriTelefonu);
        tvMusteriAdresi.setText(musteriAdresi);
        tvNavlunBirimiNo.setText(navlumBirimi);
        tvMusteriAdi.setText(musteriAdi);

        return rowView;
    }
}
