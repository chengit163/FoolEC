package com.cit.fool.ec.icon;

import com.joanzapata.iconify.Icon;

public enum FontECIcons implements Icon
{
    icon_scan('\ue602'),
    icon_ali_pay('\ue606');

    char character;

    private FontECIcons(char character)
    {
        this.character = character;
    }

    @Override
    public String key()
    {
        return this.name().replace('_', '-');
    }

    @Override
    public char character()
    {
        return this.character;
    }
}
