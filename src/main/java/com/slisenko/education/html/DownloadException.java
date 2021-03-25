package com.slisenko.education.html;

/**
 * Exception class used in case of errors when downloading a page to a file
 */
public class DownloadException extends Exception {

    public DownloadException(String message, Throwable e) {

        super(message, e);
    }
}
