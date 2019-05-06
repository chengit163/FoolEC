package com.cit.fool.ec.sign;

import com.cit.fool.core.app.AccountManager;
import com.cit.fool.ec.database.DatabaseManager;
import com.cit.fool.ec.database.entity.User;

public class SignHandler
{
    public static void onSignIn(User user, ISignListener signListener)
    {
        DatabaseManager.getInstance().getUserDao().insertOrReplace(user);
        AccountManager.setAccount(user.getUsername());
        AccountManager.setSigned(true);
        if (signListener != null)
        {
            signListener.onSignInSuccess();
        }
    }

    public static void onSignUp(User user, ISignListener signListener)
    {
        DatabaseManager.getInstance().getUserDao().insertOrReplace(user);
        AccountManager.setAccount(user.getUsername());
        AccountManager.setSigned(true);
        if (signListener != null)
        {
            signListener.onSignUpSuccess();
        }
    }
}
