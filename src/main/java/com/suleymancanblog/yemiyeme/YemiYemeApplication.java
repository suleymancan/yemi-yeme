package com.suleymancanblog.yemiyeme;

import com.suleymancanblog.yemiyeme.config.PropertyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PropertyConfig.class)
public class YemiYemeApplication {


	public static void main(String[] args) {
		SpringApplication.run(YemiYemeApplication.class, args);
	}



}
