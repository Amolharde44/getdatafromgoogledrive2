package com.DatafromGoogleDrive;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import com.google.auth.Credentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class DriveServiceAccountExample {
    private static final String SERVICE_ACCOUNT_EMAIL = "amolharde98@symmetric-hash-378505.iam.gserviceaccount.com";
    private static final String APPLICATION_NAME = "Data of google";
    private static final String FILE_ID = "1Urj47e1fKWGJazCQ30piFxD5y0tR0C2S";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = "src/main/resources/service-account.json";

    public static void main(String[] args) throws GeneralSecurityException, IOException {
//        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH));
        Drive drive = new Drive.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
            .setApplicationName(APPLICATION_NAME)
            .build();
        
        
        // Load the service account credentials from the JSON key file
//        ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(
//            DriveServiceAccountExample.class.getResourceAsStream("src/main/resources/service-account.json"));
//        String pathToKeyFile = "src/main/resources/service-account.json";
//        InputStream keyFileStream = new FileInputStream(pathToKeyFile);
//        ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(keyFileStream);

        // Authorize the service account and create the Drive client
//        Drive drive = new Drive.Builder(httpTransport, jsonFactory, credentials)
//            .setApplicationName(APPLICATION_NAME)
//            .build();
        
       

        // Get the existing permissions on the file
        PermissionList permissions = drive.permissions().list(FILE_ID).execute();
        System.out.println("Current permissions: " + permissions);

        // Create a new permission for the service account email address with "view" access
        Permission newPermission = new Permission()
            .setType("user")
            .setRole("reader")
            .setEmailAddress(SERVICE_ACCOUNT_EMAIL);
        drive.permissions().create(FILE_ID, newPermission).execute();

        // Get the updated permissions on the file
        permissions = drive.permissions().list(FILE_ID).execute();
        System.out.println("New permissions: " + permissions);
    }
}
