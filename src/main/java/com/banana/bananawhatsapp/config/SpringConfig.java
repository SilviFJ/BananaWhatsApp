package com.banana.bananawhatsapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan;

@Configuration
/*@Import({ReposConfig.class, ControllerConfig.class})*/
@PropertySource("application.properties")
@ComponentScan(basePackages = "com.banana.bananawhatsapp.persistencia")
public class SpringConfig {
    /*@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }*/
}
