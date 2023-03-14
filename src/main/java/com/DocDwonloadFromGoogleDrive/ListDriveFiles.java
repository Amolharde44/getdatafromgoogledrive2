package com.DocDwonloadFromGoogleDrive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ListDriveFiles {

    private static final String APPLICATION_NAME = "Get File ID";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "token1";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = ListDriveFiles.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String... args) throws IOException, GeneralSecurityException, SQLException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs of all files in the user's Google Drive.
        String pageToken = null;
        do {
            FileList result = service.files().list()
                    .setQ("trashed=false") // exclude trashed files
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                for (File file : files) {
                    System.out.printf("File ID: %s, File Name: %s\n", file.getId(), file.getName());
                    String url = "jdbc:sqlserver://localhost:1433;databaseName=Source Documnet";
                    String user = "Anemoi";
                    String password = "Anemoi@123";
                    Connection conn = DriverManager.getConnection(url, user, password);
                    String sql = "INSERT INTO googledrivemetadata (file_id, file_name,Serviceaccountname) VALUES (?, ?,?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    Map<String, String> filedetails=new  HashMap<>();
                    
                    String file_name=file.getName();
                    String file_id=file.getId();
                    
                    filedetails.put(file_id,file_name);
                    String Serviceaccountname="amolharde98@symmetric-hash-378505.iam.gserviceaccount.com";
                   // String baseUrl = "https://drive.google.com/uc?export=download&id=";
                   // System.out.println(filedetails+"filedetails");
                    for (Entry<String, String> entry : filedetails.entrySet()) {
                    	statement.setString(1, entry.getKey());
                    	statement.setString(2, entry.getValue());
                    	//statement.setString(3, baseUrl+entry.getKey());
                    	statement.setString(3, Serviceaccountname);
                    	statement.executeUpdate();
                    }

                }	
                	
                      
                      System.out.println("file id and file name insert in database successfully............................");
                      

                
    }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        
    
        
}
}
