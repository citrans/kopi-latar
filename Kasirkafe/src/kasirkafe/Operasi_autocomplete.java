/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Citra
 */
public class Operasi_autocomplete {
    
//  Conexion conexion =new Conexion();
//    com.mysql.jdbc.Statement stat;
//    ResultSet res;

    public DefaultComboBoxModel getLista(String cadenaEscrita){

        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            java.sql.Connection konek = new Koneksi_mysql().getConnection();
            String query = "SELECT * FROM menu WHERE nama_menu LIKE '" + cadenaEscrita + "%';";
//            st = conexion.conectar();
//            res = (ResultSet) st.executeQuery(query);
            Statement stat= konek.createStatement();
            ResultSet hasil = stat.executeQuery(query);
            while (hasil.next()) {
                modelo.addElement(hasil.getString("nama_menu"));
            }
         konek.close();
        } catch (SQLException ex) {
            Logger.getLogger(Operasi_autocomplete.class.getName()).log(Level.SEVERE, null, ex);
        }

     return modelo;

    }

    public String[] buscar(String nombre){

        String[] datos = new String[4];
        try {
            java.sql.Connection konek = new Koneksi_mysql().getConnection();
            String query = "SELECT * FROM menu WHERE nama_menu='" + nombre + "'";
//            st = conexion.conectar();
//            res = (ResultSet) st.executeQuery(query);
            Statement stat= konek.createStatement();
            ResultSet hasil = stat.executeQuery(query);
            
            while (hasil.next()) {
                for (int i = 0; i < datos.length; i++) {
                    datos[i] = hasil.getString(i + 1);
                }
            }
            konek.close();
        } catch (SQLException ex) {
            return null;
        }
        return datos;
    }

}
