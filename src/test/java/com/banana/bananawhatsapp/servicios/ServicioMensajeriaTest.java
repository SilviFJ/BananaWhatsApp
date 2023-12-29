package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServicioMensajeriaTest {
    @Autowired
    IServicioMensajeria servicio;

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() {
        Usuario remitente = new Usuario(1, "Juanita", "juana@j.com", LocalDate.now(), true);
        Usuario destinatario = new Usuario(2, "Luis", "luis@l.com", LocalDate.now(), true);
        String texto = "Helloooooooooooooooo";
        Mensaje mensajeEnviado = servicio.enviarMensaje(remitente, destinatario, texto);
        assertNotNull(mensajeEnviado);
        assertEquals(remitente, mensajeEnviado.getRemitente());
        assertEquals(destinatario, mensajeEnviado.getDestinatario());
        assertEquals(texto, mensajeEnviado.getCuerpo());
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() {
        Usuario remitente = new Usuario();
        remitente.setId(1);

        Usuario destinatario = new Usuario();
        destinatario.setId(2);

        String textoInvalido = "Hello";

        assertThrows(MensajeException.class, () -> servicio.enviarMensaje(remitente, destinatario, textoInvalido));
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() throws SQLException {
        Usuario remitente = new Usuario();
        remitente.setId(1);

        Usuario destinatario = new Usuario();
        destinatario.setId(2);

        List<Mensaje> mensajes = servicio.mostrarChatConUsuario(remitente, destinatario);
        assertTrue(mensajes != null && !mensajes.isEmpty());
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() {
        Usuario remitente = new Usuario();
        remitente.setId(0);

        Usuario destinatario = new Usuario();
        destinatario.setId(-1);
        assertThrows(UsuarioException.class, () -> servicio.mostrarChatConUsuario(remitente,destinatario));
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() {
        Usuario remitente = new Usuario();
        remitente.setId(1);

        Usuario destinatario = new Usuario();
        destinatario.setId(2);
        assertTrue(servicio.borrarChatConUsuario(remitente,destinatario));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() {
        Usuario remitente = null;
        Usuario destinatario = null;

        assertThrows(UsuarioException.class, () -> servicio.borrarChatConUsuario(remitente,destinatario));
    }
}