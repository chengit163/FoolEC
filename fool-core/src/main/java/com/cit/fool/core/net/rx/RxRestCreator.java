package com.cit.fool.core.net.rx;

import com.cit.fool.core.app.ConfigKey;
import com.cit.fool.core.app.Fool;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RxRestCreator
{
    public static WeakHashMap<String, Object> getParams()
    {
        return ParamsHolder.PARAMS;
    }

    private static final class ParamsHolder
    {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static RxRestService getRxRestService()
    {
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * 构建 Retrofit
     */
    private static final class RetrofitHolder
    {
        private static final String BASE_URL = Fool.getConfiguration(ConfigKey.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    /**
     * 构建OkHttp
     */
    private static final class OkHttpHolder
    {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Fool.getConfiguration(ConfigKey.INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptor()
        {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty())
                for (Interceptor interceptor : INTERCEPTORS)
                    BUILDER.addInterceptor(interceptor);
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }


    /**
     * 构建Service接口
     */
    private static final class RestServiceHolder
    {
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

}
