package com.khomsi.grid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GridApplication {

	public static void main(String[] args) {
		SpringApplication.run(GridApplication.class, args);
	}

}
