package com.ravehalcajpa.infra.security;

import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Specializes;
import jakarta.inject.Named;
import java.io.Serializable;

import static com.ravehalcajpa.util.Utils.addDetailMessage;
import com.github.adminfaces.template.config.AdminConfig;
import com.ravehalcajpa.login.login;
import com.ravehalcajpa.model.Usuario;
import jakarta.inject.Inject;
import java.sql.Connection;

/**
 * Created by rmpestano on 12/20/14.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login page or not.
 * By default AdminSession isLoggedIn always resolves to true so it will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user must be redirect to initial page or logon
 * you can skip this class.
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    private String currentUser;
    private String email;
    private String password;
    private boolean remember;
    private Usuario usuario;
    @Inject
    private AdminConfig adminConfig;
    private Connection cn;
    private login log = new login();
    
    
 
    
    
      // Método para iniciar sesión
    public String login() {
        try {
            Usuario loggedInUser = log.login(email, password);
            if (loggedInUser != null) {
                usuario = loggedInUser;
                currentUser = email;
                 addDetailMessage("Inicio Exitoso <b>" + email + "</b>");
                  Faces.getExternalContext().getFlash().setKeepMessages(true);
                Faces.redirect(adminConfig.getIndexPage());
                return "index"; 
            } else {
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "login"; 
        }
    }
    
    @Override
    public boolean isLoggedIn() {

        return currentUser != null;
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

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
