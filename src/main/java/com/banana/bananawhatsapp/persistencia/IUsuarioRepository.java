package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IUsuarioRepository {
    public Usuario crear(Usuario usuario) throws SQLException;

    public Usuario actualizar(Usuario usuario) throws SQLException;

    public boolean borrar(Usuario usuario) throws SQLException;

    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException;

    public String getdb_url();
}
