package com.Getdatafromonedrive;


import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.auth.confidentialClient.ClientCredentialProvider;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;

public class Downloadfilefromfileid {
    
   
    
    public static void main(String[] args) throws SQLException {
        
   
        
        final List<String> scopes = Arrays.asList("https://graph.microsoft.com/.default");
	    final String clientId = "2b2cab93-3d96-4200-890d-4b8b02a0c298";//application (client id)
	    //final String clientSecret = "w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
	    //final String tenantId = "5ed5dd64-380b-42e4-bbaf-7418a594b0ff";
	    String username="amol.harde@anemoisoftware.com.au";
	    String password="Anemoi@4444";
	    
		final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
		        .clientId(clientId)
		        .username(username)
		        .password(password)
		        .build();

		final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, usernamePasswordCredential);

		final GraphServiceClient graphClient =
		  GraphServiceClient
		    .builder()
		    .authenticationProvider(tokenCredentialAuthProvider)
		    .buildClient();
        
        // Download the file from OneDrive
		   String url = "jdbc:sqlserver://localhost:1433;databaseName=Source Documnet";
	        String usernamedb = "Anemoi";
	        String passworddb = "Anemoi@123";
	         Connection conn = DriverManager.getConnection(url, usernamedb, passworddb);
	            
	            // Execute a SQL query to retrieve file IDs and names from the OneDrive table
	            String sql = "SELECT file_id, file_name FROM onedrive";
	            Statement stmt = conn.createStatement();
	                ResultSet rs = stmt.executeQuery(sql) ;
	                    while (rs.next()) {
	                        String fileId = rs.getString("file_id");
	                        String fileName = rs.getString("file_name");
	                        System.out.println("File ID: " + fileId + ", File Name: " + fileName);
	      
        try {
            InputStream stream = graphClient
                    .me()
                    .drive()
                    .items(fileId)
                    .content()
                    .buildRequest()
                    .get();
            String home = System.getProperty("user.home");
            
            FileOutputStream output = new FileOutputStream(home + "/Downloads/"+fileName);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = stream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.close();
            System.out.println("File downloaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("File download failed.");
        }
    }
	        }
    }

	            
	       
	     
