package ru.clevertec.ecl.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ecl.aspect.LogAspect;

@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class LogConfig {

    @Bean
    public LogAspect loggableAspect() {
        return new LogAspect();
    }
}
