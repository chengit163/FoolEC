package com.cit.fool.core.app;

import com.cit.fool.core.util.storage.FoolPreferences;

public class AccountManager
{
    public static String getAccount()
    {
        return FoolPreferences.getRecentAccount();
    }

    public static void setAccount(String account)
    {
        FoolPreferences.setRecentAccount(account);
    }

    public static boolean isSigned()
    {
        String account = getAccount();
        if (account == null)
            return false;
        return FoolPreferences.isAccountSigned(account);
    }

    public static void setSigned(boolean value)
    {
        String account = getAccount();
        if (account == null)
            return;
        FoolPreferences.setAccountSigned(account, value);
    }

    public static void checkAccount(IUserChecker checker)
    {
        if (isSigned())
        {
            checker.onSignIn();
        } else
        {
            checker.onNotSignIn();
        }
    }
}
