package com.mzc.com.singletable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig {
	
	@Bean
	DynamoDbClient dynamoDbClient() {
		return DynamoDbClient.builder()
				.credentialsProvider(ProfileCredentialsProvider.create("mzc"))
				.region(Region.CA_CENTRAL_1)
				.build();
	}
	
	@Bean
	DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
		return DynamoDbEnhancedClient.builder()
				.dynamoDbClient(dynamoDbClient)
				.build();
	}
}
