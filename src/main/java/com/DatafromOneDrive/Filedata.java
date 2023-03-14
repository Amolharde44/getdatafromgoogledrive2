package com.DatafromOneDrive;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.httpcore.HttpClients;
import com.microsoft.graph.models.BaseItem;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.DriveItemSearchCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.OkHttpClient;
public class Filedata {

	
	
		
	public static void main(String[] args) {
        

//	final List<String> scopes = Arrays.asList("User.Read");
//	
//	final InteractiveBrowserCredential credential =
//	    new InteractiveBrowserCredentialBuilder()
//	        .clientId("2b2cab93-3d96-4200-890d-4b8b02a0c298")
//	        .redirectUrl("http://localhost:8080")
//	        .build();
//
//	final TokenCredentialAuthProvider authProvider =
//	    new TokenCredentialAuthProvider(scopes, credential);
//	// you can configure any OkHttpClient option and add interceptors
//	// Note: com.microsoft.graph:microsoft-graph:3.0 or above is required
//	// for a complete description of available configuration options https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/-builder/
//	final OkHttpClient httpClient = HttpClients.createDefault(authProvider)
//	    .newBuilder()
//	    .followSslRedirects(false) // sample configuration to apply to client
//	    .build();
		//final List<String> scopes = Arrays.asList("User.Read");
		final List<String> scopes = Arrays.asList("https://graph.microsoft.com/.default");
	    final String clientId = "2b2cab93-3d96-4200-890d-4b8b02a0c298";
	    final String clientSecret = "w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
	    final String tenantId = "5ed5dd64-380b-42e4-bbaf-7418a594b0ff";

	    final ClientSecretCredential credential = new ClientSecretCredentialBuilder()
	        .clientId(clientId)
	        .clientSecret(clientSecret)
	        .tenantId(tenantId)
	        .build();

	    final TokenCredentialAuthProvider authProvider =
	        new TokenCredentialAuthProvider(scopes, credential);
	    final OkHttpClient httpClient = HttpClients.createDefault(authProvider)
    	    .newBuilder()
	    	    .followSslRedirects(false) // sample configuration to apply to client
	    	    .build();

	final GraphServiceClient graphServiceClient = GraphServiceClient
	    .builder()
	    .httpClient(httpClient)
   .buildClient();
	
	
//	GraphServiceClient graphClient = GraphServiceClient.builder()
//		    .authenticationProvider(authProvider)
//		    .buildClient();

	String fileName = "Q1Y22 Result Note.pdf";
	 // name of the file to search for
	DriveItemCollectionPage searchResults = graphServiceClient
	    .me()
	    .drive()
	    .root()
	    .children()
	    .buildRequest()
	    .filter("name eq '" + fileName + "'")
	    .get();

	if (searchResults.getCurrentPage().size() > 0) {
	    // Get the first search result (assumes the file name is unique)
	    DriveItem file = searchResults.getCurrentPage().get(0);
	    if (file != null) {
	        String fileId =file.id; // get the ID of the file
	        System.out.println(fileId);
	    } else {
	    // File not found
	    	System.out.println("File not found");
	    }
	}
	}
}

