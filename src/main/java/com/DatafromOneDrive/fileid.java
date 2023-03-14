package com.DatafromOneDrive;

import com.azure.identity.AuthorizationCodeCredential;
import com.azure.identity.AuthorizationCodeCredentialBuilder;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;

public class fileid {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		String clientId = "2b2cab93-3d96-4200-890d-4b8b02a0c298";
		String clientSecret = "w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
		String[] scopes = { "https://graph.microsoft.com/.default" };
		String tenant="5ed5dd64-380b-42e4-bbaf-7418a594b0ff";
		final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
		        .clientId(clientId)
		        .clientSecret(clientSecret)
		        .tenantId(tenant)
		        .build();

		final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(clientSecretCredential);

		final GraphServiceClient graphClient =
		  GraphServiceClient
		    .builder()
		    .authenticationProvider(tokenCredentialAuthProvider)
		    .buildClient();

		//final User me = graphClient.me().buildRequest().get();
		final Drive result = graphClient
				  .me()
				  .drive()
				  .buildRequest()
				  .get();
				System.out.println("Found Drive " + result.id);
		*/
//		String clientId = "2b2cab93-3d96-4200-890d-4b8b02a0c298";
//		String clientSecret = "w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
//		String[] scopes = { "https://graph.microsoft.com/.default" };
//		String tenant="5ed5dd64-380b-42e4-bbaf-7418a594b0ff";
//		final AuthorizationCodeCredential authCodeCredential = new AuthorizationCodeCredentialBuilder()
//		        .clientId(clientId)
//		        .clientSecret(clientSecret) //required for web apps, do not set for native apps
//		        .authorizationCode(authorizationCode)
//		        .redirectUrl("http://localhost:8080")
//		        .build();
//
//		final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(authCodeCredential);
//
//		final GraphServiceClient graphClient =
//		  GraphServiceClient
//		    .builder()
//		    .authenticationProvider(tokenCredentialAuthProvider)
//		    .buildClient();
//
//		final User me = graphClient.me().buildRequest().get();
//
}
}
