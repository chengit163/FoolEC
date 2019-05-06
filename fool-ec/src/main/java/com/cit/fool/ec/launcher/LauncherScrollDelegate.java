package com.cit.fool.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.cit.fool.core.app.AccountManager;
import com.cit.fool.core.app.IUserChecker;
import com.cit.fool.core.delegates.FoolDelegate;
import com.cit.fool.ec.R;
import com.cit.fool.ec.ui.launcher.LauncherHolderCreator;

import java.util.ArrayList;

public class LauncherScrollDelegate extends FoolDelegate implements OnItemClickListener
{
    private ConvenientBanner<Integer> mConvenientBanner;
    private ArrayList<Integer> mImages;
    private ILauncherListener mLauncherListener;

    @Override
    public Object setLayout()
    {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView)
    {
        initBanner();
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

    private void initBanner()
    {
        mImages = new ArrayList<>();
        mImages.add(R.mipmap.launcher_1);
        mImages.add(R.mipmap.launcher_2);
        mImages.add(R.mipmap.launcher_3);
        mImages.add(R.mipmap.launcher_4);
        mImages.add(R.mipmap.launcher_5);
        mImages.add(R.mipmap.launcher_6);

        mConvenientBanner.setPages(new LauncherHolderCreator(), mImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,
                // 不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(true);
    }


    @Override
    public void onItemClick(int position)
    {
        if (position == mImages.size() - 1)
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
        }
    }
}
