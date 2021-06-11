package it.drop.mcroni.dwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class DwtApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DwtApplication.class);

	public static void main(String[] args) {
		LOGGER.debug("start app");
		SpringApplication.run(DwtApplication.class, args);
	}

}
