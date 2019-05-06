package com.cit.fool.core.util.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cit.fool.core.app.Fool;

public final class FoolPreferences
{
    /**
     * 提示:
     * Activity.getPreferences(int mode)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml
     * Context.getSharedPreferences(String name,int mode)生成name.xml
     */
    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(Fool.getApplicationContext());


    private static final String IS_LAUNCHER_ONCE = "is_launcher_once";
    private static final String RECENT_ACCOUNT = "recent_account";
    private static final String IS_ACCOUNT_SIGNED_ = "is_account_signed_";


    private static SharedPreferences getAppPreference()
    {
        return PREFERENCES;
    }


    public static boolean isLauncherOnce()
    {
        return getAppPreference().getBoolean(IS_LAUNCHER_ONCE, false);
    }

    public static void setLauncherOnce(boolean value)
    {
        getAppPreference().edit().putBoolean(IS_LAUNCHER_ONCE, value).apply();
    }

    public static String getRecentAccount()
    {
        return getAppPreference().getString(RECENT_ACCOUNT, null);
    }

    public static void setRecentAccount(String value)
    {
        getAppPreference().edit().putString(RECENT_ACCOUNT, value).apply();
    }

    public static boolean isAccountSigned(String account)
    {
        return getAppPreference().getBoolean(IS_ACCOUNT_SIGNED_ + account, false);
    }


    public static void setAccountSigned(String account, boolean value)
    {
        getAppPreference().edit().putBoolean(IS_ACCOUNT_SIGNED_ + account, value).apply();
    }
}
