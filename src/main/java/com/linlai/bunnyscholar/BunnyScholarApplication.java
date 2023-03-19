package com.linlai.bunnyscholar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@SpringBootApplication
@MapperScan("com.linlai.bunnyscholar.mapper")
public class BunnyScholarApplication {

    public static void main(String[] args) {
        SpringApplication.run(BunnyScholarApplication.class, args);
        System.out.println("===================");
        System.out.println("===start success===");
        System.out.println("===================");
    }

}
