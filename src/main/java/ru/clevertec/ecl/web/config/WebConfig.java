package ru.clevertec.ecl.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
@ComponentScan("ru.clevertec.ecl")
public class WebConfig implements WebMvcConfigurer {

    private final static String FILE_BASENAME = "errorMessage";
    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.addBasenames(FILE_BASENAME);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
