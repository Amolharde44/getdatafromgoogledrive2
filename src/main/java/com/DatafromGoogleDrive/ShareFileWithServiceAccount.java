package com.DatafromGoogleDrive;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;

public class ShareFileWithServiceAccount {
    private static final String SERVICE_ACCOUNT_JSON_FILE_PATH = "src/main/resources/service-account.json";
    private static final String FILE_ID = "11VqPFTuwcWxrF5Vqflqp8sypRUlglpCs";
    
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // Load the service account credential from the JSON key file
        GoogleCredential credentials = GoogleCredential.fromStream(new FileInputStream(new File(SERVICE_ACCOUNT_JSON_FILE_PATH)))
            .createScoped(Collections.singleton(DriveScopes.DRIVE));
        
        // Create a Drive client using the service account credentials
        Drive drive = new Drive.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JacksonFactory.getDefaultInstance(),
            credentials)
            .setApplicationName("My Application Name")
            .build();
        
        // Create a permission object with the desired sharing settings
        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        
        // Share the file with the new permission
        drive.permissions().create(FILE_ID, permission).execute();
        
        System.out.println("File shared successfully!");
    }
}
