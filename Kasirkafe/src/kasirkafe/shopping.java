package kasirkafe;

/**
 *
 * @author Citra
 */
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kasirkafe.mejatr.MejaTransaksi;
import javax.swing.table.TableColumn;

public final class shopping extends javax.swing.JFrame {

    private DefaultTableModel tabmode;
    String no = null;
    String user, status;
    
    private void tampil_menu(){
        Object[]baris={"ID Belanja", "Tanggal", "Nama barang", "Harga"};
        tabmode= new DefaultTableModel(null,baris);
        tabel_belanja.setModel(tabmode);
        String sql = "SELECT belanja.id_belanja, belanja.tanggal, belanja.nama_barang, belanja.harga FROM belanja, belanja WHERE belanja.id_belanja=belanja.id_belanja order by kategori_menu asc";
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String id_belanja = hasil.getString("id_belanja");
                    String tanggal = hasil.getString("tanggal");
                    String nama_barang = hasil.getString("nama_barang");
                    String harga = hasil.getString("harga");
                    String[]data= {id_belanja,tanggal,nama_barang,harga};
                    tabmode.addRow(data);
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    private void id_auto(){
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT id_belanja as no FROM menu order by id_belanja asc";
                ResultSet hasil = stat.executeQuery(sql);
                if(hasil.next()){
                    hasil.last();
                    int set_id = hasil.getInt(1)+1;
                    String nomer = String.valueOf(set_id);
                    tf_id_menu.setText(nomer); 
                }else{
                    tf_id_menu.setText("1");
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan id menu gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void reset(){
        tf_harga.setText("");
        tf_id_menu.setText("");
        tf_nama_menu.setText("");
        cb_kategori.setSelectedIndex(0);
        id_auto();
        
    }
    public void tambah_data(){
        String id_belanja, nama_barang, harga;
        int harga ;
        ambil_id();
        id_belanja = tf_id_belanja.getText();
        nama_barang = tf_nama_menu.getText();
        harga = no;
        harga = Integer.parseInt(tf_harga.getText());
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "INSERT INTO `belanja`(`id_belanja`, `nama_barang`, `harga`) VALUES ('"+id_belanja+"','"+nama_barang+"','"+harga+"')";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                tampil_menu();
                reset();
                konek.close();
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
    }
    public void update_data(){
        String id_menu, nama_menu, id_kategori;
        int harga ;
        ambil_id();
        id_menu = tf_id_menu.getText();
        nama_menu = tf_nama_menu.getText();
        id_kategori = no;
        harga = Integer.parseInt(tf_harga.getText());
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "UPDATE `menu` SET `nama_menu`='"+nama_menu+"',`harga`='"+harga+"',`id_kategori`='"+id_kategori+"' WHERE `id_menu`='"+id_menu+"'";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Berhasil Merubah Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                tampil_menu();
                reset();
                konek.close();
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Merubah Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
    }
    public void delete_data(){
         String id_belanja;
        int harga = 0;
        id_belanja = tf_id_belanja.getText();
       
        harga = Integer.parseInt(tf_harga.getText());
        try{
             try (Connection konek = new Koneksi_mysql().getConnection()) {
                 String sql = "DELETE FROM `belanja` WHERE `id_belanja` = '"+id_belanja+"'";
                 PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                 stat.executeUpdate();
                 JOptionPane.showMessageDialog(null, "Berhasil Menghapus Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                 tampil_menu();
                 reset();
             }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
    }
    public shopping() {
//        initComponents();
//        tampil_menu();
//        tampil_kategori();
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        id_auto();
//        lebarkolom();
//        tf_nama_menu.requestFocusInWindow();
    }
    public shopping (String User, String Status ){
         initComponents();
         this.user = User;
         this.status= Status;
         if("Pegawai".equals(status)){
            mi_dt_pegawai.hide();
            mi_favorit.hide();
            mi_hari_ini.hide();
            mi_lap_tr.hide();
        }
        tampil_menu();
        tampil_kategori();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        id_auto();
        lebarkolom();
        tf_nama_menu.requestFocus();
        lebarkolom();
    }
    public void lebarkolom(){ 
        TableColumn column;
        tabel_belanja.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); 
        column = tabel_belanja.getColumnModel().getColumn(0); 
        column.setPreferredWidth(80);
        column = tabel_belanja.getColumnModel().getColumn(1); 
        column.setPreferredWidth(250); 
        column = tabel_belanja.getColumnModel().getColumn(2); 
        column.setPreferredWidth(100); 
        column = tabel_belanja.getColumnModel().getColumn(3); 
        column.setPreferredWidth(200);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_id_menu = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tf_nama_menu = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tf_harga = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_belanja = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
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

        jButton2.setText("jButton2");

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(205, 101, 38));

        jPanel1.setBackground(new java.awt.Color(205, 133, 63));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID BELANJA");

        tf_id_menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_id_menu.setText("1");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("NAMA MENU");

        jLabel1.setBackground(new java.awt.Color(205, 101, 38));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DATA BELANJA");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("HARGA");

        tf_harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_hargaKeyTyped(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Bersihkan");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton4KeyPressed(evt);
            }
        });

        tabel_belanja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "TANGGAL", "ID BELANJA", "NAMA", "HARGA"
            }
        ));
        tabel_belanja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_belanjaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabel_belanjaMouseEntered(evt);
            }
        });
        tabel_belanja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabel_belanjaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_belanja);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("TANGGAL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1404, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(73, 73, 73)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf_id_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tf_nama_menu, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4))
                                    .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(70, 70, 70)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(383, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tf_id_menu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tf_nama_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton4)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(144, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(205, 101, 38));

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
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1421, 757));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tambah_data();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabel_belanjaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_belanjaMouseClicked
        // TODO add your handling code here:
        int tb_menu = tabel_belanja.getSelectedRow();
        tf_id_menu.setText(tabel_belanja.getValueAt(tb_menu, 0).toString());
        tf_nama_menu.setText(tabel_belanja.getValueAt(tb_menu, 1).toString());
        tf_harga.setText(tabel_belanja.getValueAt(tb_menu, 2).toString());        
        cb_kategori.setSelectedItem(tabel_belanja.getValueAt(tb_menu, 3).toString());
    }//GEN-LAST:event_tabel_belanjaMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tabel_belanjaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_belanjaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_belanjaMouseEntered

    private void tf_hargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_hargaKeyTyped
        // TODO add your handling code here:
        char enter= evt.getKeyChar();
         if(!(Character.isDigit(enter))){
             evt.consume();
         }
    }//GEN-LAST:event_tf_hargaKeyTyped

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
          if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             tambah_data();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             reset();
        }
    }//GEN-LAST:event_jButton4KeyPressed

    private void tabel_belanjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabel_belanjaKeyPressed
        // TODO add your handling code here:
        int tb_menu = tabel_belanja.getSelectedRow();
        tf_id_menu.setText(tabel_belanja.getValueAt(tb_menu, 0).toString());
        tf_nama_menu.setText(tabel_belanja.getValueAt(tb_menu, 1).toString());
        tf_harga.setText(tabel_belanja.getValueAt(tb_menu, 2).toString());        
        cb_kategori.setSelectedItem(tabel_belanja.getValueAt(tb_menu, 3).toString());
    }//GEN-LAST:event_tabel_belanjaKeyPressed

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
        shopping n = new shopping(user,status);
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
            java.util.logging.Logger.getLogger(shopping.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new shopping().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
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
    private javax.swing.JTable tabel_belanja;
    private javax.swing.JTextField tf_harga;
    private javax.swing.JLabel tf_id_menu;
    private javax.swing.JTextField tf_nama_menu;
    // End of variables declaration//GEN-END:variables
}
