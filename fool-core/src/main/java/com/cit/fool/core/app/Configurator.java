package com.cit.fool.core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

public class Configurator
{
    private static final HashMap<ConfigKey, Object> CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator()
    {
        CONFIGS.put(ConfigKey.CONFIG_READY, false);
    }

    public static Configurator getInstance()
    {
        return Holder.INSTANCE;
    }

    private static class Holder
    {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final HashMap<ConfigKey, Object> getConfigs()
    {
        return CONFIGS;
    }

    public final void configure()
    {
        initIcons();
        initInterceptors();
        CONFIGS.put(ConfigKey.CONFIG_READY, true);
    }

    public final Configurator withApiHost(String apiHost)
    {
        CONFIGS.put(ConfigKey.API_HOST, apiHost);
        return this;
    }

    public final Configurator withIcon(IconFontDescriptor descriptor)
    {
        ICONS.add(descriptor);
        return this;
    }

    private void initIcons()
    {
        CONFIGS.put(ConfigKey.ICON, ICONS);
        if (ICONS.size() > 0)
        {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++)
                initializer.with(ICONS.get(i));
        }
    }

    public final Configurator withInterceptor(Interceptor interceptor)
    {
        INTERCEPTORS.add(interceptor);
        return this;
    }

    private void initInterceptors()
    {
        CONFIGS.put(ConfigKey.INTERCEPTOR, INTERCEPTORS);
    }

    private void checkConfiguration()
    {
        final boolean isReady = (boolean) CONFIGS.get(ConfigKey.CONFIG_READY);
        if (!isReady)
            throw new RuntimeException("Configuration is not ready.");
    }

    @SuppressWarnings("unchecked")
    public final <T> T getConfiguration(Enum<ConfigKey> key)
    {
        checkConfiguration();
        final Object value = CONFIGS.get(key);
        if (value == null)
            throw new NullPointerException(key + " is null.");
        return (T) CONFIGS.get(key);
    }
}
