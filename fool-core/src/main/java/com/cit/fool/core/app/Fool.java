package com.cit.fool.core.app;

import android.content.Context;

import com.blankj.utilcode.util.AppUtils;

import java.util.HashMap;

public final class Fool
{

    public static Configurator init(Context context)
    {
        com.blankj.utilcode.util.Utils.init(context.getApplicationContext());
        Configurator.getInstance().getConfigs()
                .put(ConfigKey.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator()
    {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(ConfigKey key)
    {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext()
    {
        return getConfiguration(ConfigKey.APPLICATION_CONTEXT);
    }


}
