package com.cit.fool.core.net.download;

public class DownloadEvent
{
    private long total;
    private long progress;

    public DownloadEvent(long total, long progress)
    {
        this.total = total;
        this.progress = progress;
    }

    public long getTotal()
    {
        return total;
    }

    public long getProgress()
    {
        return progress;
    }
}
