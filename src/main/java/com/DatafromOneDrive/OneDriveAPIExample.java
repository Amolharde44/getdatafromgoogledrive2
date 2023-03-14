package com.DatafromOneDrive;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OneDriveAPIExample {
    public static void main(String[] args) throws IOException, InterruptedException {
    	
    	
    	String clientId = "2b2cab93-3d96-4200-890d-4b8b02a0c298";
    	String clientSecret = "w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
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
    	} else {
    	  // handle error
    	}
    	System.out.println(accessToken);
        //accessToken = "insert-access-token-here";
        String itemPath = "Q1FY22 Result Note.pdf";
        String apiUrl = "https://graph.microsoft.com/v1.0/me/drive/root:/" + itemPath;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String jsonResponse = scanner.useDelimiter("\\A").next();
                scanner.close();
                System.out.println(jsonResponse);
                
                // Extract file ID from response JSON
                String fileId = jsonResponse.split("\"id\":")[1].split(",")[0].replaceAll("\"", "");
                System.out.println("File ID: " + fileId);
            } else {
                System.out.println("Error: " + responseCode);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
