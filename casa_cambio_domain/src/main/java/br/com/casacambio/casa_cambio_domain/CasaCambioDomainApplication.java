package br.com.casacambio.casa_cambio_domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
public class CasaCambioDomainApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CasaCambioDomainApplication.class, args);
	}

}
