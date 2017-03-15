package com.netradius.sqlserver.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Abhinav Nahar
 */
@Configuration
public class DataConfig {

	public enum Action {
		migrate,
		validate,
		reset,
		noop,
	}

	@Value("${db.action}")
	private Action action;

	@Bean
	public Flyway flyway(DataSource dataSource) {
		Flyway flyway = new Flyway();
		flyway.setLocations("classpath:/db/migration");
		flyway.setDataSource(dataSource);

		switch (action) {
			case migrate:
				flyway.migrate();
				break;
			case validate:
				flyway.validate();
				break;
			case reset:
				flyway.clean();
				flyway.migrate();
				break;
			case noop:
				break;
		}
		return flyway;
	}
}
