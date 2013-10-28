package com.ml.android.productviewer.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.ml.android.productviewer.fragments.ProductViewerFragment;
import com.ml.android.productviewer.fragments.interfaces.IMercadoViewerFragmentHandler;
import com.ml.android.productviewer.util.dialogs.InfoDialog;
import com.ml.android.productviewer.util.dialogs.ProcessDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public abstract class ProductViewerFragmentActivity extends FragmentActivity implements IMercadoViewerFragmentHandler{


    protected FrameLayout fragmentContainer;
    protected int focusedId = -1;
    protected ProductViewerFragment mainFragment;
    private boolean inPause;
    protected abstract void initControls();
    protected abstract int getLayoutId();
    protected abstract int getFrameLayoutId();

    protected static ProcessDialog mProcessDialog;
    protected static InfoDialog mInfoDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        fragmentContainer = (FrameLayout) findViewById(getFrameLayoutId());
        initControls();
        if (savedInstanceState == null)
        {
            mProcessDialog = new ProcessDialog(this);
            mInfoDialog =  new InfoDialog(this);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        checkFocusedControl();

        super.onConfigurationChanged(newConfig);


        List<ProductViewerFragment> activeFragments = getAttachedFragments();
        ProductViewerFragment activeFragment = getActiveFragment();
        for (Fragment fragment : activeFragments) {

            if (fragment != null && !fragment.equals(activeFragment))
            {
                replaceByRotation(fragment);
            }
        }
        if (activeFragment != null)
        {
            replaceByRotation(activeFragment);
        }



    }

    @Override
    public void replaceByRotation(Fragment frag) {
        if (!inPause)
        {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction().detach(frag);
            tx.commit();
            tx = getSupportFragmentManager().beginTransaction().attach(frag);
            tx.commit();

        }
    }

    private void checkFocusedControl() {
        View focusView = getCurrentFocus();
        focusedId = (focusView != null) ? focusView.getId() : -1;
    }

    //region FRAGMENTS METHODS
    public ProductViewerFragment getPreviousFragment() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count > 1)
        {
            String tag = getSupportFragmentManager().getBackStackEntryAt(count - 2).getName();
            return (ProductViewerFragment) getSupportFragmentManager().findFragmentByTag(tag);
        }
        else
        {
            return mainFragment;
        }
    }

    public ProductViewerFragment getActiveFragment() {
        if ( getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            return (ProductViewerFragment) getSupportFragmentManager().findFragmentByTag(tag);
        }
        else
        {
            return mainFragment;
        }
    }
    public List<ProductViewerFragment> getAttachedFragments()
    {
        List<ProductViewerFragment> fragments = new ArrayList<ProductViewerFragment>();

        int i = getSupportFragmentManager().getBackStackEntryCount();
        while (i > 0)
        {

            String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - i).getName();
            i--;
            fragments.add((ProductViewerFragment)getSupportFragmentManager().findFragmentByTag(tag));
        }
        if (mainFragment != null)
        {
            fragments.add(mainFragment);
        }

        return fragments;
    }


    public void removeCurrentFragment()
    {
        ProductViewerFragment oldFragment =  getActiveFragment();

        if (oldFragment != null && oldFragment != mainFragment)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(oldFragment);

            ProductViewerFragment currentFragment = getPreviousFragment();

            if (currentFragment != null)
            {
                transaction.detach(currentFragment);
                transaction.attach(currentFragment);
            }
            else if(mainFragment != null)
            {
                transaction.detach(mainFragment);
                transaction.attach(mainFragment);
            }

            transaction.commit();

        }
    }
    private void clearFragmentStack() {
        if (!inPause)
        {
            FragmentManager fm = getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                int id = fm.getBackStackEntryAt(i) != null ? fm.getBackStackEntryAt(i).getId():-1;
                if (id != -1)
                {
                    fm.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        }


    }
    protected void popFragmentStack() {
        if (!inPause)
        {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();
        }
    }
    protected void tryToAddFragment(Fragment fragment,boolean goBack) {
        if (!inPause) {
            if (this.fragmentContainer != null) {
                ;
                String name = ((Object)fragment).getClass().getSimpleName();
                Fragment oldFrament = getActiveFragment();
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction().add(fragmentContainer.getId(), fragment,name);
                if (oldFrament != null)
                {
                    tx.hide(oldFrament);
                }
                tx.show(fragment);
                if (goBack) {
                    tx = tx.addToBackStack(name);
                }
                tx.commit();
            }
        }
    }

    //endregion  +

    private void dismissDialgs()
    {
        if (mProcessDialog != null)
        {
            mProcessDialog.dissmis();
        }

        if (mInfoDialog != null)
        {
            mInfoDialog.dismiss();
        }
    }
    @Override
    public void onPause() {
        inPause = true;
        if (mInfoDialog != null)
        {
            mInfoDialog.onPause();
        }
        if (mProcessDialog != null)
        {
            mProcessDialog.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        inPause = false;
        super.onResume();
        if (mInfoDialog != null)
        {
            mInfoDialog.onRestart();
        }
        if (mProcessDialog != null)
        {
            mProcessDialog.onRestart();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstance)
    {
        if (mProcessDialog != null)
        {
            mProcessDialog.onSaveInstanceState(savedInstance);
        }
        if (mInfoDialog != null)
        {
            mInfoDialog.onSaveInstanceState(savedInstance);
        }

        super.onSaveInstanceState(savedInstance);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstance)
    {
        if (mProcessDialog != null)
        {
            mProcessDialog.onRestoreInstanceState(savedInstance);
        }
        if (mInfoDialog != null)
        {
            mInfoDialog.onRestoreInstanceState(savedInstance);
        }

        super.onRestoreInstanceState(savedInstance);
    }


    @Override
    public View getFocusedView() {
        if (focusedId != -1)
        {
            return  findViewById(focusedId);
        }
        else
        {
            return null;
        }
    }



}
