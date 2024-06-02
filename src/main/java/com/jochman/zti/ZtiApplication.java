package com.jochman.zti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ZtiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZtiApplication.class, args);
    }

}
