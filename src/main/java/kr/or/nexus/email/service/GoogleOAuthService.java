package kr.or.nexus.email.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

@Service
public class GoogleOAuthService {
	private static final String APPLICATION_NAME = "Gmail API with Spring";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
	private static final String CREDENTIALS_FILE_PATH = "/client_secret_766774324158-dhn2tm4410ej68cak84671hqgbc27nsb.apps.googleusercontent.com.json";

	private static final String REDIRECT_URI = "http://localhost:8081/mail_client/callback";

	public Gmail getGmailService() throws Exception {
		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Credential credential = getCredentials(HTTP_TRANSPORT);
		return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
	}

	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
		InputStream in = GoogleOAuthService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8081)
				.setCallbackPath("/mail_client/callback").build();
		
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		if (credential.getRefreshToken() != null && credential.getExpiresInSeconds() != null
				&& credential.getExpiresInSeconds() <= 60) {
			boolean refreshed = credential.refreshToken();
			if (refreshed) {
				// Save the refreshed credential
				DataStore<StoredCredential> dataStore = flow.getCredentialDataStore();
				StoredCredential storedCredential = dataStore.get("user");
				storedCredential.setAccessToken(credential.getAccessToken());
				storedCredential.setExpirationTimeMilliseconds(credential.getExpirationTimeMilliseconds());
				dataStore.set("user", storedCredential);
			} else {
				// If refresh failed, force re-authorization
				flow.getCredentialDataStore().delete("user"); // 토큰 에러가 발생한 경우 이 코드를 52번째 줄로 옮겨서 한번 실행
				credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
			}
		} else {

		}
		return credential;
	}
	
}