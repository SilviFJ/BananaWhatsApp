package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;

import java.sql.SQLException;

public interface IUsuarioRepository {
    public Usuario crear(Usuario usuario) throws SQLException;

    public Usuario actualizar(Usuario usuario) throws SQLException;

    public boolean borrar(Usuario usuario) throws SQLException;
    
    public Usuario obtenerUsuariobyID(String usuario, Integer id) throws SQLException;

}
