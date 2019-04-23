package com.feifei.licai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
@SpringBootApplication
@MapperScan("com.feifei.licai.mapper")
public class LicaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicaiApplication.class, args);
	}

}
