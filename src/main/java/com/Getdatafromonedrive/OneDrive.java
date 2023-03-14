package com.Getdatafromonedrive;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OneDrive{
    
    public static void main(String[] args) throws Exception {
    	String clientId = "2b2cab93-3d96-4200-890d-4b8b02a0c298";
    	String clientSecret ="w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
    	String scope = "https://graph.microsoft.com/.default";

    	HttpClient httpClient = HttpClient.newHttpClient();

    	String requestBody = "client_id=" + clientId + "&client_secret=" + clientSecret + "&scope=" + scope + "&grant_type=client_credentials";

    	HttpRequest request = HttpRequest.newBuilder()
    	  .uri(URI.create("https://login.microsoftonline.com/5ed5dd64-380b-42e4-bbaf-7418a594b0ff/oauth2/v2.0/token"))
    	  .header("Content-Type", "application/x-www-form-urlencoded")
    	  .POST(HttpRequest.BodyPublishers.ofString(requestBody))
    	  .build();
    	  
    	HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    	String accessToken = null;

    	if (response.statusCode() == 200) {
    	  JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
    	  accessToken = json.get("access_token").getAsString();
    	  System.out.println(accessToken+"********");
    	} else {
    	  // handle error
    	}

        // The access token for the user's OneDrive account
    	System.out.println(accessToken);
        // accessToken = "insert-access-token-here";

        // The ID of the OneDrive drive that the file is located in
        String driveId ="me";

        // The ID of the file to download
        String fileId = "01NZWTMZF6QCZP5CWI2VDYAFV5A4IOSJQV";

        // The endpoint URL to download the file
String endpoint = "https://graph.microsoft.com/v1.0/drives/" + driveId + "/items/" + fileId + "/content";
System.out.println(endpoint);

        // Make a GET request to the endpoint and download the file
//HttpClient client = HttpClient.newBuilder().build();
//HttpRequest request1 = HttpRequest.newBuilder()
//        .uri(new URI(endpoint))
//        .header("Authorization", "Bearer " + accessToken)
//        .build();
HttpRequest request1 = HttpRequest.newBuilder()
.uri(new URI(endpoint))
.header("Authorization", "Bearer " + accessToken)
.header("Accept", "application/octet-stream")
.build();
        HttpResponse<InputStream> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofInputStream());
        
        if (response.statusCode() == 200) {
            // Write the file to disk
            InputStream inputStream = response1.body();
            String home = System.getProperty("user.home");
            FileOutputStream outputStream = new FileOutputStream((home + "/Downloads/"+"Q1FY22 Result Note.pdf"));
            byte[] buffer = new byte[100000];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            System.out.println("File downloaded successfully.");
        } else {
            // Handle the error
            System.out.println("Error: " + response.statusCode() + " " + response.body());
        }
    }
}
