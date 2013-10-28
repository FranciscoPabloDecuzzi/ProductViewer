package com.ml.android.productviewer.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.ml.android.productviewer.fragments.interfaces.IMercadoViewerFragmentHandler;


/**
 * Created by FranciscoPablo on 27/10/13.
 */
public abstract class ProductViewerFragment<THandler> extends Fragment {

    IMercadoViewerFragmentHandler handler;
    protected int irotCount;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };
    protected abstract void initControls();
    protected abstract int getLayoutId();




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        view.setOnTouchListener(onTouchListener);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initControls();
        checkFocus();
    }

    private void checkFocus() {
        View focusedView = handler.getFocusedView();

        if (focusedView != null)
        {
            focusedView.requestFocus();

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        handler = (IMercadoViewerFragmentHandler)activity;
    }

    @Override
    public void onDestroyView()
    {

        super.onDestroyView();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        irotCount++;
        super.onConfigurationChanged(newConfig);

    }

}
