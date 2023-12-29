package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class MensajeServicioImpl implements IServicioMensajeria {
    @Autowired
    IMensajeRepository mensajeRepo;

    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {
        if (remitente == null || destinatario == null){
            throw new UsuarioException("El remitente y el destinatario deben ser usuarios validos");
        }
        Mensaje mensaje = new Mensaje(null, remitente, destinatario, texto, LocalDate.now());
        try {
            mensajeRepo.crear(mensaje);
        } catch (Exception e) {
            throw new MensajeException("Error al enviar el mensaje: " + e.getMessage());
        }
        return mensaje;
    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException, SQLException {
         if (remitente == null || destinatario == null){
            throw new UsuarioException("El remitente y el destinatario deben ser usuarios validos");
         }
         List<Mensaje> mensajes = mensajeRepo.obtener(destinatario);
         return mensajes;
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        if (remitente == null || destinatario == null){
            throw new UsuarioException("El remitente y el destinatario tienen que ser usuarios validos");
        }
        try {
            mensajeRepo.borrarTodos(destinatario);
            return true;
        } catch (Exception e) {
            throw new MensajeException("Error al borrar el chat: " + e.getMessage());
        }
    }
}
