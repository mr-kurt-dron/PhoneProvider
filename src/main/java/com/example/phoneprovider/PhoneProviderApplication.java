package com.example.phoneprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PhoneProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneProviderApplication.class, args);
    }
}
