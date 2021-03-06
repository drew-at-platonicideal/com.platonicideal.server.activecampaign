package com.platonicideal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.platonicideal" })
@EnableJpaRepositories(basePackages = { "com.platonicideal" })
public class ServerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(ServerApplication.class);
        springApplication.run(args);
    }

}
