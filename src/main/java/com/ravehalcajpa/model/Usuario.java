
package com.ravehalcajpa.model;

import com.ravehalcajpa.service.EstadoUser;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id")
    private TipoUsuario tipoUsuario;
    
    @Enumerated(EnumType.STRING)
    private EstadoUser estado;

    public Usuario() {
    }

    public Usuario(int id, String username, String email, String password, TipoUsuario tipoUsuario, EstadoUser estado) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public EstadoUser getEstado() {
        return estado;
    }

    public void setEstado(EstadoUser estado) {
        this.estado = estado;
    }

    
   
    @Override
    public String toString() {
        return "usuario{" + "id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ",tipoUsuario=" + tipoUsuario + ", estado=" + estado + '}';
    }

}
