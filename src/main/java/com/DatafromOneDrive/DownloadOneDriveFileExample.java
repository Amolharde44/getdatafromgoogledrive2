package com.DatafromOneDrive;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DownloadOneDriveFileExample {
    private static final String CLIENT_ID ="2b2cab93-3d96-4200-890d-4b8b02a0c298";
    private static final String CLIENT_SECRET = "w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
    private static final String TENANT_ID = "5ed5dd64-380b-42e4-bbaf-7418a594b0ff";
    //private static final String REDIRECT_URI = "insert-redirect-uri-here";
    private static final String DRIVE_ID = "me";
    private static final String FILE_ID = "Q1FY22 Result Note.pdf";
    
    private static final String AUTHORITY = "https://login.microsoftonline.com/" + TENANT_ID + "/oauth2/v2.0/token";
    private static final String GRAPH_URL = "https://graph.microsoft.com/v1.0/drives/" + DRIVE_ID + "/items/" + FILE_ID + "/content";
    
    public static void main(String[] args) {
        try {
            String accessToken = getAccessToken();
            downloadFile(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken() throws IOException {
        URL url = new URL(AUTHORITY);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(("client_id=" + CLIENT_ID).getBytes());
        os.write(("&client_secret=" + CLIENT_SECRET).getBytes());
        os.write("&scope=https://graph.microsoft.com/.default".getBytes());
        os.write("&grant_type=client_credentials".getBytes());
        os.flush();
        os.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JsonObject json = new JsonParser().parse(response.toString()).getAsJsonObject();
        return json.get("access_token").getAsString();
    }

    private static void downloadFile(String accessToken) throws IOException {
        URL url = new URL(GRAPH_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        InputStream inputStream = conn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);

        FileOutputStream fos = new FileOutputStream("C:\\Users\\Amol\\Downloads\\Q1FY22 Result Note.pdf");
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        bos.close();
        fos.close();
        bis.close();
        inputStream.close();

        System.out.println("File downloaded successfully.");
    }
}
