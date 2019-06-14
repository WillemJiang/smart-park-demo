package org.acme.smart.park.camel;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = org.springframework.boot.SpringApplication.run(SpringApplication.class, args);

		CamelSpringBootApplicationController applicationController =
				applicationContext.getBean(CamelSpringBootApplicationController.class);
		applicationController.run();
	}

	

}
