
package com.ravehalcajpa.login;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.TipoUsuario;
import com.ravehalcajpa.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login extends conexion {

    public Usuario login(String email, String password) throws Exception {
        Usuario usu = null;
        try {
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(
                    "SELECT u.id, u.email, u.password, tu.id AS tipoUsuarioId, tu.nombre AS tipoUsuarioNombre "
                    + "FROM usuario u "
                    + "INNER JOIN tipo_usuario tu ON u.tipo_usuario_id = tu.id "
                    + "WHERE u.email = ? AND u.password = ?");
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                usu = new Usuario();
                usu.setId(rs.getInt("id"));
                usu.setEmail(rs.getString("email"));
                usu.setPassword(rs.getString("password"));
            
                TipoUsuario tipoUsuario = new TipoUsuario();
                tipoUsuario.setId(rs.getInt("tipoUsuarioId"));
                tipoUsuario.setNombre(rs.getString("tipoUsuarioNombre"));
                
                usu.setTipoUsuario(tipoUsuario);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return usu;
    }
}
