package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.controladores.ControladorUsuarios;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.controladores.ControladorUsuarios;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {
     @Autowired
     private IUsuarioRepository UsuarioControl;
     @Bean
     public ControladorUsuarios alta() {

        return (ControladorUsuarios) UsuarioControl;
     }
}

