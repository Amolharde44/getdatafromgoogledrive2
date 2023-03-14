package com.DatafromOneDrive;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.IClientCredential;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class OneDriveFileDownloader {
	private static final String CLIENT_ID = "2b2cab93-3d96-4200-890d-4b8b02a0c298";
			
	private static final String CLIENT_SECRET = "w-X8Q~33MND7nryH9NQp3SHbzD2CPwXxd8W2sakb";
	private static final String TENANT_ID = "5ed5dd64-380b-42e4-bbaf-7418a594b0ff";
	private static final String FILE_NAME = "Q1FY22 Result Note.pdf";

	public static void main(String[] args) throws Exception {
		// Step 1: Obtain an access token for the Microsoft Graph API
		IClientCredential credential = ClientCredentialFactory.createFromSecret(CLIENT_SECRET);
		IAuthenticationResult result = ConfidentialClientApplication.builder(CLIENT_ID, credential)
				.authority(String.format("https://login.microsoftonline.com/%s/", TENANT_ID)).build()
				.acquireToken(ClientCredentialParameters
						.builder(Collections.singleton("https://graph.microsoft.com/.default")).build())
				.get();
		String accessToken = result.accessToken();
		System.out.println(accessToken);
		// Step 2: Get the item ID of the file you want to download
		URL url = new URL(String.format("https://graph.microsoft.com/v1.0/me/drive/root:/Documents/%s", FILE_NAME));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Bearer " + accessToken);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		@SuppressWarnings("deprecation")
		JsonParser parser = new JsonParser();
		String itemId;
		try (InputStream inputStream = connection.getInputStream()) {
			@SuppressWarnings("deprecation")
			JsonElement element = parser.parse(new java.io.InputStreamReader(inputStream));
			itemId = element.getAsJsonObject().get("id").getAsString();
		}
		System.out.println(itemId + "itemId");
		// Step 3: Download the file
		url = new URL(String.format("https://graph.microsoft.com/v1.0/me/drive/items/%s/content", itemId));
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Bearer " + accessToken);
		connection.setDoOutput(true);
		String home = System.getProperty("user.home");

		try (InputStream inputStream = connection.getInputStream();

				FileOutputStream outputStream = new FileOutputStream(home + "/Downloads/" + FILE_NAME)) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
	}
}
