package com.ml.android.productviewer.fragments.interfaces;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public interface IMercadoViewerFragmentHandler {
    View getFocusedView();
    void replaceByRotation(Fragment frag);
}
