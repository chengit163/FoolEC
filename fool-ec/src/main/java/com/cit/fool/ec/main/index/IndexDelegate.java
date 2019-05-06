package com.cit.fool.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cit.fool.core.delegates.bottom.BottomItemDelegate;
import com.cit.fool.ec.R;

public class IndexDelegate extends BottomItemDelegate
{
    @Override
    public Object setLayout()
    {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView)
    {

    }
}
