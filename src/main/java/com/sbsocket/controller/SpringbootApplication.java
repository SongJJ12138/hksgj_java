package com.sbsocket.controller;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootApplication {
	  public static void main(String[] args) {
	        ConfigurableApplicationContext context = SpringApplication.run(SpringbootApplication.class, args);
	        context.getBean(DelongServerSocket.class).start();


	  }
}
