package de.fhkiel.advancedjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "de.fhkiel.advancedjava.persistence")
@EntityScan(basePackages = "de.fhkiel.advancedjava.domain")
@EnableScheduling
@EnableSwagger2
public class PublicTransportationPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicTransportationPlatformApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()                 .apis(RequestHandlerSelectors.basePackage("de.fhkiel.advancedjava.controller"))
				.paths(PathSelectors.any())
				.build();
	}
}
