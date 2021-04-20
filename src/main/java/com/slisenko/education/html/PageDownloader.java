package com.slisenko.education.html;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
            throw new DownloadException("Error in the resource link " + link, e);
        }

        try (InputStream reader = new BufferedInputStream(url.openStream());
             OutputStream writer = new BufferedOutputStream(new FileOutputStream(path));) {

            int sizeReceived;
            byte[] buf = new byte[SIZE_BUF];

            while ((sizeReceived = reader.read(buf)) > 0) {
                writer.write(buf, 0, sizeReceived);
            }
        } catch (IOException e) {
            throw new DownloadException("Page loading error from link " + link + " to file " + path, e);
        }
    }

    /**
     * Reads the file to a string
     * @param path File path
     * @return A string with the contents of the file
     * @throws DownloadException
     */
    public String fileToString(String path) throws DownloadException {

        StringBuilder sb = new StringBuilder();

        try (BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String s;
            while ((s = fin.readLine()) != null) {
                sb.append(s + System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new DownloadException("File " + path + " not found", e);
        } catch (IOException e) {
            throw new DownloadException("Error reading the file " + path, e);
        }
        return sb.toString();
    }

    /**
     * Search for links to resources such as js, jpg, jpeg, gif, png, bmp, ico, css
     * @param page Html page
     * @return Map with links and their corresponding file names
     */
    public Map<String, String> parseFileNames(String page) {

        String regex = "\"(https?://[\\w./-]*?([\\w.-]+(.js|.jpg|.jpeg|.gif|.png|.bmp|.ico|.css)))\"";

        Map<String, String> names = new HashMap<>();
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(page);
            while (matcher.find()) {
                names.put(matcher.group(1), matcher.group(2));
            }
        } catch (PatternSyntaxException e) {
            System.err.println("Incorrect regular expression: " + e.getMessage());
        }
        return names;
    }
}
