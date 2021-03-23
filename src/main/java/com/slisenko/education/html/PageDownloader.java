package com.slisenko.education.html;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class for downloading resources from the internet
 */
public class PageDownloader {

    private final int SIZE_BUF = 100000;

    /**
     * Downloads a resource from the internet and saves it in a file
     * @param link Link to the resource
     * @param path File path
     * @return Returns true if successful, otherwise returns false
     */
    public void download(String link, String path) throws DownloadException {

        URL url;

        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            throw new DownloadException("Error in the resource link");
        }

        try (InputStream reader = new BufferedInputStream(url.openStream());
             OutputStream writer = new BufferedOutputStream(new FileOutputStream(path));) {

            int sizeReceived;
            byte[] buf = new byte[SIZE_BUF];

            while ((sizeReceived = reader.read(buf)) > 0) {
                writer.write(buf, 0, sizeReceived);
            }
        } catch (IOException e) {
            throw new DownloadException("Page loading error");
        }
    }
}
