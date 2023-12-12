package com.banana.bananawhatsapp.config;
import com.banana.bananawhatsapp.persistencia.*;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.sql.SQLException;
import java.util.Set;

@Configuration
public class ReposConfig {
    @Autowired
    Environment env;

    @Value("${db_url}")
    String dbUrl;

    @Bean
    public IUsuarioRepository getUsuarioById () throws Exception {
        System.out.println(dbUrl);
        UsuarioRepo repo = new UsuarioRepo();
        repo.setDb_url(dbUrl);
        return repo;
    }
    @Bean
    public IMensajeRepository getSMSById() throws Exception {
        MensajeRepo repo = new MensajeRepo();
        repo.setDb_url(dbUrl);
        return repo;
    }
}
