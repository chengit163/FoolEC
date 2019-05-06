package com.cit.fool.core.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.cit.fool.core.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Iterator;

public class FoolLoader
{
    private static final int LOADER_SIZE_SCALE = 8;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<AppCompatDialog>();
    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    private static void showLoading(Context context, String type)
    {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);
        dialog.setCancelable(false);

        int deviceWidth = ScreenUtils.getScreenWidth();
        int deviceHeight = ScreenUtils.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null)
        {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context, Enum<LoaderStyle> type)
    {
        showLoading(context, type == null ? DEFAULT_LOADER : type.name());
    }

    public static void showLoading(Context context)
    {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading()
    {
        Iterator<AppCompatDialog> iterator = LOADERS.iterator();
        while (iterator.hasNext())
        {
            AppCompatDialog dialog = iterator.next();
            if (dialog != null && dialog.isShowing())
            {
                dialog.cancel();
            }
            iterator.remove();
        }
    }
}
