package com.cit.fool.ec.main;

import android.support.v4.content.ContextCompat;

import com.cit.fool.core.delegates.bottom.BaseBottomDelegate;
import com.cit.fool.core.delegates.bottom.BottomItemDelegate;
import com.cit.fool.core.delegates.bottom.BottomTabBean;
import com.cit.fool.core.delegates.bottom.ItemBuilder;
import com.cit.fool.ec.main.index.IndexDelegate;

import java.util.LinkedHashMap;

public class EcBottomDelegate extends BaseBottomDelegate
{


    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder)
    {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new IndexDelegate());
//        items.put(new BottomTabBean("{fa-compass}", "发现"), new IndexDelegate());
//        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new IndexDelegate());
//        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate()
    {
        return 0;
    }

    @Override
    public int setClickedColor()
    {
        return ContextCompat.getColor(getContext(), android.R.color.holo_purple);
    }
}
