package com.cit.fool.core.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.cit.fool.core.R;
import com.cit.fool.core.delegates.FoolDelegate;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public abstract class ProxyActivity extends AppCompatActivity implements ISupportActivity
{
    private final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);

    public abstract FoolDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    @Override
    protected void onDestroy()
    {
        mDelegate.onDestroy();
        super.onDestroy();
        //
        System.gc();
        System.runFinalization();
    }

    private void initContainer(@Nullable Bundle savedInstanceState)
    {
        final FrameLayout container = new FrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null)
        {
            mDelegate.loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    public SupportActivityDelegate getSupportDelegate()
    {
        return mDelegate;
    }

    @Override
    public ExtraTransaction extraTransaction()
    {
        return mDelegate.extraTransaction();
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
    public FragmentAnimator onCreateFragmentAnimator()
    {
        return mDelegate.onCreateFragmentAnimator();
    }

    @Override
    public void post(Runnable runnable)
    {
        mDelegate.post(runnable);
    }

    @Override
    public void onBackPressedSupport()
    {
        mDelegate.onBackPressedSupport();
    }

    @Override
    public void onBackPressed()
    {
        mDelegate.onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }
}
