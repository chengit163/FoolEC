package com.cit.fool.app;

import android.app.Application;

import com.cit.fool.core.app.Fool;
import com.cit.fool.core.net.interceptors.DownloadInterceptor;
import com.cit.fool.ec.database.DatabaseManager;
import com.cit.fool.ec.icon.FontECModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.tencent.bugly.Bugly;

public class MainApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Bugly.init(this, null, true);
        Fool.init(this)
                .withApiHost("http://192.168.19.5:8080")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontECModule())
                .withInterceptor(new DownloadInterceptor())
                .configure();
        DatabaseManager.getInstance().init(this);
    }
}
