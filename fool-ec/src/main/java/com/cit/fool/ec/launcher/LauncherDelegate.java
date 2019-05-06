package com.cit.fool.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.cit.fool.core.app.AccountManager;
import com.cit.fool.core.app.Fool;
import com.cit.fool.core.app.IUserChecker;
import com.cit.fool.core.delegates.FoolDelegate;
import com.cit.fool.core.util.storage.FoolPreferences;
import com.cit.fool.core.util.timer.BaseTimerTask;
import com.cit.fool.core.util.timer.ITimerListener;
import com.cit.fool.ec.R;
import com.cit.fool.ec.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

public class LauncherDelegate extends FoolDelegate implements ITimerListener
{

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvLauncherTimer;


    private int mCount = 5;
    private Timer mTimer;
    private ILauncherListener mLauncherListener;

    @Override
    public Object setLayout()
    {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView)
    {
        initTimer();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener)
        {
            mLauncherListener = (ILauncherListener) activity;
        }
    }

    private void initTimer()
    {
        if (mCount > 0 && mTimer == null)
        {
            mTimer = new Timer();
            final BaseTimerTask task = new BaseTimerTask(this);
            mTimer.schedule(task, 0, 1000);
        }
    }

    private void destroyTimer()
    {
        if (mTimer != null)
        {
            mTimer.cancel();
            mTimer = null;
        }
    }


    @OnClick(R2.id.tv_launcher_timer)
    public void onClick()
    {
        launcherJump();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        initTimer();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        destroyTimer();
    }


    @Override
    public void onTimer()
    {
        getProxyActivity().runOnUiThread(() ->
        {
            if (mCount >= 0)
            {
                String text = getString(R.string.launcher_jump) + "\n" + mCount + "s";
                mTvLauncherTimer.setText(text);
                mCount--;
            } else
            {
                destroyTimer();
                launcherJump();
            }
        });
    }

    private void launcherJump()
    {
        if (FoolPreferences.isLauncherOnce())
        {
            AccountManager.checkAccount(new IUserChecker()
            {
                @Override
                public void onSignIn()
                {
                    if (mLauncherListener != null)
                    {
                        mLauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn()
                {
                    if (mLauncherListener != null)
                    {
                        mLauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        } else
        {
            FoolPreferences.setLauncherOnce(true);
            getSupportDelegate().start(new LauncherScrollDelegate(), STANDARD);
        }
    }


}
