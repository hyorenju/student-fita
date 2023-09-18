package vn.edu.vnua.fita.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.vnua.fita.student.common.ThreadsNumConstant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {
    @Bean
    public ExecutorService executor(){
        ExecutorService executor = Executors.newFixedThreadPool(ThreadsNumConstant.MAX_THREADS);
        return executor;
    }
}
