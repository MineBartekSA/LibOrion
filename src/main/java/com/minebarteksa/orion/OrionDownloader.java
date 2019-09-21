package com.minebarteksa.orion;

import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.ArrayList;

public class OrionDownloader // TODO: Move and maybe change the name
{
    private static final int BufferSize = 102400;

    private final ArrayList<DownloadFile> ftd = new ArrayList<>();
    private final String downloadPath;

    public OrionDownloader() throws Exception { this("downloads/"); }

    public OrionDownloader(String path) throws Exception
    {
        path = (path.charAt(0) == '/' ? path.substring(1) : path) + (path.charAt(path.length() - 1) != '/' ? "/" : "");
        downloadPath = System.getProperty("user.dir") + "/" + path;
        if (!new File(downloadPath).exists())
            if (!new File(downloadPath).mkdirs())
                throw new Exception("Failed to create '" + path + "'!");
    }

    public void AddFile(String url, String filename) { ftd.add(new DownloadFile(url, filename)); }

    /**
     * Starts Synchronously Download added files
     */
    public void Download() { Download(true); }

    /**
     * Start Downloading added files.<br>
     * Can download in sync or async.<br>
     * When used on Construction, PreInitialization, Initialization or PostInitialization
     * it will show the progress of the download.
     * @param sync if download should be synchronous or asynchronous
     */
    public void Download(boolean sync) // TODO: Test Sync download after the game started
    {
        if (sync)
            DownloadSync();
        else
            DownloadAsync();
    }

    private void DownloadAsync()
    {
        new Thread(() -> {
            for (DownloadFile download : ftd)
            {
                if (new File(downloadPath + download.Filename).exists())
                    continue;

                Orion.log.info("Downloading '" + download.Filename + "' file...");
                try(FileOutputStream file = new FileOutputStream(downloadPath + download.Filename))
                {
                    file.getChannel().transferFrom(Channels.newChannel(new URL(download.Url).openStream()), 0, Long.MAX_VALUE);
                    Orion.log.info("Successfully downloaded '" + download.Filename + "'!");
                }
                catch (Exception e)
                {
                    Orion.log.error("Failed to download '" + download.Filename + "' file!");
                    Orion.log.error(e);
                }
            }
            ftd.clear();
        }).start();
    }

    private void DownloadSync()
    {
         for (DownloadFile download : ftd)
         {
             if (new File(downloadPath + download.Filename).exists())
                 continue;

             ProgressBar progress = null;
             Orion.log.info("Downloading '" + download.Filename + "' file...");

             try
             {
                 HttpURLConnection connection = (HttpURLConnection)new URL(download.Url).openConnection();
                 connection.connect();

                 progress = ProgressManager.push("Downloading", 100);
                 progress.step(download.Filename);
                 long size = connection.getContentLengthLong();
                 long downloaded = 0;

                 File df = new File(downloadPath + download.Filename);
                 if (!df.createNewFile())
                     throw new IOException("File already exists!");
                 FileOutputStream file = new FileOutputStream(df);
                 InputStream stream = connection.getInputStream();

                 while(true)
                 {
                     byte[] buffer = new byte[BufferSize];
                     int read = stream.read(buffer);
                     if (read == -1)
                         break;
                     file.write(buffer, 0, read);

                     downloaded += read;
                     while (progress.getStep() < (downloaded * 100) / size)
                         progress.step(download.Filename);
                 }

                 file.close();
                 stream.close();
                 connection.disconnect();
             }
             catch (IOException ioe)
             {
                 Orion.log.error("Failed to download!");
                 Orion.log.error(ioe);
             }
             catch (Exception e)
             {
                 Orion.log.error(e);
             }
             finally
             {
                 if (progress != null)
                 {
                     while(progress.getSteps() != progress.getStep())
                         progress.step("Failed to download!");
                     ProgressManager.pop(progress);
                 }
             }
         }
         ftd.clear();
    }

    private class DownloadFile
    {
        String Url;
        String Filename;

        DownloadFile(String url, String filename)
        {
            Url = url;
            Filename = filename;
        }
    }
}
