package com.slisenko.education;

import com.slisenko.education.html.DownloadException;
import com.slisenko.education.html.PageDownloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DemoPageDownloader {

    public static void main(String args[]) {

        if (args.length >= 2) {
            PageDownloader pd = new PageDownloader();
            try {
                pd.download(args[0], args[1]);
            } catch (DownloadException e) {
                System.out.println("Page download failed" + e.getMessage());
                return;
            }
            System.out.format("The page %s was successfully downloaded to a file %s", args[0], args[1]);
        } else {
            System.out.println("example: DemoPageDownloader <link> <filename>");
        }
    }
}
