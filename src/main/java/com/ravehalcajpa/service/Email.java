/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ravehalcajpa.service;

import com.ravehalcajpa.model.Usuario;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author sarela
 */
public class Email {
    
    public boolean enviaruser(Usuario us) {
        
        String remitente = "macguardar@gmail.com";
        String password = "cbsp ktfo zpen lrep"; 

        String destinatario = us.getEmail();

        // Configuración de las propiedades
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        //Mensaje
        
        String mensaje="Hola mucho gusto, te saluda el Area de administración de Revehalca para mandarte el acceso a tu cuenta:"+
                    "\nTu correo es: "+us.getEmail()+
                    "\nTu Contraseña es: "+us.getPassword();
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });
        
        try {
            // Creación del mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Envio de credenciales ");
            message.setText(mensaje);

            // Envío del mensaje
            Transport.send(message);

            System.out.println("Correo enviado satisfactoriamente.");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo: " + e.getMessage());
            return false;
        }
        
    }
    
    
        
    
    
//    
}
