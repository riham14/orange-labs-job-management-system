package com.orange.job.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class OrangeJobManagementSystemApplication {

    public static void main(String[] args) throws IOException {
        /*
        Resource resource = new ClassPathResource("application.properties");
        Properties baseProperties = PropertiesLoaderUtils.loadProperties(resource);
        Resource databasePropertiesFile = new FileSystemResource(baseProperties.getProperty("database.properties"));
        Properties databaseProperties = PropertiesLoaderUtils.loadProperties(databasePropertiesFile);
        databaseProperties.forEach((key, value) -> System.setProperty((String)key, (String)value));

         */

        SpringApplication.run(OrangeJobManagementSystemApplication.class, args);
    }

}
