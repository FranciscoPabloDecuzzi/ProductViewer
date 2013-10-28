package com.ml.android.productviewer.util.connetion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class ConnectionUtils {
	
	/**
	 * Metodo utilizado para convertir un stream de datos binarios a un String.
	 * @param inputStream Stream de datos binarios.
	 * @return String de la respuesta obtenida.
	 * @throws IOException Excepcion generada en caso de leerse un Stream invalido.
	 */
	public static String convertInputStreamToString(InputStream inputStream) throws IOException  
    {  
        if (inputStream != null)  
        {  
            StringBuilder stringBuilder = new StringBuilder();  
            String line;  
            BufferedReader reader;
            try {  
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
                while ((line = reader.readLine()) != null)  
                {  
                    stringBuilder.append(line);  
                }  
            }  
            finally  
            {  
                inputStream.close();  
            }  
  
            return stringBuilder.toString();  
        }  
        else  
        {  
            return null;  
        }  
    }
	
	/**
	 * Metodo utilizado para conocer si la respuesta obtenida desde el servidor es valida.
	 * @param response Respuesta obtenida del servidor.
	 * @return Boolean indicando si la respuesta obtenida es valida.
	 */
	public static boolean isPlainTextResponse(String response){
        if (response == null)
            return false;
        
        response = response.toLowerCase();
        return (!response.equals("") && response.indexOf("<html") == -1
                && !response.startsWith("<?xml") && !response.startsWith("<!doctype"));
    }

	/**
	 * Metodo utilizado para conocer si la respuesta obtenida desde google es valida.
	 * @param response Respuesta obtenida del servidor.
	 * @return Boolean indicando si la respuesta obtenida es valida.
	 */
	public static boolean isValidGoogleResponse(String response) {
		if (response == null)
            return false;
        
        response = response.toLowerCase();
        return (response.contains("www.google.com"));
	}

	public static boolean isValidSecureResponse(String response) {
		if (response == null)
            return false;
        
        response = response.toLowerCase();
        return (response.contains("ok"));
	}
}
