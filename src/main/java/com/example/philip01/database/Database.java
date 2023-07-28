package com.example.philip01.database;

import com.example.philip01.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.logging.Logger;

@Configuration
public class Database {
    @Autowired
    UserRepository userRepository;
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
            }
        };
    }
}
