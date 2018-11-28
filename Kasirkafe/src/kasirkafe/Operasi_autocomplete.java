/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Citra
 */
public class Operasi_autocomplete {
    
  Conexion conexion =new Conexion();
    com.mysql.jdbc.Statement st;
    ResultSet res;

    public DefaultComboBoxModel getLista(String cadenaEscrita){

        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {

            String query = "SELECT * FROM menu WHERE nama_menu LIKE '" + cadenaEscrita + "%';";
            st = conexion.conectar();
            res = (ResultSet) st.executeQuery(query);
            while (res.next()) {
                modelo.addElement(res.getString("nama_menu"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Operasi_autocomplete.class.getName()).log(Level.SEVERE, null, ex);
        }

     return modelo;

    }

    public String[] buscar(String nombre){

        String[] datos = new String[4];
        try {

            String query = "SELECT * FROM menu WHERE nama_menu='" + nombre + "'";
            st = conexion.conectar();
            res = (ResultSet) st.executeQuery(query);
            while (res.next()) {
                for (int i = 0; i < datos.length; i++) {
                    datos[i] = res.getString(i + 1);
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return datos;
    }

}
