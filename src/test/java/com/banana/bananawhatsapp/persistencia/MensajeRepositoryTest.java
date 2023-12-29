package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
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
class MensajeRepositoryTest {
    @Autowired
    IMensajeRepository repo;
    @Test
    public void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException {
        Usuario remitente = new Usuario(8, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        Usuario destinatario = new Usuario(9, "Juana", "juana@dxc.com", LocalDate.now(), true);
        Mensaje mensaje = new Mensaje(null, remitente, destinatario, "Hola, ¿cómo estás?", LocalDate.now());
        try{
            boolean esValido = mensaje.valido();
            assertTrue(esValido);
        } catch (MensajeException e) {
           throw new MensajeException("Se esperaba un mensaje valido " + e.getMessage());
        }
    }


    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() {
        Usuario remitente = new Usuario(8, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        Usuario destinatario = new Usuario(9, "Juana", "juana@dxc.com", LocalDate.now(), true);
        Mensaje mensaje = new Mensaje(null, remitente, destinatario, "?", LocalDate.now());
        assertThrows(MensajeException.class, () -> {
            try {
                repo.crear(mensaje);
            } catch (SQLException e){
                throw new SQLException("Excepcion SQL al crear el mensaje");
            }
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws SQLException {
        MensajeJDBCRepo mensajeJDBCRepo = new MensajeJDBCRepo();
        mensajeJDBCRepo.setDb_url("jdbc:mysql://localhost/bananawhatsappdb?user=bananauser&password=banana123");
        Usuario usuario = new Usuario(1, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        List<Mensaje> mensajesObtenidos = mensajeJDBCRepo.obtener(usuario);
        assertEquals(2, mensajesObtenidos.size());
        assertEquals("Muy bien! y tu?", mensajesObtenidos.get(0).getCuerpo());
        assertEquals("Chachi!", mensajesObtenidos.get(1).getCuerpo());
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
        Usuario usuarioNoValido = null;
        IMensajeRepository repo = new MensajeJDBCRepo();
        assertThrows(UsuarioException.class, () -> {
            repo.obtener(usuarioNoValido);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws SQLException {
        MensajeJDBCRepo mensajeJDBCRepo = new MensajeJDBCRepo();
        mensajeJDBCRepo.setDb_url("jdbc:mysql://localhost/bananawhatsappdb?user=bananauser&password=banana123");
        Usuario usuario = new Usuario(1, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        //Obtenemos la lista de mensajes antes de borrarlos
        List<Mensaje> mensajesAntes = mensajeJDBCRepo.obtener(usuario);
        int cantidadMensajesAntes = mensajesAntes.size();
        //Se borran todos los mensajes para el usuario
        mensajeJDBCRepo.borrarTodos(usuario);
        //Obtener la cantidad de mensajes despues de borrar
        List<Mensaje> mensajesDespues = mensajeJDBCRepo.obtener(usuario);
        int cantidadMensajesDespues = mensajesDespues.size();
        // Se verifica que la cantidad de mensajes es 0
        assertEquals(0, cantidadMensajesDespues);
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() {
        MensajeJDBCRepo mensajeJDBCRepo = new MensajeJDBCRepo();
        Usuario usuarioNoValido =  new Usuario(null, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        assertThrows(SQLException.class, () -> mensajeJDBCRepo.borrarTodos(usuarioNoValido));
    }
}