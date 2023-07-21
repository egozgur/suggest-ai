package com.reddlyne.suggestai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class SuggestaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuggestaiApplication.class, args);
	}

}

