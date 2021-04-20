package com.slisenko.education;

import com.slisenko.education.html.DownloadException;
import com.slisenko.education.html.PageDownloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DemoPageDownloader {

    public static void main(String args[]) {

        if (args.length >= 2) {

            // Downloading a page to a file
            PageDownloader pd = new PageDownloader();
            try {
                pd.download(args[0], args[1]);
            } catch (DownloadException e) {
                System.out.println(e.getMessage());
                System.out.println("Page download failed");
                return;
            }

            // Convert the received file to a string
            String page;
            try {
                page = pd.fileToString(args[1]);
            } catch (DownloadException e) {
                System.err.println(e.getMessage());
                return;
            }

            // Parsing links to replace them with filenames
            Map<String, String> names = pd.parseFileNames(page);

            if (!names.isEmpty()) {
                int count = 0;
                for (Map.Entry<String, String> entry : names.entrySet()) {
                    count++;
                    System.out.print("url " + count + ": " + entry.getKey());
                    System.out.println(", to file: " + entry.getValue());
                }
            }

            System.out.format("The page %s was successfully downloaded to a file %s", args[0], args[1]);
        } else {
            System.out.println("example: DemoPageDownloader <link> <filename>");
        }
    }
}
