package com.gatepass.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableElasticsearchRepositories(basePackages 
	= "com.gatepass.main.elastic.repository")
@EnableMongoRepositories(basePackages 
	= "com.gatepass.main.mongodb.repository")
public class GatePass2021Application {

	public static void main(String[] args) {
		SpringApplication.run(GatePass2021Application.class, args);
	}

}
