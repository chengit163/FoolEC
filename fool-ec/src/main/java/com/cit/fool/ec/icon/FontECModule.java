package com.cit.fool.ec.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

public class FontECModule implements IconFontDescriptor
{
    @Override
    public String ttfFileName()
    {
        return "iconfont-ec.ttf";
    }

    @Override
    public Icon[] characters()
    {
        return FontECIcons.values();
    }
}
