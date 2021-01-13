package com.wry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wry.mapper")
public class AntDesignVueAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntDesignVueAdminApplication.class, args);
	}

}
