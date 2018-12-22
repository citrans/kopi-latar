/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;
//import Koneksi.koneksi;
//import cls.ClassDB;
//import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;
import java.awt.print.*;
import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Mar
 */
public class Laporanbulanandetail extends javax.swing.JFrame {

    /**
     * Creates new form LaporanTransaksiKasir
     */
    private DefaultTableModel model;
    private String kode;
    String user, status,id_pegawai;
    int id_trans;
    Calendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    String tanggal = Integer.toString(hari)+"-"+Integer.toString(bulan+1)+"-"+Integer.toString(tahun);
    public Laporanbulanandetail(){
        initComponents();
    }
    public Laporanbulanandetail(int id_tr, String User) {
        initComponents();
        this.id_trans= id_tr;
        this.user = User;
        lb_nama.setText(user);
        lb_tanggal.setText(hari+"-"+bulan+"-"+tahun);
        lb_id_tr.setText(Integer.toString(id_tr));
//             ambil_id_peg();
             this.setExtendedState(JFrame.MAXIMIZED_BOTH);
             model = new DefaultTableModel ( );
             tblLapBul.setModel(model);
             model.addColumn("Nomor meja");
             //model.addColumn("ID_Pegawai");
             //model.addColumn("Tgl_Transaksi");
             model.addColumn("Nama Menu");
             model.addColumn("Jumlah Pesanan");
             model.addColumn("Total Harga");
//             model.addColumn("Kembalian");
            

            jLabel2.setText(id_pegawai);
             getData(); 
    }
    
//     public LaporanTransaksiKasir(String User, String Status) {
//        initComponents();
//           model = new DefaultTableModel ( );
//             tblLapBul.setModel(model);
//             model.addColumn("ID_Transaksi");
//             model.addColumn("ID_Pegawai");
//             model.addColumn("Tgl_Transaksi");
//             model.addColumn("Jumlah_Pesanan");
//             model.addColumn("Total_Harga");
//             model.addColumn("Bayar");
//             model.addColumn("Kembalian");
//            ambil_id_peg();
//            jLabel2.setText(id_pegawai);
//             getData(); 
//    }
    
     public void ambil_id_peg(){
        
        try{
            
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT id_pegawai as id FROM pegawai WHERE `nama_pegawai`='"+user+"'";
                ResultSet hasil = stat.executeQuery(sql);
                
                while(hasil.next()){
                    int id = hasil.getInt(1);
                    id_pegawai = String.valueOf(id);                       
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
     }
    
    public void getData( ){
     //menghapus isi table tblGaji
     model.getDataVector( ).removeAllElements( );
     model.fireTableDataChanged( );
     ambil_id_peg();

     try{
           //membuat statemen pemanggilan data pada table tblGaji dari database
            Connection konek = new Koneksi_mysql().getConnection();
            Statement stat = konek.createStatement();
            String sql = "SELECT sementara.*, menu.nama_menu FROM `sementara`, menu WHERE sementara.id_menu=menu.id_menu AND sementara.id_transaksi ='"+id_trans+"'";
            //ResultSet hasil = stat.executeQuery(sql);
          // String sql        = "Select ";
         
           ResultSet res   = stat.executeQuery(sql);
          

           //penelusuran baris pada tabel tblGaji dari database
           while(res.next ()){
                Object[ ] obj = new Object[7];
                obj[0] = res.getString("no_meja");
                //obj[1] = res.getString("id_pegawai");
                //obj[2] = res.getString("tgl_transaksi");
                obj[1] = res.getString("nama_menu");
                obj[2] = res.getString("jumlah_pesanan");
                obj[3] = res.getString("total");
//                obj[4] = res.getString("kembalian");
                

                model.addRow(obj);
            }
      }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage() );
      }
} 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLapBul = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_tanggal = new javax.swing.JLabel();
        lb_nama = new javax.swing.JLabel();
        bt_kembali = new javax.swing.JButton();
        lb_id_tr = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(205, 133, 63));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID TRANSAKSI");

        tblLapBul.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        jScrollPane1.setViewportView(tblLapBul);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("NAMA PEGAWAI");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("TANGGAL TRANSAKSI");

        lb_tanggal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_tanggal.setText("tanggal");

        lb_nama.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_nama.setText("nama");

        bt_kembali.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bt_kembali.setText("KEMBALI");
        bt_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_kembaliActionPerformed(evt);
            }
        });

        lb_id_tr.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_id_tr.setText("jLabel2");

        jLabel1.setBackground(new java.awt.Color(205, 133, 63));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LAPORAN HARIAN");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(70, 70, 70)
                                .addComponent(lb_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(328, 328, 328)
                                .addComponent(jLabel2)
                                .addGap(43, 43, 43)
                                .addComponent(lb_id_tr)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bt_kembali))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(lb_id_tr))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(bt_kembali))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_kembaliActionPerformed
        // TODO add your handling code here:
        LaporanHarian n = new LaporanHarian(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bt_kembaliActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Laporanbulanandetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laporanbulanandetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laporanbulanandetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laporanbulanandetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporanbulanandetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_kembali;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_id_tr;
    private javax.swing.JLabel lb_nama;
    private javax.swing.JLabel lb_tanggal;
    private javax.swing.JTable tblLapBul;
    // End of variables declaration//GEN-END:variables
}
