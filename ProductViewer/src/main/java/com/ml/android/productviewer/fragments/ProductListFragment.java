package com.ml.android.productviewer.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ml.android.productviewer.R;
import com.ml.android.productviewer.entities.Product;
import com.ml.android.productviewer.fragments.interfaces.IProductListFragmentHandler;
import com.ml.android.productviewer.util.mappers.PriceStringMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class ProductListFragment extends ProductViewerFragment {

    private static final String PRODUCT_KEY = "product";
    private static final String TITLE_KEY = "title";
    private static final String PRICE_KEY = "price";

    private ListView mProductListView;
    private static ArrayList<Product> mProducts;

    public ProductListFragment()
    {

    }


    @Override
    protected void initControls() {
        mProductListView = (ListView)getView().findViewById(R.id.list_products);
        loadList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_list;
    }

    public void refreshProducts(ArrayList<Product> products) {
        this.mProducts = products;
        if (getActivity() != null)
        {
            loadList();
        }
    }

    protected void loadList()
    {

        ArrayList<Map<String, Object>> historyItems =  new ArrayList<Map<String, Object>>();
        Map<String, Object> mItem = null;

        if (mProducts != null)
        {
            for (Product product : mProducts) {
                mItem = new HashMap<String, Object>();
                mItem.put("product", product);
                mItem.put("title", product.getTitle());
                mItem.put("price", PriceStringMapper.formatDouble(product.getPrice())+" "+product.getCurrency());
                historyItems.add(mItem);
            }

            SimpleAdapter adapter = new SimpleAdapter(
                    getActivity(),
                    historyItems,
                    R.layout.product_list_item,
                    new String[] {TITLE_KEY, PRICE_KEY},
                    new int[] { R.id.itemTitle, R.id.itemPrice});

            mProductListView.setAdapter(adapter);
            mProductListView.setOnItemClickListener(onItemClickListener);
        }
    }

    private final ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {

            Product product = (Product)(((HashMap<String, Object>) mProductListView.getItemAtPosition(index)).get(PRODUCT_KEY));
           ((IProductListFragmentHandler)handler).productSelected(product);

        }
    };


}
