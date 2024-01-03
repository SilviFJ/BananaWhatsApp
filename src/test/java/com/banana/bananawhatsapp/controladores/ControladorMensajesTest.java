package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.MensajeJDBCRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorMensajesTest {
    @Autowired
    ControladorMensajes controladorMensajes;

    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() {
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
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {
        Usuario remitente = new Usuario(8, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        Usuario destinatario = new Usuario(9, "Juana", "juana@dxc.com", LocalDate.now(), true);
        Mensaje mensaje = new Mensaje(null, remitente, destinatario, "?", LocalDate.now());
        assertThrows(MensajeException.class, () -> {
                controladorMensajes.enviarMensaje(8,9,"?");
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {
        //MensajeJDBCRepo mensajeJDBCRepo = new MensajeJDBCRepo();
        //mensajeJDBCRepo.setDb_url("jdbc:mysql://localhost/bananawhatsappdb?user=bananauser&password=banana123");
        //Usuario usuario = new Usuario(1, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        //List<Mensaje> mensajesObtenidos = mensajeJDBCRepo.obtener(usuario);
        //assertEquals(1, mensajesObtenidos.size());
        //assertEquals("Helloooooooooooooooo", mensajesObtenidos.get(0).getCuerpo());
        controladorMensajes.mostrarChat(1,2);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {
        assertThrows(MensajeException.class, () -> {
                controladorMensajes.mostrarChat(8,9);
        });

    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() throws SQLException {
        MensajeJDBCRepo mensajeJDBCRepo = new MensajeJDBCRepo();
        mensajeJDBCRepo.setDb_url("jdbc:mysql://localhost/bananawhatsappdb?user=bananauser&password=banana123");
        Usuario usuario1 = new Usuario(1, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        Usuario usuario2 = new Usuario(2, "Luis", "luis@dxc.com", LocalDate.now(), true);
        //Obtenemos la lista de mensajes antes de borrarlos
        List<Mensaje> mensajesAntesUsuario1 = mensajeJDBCRepo.obtener(usuario1);
        List<Mensaje> mensajesAntesUsuario2 = mensajeJDBCRepo.obtener(usuario2);
        int cantidadMensajesAntesUsuario1 = mensajesAntesUsuario1.size();
        int cantidadMensajesAntesUsuario2 = mensajesAntesUsuario2.size();
        //Se borran todos los mensajes para el usuario
        mensajeJDBCRepo.borrarTodos(usuario1, usuario2);
        //Obtener la cantidad de mensajes despues de borrar
        List<Mensaje> mensajesDespuesUsuario1 = mensajeJDBCRepo.obtener(usuario1);
        List<Mensaje> mensajesDespuesUsuario2 = mensajeJDBCRepo.obtener(usuario2);
        int cantidadMensajesDespuesUsuario1 = mensajesDespuesUsuario1.size();
        int cantidadMensajesDespuesUsuario2 = mensajesDespuesUsuario2.size();
        // Se verifica que la cantidad de mensajes es 0
        assertEquals(0, cantidadMensajesDespuesUsuario1);
        assertEquals(0, cantidadMensajesDespuesUsuario2);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {
        MensajeJDBCRepo mensajeJDBCRepo = new MensajeJDBCRepo();
        Usuario remitenteNoValido =  new Usuario(null, "Pedro", "pedro@dxc.com", LocalDate.now(), true);
        Usuario destinatarioNoValido =  new Usuario(null, "Juan", "juan@dxc.com", LocalDate.now(), true);
        assertThrows(SQLException.class, () -> mensajeJDBCRepo.borrarTodos(remitenteNoValido, destinatarioNoValido));
    }
}