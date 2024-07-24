package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.PerfilUsuario;
import com.ravehalcajpa.model.TipoUsuario;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.DAOedit;
import jakarta.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PerfilUsuarioDAO extends conexion implements DAOedit<PerfilUsuario> {

    @Transactional
    @Override
    public PerfilUsuario getById(Long id) {
        String sql = "SELECT * FROM PerfilUsuario WHERE id = ?";
        try {
            try {
                conectar();  // Intenta conectar a la base de datos
            } catch (Exception e) {
                Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, "Error al conectar a la base de datos", e);
                return null;  // Si hay un error al conectar, retorna null
            }

            try {
                PreparedStatement st = this.getCn().prepareStatement(sql);
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    PerfilUsuario perfilusuario = new PerfilUsuario();
                    perfilusuario.setId(rs.getInt("id"));
                    perfilusuario.setNombre(rs.getString("nombre"));
                    perfilusuario.setApellido(rs.getString("apellido"));
                    perfilusuario.setDni(rs.getInt("dni"));
                    perfilusuario.setNacionalidad(rs.getString("nacionalidad"));
                    perfilusuario.setDistrito(rs.getString("distrito"));
                    perfilusuario.setDireccion(rs.getString("direccion"));

                    Usuario usu = new Usuario();
                    usu.setId(rs.getInt("usuario_id"));
                    perfilusuario.setUsuario(usu);

                    return perfilusuario;
                }
            } catch (SQLException e) {
                Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, "Error al ejecutar la consulta", e);
            } finally {
                try {
                    cerrar();  // Cierra la conexión
                } catch (Exception ex) {
                    Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, "Error general", ex);
        }
        return null;  // Retorna null si no se encuentra el perfil o hay un error
    }
    
    @Override
    @Transactional
    public List<PerfilUsuario> getAll() {
        String sql = "SELECT u.id, u.nombre, u.apellido, u.dni, u.nacionalidad, u.distrito, u.direccion, t.password FROM usuario t"
                + " LEFT JOIN PerfilUsuario u ON t.id = u.usuario_id;";
        List<PerfilUsuario> lista = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                PerfilUsuario perfilusuario = new PerfilUsuario();
                perfilusuario.setId(rs.getInt("id"));
                perfilusuario.setNombre(rs.getString("nombre"));
                perfilusuario.setApellido(rs.getString("apellido"));
                perfilusuario.setDni(rs.getInt("dni"));
                perfilusuario.setNacionalidad(rs.getString("nacionalidad"));
                perfilusuario.setDistrito(rs.getString("distrito"));
                perfilusuario.setDireccion(rs.getString("direccion"));

                Usuario usu = new Usuario();
                usu.setPassword(rs.getString("password"));
                perfilusuario.setUsuario(usu);
                lista.add(perfilusuario);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Transactional
    public List<PerfilUsuario> getFilteredAttributes() throws SQLException, Exception {
        String sql = "SELECT id, nombre, apellido, dni, nacionalidad, distrito, direccion FROM perfilusuario";
        List<PerfilUsuario> lista = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                PerfilUsuario p = new PerfilUsuario();
                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setDni(rs.getInt("dni"));
                p.setNacionalidad(rs.getString("nacionalidad"));
                p.setDistrito(rs.getString("distrito"));
                p.setDireccion(rs.getString("direccion"));
                lista.add(p);
            }

            return lista;
        } finally {
            cerrar();
        }
    }

    @Transactional
    @Override
    public PerfilUsuario update(PerfilUsuario entity) {

        String sql = "UPDATE PerfilUsuario SET nombre = ?, apellido = ?, dni = ?, nacionalidad = ?, distrito = ?, direccion = ? WHERE id = ?";

        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setString(1, entity.getNombre());
            st.setString(2, entity.getApellido());
            st.setInt(3, entity.getDni());
            st.setString(4, entity.getNacionalidad());
            st.setString(5, entity.getDistrito());
            st.setString(6, entity.getDireccion());
            st.setLong(7, entity.getId());

            st.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return entity;
    }

    @Transactional
    public PerfilUsuario getByIdUsuario(int id) throws Exception {
        String sql = "SELECT perfilusuario.id, perfilusuario.nombre, perfilusuario.apellido, perfilusuario.dni, perfilusuario.nacionalidad, perfilusuario.distrito, perfilusuario.direccion, tipo_usuario.nombre AS tipo_usuario_nombre "
                + "FROM perfilusuario "
                + "JOIN usuario ON perfilusuario.usuario_id = usuario.id "
                + "JOIN tipo_usuario ON usuario.tipo_usuario_id = tipo_usuario.id "
                + "WHERE usuario.id = ?;";

        PerfilUsuario perfilusuario = null;
        TipoUsuario tpUser = null;

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conectar();
            st = this.getCn().prepareStatement(sql);
            st.setInt(1, id);  // Ajuste en el tipo de dato del ID

            rs = st.executeQuery();
            if (rs.next()) {
                perfilusuario = new PerfilUsuario();
                perfilusuario.setId(rs.getInt("id"));
                perfilusuario.setNombre(rs.getString("nombre"));
                perfilusuario.setApellido(rs.getString("apellido"));
                perfilusuario.setDni(rs.getInt("dni"));
                perfilusuario.setNacionalidad(rs.getString("nacionalidad"));
                perfilusuario.setDistrito(rs.getString("distrito"));
                perfilusuario.setDireccion(rs.getString("direccion"));

                tpUser = new TipoUsuario();
                tpUser.setNombre(rs.getString("tipo_usuario_nombre"));

                Usuario usuario = new Usuario();
                usuario.setTipoUsuario(tpUser); // Establecer el TipoUsuario en Usuario
                perfilusuario.setUsuario(usuario); // Asignar Usuario al PerfilUsuario
            }
        } catch (SQLException e) {
            Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(PerfilUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return perfilusuario;
    }

}
