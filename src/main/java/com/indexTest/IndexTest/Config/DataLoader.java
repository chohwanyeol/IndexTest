package com.indexTest.IndexTest.Config;

import com.indexTest.IndexTest.Entity.UserNoIndex;
import com.indexTest.IndexTest.Entity.UserWithIndex;
import com.indexTest.IndexTest.repository.UserNoIndexRepository;
import com.indexTest.IndexTest.repository.UserWithIndexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(UserNoIndexRepository userNoIndexRepository, UserWithIndexRepository userWithIndexRepository) {
        return args -> {
            if (userNoIndexRepository.count() == 0) {
                IntStream.rangeClosed(1, 100_000).forEach(i -> {
                    UserNoIndex userNoIndex = UserNoIndex.builder()
                            .name("User" + i)
                            .email("test" + i + "@example.com")
                            .age(20 + (i % 30))
                            .build();
                    userNoIndexRepository.save(userNoIndex);
                });
            }
            if (userWithIndexRepository.count() == 0) {
                IntStream.rangeClosed(1, 100_000).forEach(i -> {
                    UserWithIndex userWithIndex = UserWithIndex.builder()
                            .name("User" + i)
                            .email("test" + i + "@example.com")
                            .age(20 + (i % 30))
                            .build();
                    userWithIndexRepository.save(userWithIndex);
                });
            }
        };
    }
}
