package com.DatafromGoogleDrive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetFileIDsFromGoogleDrive {

    private static final String APPLICATION_NAME = "DocfromGoogle";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_READONLY);
    private static final String USER_EMAIL = "amolharde98@gmail.com"; // Replace with user's email address

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // Build the HTTP transport and credentials
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);

        // Build the Drive API client
        Drive drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // List all files in the user's Google Drive account
        List<File> files = new ArrayList<>();
        String nextPageToken = null;
        do {
            FileList fileList = drive.files().list()
                    .setQ("trashed=false") // Exclude deleted files
                    .setFields("nextPageToken, files(id)") // Only fetch file IDs
                    .setPageToken(nextPageToken)
                    .execute();
            files.addAll(fileList.getFiles());
            nextPageToken = fileList.getNextPageToken();
        } while (nextPageToken != null);

        // Print the file IDs
        System.out.printf("File IDs for user %s:\n", USER_EMAIL);
        for (File file : files) {
            System.out.println(file.getId());
        }
    }

    /**
     * Authorize the HTTP transport with the user's credentials
     */
    private static Credential authorize(HttpTransport httpTransport) throws IOException {
        // Load the client secrets JSON file from the current directory
        InputStream inputStream = GetFileIDsFromGoogleDrive.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));

        // Build the OAuth2 credentials
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(clientSecrets)
                .build();
        credential.setAccessToken("ACCESS_TOKEN"); // Replace with user's access token
        credential.setRefreshToken("REFRESH_TOKEN"); // Replace with user's refresh token
        return credential;
    }
}
