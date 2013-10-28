package com.ml.android.productviewer.util.connetion.interfaces;

import org.json.JSONObject;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public interface IConnectionResultListener {
    public void onConnectionOK(JSONObject jsonObject);
    public void onConnectionFail();
}
