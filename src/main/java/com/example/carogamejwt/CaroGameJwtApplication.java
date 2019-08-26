package com.example.carogamejwt;

import com.example.carogamejwt.service.RoomService;
import com.example.carogamejwt.service.UserService;
import com.example.carogamejwt.service.impl.RoomServiceImpl;
import com.example.carogamejwt.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CaroGameJwtApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CaroGameJwtApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CaroGameJwtApplication.class);
    }

    @Bean
    public RoomService roomService() {
        return new RoomServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
