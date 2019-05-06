package com.cit.fool.core.delegates;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cit.fool.core.activitys.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public abstract class BaseDelegate extends Fragment implements ISupportFragment
{
    private static final boolean DEBUG = true;
    private static final String TAG = "BaseDelegate";
    //
    private final SupportFragmentDelegate mDelegate = new SupportFragmentDelegate(this);
    private Unbinder mUnbinder;

    protected FragmentActivity mFragmentActivity;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    public final ProxyActivity getProxyActivity()
    {
        return (ProxyActivity) mFragmentActivity;
    }

    @Override
    public void onAttach(Activity activity)
    {
        if (DEBUG) Log.d(TAG, "onAttach: " + toString());
        super.onAttach(activity);
        mDelegate.onAttach(activity);
        mFragmentActivity = mDelegate.getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (DEBUG) Log.d(TAG, "onCreate: " + toString());
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (DEBUG) Log.d(TAG, "onCreateView: " + toString());
        final View rootView;
        if (setLayout() instanceof Integer)
        {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View)
        {
            rootView = (View) setLayout();
        } else
        {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        if (DEBUG) Log.d(TAG, "onActivityCreated: " + toString());
        super.onActivityCreated(savedInstanceState);
        mDelegate.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        if (DEBUG) Log.d(TAG, "onResume: " + toString());
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    public void onPause()
    {
        if (DEBUG) Log.d(TAG, "onPause: " + toString());
        super.onPause();
        mDelegate.onPause();
    }

    @Override
    public void onStop()
    {
        if (DEBUG) Log.d(TAG, "onStop: " + toString());
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        if (DEBUG) Log.d(TAG, "onDestroyView: " + toString());
        super.onDestroyView();
        if (mUnbinder != null)
        {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    @Override
    public void onDestroy()
    {
        if (DEBUG) Log.d(TAG, "onDestroy: " + toString());
        super.onDestroy();
        mDelegate.onDestroy();
    }

    @Override
    public void onDetach()
    {
        if (DEBUG) Log.d(TAG, "onDetach: " + toString());
        super.onDetach();
    }

    //

    @Override
    public SupportFragmentDelegate getSupportDelegate()
    {
        return mDelegate;
    }

    @Override
    public ExtraTransaction extraTransaction()
    {
        return mDelegate.extraTransaction();
    }

    @Override
    public void enqueueAction(Runnable runnable)
    {
        mDelegate.enqueueAction(runnable);
    }

    @Override
    public void post(Runnable runnable)
    {
        mDelegate.post(runnable);
    }

    @Override
    public void onEnterAnimationEnd(@Nullable Bundle savedInstanceState)
    {
        mDelegate.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState)
    {
        mDelegate.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onSupportVisible()
    {
        mDelegate.onSupportVisible();
    }

    @Override
    public void onSupportInvisible()
    {
        mDelegate.onSupportInvisible();
    }

    @Override
    public boolean isSupportVisible()
    {
        return mDelegate.isSupportVisible();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator()
    {
        return mDelegate.onCreateFragmentAnimator();
    }

    @Override
    public FragmentAnimator getFragmentAnimator()
    {
        return mDelegate.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator)
    {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public void setFragmentResult(int resultCode, Bundle bundle)
    {
        mDelegate.setFragmentResult(resultCode, bundle);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data)
    {
        mDelegate.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void onNewBundle(Bundle args)
    {
        mDelegate.onNewBundle(args);
    }

    @Override
    public void putNewBundle(Bundle newBundle)
    {
        mDelegate.putNewBundle(newBundle);
    }

    @Override
    public boolean onBackPressedSupport()
    {
        return mDelegate.onBackPressedSupport();
    }
}
