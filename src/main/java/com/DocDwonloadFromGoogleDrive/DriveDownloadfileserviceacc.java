package com.DocDwonloadFromGoogleDrive;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class DriveDownloadfileserviceacc {

    private static final String SERVICE_ACCOUNT_JSON_FILE_PATH = "src/main/resources/service-account.json";
    private static final String FILE_ID = "1jiiUu2nADBlq6IA8YIdmAQe7fXIjqTo_";
    		
    

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        @SuppressWarnings("deprecation")
		GoogleCredential credentials = GoogleCredential.fromStream(
            new FileInputStream(SERVICE_ACCOUNT_JSON_FILE_PATH))
            .createScoped(Collections.singleton(DriveScopes.DRIVE));
        Drive drive = new Drive.Builder(httpTransport, JacksonFactory.getDefaultInstance(), credentials)
            .setApplicationName("DocfromGoogle").build();
        String home=System.getProperty("user.home");
        
       // String FileName="5th sem.pdf";
        File file = drive.files().get(FILE_ID).execute();
        String FileName=file.getName();
        FileOutputStream outputStream = new FileOutputStream(home+"/Downloads/"+FileName);
        
        drive.files().get(FILE_ID).executeMediaAndDownloadTo(outputStream);
        System.out.println("File downloaded successfully.");
    }
}
