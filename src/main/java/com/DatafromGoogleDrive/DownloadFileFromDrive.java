package com.DatafromGoogleDrive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;

import com.google.api.services.drive.DriveScopes;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class DownloadFileFromDrive {
    private static final String APPLICATION_NAME ="DocfromGoogle";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "token";
    
    

    private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = DownloadFileFromDrive.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, Collections.singleton(DriveScopes.DRIVE_READONLY))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static  void main(String... args) throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        Drive service = new Drive.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
//                .setApplicationName(APPLICATION_NAME)
//                .build();
        Drive service = new Drive.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
      
        // Base URL of the file to download
        String baseUrl = "https://drive.google.com/uc?export=download&id=";
        System.out.println(baseUrl);

        // ID of the file to download
        String fileId = "1X5v1tjFLjgZ73Mln7NW0fGKZIZ4V6BPF";

        // Construct the URL for downloading the file
        String downloadUrl = baseUrl + fileId;
        System.out.println(downloadUrl);
       

        // Create an HTTP request to download the file
        HttpRequest httpRequest = service.getRequestFactory()
                .buildGetRequest(new GenericUrl(downloadUrl));
        System.out.println("Inside http req");

        // Execute the HTTP request and get the file content
        HttpResponse response = httpRequest.execute();
        System.out.println("executed httprequest....");
       
        InputStream inputStream = response.getContent();
        System.out.println("input stream response got the content...");
        
        
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//          outputStream.write(buffer, 0, bytesRead);
//        }
//        inputStream.close();
//
//        byte[] docContent = outputStream.toByteArray();
//        System.out.println("Document content: " + new String(docContent));
//      }
        
        
        
        
        
     // Create a file output stream to write the file content to a local file
        
        String home=System.getProperty("user.home");
        String FileName="Q4FY22 Result Note.pdf";
        FileOutputStream outputStream = new FileOutputStream(home+"/Downloads/"+FileName);
        System.out.println("out stream executed...");
        service.files().get(fileId)
        .executeMediaAndDownloadTo(outputStream);
        

// Close the output stream
    // outputStream.close();

        // Write the file content to the local file
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
       
        
        
        System.out.println("File downloaded successfully.");
       
//        inputStream.close();
//        outputStream.close();
		
        
    }
    
}
