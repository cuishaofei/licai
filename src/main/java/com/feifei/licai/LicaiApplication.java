package com.feifei.licai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.feifei.licai.mapper")
public class LicaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicaiApplication.class, args);
	}

}
