package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.persistencia.*;
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
    public IUsuarioRepository getClientById () throws Exception {
        System.out.println(dbUrl);
        IUsuarioRepository repo = new IUsuarioRepository() {
            @Override
            public Usuario crear(Usuario usuario) throws SQLException {
                return null;
            }

            @Override
            public Usuario actualizar(Usuario usuario) throws SQLException {
                return null;
            }

            @Override
            public boolean borrar(Usuario usuario) throws SQLException {
                return false;
            }

            @Override
            public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
                return null;
            }

            @Override
            public String getdb_url() {
                return null;
            }
        };
        return repo;
    }
}
