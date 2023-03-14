package com.DatafromOneDrive;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.*;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.*;
import java.io.*;

public class OneDriveDownload {
	 public static void main(String[] args) throws Exception {

String clientId = "2b2cab93-3d96-4200-890d-4b8b02a0c298";
String clientSecret = "w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
String[] scopes = { "https://graph.microsoft.com/.default" };

ClientSecretCredential credential = new ClientSecretCredentialBuilder()
    .clientId(clientId)
    .clientSecret(clientSecret)
    .tenantId("5ed5dd64-380b-42e4-bbaf-7418a594b0ff")
    .build();

GraphServiceClient graphClient = GraphServiceClient.builder()
    .authenticationProvider(new TokenCredentialAuthProvider(credential))
    .buildClient();
DriveItemCollectionPage files = graphClient.me().drive().root().children().buildRequest().get();
DriveItem file = files.getCurrentPage().get(0);
String fileId = file.id;
String fileName = file.name;
System.out.println("File ID: " + fileId);
System.out.println("File Name: " + fileName);
}
}
