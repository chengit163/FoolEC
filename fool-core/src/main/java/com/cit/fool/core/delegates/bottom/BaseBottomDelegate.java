package com.cit.fool.core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.cit.fool.core.R;
import com.cit.fool.core.R2;
import com.cit.fool.core.delegates.FoolDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

public abstract class BaseBottomDelegate extends FoolDelegate implements View.OnClickListener
{
    @BindView(R2.id.bottom_bar_delegate_container)
    ContentFrameLayout mBottomBarDelegateContainer;
    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar;

    private final ArrayList<BottomTabBean> mTabs = new ArrayList<>();
    private final ArrayList<BottomItemDelegate> mDelegates = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> mItems = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;//当前显示的delegate
    private int mIndexDelegate = 0;// 默认的delegate
    private int mClickedColor = Color.RED;//点击后的颜色

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public Object setLayout()
    {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0)
        {
            mClickedColor = setClickedColor();
        }
        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        mItems.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : mItems.entrySet())
        {
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            mTabs.add(key);
            mDelegates.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView)
    {
        final int size = mItems.size();
        for (int i = 0; i < size; i++)
        {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = mTabs.get(i);
            //初始化数据Å
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i == mIndexDelegate)
            {
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }
        final ISupportFragment[] delegateArray = mDelegates.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);
    }

    private void resetColor()
    {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++)
        {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v)
    {
        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(mClickedColor);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);

        getSupportDelegate().showHideFragment(mDelegates.get(tag), mDelegates.get(mCurrentDelegate));
        //注意先后顺序
        mCurrentDelegate = tag;
    }

}
