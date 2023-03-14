package com.DatafromOneDrive;
import java.util.Arrays;
import java.util.List;

import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.httpcore.HttpClients;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.OkHttpClient;
public class onedrivefiledata {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		

		final List<String> scopes = Arrays.asList("User.Read");

		final InteractiveBrowserCredential credential =
		    new InteractiveBrowserCredentialBuilder()
		        .clientId("76588ccb-4c9a-4927-94f7-9e7bf8de66ae")
		        .redirectUrl("http://localhost:8080")
		        .build();

		final TokenCredentialAuthProvider authProvider =
		    new TokenCredentialAuthProvider(scopes, credential);
		// you can configure any OkHttpClient option and add interceptors
		// Note: com.microsoft.graph:microsoft-graph:3.0 or above is required
		// for a complete description of available configuration options https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/-builder/
		final OkHttpClient httpClient = HttpClients.createDefault(authProvider)
		    .newBuilder()
		    .followSslRedirects(false) // sample configuration to apply to client
		    .build();

		final GraphServiceClient graphServiceClient = GraphServiceClient
		    .builder()
		    .httpClient(httpClient)
		    .buildClient();
		
	}

}
