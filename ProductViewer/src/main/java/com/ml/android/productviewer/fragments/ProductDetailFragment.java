package com.ml.android.productviewer.fragments;



import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ml.android.productviewer.R;
import com.ml.android.productviewer.entities.Product;
import com.ml.android.productviewer.fragments.interfaces.IProductDetailFragmentHandler;
import com.ml.android.productviewer.util.connetion.BitmapDownloadTask;
import com.ml.android.productviewer.util.connetion.interfaces.IBitmapDownloadListener;
import com.ml.android.productviewer.util.mappers.PriceStringMapper;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class ProductDetailFragment extends ProductViewerFragment<IProductDetailFragmentHandler> implements IBitmapDownloadListener{

    private TextView mTitle;
    private TextView mPrice;
    private ImageView mThumbnail;
    private TextView mButtonDetail;
    private static Product product;
    private Bitmap mBitmapImage;

    public static Product getProduct() {
        return product;
    }

    public static void setProduct(Product product) {
        ProductDetailFragment.product = product;
    }


    @Override
    protected void initControls() {
        mTitle = (TextView)getView().findViewById(R.id.product_title);
        mTitle.setText(product.getTitle());
        mPrice = (TextView)getView().findViewById(R.id.product_price);
        mPrice.setText(PriceStringMapper.formatDouble(product.getPrice())+" "+product.getCurrency());
        mThumbnail= (ImageView)getView().findViewById(R.id.product_thumbnail);
        mButtonDetail = (TextView)getView().findViewById(R.id.btn_product_detail);
        if (mBitmapImage != null)
        {
            mThumbnail.setImageBitmap(mBitmapImage);
        }
        else
        {
            new BitmapDownloadTask(this).execute(product.getThumbnail());
        }
        mButtonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IProductDetailFragmentHandler)handler).openWeb(product.getPermalink());
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ((IProductDetailFragmentHandler)handler).onDetailInView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBitmapImage = null;
        ((IProductDetailFragmentHandler)handler).onDetailViewed();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_detail;
    }

    @Override
    public void bitmapDownloaded(Bitmap bitmap) {
        mBitmapImage = bitmap;
        if (mThumbnail != null)
        {
            mThumbnail.setImageBitmap(mBitmapImage);
        }
    }
}

