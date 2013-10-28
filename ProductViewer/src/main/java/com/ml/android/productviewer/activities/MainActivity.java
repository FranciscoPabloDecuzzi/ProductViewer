package com.ml.android.productviewer.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.ml.android.productviewer.R;
import com.ml.android.productviewer.entities.Product;
import com.ml.android.productviewer.fragments.ProductDetailFragment;
import com.ml.android.productviewer.fragments.ProductListFragment;
import com.ml.android.productviewer.fragments.interfaces.IProductDetailFragmentHandler;
import com.ml.android.productviewer.fragments.interfaces.IProductListFragmentHandler;
import com.ml.android.productviewer.util.connetion.ConnectionManager;
import com.ml.android.productviewer.util.connetion.MercadoLibreWS;
import com.ml.android.productviewer.util.connetion.interfaces.IConnectionResultListener;
import com.ml.android.productviewer.util.mappers.ProductMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class MainActivity extends ProductViewerFragmentActivity implements SearchView.OnQueryTextListener, IConnectionResultListener
,IProductListFragmentHandler,
 IProductDetailFragmentHandler{

    private SearchView mSearchView;
    private Menu mMenu;
    @Override
    protected void initControls() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFrameLayoutId() {
        return R.id.frameLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null)
        {
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        }
        super.onCreate(savedInstanceState);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        mMenu = menu;
        setupMenu(menu);

        return true;
    }

    private void setupMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
    }

    private void setupSearchView(MenuItem searchItem) {

         mSearchView.setIconifiedByDefault(false);
         mSearchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {
            return false;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public boolean onQueryTextSubmit(String query) {
            MenuItem searchItem = mMenu.findItem(R.id.action_search);
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB){
                searchItem.collapseActionView();
            }
            mProcessDialog.show(getString(R.string.msg_getting_products), getString(R.string.msg_getting_please_wait));
            MercadoLibreWS.getInstance().getProductList(query, new ConnectionManager(this, this));
            return false;
    }

    protected boolean isAlwaysExpanded() {
       return false;
    }

    @Override
    public void onConnectionOK(JSONObject jsonObject) {

        mProcessDialog.dissmis();
        try
        {
           JSONArray results = jsonObject.getJSONArray(getString(R.string.atributte_results));
           Product product;
           ArrayList<Product> products = new ArrayList<Product>();

           for(int i = 0; i < results.length(); i++)
           {
              product = ProductMapper.newFormJson(results.getJSONObject(i),this);
              products.add(product);
           }
            ProductListFragment fragment = new ProductListFragment();

            if (getActiveFragment() == null)
            {
                mainFragment = fragment;
                tryToAddFragment(fragment,false);
            }
            ((ProductListFragment)mainFragment).refreshProducts(products);

        }
        catch (Exception e)
        {
            onConnectionFail();
        }

    }

    @Override
    public void onConnectionFail() {

        mProcessDialog.dissmis();
        mInfoDialog.show(getString(R.string.msg_genericConnectionError), getString(R.string.msg_ok));
    }


    @Override
    public void productSelected(Product product) {

        ProductDetailFragment frg = new ProductDetailFragment();
        frg.setProduct(product);
        tryToAddFragment(frg,true);
    }

    @Override
    public void openWeb(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onDetailViewed() {
        setupMenu(mMenu);
    }

    @Override
    public void onDetailInView() {

        mMenu.removeItem(R.id.action_search);
    }


}
