package com.DatafromGoogleDrive;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Get;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;

public class GoogleDriveApiExample {
  private static final String APPLICATION_NAME = "DocfromGoogle";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  private static final String ACCESS_TOKEN_FILE_PATH = "token_uri";
  private static final String[] SCOPES = {DriveScopes.DRIVE_FILE};

  public static void main(String[] args) throws IOException {
    Credential credential = authorize();
    Drive drive = new Drive.Builder(new NetHttpTransport(), JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();

    String fileId = "12eG_3-n37d6s4XT8ZvgLuFXfE5vYaijd";
    
    String home=System.getProperty("user.home");
    String FileName="Q4FY22 Result Note.pdf";
    OutputStream outputStream = new FileOutputStream(home+"/Downloads/"+FileName);
    drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);
    System.out.println("File downloaded!");
  }

  private static Credential authorize() throws IOException {
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(), JSON_FACTORY, loadClientSecrets(), Arrays.asList(SCOPES))
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(ACCESS_TOKEN_FILE_PATH)))
            .setAccessType("offline")
            .build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
        .authorize("user");
    return credential;
  }

  private static GoogleClientSecrets loadClientSecrets() throws IOException {
    InputStreamReader clientSecretsReader = new InputStreamReader(
        GoogleDriveApiExample.class.getResourceAsStream(CREDENTIALS_FILE_PATH));
    return GoogleClientSecrets.load(JSON_FACTORY, clientSecretsReader);
  }
}
