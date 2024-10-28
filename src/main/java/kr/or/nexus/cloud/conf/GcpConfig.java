package kr.or.nexus.cloud.conf;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class GcpConfig {
	
//	@Bean
//    public Storage storage() {
//        return StorageOptions.getDefaultInstance().getService();
//    }
	
	@Value("${gcp.project.id}")
    private String projectId;

    @Value("${gcp.credentials.location}")
    private Resource credentialsResource;

    @Bean
    public Storage storage() throws IOException {
        // InputStream을 사용하여 credentialsResource를 읽습니다.
        try (InputStream credentialsStream = credentialsResource.getInputStream()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
            return StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(credentials)
                    .build()
                    .getService();
        }
    }
}
