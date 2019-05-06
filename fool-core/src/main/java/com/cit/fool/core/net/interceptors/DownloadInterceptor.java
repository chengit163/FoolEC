package com.cit.fool.core.net.interceptors;

import android.util.Log;

import com.cit.fool.core.net.download.DownloadEvent;
import com.cit.fool.core.net.rx.RxBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class DownloadInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }

    private class ProgressResponseBody extends ResponseBody
    {
        private ResponseBody responseBody;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody)
        {
            this.responseBody = responseBody;
        }

        @Override
        public MediaType contentType()
        {
            return responseBody.contentType();
        }

        @Override
        public long contentLength()
        {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source()
        {
            if (bufferedSource == null)
            {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source)
        {
            return new ForwardingSource(source)
            {
                private long bytesReaded = 0;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException
                {
                    long bytesRead = super.read(sink, byteCount);
                    bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                    RxBus.getInstance().post(new DownloadEvent(contentLength(), bytesReaded));
                    return bytesRead;
                }
            };
        }
    }
}
