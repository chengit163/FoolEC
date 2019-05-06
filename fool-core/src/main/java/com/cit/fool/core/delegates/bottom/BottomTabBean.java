package com.cit.fool.core.delegates.bottom;

public class BottomTabBean
{

    private final CharSequence icon;
    private final CharSequence title;

    public BottomTabBean(CharSequence icon, CharSequence title)
    {
        this.icon = icon;
        this.title = title;
    }

    public CharSequence getIcon()
    {
        return icon;
    }

    public CharSequence getTitle()
    {
        return title;
    }
}