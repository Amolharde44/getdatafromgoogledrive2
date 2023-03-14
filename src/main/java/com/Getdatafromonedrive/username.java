package com.Getdatafromonedrive;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;

public class username {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		
		
			DriveItemCollectionPage files = graphClient.me().drive().root().children().buildRequest().get();
			
			for (int i = 0; i < files.getCurrentPage().size(); i++) {
				
			
			DriveItem file = files.getCurrentPage().get(i);
			String fileId = file.id;
			String fileName = file.name;
			System.out.println("File ID: " + fileId);
			System.out.println("File Name: " + fileName);
		

		    String url = "jdbc:sqlserver://localhost:1433;databaseName=Source Documnet";
		    String user = "Anemoi";
		    String passworddb = "Anemoi@123";

		    try (Connection conn = DriverManager.getConnection(url, user, passworddb);
		            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO OneDrive (file_name,file_id ) VALUES (?, ?)")) {

		         

		         // Set the parameters for the prepared statement
		         pstmt.setString(1, fileName);
		         pstmt.setString(2, fileId);

		         // Execute the prepared statement
		         int rows = pstmt.executeUpdate();

		         System.out.println(rows + " row(s) inserted successfully into OneDrive table.");

		       } catch (SQLException e) {
		         e.printStackTrace();
		       }

		//final User me = graphClient.me().buildRequest().get();
		final Drive result = graphClient
				  .me()
				  .drive()
				  .buildRequest()
				  .get();
				System.out.println("Found Drive " + result.id);
			System.out.println("Drive name\n"+result.name);	
			
			}	
	}
}
