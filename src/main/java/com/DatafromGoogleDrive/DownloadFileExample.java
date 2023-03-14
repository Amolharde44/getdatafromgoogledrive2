package com.DatafromGoogleDrive;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileExample {
    public static void main(String[] args) {
        String fileUrl = "https://drive.google.com/uc?export=download&id=1Td2nfvdoFMpVcWgUjYRzMKYqDUhdFxAH";
        String savePath = "C:/Users/Amol/Downloads/TC.pdf";

        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(savePath);
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                System.out.println("File downloaded successfully.");
            } else {
                System.out.println("Error: " + responseCode);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
