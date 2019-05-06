package com.cit.fool.ec.ui.launcher;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

public class LauncherHolder implements Holder<Integer>
{

    private ImageView mImageView;

    @Override
    public View createView(Context context)
    {
        mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data)
    {
        mImageView.setBackgroundResource(data);
    }
}