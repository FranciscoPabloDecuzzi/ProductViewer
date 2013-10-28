package com.ml.android.productviewer.util.connetion;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ml.android.productviewer.util.connetion.interfaces.IConnectionResultListener;

import org.json.JSONObject;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class ConnectionManager {
    private IConnectionResultListener listener;
    private Context context;

    public  ConnectionManager(IConnectionResultListener listener, Context context)
    {
        this.listener = listener;
        this.context = context;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.arg1)
            {
                case AsyncConnection.CONNECTION_OK:
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        listener.onConnectionOK(jsonObject);


                    }
                    catch (Exception e)
                    {
                        listener.onConnectionFail();
                    }
                    break;
                }
                case AsyncConnection.CONNECTION_FAILED: {
                    listener.onConnectionFail();
                    break;
                }
            }
        }
    };

    public void dispose()
    {
        setHandler(null);
        listener = null;
    }

    public Handler getHandler() {
        return handler;
    }

    private void setHandler(Handler handler) {
        this.handler = handler;
    }
}
