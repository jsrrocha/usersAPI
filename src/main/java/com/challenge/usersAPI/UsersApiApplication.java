package com.challenge.usersAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.challenge.usersAPI.util.PessoaUtil;

@SpringBootApplication
public class UsersApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(UsersApiApplication.class, args);
		
		PessoaUtil pessoaUtil = applicationContext.getBean(PessoaUtil.class);
		pessoaUtil.callPersistDataEachMinute();
	}

}
