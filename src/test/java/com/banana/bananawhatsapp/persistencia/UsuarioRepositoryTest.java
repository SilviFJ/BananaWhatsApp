package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.banana.bananawhatsapp.config.SpringConfig;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class UsuarioRepositoryTest {

    @Autowired
    IUsuarioRepository repo;
    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws SQLException {
        Usuario nuevo = new Usuario(null, "Pedro Juan", "jj@j.com", LocalDate.now(), true);
        repo.crear(nuevo);
        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() {
        Usuario nuevo = new Usuario(null, "Pedro Juan", "A", LocalDate.now(), true);
        assertThrows(Exception.class, () ->{
          repo.crear(nuevo);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws SQLException{
        Usuario usuarioInicial = new Usuario(null, "Perico Palotes", "perico@dxc.com", LocalDate.now(), true );
        // Se inserta un usuario nuevo en la base de datos para despues modificarlo
        try{
            repo.crear(usuarioInicial);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al crear usuario inicial para modificar");
        }

        // Modificamos los datos del usuario inicial
        usuarioInicial.setNombre("Jose Angel");
        usuarioInicial.setEmail("joseangel@dxc.com");
        try{
            Usuario usuarioActualizado = repo.actualizar(usuarioInicial);
            assertNotNull(usuarioActualizado);
            assertTrue(usuarioActualizado.getId()>0);
            assertEquals("Jose Angel", usuarioActualizado.getNombre());
            assertEquals("joseangel@dxc.com", usuarioActualizado.getEmail());
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Error al actualizar el usuario");
        }
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() throws SQLException {
        Usuario usuarioInicial = new Usuario(null, "Perico Palotes", "perico@dxc.com", LocalDate.now(), true );
        // Se inserta un usuario nuevo en la base de datos para despues modificarlo
        try{
            repo.crear(usuarioInicial);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al crear el usuario inicial");
        }

        // Modificamos los datos del usuario inicial
        usuarioInicial.setNombre(null);
        assertThrows(UsuarioException.class, () -> {
            try {
                repo.actualizar(usuarioInicial);
            } catch (SQLException e){
                e.printStackTrace();
                throw new SQLException("Error al actualizar el usuario");
            }
        });
    }


    @Test
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() throws SQLException {
        // Crear un usuario inicial en la base de datos para luego borrarlo
        Usuario usuarioInicial = new Usuario(null, "Juanita", "juanita@dxc.com", LocalDate.now(), true );

        try {
            repo.crear(usuarioInicial);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al crear el usuario inicial en la base de datos.");
        }

        // Intentar borrar el usuario y verificar que se borre correctamente
        try {
            boolean borradoExitoso = repo.borrar(usuarioInicial);
            assertTrue(borradoExitoso, "El usuario se ha borrado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al intentar borrar el usuario de la base de datos.");
        }
    }

   @Test
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() throws SQLException {
        // Simulamos un usuario inválido (por ejemplo, un usuario sin ID)
        Usuario usuarioInvalido = new Usuario();
        // Esto generará una excepción en el método borrar
        usuarioInvalido.setId(null);
        // Utilizamos assertThrows para verificar si se lanza la excepción esperada
        assertThrows(NullPointerException.class, () -> repo.borrar(usuarioInvalido));
    }
}