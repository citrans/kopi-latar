
package kasirkafe;
/**
 *
 * @author Citra
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi_mysql {
    Connection koneksi;
    public  Connection getConnection(){
        try{
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/kopi_latar", "root", "");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "KOneksi ke database gagal", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        return koneksi;
    }
}
