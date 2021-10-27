package com.waracle.cakemgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CakeManagerClientApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CakeManagerClientApplication.class, args);
    }

}
