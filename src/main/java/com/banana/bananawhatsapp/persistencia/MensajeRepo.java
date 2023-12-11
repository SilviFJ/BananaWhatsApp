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

@Getter
@Setter
public class MensajeRepo implements IMensajeRepository{
    private String db_url;
    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {
        String sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            stmt.setString(2, mensaje.getCuerpo().toString());
            stmt.setDate(3, Date.valueOf(mensaje.getFecha()));
            stmt.setInt(4, mensaje.getRemitente().getId());
            stmt.setInt(5, mensaje.getDestinatario().getId());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                mensaje.setId(genKeys.getInt(1));
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

        return mensaje;
    }







































    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        return null;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        return false;
    }
}
