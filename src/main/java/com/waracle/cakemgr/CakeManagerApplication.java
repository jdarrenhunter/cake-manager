package com.waracle.cakemgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.waracle.cakemgr.servlet")
public class CakeManagerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CakeManagerApplication.class, args);
	}

}
