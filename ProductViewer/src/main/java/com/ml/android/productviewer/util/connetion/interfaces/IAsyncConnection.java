package com.ml.android.productviewer.util.connetion.interfaces;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public interface IAsyncConnection extends Runnable{
	/**
	 * Url de GOOGLE
	 */
	static final String HTTP_GOOGLE_CONECTION = "http://www.google.com";

	/**
	 * Conexi�n realizada con �xito.
	 */
	static final int CONNECTION_OK = 1;
	
	/**
	 * Conexi�n fallida.
	 */
	static final int CONNECTION_FAILED = 0;

	public  class Error{
		/**
		 * Conexi�n fallida por time out.
		 */
		public static final int CONNECTION_TIME_OUT = -1;
		/**
		 * Conexi�n fallida por respuesta erronea de www.google.com.
		 */
		public static final int CONNECTION_INVALID_GOOGLE_RESPONSE = -2;
	}

	/**
	 * M�todo encargado de iniciar la ejecuci�n de AsyncConnection.
	 */
	public void start();
	/**
	 * M�todo encargado de descargar de memoria el objeto AsyncConnection.
	 */
	public void dispose();
}
