package com.example.demo.lookup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplicationLookup {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplicationLookup.class, args);

		SingletonBean1 singletonBean = context.getBean(SingletonBean1.class);
		singletonBean.usePrototype();
		singletonBean.usePrototype();
	}

}
