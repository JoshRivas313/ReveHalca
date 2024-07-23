package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.TipoUsuario;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.DAOuser;
import com.ravehalcajpa.service.EstadoUser;
import jakarta.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO extends conexion implements DAOuser<Usuario> {

    @Transactional
    @Override
    public Usuario create(Usuario u) {
        String sql = "INSERT INTO Usuario (username, email, password, tipo_usuario_id, estado) VALUES (?, ?, ?, ?, ?)";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setString(1, u.getUsername());
            st.setString(2, u.getEmail());
            st.setString(3, u.getPassword());
            st.setInt(4, u.getTipoUsuario().getId());
            st.setString(5, u.getEstado().name());
            

            st.executeUpdate();
            
            
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Usuario getById(int id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));

                usuario.setUsername(rs.getString("username"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));

                TipoUsuario tusu = new TipoUsuario();
                tusu.setId(rs.getInt("tipo_usuario_id"));
                usuario.setTipoUsuario(tusu);

                return usuario;
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public List<Usuario> getAll() {
        String sql = "SELECT u.id, u.username, u.email, u.password, t.nombre, u.estado FROM Usuario u"
                + " JOIN tipo_usuario t ON u.tipo_usuario_id = t.id;";
        List<Usuario> lista = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));

                TipoUsuario tusu = new TipoUsuario();
                tusu.setNombre(rs.getString("nombre"));
                u.setEstado(EstadoUser.valueOf(rs.getNString("estado")));

                u.setTipoUsuario(tusu);
                lista.add(u);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Usuario update(Usuario entity) {
        String sql = "UPDATE Usuario SET username = ?, email = ?, password = ?, tipo_usuario_id = ?, estado = ? WHERE id = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setString(1, entity.getUsername());
            st.setString(2, entity.getEmail());
            st.setString(3, entity.getPassword());
            st.setInt(4, entity.getTipoUsuario().getId());
            st.setString(5, entity.getEstado().name());
            st.setInt(6, entity.getId());
            st.executeUpdate();
            return entity;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id) {

        String sql = "UPDATE Usuario SET estado = 'Baja' WHERE id = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, id);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    
    @Transactional
    public List<Usuario> getAllMozo() {
        String sql = "SELECT id, username FROM Usuario";
        List<Usuario> lista = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                lista.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }
}
