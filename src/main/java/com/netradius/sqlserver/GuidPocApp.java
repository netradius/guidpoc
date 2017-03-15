package com.netradius.sqlserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

/**
 * Runs the application.
 *
 * @author Abhinav Nahar
 */
@SpringBootApplication
public class GuidPocApp {
	public static void main(String[] args) {
		SpringApplication.run(GuidPocApp.class, args);
	}
}