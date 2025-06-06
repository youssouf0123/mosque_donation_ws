package com.mosque;

import com.mosque.configuration.JpaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = { "com.mosque" })
public class MosqueDonationApp {

	Logger logger = LoggerFactory.getLogger(MosqueDonationApp.class);

	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(MosqueDonationApp.class, args);
	}

}