package com.banana.bananawhatsapp.persistencia;
import org.springframework.stereotype.Repository;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;

import com.banana.bananawhatsapp.properties.PropertyValues;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Getter
@Setter
public class UsuarioRepo implements IUsuarioRepository {
    private String db_url;
    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setBoolean(2, usuario.isActivo());
            stmt.setDate(3, Date.valueOf(usuario.getAlta()));
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getNombre());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                usuario.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("Mensaje creado erroneamente!!!");
            }

        } catch (SQLException e) {
          e.printStackTrace();
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return usuario;
    }

    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
        return null;
    }

    @Override
    public boolean borrar(Usuario usuario) throws SQLException {
        return false;
    }

    @Override
    public Usuario getUsuarioById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        return null;
    }

    @Override
    public String getdb_url() {
        return this.db_url;
    }

}
