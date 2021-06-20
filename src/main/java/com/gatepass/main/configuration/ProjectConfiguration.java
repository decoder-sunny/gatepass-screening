package com.gatepass.main.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gatepass.main.security.RoleAuthentication;

@Configuration
@EnableTransactionManagement
public class ProjectConfiguration {

    
    
    @Value("${jsa.aws.access_key_id}")
	private String awsId;

	@Value("${jsa.aws.secret_access_key}")
	private String awsKey;
	
	@Value("${jsa.s3.region}")
	private String region;

 	
    
    @Bean
   	public AmazonS3 s3client() {		
   		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
   		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
   								.withRegion(Regions.fromName(region))
   		                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
   		                        .build();
   		
   		return s3Client;
   	}   
    
    @Bean
    public RoleAuthentication getRole() {
    	return new RoleAuthentication();
    }
    
    @Bean
    RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .build();

        return RestClients.create(clientConfiguration)
            .rest();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
