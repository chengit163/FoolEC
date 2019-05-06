package com.cit.fool.core.delegates.bottom;

import android.widget.Toast;

import com.cit.fool.core.R;
import com.cit.fool.core.app.Fool;
import com.cit.fool.core.delegates.FoolDelegate;

public abstract class BottomItemDelegate extends FoolDelegate
{
    private static final long WAIT_TIME = 2000L;
    private long touchTime = 0L;

    @Override
    public boolean onBackPressedSupport()
    {
        if (System.currentTimeMillis() - touchTime > WAIT_TIME)
        {
            Toast.makeText(mFragmentActivity, "双击退出" + Fool.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
            touchTime = System.currentTimeMillis();
        } else
        {
            mFragmentActivity.finish();
        }
        return true;
    }
}
