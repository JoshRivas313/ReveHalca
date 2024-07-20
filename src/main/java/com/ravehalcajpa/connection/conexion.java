/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ravehalcajpa.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author U21227807
 */
public class conexion {
     private Connection cn;

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }
    
    public void conectar() throws Exception{
        try{
        Class.forName("com.mysql.jdbc.Driver");
        cn = DriverManager.getConnection("jdbc:mysql://localhost:3307/pruebadb?user=root&password=");
        
        }catch(Exception e){
            throw e;
        }
     }
    public void cerrar() throws Exception{
        try{
            if(cn == null){
            if (cn.isClosed() == false){
                cn.close();
            }
        }
        } catch (Exception e){
            throw e;
        }
        
    }
}
