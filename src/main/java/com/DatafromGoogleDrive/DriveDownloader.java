package com.DatafromGoogleDrive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class DriveDownloader {
    private static final String APPLICATION_NAME = "Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build the HTTP transport and create a new authorized API client
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = getCredentials(httpTransport);

        Drive drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Set the ID of the file to download
        String fileId = "1pppEeXI9WZ5dTWFXADB-X_DrJWNv-bMk";

        // Create a file output stream to save the downloaded file
        
        String home=System.getProperty("user.home");
        String FileName="Q4FY21 Result Note.pdf";
       
        java.io.File localFile = new java.io.File(home+"/Downloads/"+FileName);
        FileOutputStream outputStream = new FileOutputStream(localFile);

        // Download the file from Google Drive and save it to the local file
        drive.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);

        // Close the output stream
        outputStream.close();

        System.out.println("File downloaded to: " + localFile.getAbsolutePath());
    }

    /**
     * Creates an authorized Credential object.
     * @param httpTransport The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    @SuppressWarnings("deprecation")
	private static Credential getCredentials(NetHttpTransport httpTransport) throws IOException {
        // Load client secrets
        InputStream in = DriveDownloader.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleCredential credential = GoogleCredential.fromStream(in, httpTransport, JSON_FACTORY);

        // Return the credential
        return credential.createScoped(SCOPES);
    }
}
