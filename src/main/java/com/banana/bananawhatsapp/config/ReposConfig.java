package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.persistencia.UsuarioJDBCRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ReposConfig {
    @Value("${db_url}")
    String dbUrl;

    @Bean
    IUsuarioRepository createIUsuarioRepository(){
        UsuarioJDBCRepo repo = new UsuarioJDBCRepo();
        repo.setDb_url(dbUrl);
        return repo;
    }
    /*@Bean
    public IUsuarioRepository getUsuarioById () throws Exception {
        System.out.println(dbUrl);
        UsuarioJDBCRepo repo = new UsuarioJDBCRepo();
        repo.setDb_url(dbUrl);
        return repo;
    }
    /*@Bean
    public IMensajeRepository getSMSById() throws Exception {
        MensajeRepo repo = new MensajeRepo();
        repo.setDb_url(dbUrl);
        return repo;
    }*/
}
