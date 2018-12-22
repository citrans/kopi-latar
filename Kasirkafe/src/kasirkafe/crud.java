/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import kasirkafe.mejatr.MejaTransaksi;
import javax.swing.table.TableColumn;

/**
 *
 * @author Samsung
 */
public final class crud extends javax.swing.JFrame {
    private DefaultTableModel tabmode;
    String no = null;
    String user,setatus;
    /**
     * Creates new form crud
     */
    public crud() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
        tampilkan();
        id_auto();
        reset();
        lebarkolom();
    }
    
    public crud(String User, String Status) {
        initComponents();
        this.user = User;
        this.setatus=Status;
         if("Pegawai".equals(setatus)){
            mi_dt_pegawai.hide();
            mi_favorit.hide();
            mi_hari_ini.hide();
            mi_lap_tr.hide();
        }
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
        tampilkan();
        id_auto();
        reset();
        lebarkolom();
    }
    void update_data(){
        String ID,nama_pegawai,alamatt,userr,pass,statuss,no_hp;
        ID = id.getText();
        nama_pegawai = nama.getText();
        alamatt = alamat.getText();
        no_hp = hp2.getText();
        userr = nama.getText();
        pass = password.getText();
        statuss = status.getSelectedItem().toString();
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "UPDATE pegawai SET nama_pegawai='"+nama_pegawai+"',alamat='"+alamatt+"',no_hp='"+no_hp+"',username='"+userr+"',password='"+pass+"',status='"+statuss+"' WHERE id_pegawai='"+ID+"'";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Berhasil merubah data","informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilkan();
                reset();
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal merubah data","informasi",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void insert_data(){
        String ID,nama_pegawai,hp0,alamatt,userr,pass,statuss,hpp;
        String no_hp ;
        id_auto();
        ID = id.getText();
        nama_pegawai = nama.getText();
        alamatt = alamat.getText();
        no_hp = hp2.getText();
        userr = username.getText();
        pass = password.getText();
        statuss = status.getSelectedItem().toString();
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "INSERT INTO pegawai(id_pegawai,nama_pegawai,alamat,no_hp,username,password,status) VALUES ('"+ID+"','"+nama_pegawai+"','"+alamatt+"','"+no_hp+"','"+userr+"','"+pass+"','"+statuss+"')";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Berhasil menyimpan data","informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilkan();
                reset();
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data","informasi",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void delete_data(){
        String ID;
        ID = id.getText();
        
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "DELETE FROM `pegawai` WHERE `id_pegawai` = '"+ID+"'";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data","informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilkan();
                reset();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal menghapus data","informasi",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void tampilkan() {
        Object[]baris={"ID","Nama","Alamat","No Hp","Username","Password","Status"};
        tabmode = new DefaultTableModel(null,baris);
        jTable1.setModel(tabmode);
        String sql = "SELECT * FROM `pegawai`";
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat = konek.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String id_pegawai = hasil.getString("id_pegawai");
                    String nama_pegawai = hasil.getString("nama_pegawai");
                    String alamatt = hasil.getString("alamat");
                    String no_hp = hasil.getString("no_hp");
                    String usernamee = hasil.getString("username");
                    String passwordd = hasil.getString("password");
                    String statuss = hasil.getString("status");
                    String[]data= {id_pegawai,nama_pegawai,alamatt,no_hp,usernamee,passwordd,statuss};
                    tabmode.addRow(data);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void id_auto(){
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat = konek.createStatement();
                String sql = "SELECT id_pegawai as no FROM pegawai order by id_pegawai asc";
                ResultSet hasil = stat.executeQuery(sql);
                if(hasil.next()){
                    hasil.last();
                    int set_id = hasil.getInt(1)+1;
                    String noo = String.valueOf(set_id);
                    id.setText(noo);
                }else{
                    id.setText("1");
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal","informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void reset(){
        id.setText("");
        nama.setText("");
        username.setText("");
        alamat.setText("");
        hp2.setText("");
        nama.setText("");
        password.setText("");
        id_auto();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        username = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        hp2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        alamat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
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

        jLabel10.setText("jLabel10");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(205, 133, 63));

        jPanel1.setBackground(new java.awt.Color(205, 133, 63));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DATA PEGAWAI");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID PEGAWAI");

        id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        id.setText("jLabel8");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("NAMA");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("USERNAME");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("PASSWORD");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("NO HP");

        hp2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hp2KeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ALAMAT");

        alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("STATUS");

        status.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PEGAWAI", "PEMILIK" }));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Ubah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Bersihkan");
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

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Kembali");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(117, 117, 117)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hp2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                            .addGap(87, 87, 87)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(id))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update_data();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       insert_data();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int tb_pegawai = jTable1.getSelectedRow();
        String a =jTable1.getValueAt(tb_pegawai, 0).toString();
        id.setText(a);
        nama.setText(jTable1.getValueAt(tb_pegawai, 1).toString());
        username.setText(jTable1.getValueAt(tb_pegawai, 4).toString());
        alamat.setText(jTable1.getValueAt(tb_pegawai, 2).toString());
        hp2.setText(jTable1.getValueAt(tb_pegawai, 3).toString());
        nama.setText(jTable1.getValueAt(tb_pegawai, 4).toString());
        password.setText(jTable1.getValueAt(tb_pegawai, 5).toString());
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
          delete_data();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new Halaman_utama(user, setatus).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton5KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            reset();
        }
    }//GEN-LAST:event_jButton5KeyPressed

    private void hp2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hp2KeyTyped
        // TODO add your handling code here:
        char enter= evt.getKeyChar();
         if(!(Character.isDigit(enter))){
             evt.consume();
         }
    }//GEN-LAST:event_hp2KeyTyped

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void mi_berandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_berandaActionPerformed
        // TODO add your handling code here:
        Halaman_utama n = new Halaman_utama(user, setatus);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_berandaActionPerformed

    private void mi_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_transaksiActionPerformed
        // TODO add your handling code here:
        MejaTransaksi n = new MejaTransaksi(user, setatus);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_transaksiActionPerformed

    private void mb_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mb_transaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mb_transaksiActionPerformed

    private void mi_menu_kafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_menu_kafeActionPerformed
        // TODO add your handling code here:
        form_menu_makanan n = new form_menu_makanan(user,setatus);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_menu_kafeActionPerformed

    private void mi_dt_pegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_dt_pegawaiActionPerformed
        // TODO add your handling code here:
        crud n = new crud(user, setatus);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_dt_pegawaiActionPerformed

    private void mb_data_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mb_data_menuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mb_data_menuActionPerformed

    private void mi_hari_iniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_hari_iniActionPerformed
        // TODO add your handling code here:
        LaporanHarian n =new LaporanHarian(user, setatus);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_hari_iniActionPerformed

    private void mi_favoritActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_favoritActionPerformed
        // TODO add your handling code here:
        produkfavorit n = new produkfavorit(user, setatus);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_favoritActionPerformed

    private void mi_lap_trActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_lap_trActionPerformed
        // TODO add your handling code here:
        LaporanTransaksiPemilik n = new LaporanTransaksiPemilik(user, setatus);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_lap_trActionPerformed

    private void mi_kasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_kasirActionPerformed
        // TODO add your handling code here:
        LaporanTransaksiKasir n = new LaporanTransaksiKasir(user,setatus);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_kasirActionPerformed

    private void mi_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_keluarActionPerformed
        // TODO add your handling code here:
        login n = new login();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mi_keluarActionPerformed

    private void alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatActionPerformed

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
            java.util.logging.Logger.getLogger(crud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new crud().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamat;
    private javax.swing.JTextField hp2;
    private javax.swing.JLabel id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
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
    private javax.swing.JTextField nama;
    private javax.swing.JTextField password;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables


    

//  private void tampilkan() {
//        Object[]baris={"ID","Nama","Alamat","No Hp","Username","Password","Status"};
//        tabmode = new DefaultTableModel(null,baris);
//        jTable1.setModel(tabmode);
//        String sql = "SELECT * FROM `pegawai`";
//        try{
//            Connection konek = new Koneksi_mysql().getConnection();
//            Statement stat = konek.createStatement();
//            ResultSet hasil = stat.executeQuery(sql);
//            while(hasil.next()){
//                String id_pegawai = hasil.getString("id_pegawai");
//                String nama_pegawai = hasil.getString("nama_pegawai");
//                String alamat = hasil.getString("alamat");
//                String no_hp = hasil.getString("no_hp");
//                String username = hasil.getString("username");
//                String password = hasil.getString("password");
//                String status = hasil.getString("status");
//                String[]data= {id_pegawai,nama_pegawai,alamat,no_hp,username,password,status};
//                tabmode.addRow(data);
//            }
//            konek.close();
//        }catch(SQLException e){
//            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
//        }
//    }
//    public void id_auto(){
//        try{
//            Connection konek = new Koneksi_mysql().getConnection();
//            Statement stat = konek.createStatement();
//            String sql = "SELECT id_pegawai as no FROM pegawai order by id_pegawai asc";
//            ResultSet hasil = stat.executeQuery(sql);
//            if(hasil.next()){
//                 hasil.last();
//                    int set_id = hasil.getInt(1)+1;
//                    String no = String.valueOf(set_id);
//                    id.setText(no);
//            }else{
//                    id.setText("1");
//                }
//            
//            konek.close();
//        } catch(SQLException e){
//            JOptionPane.showMessageDialog(null, "menampilkan data gagal","informasi", JOptionPane.INFORMATION_MESSAGE);
//        }
//    }
//    public void reset(){
//        id.setText("");
//        nama.setText("");
//        username.setText("");
//        alamat.setText("");
//        hp2.setText("");
//        nama.setText("");
//        password.setText("");
//        id_auto();
//    }
    public void lebarkolom(){ 
        TableColumn column;
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); 
        column = jTable1.getColumnModel().getColumn(0); 
        column.setPreferredWidth(30);
        column = jTable1.getColumnModel().getColumn(1); 
        column.setPreferredWidth(100); 
        column = jTable1.getColumnModel().getColumn(2); 
        column.setPreferredWidth(75); 
        column = jTable1.getColumnModel().getColumn(3); 
        column.setPreferredWidth(125);
        column = jTable1.getColumnModel().getColumn(4); 
        column.setPreferredWidth(75);
        column = jTable1.getColumnModel().getColumn(5); 
        column.setPreferredWidth(75);
        column = jTable1.getColumnModel().getColumn(6); 
        column.setPreferredWidth(75);
    }
}
