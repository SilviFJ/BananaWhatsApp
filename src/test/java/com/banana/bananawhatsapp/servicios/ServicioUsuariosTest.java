package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})

class ServicioUsuariosTest {

    @Autowired
    IServicioUsuarios servicio;

    @Test
    void dadoUnUsuarioValido_cuandoCrearUsuario_entoncesUsuarioValido() throws Exception {
        Usuario nuevo = new Usuario(null,"Carmen","carmen@dxc.com", LocalDate.now(), true);
        servicio.crearUsuario(nuevo);
        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrearUsuario_entoncesExcepcion() {
        Usuario nuevo = new Usuario(null,"Carmen","com", LocalDate.now(), true);
        assertThrows(UsuarioException.class, () -> {
            servicio.crearUsuario(nuevo);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarUsuario_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarUsuario_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizarUsuario_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizarUsuario_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDesinatarios_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDesinatarios_entoncesExcepcion() {
    }
}