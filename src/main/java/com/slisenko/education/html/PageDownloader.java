package com.slisenko.education.html;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Class for downloading resources from the internet
 */
public class PageDownloader {

    private final int SIZE_BUF = 256;

    /**
     * Downloads a resource from the internet and saves it in a file
     * @param urlStr Link to the resource
     * @param pathStr File path
     * @return Returns true if successful, otherwise returns false
     */
    public boolean download(String urlStr, String pathStr) {

        URL url;

        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            System.err.println(this.getClass().getSimpleName() + ":MalformedURLException:" + e.getMessage());
            return false;
        }

        try (InputStream reader = new BufferedInputStream(url.openStream());
             OutputStream writer = new BufferedOutputStream(new FileOutputStream(pathStr));) {

            int sizeReceived;
            byte[] buf = new byte[SIZE_BUF];

            while ((sizeReceived = reader.read(buf)) > 0) {
                writer.write(buf, 0, sizeReceived);
            }
        } catch (IOException e) {
            System.err.println(this.getClass().getSimpleName() + ":IOException:" + e.getMessage());
            return false;
        }

        return true;
    }
}
