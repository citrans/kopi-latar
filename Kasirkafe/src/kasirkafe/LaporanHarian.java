/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;

import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import kasirkafe.mejatr.MejaTransaksi;

/**
 *
 * @author Siggy
 */
public class LaporanHarian extends javax.swing.JFrame {
    String user,status;
    private DefaultTableModel model;
    Calendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
    /**
     * Creates new form LaporanHarian
     */
    public LaporanHarian() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
        lb_tgl.setText(hari+"-"+(bulan+1)+"-"+tahun);
        tampil_laporan();
        tampil_total();
        lebarkolom();
    }
    public LaporanHarian(String User, String Status){
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
        this.user = User;
        this.status = Status;
        if("Pegawai".equals(status)){
            mi_dt_pegawai.hide();
            mi_favorit.hide();
            mi_hari_ini.hide();
            mi_lap_tr.hide();
        }
        lb_tgl.setText(hari+"-"+(bulan+1)+"-"+tahun);
        tampil_laporan();
        tampil_total();
        lebarkolom();
    }
    private void tampil_total(){
        int total_harga_bayar ;
        String thb ;
        int jumlahBaris = tabel_lap.getRowCount();
        int hargaBarang;
        int pot = 0;
        TableModel tabelModel;
        tabelModel = tabel_lap.getModel();
        total_harga_bayar = 0;
        for (int i=0; i<jumlahBaris; i++){
            hargaBarang = Integer.parseInt(tabelModel.getValueAt(i, 6).toString());
            total_harga_bayar = total_harga_bayar + hargaBarang;
        }
        thb = Integer.toString(total_harga_bayar);
        tf_total.setText(thb);
       }
    public void lebarkolom(){ 
        TableColumn column;
        tabel_lap.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); 
        column = tabel_lap.getColumnModel().getColumn(0); 
        column.setPreferredWidth(100);
        column = tabel_lap.getColumnModel().getColumn(1); 
        column.setPreferredWidth(152); 
        column = tabel_lap.getColumnModel().getColumn(2); 
        column.setPreferredWidth(150); 
        column = tabel_lap.getColumnModel().getColumn(3); 
        column.setPreferredWidth(150);
        column = tabel_lap.getColumnModel().getColumn(4); 
        column.setPreferredWidth(150);
        column = tabel_lap.getColumnModel().getColumn(5); 
        column.setPreferredWidth(150);
        column = tabel_lap.getColumnModel().getColumn(6); 
        column.setPreferredWidth(150);
        column = tabel_lap.getColumnModel().getColumn(7); 
        column.setPreferredWidth(156);
    }
    private void tampil_laporan(){
        Object[]baris={"ID Transaksi","Nama Pegawai", "Jumlah Pesanan","Total Harga", "Diskon","Total", "Bayar", "Kembalian"};
        model= new DefaultTableModel(null,baris);
        tabel_lap.setModel(model);
        String tgl = lb_tgl.getText();
        String sql = "SELECT pegawai.*, transaksi.* FROM pegawai,transaksi WHERE transaksi.id_pegawai = pegawai.id_pegawai AND tgl_transaksi= '"+tgl+"' ";
        try{
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String id_transaksi = hasil.getString("id_transaksi");
                    String nama_pegawai = hasil.getString("nama_pegawai");
                    String jumlah_pesanan= hasil.getString("jumlah_pesanan");
                    String total_harga = hasil.getString("total_harga");
                    String diskon = hasil.getString("diskon");
                    String tot = hasil.getString("total");
                    String bayar = hasil.getString("bayar");
                    String kembalian = hasil.getString("kembalian");
                    String[]data= { id_transaksi,nama_pegawai,jumlah_pesanan,total_harga,diskon, tot, bayar, kembalian};
                    model.addRow(data);
                } konek.close();
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data pesanan gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    void tampil_lap_cari(){
        Date d = new Date();
        SimpleDateFormat t = new SimpleDateFormat("yyyy");
        SimpleDateFormat b = new SimpleDateFormat("MM");
        SimpleDateFormat h = new SimpleDateFormat("dd");
        String year = t.format(jd_tanggal.getDate());
        String bln = b.format(jd_tanggal.getDate());
        String hri = h.format(jd_tanggal.getDate());
        String tanggal = hri+"-"+bln+"-"+year;
        lb_jajal.setText(tanggal);
        Object[]baris={"ID Transaksi","Nama Pegawai", "Jumlah Pesanan","Total Harga", "Diskon","Total", "Bayar", "Kembalian"};
        model= new DefaultTableModel(null,baris);
        tabel_lap.setModel(model);
        String tgl = lb_tgl.getText();
        String sql = "SELECT pegawai.*, transaksi.* FROM pegawai,transaksi WHERE transaksi.id_pegawai = pegawai.id_pegawai AND tgl_transaksi= '"+tanggal+"' ";
        try{
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String id_transaksi = hasil.getString("id_transaksi");
                    String nama_pegawai = hasil.getString("nama_pegawai");
                    String jumlah_pesanan= hasil.getString("jumlah_pesanan");
                    String total_harga = hasil.getString("total_harga");
                    String diskon = hasil.getString("diskon");
                    String tot = hasil.getString("total");
                    String bayar = hasil.getString("bayar");
                    String kembalian = hasil.getString("kembalian");
                    String[]data= { id_transaksi,nama_pegawai,jumlah_pesanan,total_harga,diskon, tot, bayar, kembalian};
                    model.addRow(data);
                } konek.close();
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data pesanan gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
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
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jd_tanggal = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lb_tgl = new javax.swing.JLabel();
        lb_id_tr = new javax.swing.JLabel();
        lb_jajal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tf_total = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_lap = new javax.swing.JTable();
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

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("LAPORAN BULANAN");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("PILIH TANGGAL");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("CARI");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("DETAIL");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        lb_tgl.setText("jLabel3");

        lb_id_tr.setText("jLabel3");

        lb_jajal.setText("jLabel3");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("TOTAL PENDAPATAN");

        tf_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tf_total.setText("jTextField1");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("PRINT");

        jLabel1.setBackground(new java.awt.Color(205, 133, 63));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LAPORAN HARIAN");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tabel_lap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane2.setViewportView(tabel_lap);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(125, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(36, 36, 36)
                                .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_jajal))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(309, 309, 309)
                                .addComponent(lb_tgl))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(40, 40, 40)
                                .addComponent(jd_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_id_tr)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(235, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addComponent(lb_tgl))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_id_tr)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(lb_jajal)
                        .addGap(14, 14, 14)
                        .addComponent(jButton4)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jd_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(116, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
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
        LaporanHarian n = new LaporanHarian(user, status);
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tampil_lap_cari();
        tampil_total();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int id = Integer.parseInt(lb_id_tr.getText());
        Laporanhariandetail n = new Laporanhariandetail(id, user);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(LaporanHarian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LaporanHarian().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private com.toedter.calendar.JDateChooser jd_tanggal;
    private javax.swing.JLabel lb_id_tr;
    private javax.swing.JLabel lb_jajal;
    private javax.swing.JLabel lb_tgl;
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
    private javax.swing.JTable tabel_lap;
    private javax.swing.JTextField tf_total;
    // End of variables declaration//GEN-END:variables
}
