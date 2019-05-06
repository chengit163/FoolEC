package com.cit.fool.core.net.rx;

import android.content.Context;

import com.cit.fool.core.loader.LoaderStyle;
import com.cit.fool.core.net.callback.IComplete;
import com.cit.fool.core.net.callback.IError;
import com.cit.fool.core.net.callback.IPrepare;
import com.cit.fool.core.net.callback.IProgress;
import com.cit.fool.core.net.callback.ISuccess;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RxRestClientBuilder
{

    private String mUrl;
    private WeakHashMap<String, Object> mParams;
    //loading
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    //
    private File mFile;
    private RequestBody mBody;
    //callback
    private IPrepare mPrepare;
    private IComplete mComplete;
    private ISuccess mSuccess;
    private IError mError;
    private IProgress mProgress;

    public RxRestClientBuilder()
    {
//        mParams = new WeakHashMap<>();
    }

    public final RxRestClientBuilder url(String url)
    {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params)
    {
        if (mParams == null) mParams = new WeakHashMap<>();
        mParams.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value)
    {
        if (mParams == null) mParams = new WeakHashMap<>();
        mParams.put(key, value);
        return this;
    }

    public final RxRestClientBuilder loader(Context context)
    {
        this.mContext = context;
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle loaderStyle)
    {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RxRestClientBuilder file(File file)
    {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String file)
    {
        this.mFile = new File(file);
        return this;
    }

    public final RxRestClientBuilder raw(String raw)
    {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder prepare(IPrepare prepare)
    {
        this.mPrepare = prepare;
        return this;
    }

    public final RxRestClientBuilder complete(IComplete complete)
    {
        this.mComplete = complete;
        return this;
    }

    public final RxRestClientBuilder success(ISuccess success)
    {
        this.mSuccess = success;
        return this;
    }

    public final RxRestClientBuilder error(IError error)
    {
        this.mError = error;
        return this;
    }

    public final RxRestClientBuilder progress(IProgress progress)
    {
        this.mProgress = progress;
        return this;
    }

    public final RxRestClient build()
    {
        return new RxRestClient(mUrl, mParams, mContext, mLoaderStyle, mFile, mBody, mPrepare, mComplete, mSuccess, mError, mProgress);
    }
}
