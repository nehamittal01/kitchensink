package org.jboss.eap.quickstarts.kitchensink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableConfigurationProperties
@ComponentScan(value = {"org.jboss.eap.quickstarts.kitchensink.*"})

public class KitchensinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(KitchensinkApplication.class, args);
	}
}
