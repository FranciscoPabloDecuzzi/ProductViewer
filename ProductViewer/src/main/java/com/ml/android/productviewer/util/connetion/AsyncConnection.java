package com.ml.android.productviewer.util.connetion;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.ml.android.productviewer.util.connetion.interfaces.IAsyncConnection;

import android.net.http.AndroidHttpClient;
import android.os.Handler;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class AsyncConnection implements IAsyncConnection {
	
	/**
	 * Método encargado de descargar de memoria el objeto AsyncConnection.
	 */
	public void dispose(){
		sURL = null;
		response = null;
		sResponse = null;
		listener = null;
	}
	
	private String sURL = "";
	private String sResponse;
	private HttpResponse response;
	private Handler listener;
	

	
	/**
	 * Código propio de AsyncConnection que se envía con los mensajes del Handler.
	 */
	public static final int ASYNC_CONNECTION_CODE = 100;
	
	/**
	 * Constructor de la clase AsyncConnection.
	 * @param url URL del servidor con el que se desea comunicar.
	 * @param list Handler que va a estar escuchando los mensajes de AsyncConnection.
	 */
	public AsyncConnection(String url, Handler list){
		sURL = url;
		listener = list;
	}



	/**
	 * M�todo sobreescrito de la clase Runneable, encargado de iniciar la ejecuci�n de la clase AsyncConnection en un nuevo Thread.
	 * NO LLAMAR MANUALMENTE.
	 */
	@Override
	public void run() {		
		AndroidHttpClient client = AndroidHttpClient.newInstance(null);
		executeGet(client);

	}

	private void executeGet(AndroidHttpClient client) {
		HttpGet method = new HttpGet(sURL);
		
		try {
			response = client.execute(method);
			sResponse = ConnectionUtils.convertInputStreamToString(response.getEntity().getContent());
			listener.sendMessage(listener.obtainMessage(ASYNC_CONNECTION_CODE, CONNECTION_OK,CONNECTION_OK,sResponse));
		} catch (IOException e) {
			sResponse = e.getClass().getName() + ": " + e.getMessage();
			listener.sendMessage(listener.obtainMessage(ASYNC_CONNECTION_CODE, CONNECTION_FAILED));
		}
        client.close();
	}

	/**
	 * M�todo encargado de iniciar la ejecuci�n de AsyncConnection.
	 */
	public void start(){
		new Thread(this).start();
	}
	

}
	
	
	
