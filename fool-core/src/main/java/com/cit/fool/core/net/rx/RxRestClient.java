package com.cit.fool.core.net.rx;

import android.content.Context;

import com.cit.fool.core.loader.FoolLoader;
import com.cit.fool.core.loader.LoaderStyle;
import com.cit.fool.core.net.HttpMethod;
import com.cit.fool.core.net.callback.IComplete;
import com.cit.fool.core.net.callback.IError;
import com.cit.fool.core.net.callback.IPrepare;
import com.cit.fool.core.net.callback.IProgress;
import com.cit.fool.core.net.callback.ISuccess;
import com.cit.fool.core.net.download.DownloadEvent;
import com.cit.fool.core.net.download.DownloadUtil;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RxRestClient
{
    private final String mUrl;
    private final WeakHashMap<String, Object> mParams;
    //loading
    private final Context mContext;
    private final LoaderStyle mLoaderStyle;
    //
    private final File mFile;
    private final RequestBody mBody;
    //callback
    private final IPrepare mPrepare;
    private final IComplete mComplete;
    private final ISuccess mSuccess;
    private final IError mError;
    private final IProgress mProgress;

    public RxRestClient(String url, WeakHashMap<String, Object> params,
                        Context context, LoaderStyle loaderStyle, File file, RequestBody body,
                        IPrepare prepare, IComplete complete, ISuccess success, IError error, IProgress progress)
    {
        this.mUrl = url;
        this.mParams = params == null ? RxRestCreator.getParams() : params;
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        this.mFile = file;
        this.mBody = body;
        this.mPrepare = prepare;
        this.mComplete = complete;
        this.mSuccess = success;
        this.mError = error;
        this.mProgress = progress;
    }


    public static RxRestClientBuilder builder()
    {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method)
    {
        final RxRestService service = RxRestCreator.getRxRestService();
        Observable<String> observable = null;

        switch (method)
        {
            case GET:
                observable = service.get(mUrl, mParams);
                break;
            case POST:
                observable = service.post(mUrl, mParams);
                break;
            case POST_RAW:
                observable = service.postRaw(mUrl, mBody);
                break;
            case PUT:
                observable = service.put(mUrl, mParams);
                break;
            case PUT_RAW:
                observable = service.putRaw(mUrl, mBody);
                break;
            case DELETE:
                observable = service.delete(mUrl, mParams);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mFile);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", mFile.getName(), requestBody);
                observable = RxRestCreator.getRxRestService().upload(mUrl, body);
                break;
            default:
                break;
        }

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        if (mContext != null)
                        {
                            FoolLoader.showLoading(mContext, mLoaderStyle);
                        }
                        if (mPrepare != null)
                        {
                            mPrepare.onPrepare();
                        }
                    }

                    @Override
                    public void onNext(String s)
                    {
                        if (mSuccess != null)
                        {
                            mSuccess.onSuccess(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        if (mContext != null)
                        {
                            FoolLoader.stopLoading();
                        }
                        if (mError != null)
                        {
                            mError.onError(e);
                        }
                    }

                    @Override
                    public void onComplete()
                    {
                        if (mContext != null)
                        {
                            FoolLoader.stopLoading();
                        }
                        if (mComplete != null)
                        {
                            mComplete.onComplete();
                        }
                    }
                });

        return observable;
    }

    public final Observable<String> get()
    {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post()
    {
        if (mBody == null)
        {
            return request(HttpMethod.POST);
        } else
        {
            //原始数据PARAMS必须为null
            if (mParams == null || mParams.isEmpty())
            {
                return request(HttpMethod.POST_RAW);
            } else
            {
                throw new RuntimeException("params must be null");
            }

        }
    }

    public final Observable<String> put()
    {
        if (mBody == null)
        {
            return request(HttpMethod.PUT);
        } else
        {
            //原始数据PARAMS必须为null
            if (mParams == null || mParams.isEmpty())
            {
                return request(HttpMethod.PUT_RAW);
            } else
            {
                throw new RuntimeException("params must be null");
            }
        }
    }

    public final Observable<String> delete()
    {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload()
    {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download()
    {
        Observable<ResponseBody> observable = RxRestCreator.getRxRestService().download(mUrl, mParams);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(x -> DownloadUtil.saveFile(x, mFile))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>()
                {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        if (mContext != null)
                        {
                            FoolLoader.showLoading(mContext, mLoaderStyle);
                        }
                        disposable = RxBus.getInstance().register(DownloadEvent.class, x ->
                        {
                            if (mProgress != null)
                            {
                                mProgress.onProgress(x.getTotal(), x.getProgress());
                            }
                        });
                    }

                    @Override
                    public void onNext(ResponseBody responseBody)
                    {
                        if (mSuccess != null)
                        {
                            mSuccess.onSuccess(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        RxBus.getInstance().unregister(disposable);
                        if (mContext != null)
                        {
                            FoolLoader.stopLoading();
                        }
                        if (mError != null)
                        {
                            mError.onError(e);
                        }
                    }

                    @Override
                    public void onComplete()
                    {
                        RxBus.getInstance().unregister(disposable);
                        if (mContext != null)
                        {
                            FoolLoader.stopLoading();
                        }
                        if (mComplete != null)
                        {
                            mComplete.onComplete();
                        }
                    }
                });

        return observable;
    }
}
