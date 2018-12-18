package kasirkafe;

/**
 *
 * @author Citra
 */
import Belum_terpakai.Transaksi;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kasirkafe.mejatr.MejaTransaksi;

public class form_menu_makanan extends javax.swing.JFrame {

    private DefaultTableModel tabmode;
    String no = null;
    String user, status;
    
    private void tampil_menu(){
        Object[]baris={"kode menu", "nama menu", "harga menu", "kategori"};
        tabmode= new DefaultTableModel(null,baris);
        tabel_menu.setModel(tabmode);
        String sql = "SELECT menu.id_menu, menu.nama_menu, menu.harga, kategori.kategori_menu FROM menu, kategori WHERE menu.id_kategori=kategori.id_kategori order by kategori_menu asc";
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String id_menu = hasil.getString("id_menu");
                    String nama_menu = hasil.getString("nama_menu");
                    String harga = hasil.getString("harga");
                    String kategori = hasil.getString("kategori_menu");
                    String[]data= {id_menu,nama_menu,harga,kategori};
                    tabmode.addRow(data);
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    private void tampil_kategori(){
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "SELECT * FROM kategori";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                ResultSet hasil = stat.executeQuery();
                while(hasil.next()){
                    String kategori = hasil.getString("kategori_menu");
                    cb_kategori.addItem(kategori);
                    ambil_id();
                }
            }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void id_auto(){
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT id_menu as no FROM menu order by id_menu asc";
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
    
    public void ambil_id(){
        
        try{
            String kategori = cb_kategori.getSelectedItem().toString();
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT id_kategori as id FROM kategori WHERE `kategori_menu`='"+kategori+"'";
                ResultSet hasil = stat.executeQuery(sql);
                
                while(hasil.next()){
                    //  hasil.first();
                    int id = hasil.getInt(1);
                    no = String.valueOf(id);
                    
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan id kategori gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
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
        String id_menu, nama_menu, id_kategori;
        int harga ;
        ambil_id();
        id_menu = tf_id_menu.getText();
        nama_menu = tf_nama_menu.getText();
        id_kategori = no;
        harga = Integer.parseInt(tf_harga.getText());
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "INSERT INTO `menu`(`id_menu`, `nama_menu`, `harga`, `id_kategori`) VALUES ('"+id_menu+"','"+nama_menu+"','"+harga+"','"+id_kategori+"')";
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
         String id_menu;
        int harga = 0;
        id_menu = tf_id_menu.getText();
       
        harga = Integer.parseInt(tf_harga.getText());
        try{
             try (Connection konek = new Koneksi_mysql().getConnection()) {
                 String sql = "DELETE FROM `menu` WHERE `id_menu` = '"+id_menu+"'";
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
    
    public form_menu_makanan() {
        initComponents();
        tampil_menu();
        tampil_kategori();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        id_auto();
        tf_nama_menu.requestFocus();
    }
    
    public form_menu_makanan (String User, String Status ){
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
        tf_nama_menu.requestFocus();
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_menu = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btdelete = new javax.swing.JButton();
        tbupdate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tf_nama_menu = new javax.swing.JTextField();
        cb_kategori = new javax.swing.JComboBox<>();
        tf_harga = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        tf_id_menu = new javax.swing.JLabel();
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

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Menu Makanan dan Minuman");

        tabel_menu.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_menuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabel_menuMouseEntered(evt);
            }
        });
        tabel_menu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabel_menuKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_menu);

        jButton1.setText("Tambah Menu");
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

        btdelete.setText("Hapus");
        btdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdeleteActionPerformed(evt);
            }
        });
        btdelete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btdeleteKeyPressed(evt);
            }
        });

        tbupdate.setText("Ubah");
        tbupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbupdateActionPerformed(evt);
            }
        });
        tbupdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbupdateKeyPressed(evt);
            }
        });

        jLabel2.setText("ID MENU");

        jLabel4.setText("KATEGORI");

        jLabel5.setText("NAMA MENU");

        jLabel6.setText("HARGA");

        cb_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kategori" }));
        cb_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_kategoriActionPerformed(evt);
            }
        });

        tf_harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_hargaKeyTyped(evt);
            }
        });

        jButton4.setText("bersihkan");
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

        jButton5.setText("Tambah Kategori");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton5KeyPressed(evt);
            }
        });

        tf_id_menu.setText("1");

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(30, 30, 30)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tbupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_id_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cb_kategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tf_nama_menu)
                                    .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(22, 22, 22))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tf_id_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cb_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(tf_nama_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(tbupdate))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btdelete)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1295, 431));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tambah_data();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabel_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_menuMouseClicked
        // TODO add your handling code here:
        int tb_menu = tabel_menu.getSelectedRow();
        tf_id_menu.setText(tabel_menu.getValueAt(tb_menu, 0).toString());
        tf_nama_menu.setText(tabel_menu.getValueAt(tb_menu, 1).toString());
        tf_harga.setText(tabel_menu.getValueAt(tb_menu, 2).toString());        
        cb_kategori.setSelectedItem(tabel_menu.getValueAt(tb_menu, 3).toString());
    }//GEN-LAST:event_tabel_menuMouseClicked

    private void tbupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbupdateActionPerformed
        int hapusselected = JOptionPane.showConfirmDialog(null, "Hapus Data?","Close Message", JOptionPane.YES_NO_OPTION);
         if(hapusselected == JOptionPane.YES_OPTION){
        update_data();
         }
    }//GEN-LAST:event_tbupdateActionPerformed

    private void cb_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_kategoriActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdeleteActionPerformed
        // TODO add your handling code here:
        int hapusselected = JOptionPane.showConfirmDialog(null, "Hapus Data?","Close Message", JOptionPane.YES_NO_OPTION);
         if(hapusselected == JOptionPane.YES_OPTION){
        delete_data();
         }
    }//GEN-LAST:event_btdeleteActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Input_kategori n = new Input_kategori();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tabel_menuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_menuMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_menuMouseEntered

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

    private void tbupdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbupdateKeyPressed
         // TODO add your handling code here:
          if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             update_data();
        }
    }//GEN-LAST:event_tbupdateKeyPressed

    private void btdeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btdeleteKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             delete_data();
        }
    }//GEN-LAST:event_btdeleteKeyPressed

    private void jButton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             reset();
        }
    }//GEN-LAST:event_jButton4KeyPressed

    private void jButton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton5KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             Input_kategori n = new Input_kategori();
             n.setVisible(true);
             this.setVisible(false);
        }
    }//GEN-LAST:event_jButton5KeyPressed

    private void tabel_menuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabel_menuKeyPressed
        // TODO add your handling code here:
        int tb_menu = tabel_menu.getSelectedRow();
        tf_id_menu.setText(tabel_menu.getValueAt(tb_menu, 0).toString());
        tf_nama_menu.setText(tabel_menu.getValueAt(tb_menu, 1).toString());
        tf_harga.setText(tabel_menu.getValueAt(tb_menu, 2).toString());        
        cb_kategori.setSelectedItem(tabel_menu.getValueAt(tb_menu, 3).toString());
    }//GEN-LAST:event_tabel_menuKeyPressed

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
        LaporanTransaksiPemilik n = new LaporanTransaksiPemilik(user, status);
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
            java.util.logging.Logger.getLogger(form_menu_makanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new form_menu_makanan().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btdelete;
    private javax.swing.JComboBox<String> cb_kategori;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
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
    private javax.swing.JTable tabel_menu;
    private javax.swing.JButton tbupdate;
    private javax.swing.JTextField tf_harga;
    private javax.swing.JLabel tf_id_menu;
    private javax.swing.JTextField tf_nama_menu;
    // End of variables declaration//GEN-END:variables
}
