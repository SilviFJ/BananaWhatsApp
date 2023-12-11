package com.banana.bananawhatsapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({ReposConfig.class, ControllerConfig.class})
@PropertySource("classpath:config.properties")
//@ComponentScan(basePackages = {"com.dxc.mypersonalbankapi.persistencia", "com.dxc.mypersonalbankapi.controladores"})
public class SpringConfig {
    /*@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }*/
}
