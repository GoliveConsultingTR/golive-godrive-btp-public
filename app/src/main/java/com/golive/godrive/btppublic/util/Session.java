package com.golive.godrive.btppublic.util;

import com.golive.godrive.btppublic.model.dataModel.DeliveryModel;
import com.golive.godrive.btppublic.model.dataModel.Work;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private static Session mInstance;
    public List<Work> worklistTm = new ArrayList<>();

    public long btnThresholdTime = 1000;

    public List<DeliveryModel> deliveryModel = new ArrayList<>();
    public int pozisyon;
    public List<Work> worklistTmAdapter = new ArrayList<>();
    public static Session Instance() {
        if (mInstance == null) {
            mInstance = new Session();
        }
        return mInstance;
    }
}
