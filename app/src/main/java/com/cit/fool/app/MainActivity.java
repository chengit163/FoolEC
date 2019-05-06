package com.cit.fool.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.cit.fool.core.activitys.ProxyActivity;
import com.cit.fool.core.delegates.FoolDelegate;
import com.cit.fool.ec.launcher.ILauncherListener;
import com.cit.fool.ec.launcher.LauncherDelegate;
import com.cit.fool.ec.launcher.OnLauncherFinishTag;
import com.cit.fool.ec.main.EcBottomDelegate;
import com.cit.fool.ec.sign.ISignListener;
import com.cit.fool.ec.sign.SignInDelegate;

import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity implements ILauncherListener, ISignListener
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this, false);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(android.R.color.holo_purple));
    }

    @Override
    public FoolDelegate setRootDelegate()
    {
        return new LauncherDelegate();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag)
    {
        switch (tag)
        {
            case SIGNED:
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    public void onSignInSuccess()
    {
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess()
    {
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }
}
