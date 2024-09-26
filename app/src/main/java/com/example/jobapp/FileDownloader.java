package com.example.jobapp;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FileDownloader {

    private static final String TAG = "FileDownloader";

    /**
     * Starts downloading a file using Android's DownloadManager.
     *
     * @param context   The context from which the method is called.
     * @param fileUrl   The URL of the file to download.
     * @param fileName  The name to save the file as.
     */
    public static void downloadFile(Context context, String fileUrl, String fileName) {
        if (!isValidUrl(fileUrl)) {
            Toast.makeText(context, "Invalid file URL", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Log the file URL for debugging
            Log.d(TAG, "File URL: " + fileUrl);

            Uri uri = Uri.parse(fileUrl);

            // Create a request for downloading the file
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(fileName);
            request.setDescription("Downloading file...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            // Set network types
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

            // Get the DownloadManager system service
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            if (downloadManager != null) {
                long downloadId = downloadManager.enqueue(request);
                Log.d(TAG, "Download started with ID: " + downloadId);
                Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "DownloadManager not available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Download failed", e);
            Toast.makeText(context, "Failed to start download", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if the URL is valid.
     *
     * @param url The URL to check.
     * @return True if the URL is valid, otherwise false.
     */
    private static boolean isValidUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
    }
}
