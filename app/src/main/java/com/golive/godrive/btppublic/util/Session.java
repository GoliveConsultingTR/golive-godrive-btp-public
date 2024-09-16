package com.golive.godrive.btppublic.util;

import com.golive.godrive.btppublic.model.dataModel.Work;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private static Session mInstance;
    public List<Work> worklistTm = new ArrayList<>();

    public static Session Instance() {
        if (mInstance == null) {
            mInstance = new Session();
        }
        return mInstance;
    }
}
