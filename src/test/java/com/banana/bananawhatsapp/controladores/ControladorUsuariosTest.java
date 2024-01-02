package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})

class ControladorUsuariosTest {
    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario nuevo = new Usuario(null, "Pedro Juan", "jj@j.com", LocalDate.now(), true);
        controladorUsuarios.alta(nuevo);
        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0));
    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        Usuario nuevo = new Usuario(null, "Pedro Juan", "A", LocalDate.now(), true);
        assertThrows(Exception.class, () ->{
          controladorUsuarios.alta(nuevo);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws SQLException {
        Usuario usuarioInicial = new Usuario(null, "Perico Palotes", "perico@dxc.com", LocalDate.now(), true );
        // Se inserta un usuario nuevo en la base de datos para despues modificarlo
        controladorUsuarios.alta(usuarioInicial);

        // Modificamos los datos del usuario inicial
        usuarioInicial.setNombre("Jose Angel");
        usuarioInicial.setEmail("joseangel@dxc.com");
        Usuario usuarioActualizado = controladorUsuarios.actualizar(usuarioInicial);
        assertNotNull(usuarioActualizado);
        assertTrue(usuarioActualizado.getId()>0);
        assertEquals("Jose Angel", usuarioActualizado.getNombre());
        assertEquals("joseangel@dxc.com", usuarioActualizado.getEmail());
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() throws SQLException {
        Usuario usuarioInicial = new Usuario(null, "Perico Palotes", "perico@dxc.com", LocalDate.now(), true );
        // Se inserta un usuario nuevo en la base de datos para despues modificarlo
        controladorUsuarios.alta(usuarioInicial);

        // Modificamos los datos del usuario inicial
        usuarioInicial.setNombre(null);
        assertThrows(UsuarioException.class, () -> {
            controladorUsuarios.actualizar(usuarioInicial);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() throws SQLException {
        // Crear un usuario inicial en la base de datos para luego borrarlo
        Usuario usuarioInicial = new Usuario(null, "Juanita", "juanita@dxc.com", LocalDate.now(), true );

        controladorUsuarios.alta(usuarioInicial);

        // Intentar borrar el usuario y verificar que se borre correctamente
        boolean borradoExitoso = controladorUsuarios.baja(usuarioInicial);
        assertTrue(borradoExitoso, "El usuario se ha borrado correctamente.");
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        // Simulamos un usuario inválido (por ejemplo, un usuario sin ID)
        Usuario usuarioInvalido = new Usuario();
        // Esto generará una excepción en el método borrar
        usuarioInvalido.setId(null);
        // Utilizamos assertThrows para verificar si se lanza la excepción esperada
        assertThrows(NullPointerException.class, () -> controladorUsuarios.baja(usuarioInvalido));
    }
}