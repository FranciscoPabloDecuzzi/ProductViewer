package com.ml.android.productviewer.util.mappers;

import android.content.Context;

import com.ml.android.productviewer.entities.Product;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class ProductMapper {
    public static Product newFormJson(JSONObject jsonObject,Context context) throws JSONException
    {
            String title = jsonObject.getString(context.getString(com.ml.android.productviewer.R.string.atributte_product_title));
            String thumbail = jsonObject.getString(context.getString(com.ml.android.productviewer.R.string.atributte_product_thumbnail));
            double price = jsonObject.getDouble(context.getString(com.ml.android.productviewer.R.string.atributte_product_price));
            String currency = jsonObject.getString(context.getString(com.ml.android.productviewer.R.string.atributte_product_currency));
            String permalink = jsonObject.getString(context.getString(com.ml.android.productviewer.R.string.atributte_product_permalink));

        return new Product(title,price,currency,thumbail,permalink);

    }
}
