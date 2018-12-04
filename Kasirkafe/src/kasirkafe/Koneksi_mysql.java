/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasir;

/**
 *
 * @author Samsung
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi_mysql {
    Connection koneksi;
    public Connection getConnection(){
        try{
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/kopi_latar","root","");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Koneksi ke database gagal","Informasi",JOptionPane.INFORMATION_MESSAGE);
        }
        return koneksi;
    } 
    
}
