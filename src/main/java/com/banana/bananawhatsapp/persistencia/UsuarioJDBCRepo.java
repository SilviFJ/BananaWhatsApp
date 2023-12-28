package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Setter
@Getter
public class UsuarioJDBCRepo implements IUsuarioRepository{
    private String db_url;
    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario values (NULL,?,?,?,?)";
        try (
            Connection conn = DriverManager.getConnection(db_url);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            usuario.valido();
            stmt.setBoolean(1,usuario.isActivo());
            stmt.setDate(2,Date.valueOf(usuario.getAlta()));
            stmt.setString(3,usuario.getEmail());
            stmt.setString(4,usuario.getNombre());
            int rows = stmt.executeUpdate();
            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()){
                usuario.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("ID no asignado");
            }
        } catch (UsuarioException e){
            e.printStackTrace();
            throw new UsuarioException("Usuario no valido");
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException(e);
        }
        return usuario;
    }

    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario set nombre = ?, email = ? WHERE id = ?";
        try (
            Connection conn = DriverManager.getConnection(db_url);
            /*PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)*/
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            usuario.valido();
            stmt.setString(1,usuario.getNombre());
            stmt.setString(2,usuario.getEmail());
            stmt.setInt(3,usuario.getId());

            int rows = stmt.executeUpdate();
            /*ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()){
                usuario.setId(genKeys.getInt(2));
            } else {
                throw new SQLException("Mail no correcto");
            }*/
            if (rows == 0){
                throw new SQLException("No se actualizo ningun registro");
            }
        } catch (UsuarioException e){
            e.printStackTrace();
            throw new UsuarioException("Error al actualizar el usuario");
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException(e);
        }
        return usuario;
    }

    @Override
    public boolean borrar(Usuario usuario) throws SQLException{
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (
            Connection conn = DriverManager.getConnection(db_url);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1,usuario.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Error al borrar el usuario de la base de datos");
        }
    }

    @Override
    public Usuario obtenerUsuariobyID(String usuario, Integer id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (
            Connection conn = DriverManager.getConnection(db_url);
            /*PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)*/
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                Usuario usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId(resultSet.getInt("id"));
                usuarioEncontrado.setNombre(resultSet.getString("nombre"));
                usuarioEncontrado.setEmail(resultSet.getString("email"));
                return usuarioEncontrado;
            } else {
                throw new SQLException("Usuario no encontrado");
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
