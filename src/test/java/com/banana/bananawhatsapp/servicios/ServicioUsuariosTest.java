package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

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
        Usuario nuevoUsuario = new Usuario(null,"Cecilia","Cecilia@dxc.com", LocalDate.now(), true);
        Usuario usuarioGuardado = servicio.crearUsuario(nuevoUsuario);
        assertThat(usuarioGuardado, notNullValue());
        assertNotNull(usuarioGuardado.getId());

        boolean borrado = servicio.borrarUsuario(usuarioGuardado);
        assertTrue(borrado);
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarUsuario_entoncesExcepcion() {
        Usuario nuevoInvalido = new Usuario(null,"Cecilia","Cecilia@dxc.com", LocalDate.now(), true);
        assertThrows(NullPointerException.class, () -> {
            servicio.borrarUsuario(nuevoInvalido);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizarUsuario_entoncesUsuarioValido() {
        Usuario usuarioExistente = new Usuario(1,"Juana","juana@j.com", LocalDate.now(), true);
        String nuevoNombre = "Juanita";
        usuarioExistente.setNombre(nuevoNombre);
        Usuario usuarioActualizado = servicio.actualizarUsuario(usuarioExistente);
        assertNotNull(usuarioActualizado);
        assertEquals(nuevoNombre, usuarioActualizado.getNombre());
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizarUsuario_entoncesExcepcion() {
        Usuario usuarioInvalido = new Usuario(null,"Juana","juana@j.com", LocalDate.now(), true);
        assertThrows(NullPointerException.class, () -> {
            servicio.actualizarUsuario(usuarioInvalido);
        });
    }
}