package com.golive.godrive.btppublic.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.golive.godrive.btppublic.R;
import com.golive.godrive.btppublic.model.dataModel.Work;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Work> workList;
    private Activity currentActivity;

    public ListViewAdapter(Activity activity, List<Work> workList) {

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

        rowView = inflater.inflate(R.layout.listview_adapter, null);


        TextView tvProduct = rowView.findViewById(R.id.tv_product);
        TextView tvPlan = rowView.findViewById(R.id.tv_plan);

        final Work work = workList.get(position);


        String productId        = work.getProduct()   != null ? work.getProduct()   : "";
        String planned      = work.getPlanned()  != null ? work.getPlanned()  : "";

        //String Miktar = work.getSalesOrder();
        //BigDecimal miktar = new BigDecimal(Miktar);



        tvProduct.setText(productId);
        tvPlan.setText(planned);

        return rowView;

    }
}