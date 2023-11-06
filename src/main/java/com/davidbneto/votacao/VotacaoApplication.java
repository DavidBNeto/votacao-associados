package com.davidbneto.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy
public class VotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotacaoApplication.class, args);
	}

}
