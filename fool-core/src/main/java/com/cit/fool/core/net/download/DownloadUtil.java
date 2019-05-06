package com.cit.fool.core.net.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class DownloadUtil
{

    public static void saveFile(ResponseBody body, File file)
    {
        InputStream is = null;
        FileOutputStream fos = null;
        try
        {
            is = body.byteStream();
            fos = new FileOutputStream(file);
            //
            int n;
            byte[] b = new byte[1024];
            while ((n = is.read(b)) != -1)
            {
                fos.write(b, 0, n);
            }
            fos.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
