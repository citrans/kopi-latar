/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;

/**
 *
 * @author Mar
 */
//import Koneksi.koneksi;
//import cls.ClassDB;
//import com.mysql.jdbc.PreparedStatement;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;
import java.awt.print.*;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kasirkafe.mejatr.MejaTransaksi;
public final class produkfavorit extends javax.swing.JFrame {

    /**
     * Creates new form produkfavorit
     */
    
     private DefaultTableModel model;
     private DefaultTableModel tabel2;
    private String kode, user, status;
   
     
    public produkfavorit() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
                   
        model = new DefaultTableModel ( );
             tbl_fav.setModel(model);
             model.addColumn("Nama Menu");
             model.addColumn("Total Terjual");
             getData(); 
             tanggal_jam_sekarang();
    }
    public produkfavorit(String User, String Status){
             initComponents();
             this.setExtendedState(JFrame.MAXIMIZED_BOTH);
             this.user = User;
             this.status = Status;
             if("Pegawai".equals(status)){
            mi_dt_pegawai.hide();
            mi_favorit.hide();
            mi_hari_ini.hide();
            mi_lap_tr.hide();
        }
             model = new DefaultTableModel ( );
             tabel2 = new DefaultTableModel();
             tbl_fav.setModel(model);
             model.addColumn("Nama Menu");
             model.addColumn("Total Terjual");
             getData();
             tanggal_jam_sekarang();
    }
public void getData( ){
     //menghapus isi table tblGaji
     model.getDataVector( ).removeAllElements( );
     model.fireTableDataChanged( );
     try{
           Connection konek = new Koneksi_mysql().getConnection();
           Statement stat = konek.createStatement();
           String sql        = "SELECT * FROM `menu_laku` WHERE jumlah_laku > 0 ORDER BY jumlah_laku DESC";
           ResultSet res   = stat.executeQuery(sql);
           while(res.next ()){
                Object[ ] obj = new Object[2];
                obj[0] = res.getString("nama_menu");
                obj[1] = res.getString("jumlah_laku");
                model.addRow(obj);
            }
      }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage() );
      }
}
        void tampil_lap_cari(){
        int a = jm_bulan.getMonth();
        int b = jy_tahun.getYear();
        int bulan = a+1;
        String tanggal = Integer.toString(b)+"-"+Integer.toString(bulan);
        SimpleDateFormat h = new SimpleDateFormat("dd");
        Object[]baris={"Nama Menu","Jumlah Laku"};
        model= new DefaultTableModel(null,baris);
        tbl_fav.setModel(model);
        String sql = "SELECT * FROM produk_fav WHERE tanggal LIKE '%"+tanggal+"%'";
        try{
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String nama_produk = hasil.getString("nama_menu");
                    String jumlah_laku = hasil.getString("jumlah_laku");
                    String[]data= { nama_produk, jumlah_laku};
                    model.addRow(data);
                } konek.close();
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data pesanan gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
          private void tanggal_jam_sekarang(){
        Thread p;
        p = new Thread(){
            @Override
            public void run(){
                for(;;){
                    String month = null;
                    Calendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    lb_tgl_today.setText(hari+"-"+(bulan+1)+"-"+tahun);                    
                }
            }
        };
        p.start();
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_fav = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        bt_cari = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lb_tgl_today = new javax.swing.JLabel();
        bt_print = new javax.swing.JButton();
        jm_bulan = new com.toedter.calendar.JMonthChooser();
        jy_tahun = new com.toedter.calendar.JYearChooser();
        bt_bulan_ini = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mb_transaksi = new javax.swing.JMenu();
        mi_beranda = new javax.swing.JMenuItem();
        mi_transaksi = new javax.swing.JMenuItem();
        mb_data_menu = new javax.swing.JMenu();
        mi_menu_kafe = new javax.swing.JMenuItem();
        mi_dt_pegawai = new javax.swing.JMenuItem();
        mb_tr_today = new javax.swing.JMenu();
        mi_hari_ini = new javax.swing.JMenuItem();
        mi_favorit = new javax.swing.JMenuItem();
        mi_lap_tr = new javax.swing.JMenuItem();
        mi_kasir = new javax.swing.JMenuItem();
        mb_keluar = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mi_keluar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(205, 133, 63));

        jLabel1.setBackground(new java.awt.Color(205, 133, 63));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PRODUK FAVORIT");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbl_fav.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_fav);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("PILIH TANGGAL");

        bt_cari.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bt_cari.setText("CARI");
        bt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cariActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("TANGGAL HARI INI");

        lb_tgl_today.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_tgl_today.setText("jLabel4");

        bt_print.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bt_print.setText("PRINT");
        bt_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_printActionPerformed(evt);
            }
        });

        bt_bulan_ini.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bt_bulan_ini.setText("Bulan Ini");
        bt_bulan_ini.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_bulan_iniActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(bt_print, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2))
                            .addGap(45, 45, 45)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lb_tgl_today)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jm_bulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jy_tahun, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(47, 47, 47)
                                    .addComponent(bt_cari)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addGap(177, 177, 177)
                            .addComponent(bt_bulan_ini))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_tgl_today, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(bt_cari)
                                    .addComponent(bt_bulan_ini)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jm_bulan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jy_tahun, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(bt_print, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        mb_transaksi.setText("Menu Utama");
        mb_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mb_transaksiActionPerformed(evt);
            }
        });

        mi_beranda.setText("Beranda");
        mi_beranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_berandaActionPerformed(evt);
            }
        });
        mb_transaksi.add(mi_beranda);

        mi_transaksi.setText("Transaksi");
        mi_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_transaksiActionPerformed(evt);
            }
        });
        mb_transaksi.add(mi_transaksi);

        jMenuBar1.add(mb_transaksi);

        mb_data_menu.setText("Data Utama");
        mb_data_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mb_data_menuActionPerformed(evt);
            }
        });

        mi_menu_kafe.setText("Menu Kafe");
        mi_menu_kafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_menu_kafeActionPerformed(evt);
            }
        });
        mb_data_menu.add(mi_menu_kafe);

        mi_dt_pegawai.setText("Data Pegawai");
        mi_dt_pegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_dt_pegawaiActionPerformed(evt);
            }
        });
        mb_data_menu.add(mi_dt_pegawai);

        jMenuBar1.add(mb_data_menu);

        mb_tr_today.setText("Laporan");

        mi_hari_ini.setText("Laporan Hari ini");
        mi_hari_ini.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_hari_iniActionPerformed(evt);
            }
        });
        mb_tr_today.add(mi_hari_ini);

        mi_favorit.setText("Laporan Favorit");
        mi_favorit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_favoritActionPerformed(evt);
            }
        });
        mb_tr_today.add(mi_favorit);

        mi_lap_tr.setText("Laporan Transaksi");
        mi_lap_tr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_lap_trActionPerformed(evt);
            }
        });
        mb_tr_today.add(mi_lap_tr);

        mi_kasir.setText("Laporan Kasir");
        mi_kasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_kasirActionPerformed(evt);
            }
        });
        mb_tr_today.add(mi_kasir);

        jMenuBar1.add(mb_tr_today);

        mb_keluar.setText("Keluar");
        mb_keluar.add(jSeparator1);

        mi_keluar.setText("Keluar");
        mi_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_keluarActionPerformed(evt);
            }
        });
        mb_keluar.add(mi_keluar);

        jMenuBar1.add(mb_keluar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mi_berandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_berandaActionPerformed
        // TODO add your handling code here:
        Halaman_utama n = new Halaman_utama(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_berandaActionPerformed

    private void mi_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_transaksiActionPerformed
        // TODO add your handling code here:
        MejaTransaksi n = new MejaTransaksi(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_transaksiActionPerformed

    private void mb_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mb_transaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mb_transaksiActionPerformed

    private void mi_menu_kafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_menu_kafeActionPerformed
        // TODO add your handling code here:
        form_menu_makanan n = new form_menu_makanan(user,status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_menu_kafeActionPerformed

    private void mi_dt_pegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_dt_pegawaiActionPerformed
        // TODO add your handling code here:
        crud n = new crud(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_dt_pegawaiActionPerformed

    private void mb_data_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mb_data_menuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mb_data_menuActionPerformed

    private void mi_hari_iniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_hari_iniActionPerformed
        // TODO add your handling code here:
        LaporanHarian n =new LaporanHarian(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_hari_iniActionPerformed

    private void mi_favoritActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_favoritActionPerformed
        // TODO add your handling code here:
        produkfavorit n = new produkfavorit(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_favoritActionPerformed

    private void mi_lap_trActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_lap_trActionPerformed
        // TODO add your handling code here:
        LaporanBulanan n = new LaporanBulanan(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_lap_trActionPerformed

    private void mi_kasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_kasirActionPerformed
        // TODO add your handling code here:
        LaporanTransaksiKasir n = new LaporanTransaksiKasir(user,status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_kasirActionPerformed

    private void mi_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_keluarActionPerformed
        // TODO add your handling code here:
        login n = new login();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_keluarActionPerformed

    private void bt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cariActionPerformed
        // TODO add your handling code here:
        tampil_lap_cari();
    }//GEN-LAST:event_bt_cariActionPerformed

    private void bt_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_printActionPerformed
        // TODO add your handling code here:
        MessageFormat header = new MessageFormat("Laporan Favorite \n Bulan :");
        MessageFormat footer = new MessageFormat("halaman{0,number, integer}");
        try {
            tbl_fav.print(JTable.PrintMode.NORMAL, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(LaporanBulanan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_printActionPerformed

    private void bt_bulan_iniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_bulan_iniActionPerformed
        // TODO add your handling code here:
        getData();
    }//GEN-LAST:event_bt_bulan_iniActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(produkfavorit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         //</editor-fold>
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new produkfavorit().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_bulan_ini;
    private javax.swing.JButton bt_cari;
    private javax.swing.JButton bt_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private com.toedter.calendar.JMonthChooser jm_bulan;
    private com.toedter.calendar.JYearChooser jy_tahun;
    private javax.swing.JLabel lb_tgl_today;
    private javax.swing.JMenu mb_data_menu;
    private javax.swing.JMenu mb_keluar;
    private javax.swing.JMenu mb_tr_today;
    private javax.swing.JMenu mb_transaksi;
    private javax.swing.JMenuItem mi_beranda;
    private javax.swing.JMenuItem mi_dt_pegawai;
    private javax.swing.JMenuItem mi_favorit;
    private javax.swing.JMenuItem mi_hari_ini;
    private javax.swing.JMenuItem mi_kasir;
    private javax.swing.JMenuItem mi_keluar;
    private javax.swing.JMenuItem mi_lap_tr;
    private javax.swing.JMenuItem mi_menu_kafe;
    private javax.swing.JMenuItem mi_transaksi;
    private javax.swing.JTable tbl_fav;
    // End of variables declaration//GEN-END:variables

   
}
