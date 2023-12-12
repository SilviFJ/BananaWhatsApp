package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;

import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface IMensajeRepository {
    public Mensaje crear(Mensaje mensaje) throws SQLException;

    public List<Mensaje> obtener(Usuario usuario) throws SQLException;
    public Mensaje getSMSById(Integer id) throws Exception;

    public boolean borrarTodos(Usuario usuario) throws SQLException;
}
