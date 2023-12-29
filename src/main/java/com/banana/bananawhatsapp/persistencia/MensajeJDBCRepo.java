package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

@Setter
@Getter
public class MensajeJDBCRepo implements IMensajeRepository{
    private String db_url;
    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {
        String sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";
        try (
            Connection conn = DriverManager.getConnection(db_url);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            mensaje.valido();
            stmt.setString(1,mensaje.getCuerpo());
            stmt.setDate(2,Date.valueOf(mensaje.getFecha()));
            stmt.setInt(3, mensaje.getRemitente().getId());
            stmt.setInt(4,mensaje.getDestinatario().getId());
            int rows = stmt.executeUpdate();
            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()){
                mensaje.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("ID no asignado");
            }
        } catch (MensajeException e){
            e.printStackTrace();
            throw new MensajeException("Mensaje no valido");
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException(e);
        }
        return mensaje;
    }

   @Override
    public List<Mensaje> obtener(Usuario usuario) throws UsuarioException {
        if (usuario == null || usuario.getId() <= 0){
            throw new UsuarioException("Usuario no valido");
        }
        List<Mensaje> mensajes = new ArrayList<>();
        String sql = "SELECT * FROM mensaje WHERE to_user = ?";

        try (
            Connection conn = DriverManager.getConnection(db_url);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setInt(1, usuario.getId());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer remitenteId = resultSet.getInt("from_user");
                Integer destinatarioId = resultSet.getInt("to_user");
                String cuerpo = resultSet.getString("cuerpo");
                LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
                Usuario remitente = obtenerUsuarioPorId(remitenteId);
                Usuario destinatario = usuario;
                Mensaje mensaje = new Mensaje(id, remitente, destinatario, cuerpo, fecha);
                mensajes.add(mensaje);
            }
        } catch (SQLException e){
            throw new UsuarioException("Error al obtener la lista de mensajes");
        }
        return mensajes;
    }

    private Usuario obtenerUsuarioPorId(Integer id) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (
            Connection conn = DriverManager.getConnection(db_url);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setInt(1,id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String email = resultSet.getString("email");
                LocalDate alta = resultSet.getDate("alta").toLocalDate();
                boolean activo = resultSet.getBoolean("activo");
                usuario = new Usuario(id, nombre, email, alta, activo);

            }
        } catch (SQLException e){
            throw new SQLException("Error al intentar borrar");
        }
        return usuario;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM mensaje WHERE to_user = ?";
        int rowsAffected;
        try (
            Connection conn = DriverManager.getConnection(db_url);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            stmt.setInt(1, usuario.getId());
            rowsAffected = stmt.executeUpdate();
        }

        return rowsAffected > 0;
    }
}