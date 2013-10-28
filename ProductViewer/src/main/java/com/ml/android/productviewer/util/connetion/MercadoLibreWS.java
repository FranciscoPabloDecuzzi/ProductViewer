package com.ml.android.productviewer.util.connetion;

import java.net.URLEncoder;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class MercadoLibreWS {
    private AsyncConnection _connection;
    private static MercadoLibreWS _instance;
    protected static final String ML_URL = "https://api.mercadolibre.com/sites/MLA/search?q=%1$s";

    public static MercadoLibreWS getInstance() {
        if(null == _instance)
        {
           _instance = new MercadoLibreWS();


        }

        return _instance;
    }

    public void getProductList(String query, ConnectionManager connectionManager) {


        String url = String.format(ML_URL, URLEncoder.encode(query));

        _connection = new AsyncConnection(url, connectionManager.getHandler());
        _connection.start();
    }

}
