package com.ml.android.productviewer.util.connetion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.ml.android.productviewer.util.connetion.interfaces.IBitmapDownloadListener;

import java.io.InputStream;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class BitmapDownloadTask extends AsyncTask<String, Void, Bitmap> {
        IBitmapDownloadListener listener;

        public BitmapDownloadTask(IBitmapDownloadListener listener) {
            this.listener = listener;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 =  BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (listener != null)
            {
                listener.bitmapDownloaded(result);
            }
        }
}
