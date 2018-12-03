/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe.mejatr.panelmeja;

import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import kasirkafe.Koneksi_mysql;
import kasirkafe.Operasi_autocomplete;

/**
 *
 * @author Citra
 */
public class meja2 extends javax.swing.JPanel {
       private DefaultTableModel tabmode;
       String no = null;
       String id_pegawa ="";
       Operasi_autocomplete operasi =new Operasi_autocomplete();
      // MejaTransaksi m = new MejaTransaksi();
       
       /**
     * Creates new form meja1
     */
       public meja2() {
            initComponents();
            tanggal_jam_sekarang();
                cb_barang.requestFocus();
                cb_barang.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

                   @Override
                    public void keyReleased(KeyEvent evt) {

                         String barang = cb_barang.getEditor().getItem().toString();

                        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                           dapil(cb_barang.getEditor().getItem().toString());
                            String menu = cb_barang.getEditor().getItem().toString();
                           cb_barang.setSelectedItem(menu);
                        }


                        if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105 || evt.getKeyCode() == 8) {
                            cb_barang.setModel(operasi.getLista(barang));
                            if (cb_barang.getItemCount() > 0) {
                                cb_barang.getEditor().setItem(barang);
                                cb_barang.showPopup();                     

                            } else {
                                cb_barang.addItem(barang);
                            }
                        }
                    }
                });
               // tampil_menu();
                id_auto();
                tampil_pesanan();
                tf_total_bayar.setText("0");
                tf_total_tampil.setText("0");
                tampil_total();

                tf_harga.disable();
                tf_total.disable();
                tf_total_tampil.disable();
                tf_total_bayar.disable();
                tf_kembali.disable();
        }
       public void dapil(String nombre) {
        String data[] = operasi.buscar(nombre);
        if (data[0] != null) {
            tf_harga.setText(data[2]);
        } else {
            dapil(cb_barang.getItemAt(0));
                    cb_barang.setSelectedIndex(0);
        }
    }
       private void tampil_pesanan(){
        String id_tr = lb_transaksi.getText();
        int id_meja = 2;
        Object[]baris={"no transaksi","nama menu", "harga","jumlah barang", "total",};
        tabmode= new DefaultTableModel(null,baris);
        tb_barang.setModel(tabmode);
        String sql = "SELECT menu.nama_menu, menu.harga, sementara.id_transaksi, sementara.jumlah_pesanan, sementara.total FROM menu, sementara WHERE menu.id_menu=sementara.id_menu AND sementara.id_transaksi = '"+id_tr+"' AND sementara.no_meja = '"+id_meja+"'";
        try{
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String total = hasil.getString("total");
                    String nama_menu = hasil.getString("nama_menu");
                    String id_transaksi= hasil.getString("id_transaksi");
                    String harga = hasil.getString("harga");
                    String jumlah_pesanan = hasil.getString("jumlah_pesanan");
                    String[]data= { id_transaksi,nama_menu,harga,jumlah_pesanan,total};
                    tabmode.addRow(data);
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data pesanan gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
       public void total_h_b(){
        String a = tf_harga.getText();
        String b = tf_unit.getText();
        int harga = Integer.parseInt(a);
        int unit = Integer.parseInt(b);
        int total = harga*unit;
        String c = Integer.toString(total);
        tf_total.setText(c);
       }
       public void kembalian(){
         String a =tf_bayar.getText();
         String b = tf_total_bayar.getText();
         int bay = Integer.parseInt(a);
         int tot = Integer.parseInt(b);
         int kem = bay - tot;
         String k = Integer.toString(kem);
         tf_kembali.setText(k);
       }
       public void ambil_id(){
        
        try{
            String menu = cb_barang.getSelectedItem().toString();
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT id_menu as id FROM menu WHERE `nama_menu`='"+menu+"'";
                ResultSet hasil = stat.executeQuery(sql);
                
                while(hasil.next()){
                    int id = hasil.getInt(1);
                    no = String.valueOf(id);                           
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
       public void ambil_id_peg(){
        
        try{
            String peg = lb_pegawai.getText() ;
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT id_pegawai as id FROM pegawai WHERE `nama_pegawai`='"+peg+"'";
                ResultSet hasil = stat.executeQuery(sql);
                
                while(hasil.next()){
                    int id = hasil.getInt(1);
                    id_pegawa = String.valueOf(id);                       
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
       private void id_auto(){
        try{
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT transaksi.id_transaksi, sementara.no_meja FROM transaksi,sementara WHERE sementara.id_transaksi = transaksi.id_transaksi AND sementara.no_meja=2  order by id_transaksi asc";
                ResultSet hasil = stat.executeQuery(sql);
                if(hasil.next()){
                    hasil.last();
                    int set_id = hasil.getInt(1)+20;
                    String id = String.valueOf(set_id);
                    lb_transaksi.setText(id);   
                }else{
                    lb_transaksi.setText("2");
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
       public void reset(){
        tf_harga.setText("");
        tf_total.setText("");
        tf_unit.setText("");
        cb_barang.setSelectedItem("");
        tampil_pesanan();
    
    }
       public void bersih(){
           tf_total_bayar.setText("");
           tf_total_tampil.setText("");
           tf_bayar.setText("");
           tf_kembali.setText("");
           cb_barang.setSelectedItem("");
           cb_barang.requestFocus();
           id_auto();
        
       }
       private void tampil_total(){
        int total_harga_bayar ;
        String thb ;
        int jumlahBaris = tb_barang.getRowCount();
        int hargaBarang;
        TableModel tabelModel;
        tabelModel = tb_barang.getModel();
        total_harga_bayar = 0;
        for (int i=0; i<jumlahBaris; i++){
            hargaBarang = Integer.parseInt(tabelModel.getValueAt(i, 4).toString());
            total_harga_bayar = total_harga_bayar + hargaBarang;
        }
        thb = Integer.toString(total_harga_bayar);
        tf_total_bayar.setText(thb);
        tf_total_tampil.setText(thb);
       }
       public void hapus_data(){
        ambil_id();
        String id_menu, no_meja, id_transaksi;
        int harga = 0;
        id_menu = no;
        no_meja = "2";
        id_transaksi = lb_transaksi.getText();
        try{
            try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "DELETE FROM `sementara` WHERE `id_menu` = '"+id_menu+"' AND `no_meja` = '"+no_meja+"' AND `id_transaksi` = '"+id_transaksi+"'";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Berhasil Menghapus Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                reset();
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
          tampil_total();
       }
       public void tambah_data(){
        java.sql.Connection konek = new Koneksi_mysql().getConnection();
        String id_menu, no_meja = "2", id_transaksi, nama_menu;
        int total , jumlah_pesan;
        ambil_id();
        id_menu = no;
        id_transaksi = lb_transaksi.getText();
        total = Integer.parseInt(tf_total.getText());
        jumlah_pesan = Integer.parseInt(tf_unit.getText());
        nama_menu =cb_barang.getSelectedItem().toString();
        
        try{
            Statement stat= konek.createStatement();
            String query = "SELECT * FROM `sementara` WHERE id_menu = '"+id_menu+"' AND id_transaksi ='"+id_transaksi+"'";
            ResultSet hasil = stat.executeQuery(query);
            if(hasil.next()){
                    int tot_har = Integer.parseInt(hasil.getString("total"));
                    int jum_pen = Integer.parseInt(hasil.getString("jumlah_pesanan"));
                    tot_har = tot_har+total;
                    jum_pen= jum_pen+ jumlah_pesan;
                    try{
                    String sql = "UPDATE `sementara` SET `jumlah_pesanan`='"+jum_pen+"',`total`='"+tot_har+"' WHERE `id_menu`='"+id_menu+"' AND `id_transaksi`='"+id_transaksi+"' AND `no_meja`='"+no_meja+"'" ;
                    PreparedStatement Prstat = (PreparedStatement) konek.prepareStatement(sql);
                    Prstat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                    }catch(HeadlessException | SQLException e){
                        JOptionPane.showMessageDialog(null, "update Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
                    }
                   
                }else{
                     try{
                    String sql = "INSERT INTO `sementara`(`no_meja`, `id_menu`, `id_transaksi`, `jumlah_pesanan`, `total`) VALUES ('"+no_meja+"','"+id_menu+"','"+id_transaksi+"','"+jumlah_pesan+"','"+total+"')";
                    PreparedStatement Pstat = (PreparedStatement) konek.prepareStatement(sql);
                    Pstat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                    //konek.close();
                    }catch(HeadlessException | SQLException e){
                        JOptionPane.showMessageDialog(null, "insert Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
                    }
                }
            konek.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        tampil_total();
       }
       public void simpan_data(){
           String id_pegawai, tgl_transaksi, id_transaksi;
        int total_harga = 0, jumlah_pesanan =0, bayar=0, kembalian=0;
        ambil_id_peg();
        id_pegawai = id_pegawa;
        id_transaksi = lb_transaksi.getText();
        tgl_transaksi = tanggal.getText();
        total_harga = Integer.parseInt(tf_total_bayar.getText());
        bayar = Integer.parseInt(tf_bayar.getText());
        kembalian = Integer.parseInt(tf_kembali.getText());
        int jumlahBaris = tb_barang.getRowCount();
        int jumlahPesan;
        TableModel tabelModel;
        tabelModel = tb_barang.getModel();
        for (int i=0; i<jumlahBaris; i++){
            jumlahPesan = Integer.parseInt(tabelModel.getValueAt(i, 3).toString());
            jumlah_pesanan = jumlah_pesanan + jumlahPesan;
        }
        try{
               try (java.sql.Connection konek = new Koneksi_mysql().getConnection()) {
                   String sql = "INSERT INTO `transaksi`(`id_transaksi`, `id_pegawai`, `tgl_transaksi`, `jumlah_pesanan`, `total_harga`, `bayar`, `kembalian`) VALUES ('"+id_transaksi+"','"+id_pegawai+"','"+tgl_transaksi+"','"+jumlah_pesanan+"','"+total_harga+"','"+bayar+"','"+kembalian+"')";
                   PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                   stat.executeUpdate();
                   JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
               }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
        id_auto();
        bersih();
        tampil_pesanan();
        tf_total_bayar.setText("0");
        tf_total_tampil.setText("0");
       }
       private void tanggal_jam_sekarang(){
        Thread p;
        p = new Thread(){
            @Override
            public void run(){
                for(;;){
                    Calendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    tanggal.setText(hari+"-"+(bulan+1)+"-"+tahun);
                   
                    
                    int jam = cal.get(Calendar.HOUR);
                    int menit = cal.get(Calendar.MINUTE);
                    int detik = cal.get(Calendar.SECOND);
                    int AM_PM = cal.get(Calendar.AM_PM);
                    
                    String day_night ;
                    if (AM_PM == 1)
                    {
                        day_night = "PM";
                    }
                    else
                    {
                        day_night = "AM";
                    }
                    if(jam == 0){
                        jam=12;
                    }
                    String waktu = jam + ":" + menit + ":" + detik + "" + day_night;
                    jammm.setText(waktu);
                    
                    try{
                        sleep(1000);
                    }catch(InterruptedException ex){
                        Logger.getLogger(meja2.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

        bt_simpan = new javax.swing.JButton();
        bt_keluar = new javax.swing.JButton();
        tf_kembali = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tf_bayar = new javax.swing.JTextField();
        tf_total_bayar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_barang = new javax.swing.JTable();
        tb_hapus = new javax.swing.JButton();
        bt_tambah = new javax.swing.JButton();
        tf_total = new javax.swing.JTextField();
        tf_unit = new javax.swing.JTextField();
        tf_harga = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cb_barang = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_jam = new javax.swing.JLabel();
        jammm = new javax.swing.JLabel();
        tanggal = new javax.swing.JLabel();
        lb_tanggal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lb_transaksi = new javax.swing.JLabel();
        lb_pegawai = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tf_total_tampil = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        bt_simpan.setText("Simpan");
        bt_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpanActionPerformed(evt);
            }
        });
        bt_simpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_simpanKeyPressed(evt);
            }
        });

        bt_keluar.setText("Keluar");
        bt_keluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_keluarKeyPressed(evt);
            }
        });

        jLabel9.setText("Kembalian:");

        jLabel11.setText("bayar:");

        tf_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_bayarActionPerformed(evt);
            }
        });
        tf_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_bayarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_bayarKeyTyped(evt);
            }
        });

        tf_total_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_total_bayarActionPerformed(evt);
            }
        });

        jLabel10.setText("Total :");

        tb_barang.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_barangMouseClicked(evt);
            }
        });
        tb_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_barangKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tb_barang);

        tb_hapus.setText("Hapus");
        tb_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb_hapusActionPerformed(evt);
            }
        });
        tb_hapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_hapusKeyPressed(evt);
            }
        });

        bt_tambah.setText("Tambah");
        bt_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tambahActionPerformed(evt);
            }
        });
        bt_tambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_tambahKeyPressed(evt);
            }
        });

        tf_unit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_unitActionPerformed(evt);
            }
        });
        tf_unit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_unitKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_unitKeyTyped(evt);
            }
        });

        jLabel8.setText("Detail Data Barang");

        cb_barang.setEditable(true);
        cb_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_barangActionPerformed(evt);
            }
        });
        cb_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cb_barangKeyPressed(evt);
            }
        });

        jLabel3.setText("Data Barang :");

        jLabel4.setText("Jumlah");

        lb_jam.setText("Jam :");

        lb_tanggal.setText("Tanggal : ");

        jLabel5.setText("Kasir :");

        jLabel12.setText("No Transaksi:");

        lb_transaksi.setText("2");

        lb_pegawai.setText("citra");

        jLabel7.setText("TOTAL :");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TRANSAKSI MEJA 2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1016, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cb_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tf_unit, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bt_tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tb_hapus))
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel5)
                            .addComponent(lb_tanggal)
                            .addComponent(lb_jam))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jammm, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_pegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)))
                        .addGap(18, 18, 18)
                        .addComponent(tf_total_tampil, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bt_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel9))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf_total_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tf_bayar)
                                        .addComponent(tf_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lb_tanggal)
                        .addGap(18, 18, 18)
                        .addComponent(lb_jam))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(tf_total_tampil, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lb_transaksi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_pegawai)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jammm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cb_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tb_hapus)
                                    .addComponent(bt_tambah)
                                    .addComponent(tf_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(13, 13, 13)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(tf_total_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(tf_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(tf_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_simpan)
                    .addComponent(bt_keluar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
        // TODO add your handling code here:
        int total_harga = Integer.parseInt(tf_total_bayar.getText());
        int bayar = Integer.parseInt(tf_bayar.getText());
        if(total_harga >= bayar){
            JOptionPane.showMessageDialog(null, "Uang Pembayaran Kurang", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
        int hapusselected = JOptionPane.showConfirmDialog(null, "Simpan Data Transaksi?","Close Message", JOptionPane.YES_NO_OPTION);
        if(hapusselected == JOptionPane.YES_OPTION){
            simpan_data();
        }
        }
    }//GEN-LAST:event_bt_simpanActionPerformed

    private void bt_simpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_simpanKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String kem = tf_kembali.getText();
            if(kem == null){
                JOptionPane.showMessageDialog(null, "Uang Pembayaran Kurang", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }else{
            int hapusselected = JOptionPane.showConfirmDialog(null, "Simpan Data Transaksi?","Close Message", JOptionPane.YES_NO_OPTION);
            if(hapusselected == JOptionPane.YES_OPTION){
                simpan_data();
            }
            }
        }
         if(evt.getKeyCode() == KeyEvent.VK_K){
            tf_bayar.requestFocus();
        }
    }//GEN-LAST:event_bt_simpanKeyPressed

    private void tf_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_bayarActionPerformed

    private void tf_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_bayarKeyPressed
        // TODO add your handling code here:
             if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                    String a =tf_bayar.getText();
                    String b = tf_total_bayar.getText();
                    int bay = Integer.parseInt(a);
                    int tot = Integer.parseInt(b);
                    int kem = bay - tot;
                    String k = Integer.toString(kem);
                    //tf_kembali.setText(k);
                    if (kem < 0){
                           JOptionPane.showMessageDialog(null, "Uang Pembayaran Kurang", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                           }else{
                        kembalian();
                    }
                       }
         
         
        if(evt.getKeyCode() == KeyEvent.VK_K){
            cb_barang.requestFocus();
        }
    }//GEN-LAST:event_tf_bayarKeyPressed

    private void tf_bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_bayarKeyTyped
        // TODO add your handling code here:
        char enter= evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_tf_bayarKeyTyped

    private void tf_total_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_total_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_total_bayarActionPerformed

    private void tb_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_barangMouseClicked
        // TODO add your handling code here:
        int tb_menu = tb_barang.getSelectedRow();
        tf_harga.setText(tb_barang.getValueAt(tb_menu, 2).toString());
        tf_unit.setText(tb_barang.getValueAt(tb_menu, 3).toString());
        tf_total.setText(tb_barang.getValueAt(tb_menu, 4).toString());
        cb_barang.setSelectedItem(tb_barang.getValueAt(tb_menu, 1).toString());
    }//GEN-LAST:event_tb_barangMouseClicked

    private void tb_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_barangKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            int tb_menu = tb_barang.getSelectedRow();
            tf_harga.setText(tb_barang.getValueAt(tb_menu, 2).toString());
            tf_unit.setText(tb_barang.getValueAt(tb_menu, 3).toString());
            tf_total.setText(tb_barang.getValueAt(tb_menu, 4).toString());
            cb_barang.setSelectedItem(tb_barang.getValueAt(tb_menu, 1).toString());
            tf_unit.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_SPACE){
            tf_total.requestFocus();
        }
    }//GEN-LAST:event_tb_barangKeyPressed

    private void tb_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb_hapusActionPerformed
        // TODO add your handling code here:
        int hapusselected = JOptionPane.showConfirmDialog(null, "Hapus Data?","Close Message", JOptionPane.YES_NO_OPTION);
        if(hapusselected == JOptionPane.YES_OPTION){
            hapus_data();
        }
    }//GEN-LAST:event_tb_hapusActionPerformed

    private void tb_hapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_hapusKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            hapus_data();
        }
        if(evt.getKeyCode() == KeyEvent.VK_T){
            tf_bayar.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_Y){
            cb_barang.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_K){
            bt_tambah.requestFocus();
        }
    }//GEN-LAST:event_tb_hapusKeyPressed

    private void bt_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tambahActionPerformed
        // TODO add your handling code here:
        tambah_data();
    }//GEN-LAST:event_bt_tambahActionPerformed

    private void bt_tambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_tambahKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tambah_data();
        }
        if(evt.getKeyCode() == KeyEvent.VK_T){
            tf_bayar.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_Y){
            cb_barang.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_E){
            tb_barang.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_K){
            tf_unit.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_G){
           bt_keluar.requestFocus();
        }
    }//GEN-LAST:event_bt_tambahKeyPressed

    private void tf_unitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_unitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_unitActionPerformed

    private void tf_unitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_unitKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            total_h_b();
        }
        if(evt.getKeyCode() == KeyEvent.VK_K){
            cb_barang.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_M){
            String a = tf_harga.getText();
            String b = tf_unit.getText();
            int harga = Integer.parseInt(a);
            int unit = Integer.parseInt(b);
            unit = unit * (-1);
            int total = harga*unit;
            String c = Integer.toString(total);
            tf_unit.setText(Integer.toString(unit));
            tf_total.setText(c);
        }
    }//GEN-LAST:event_tf_unitKeyPressed

    private void tf_unitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_unitKeyTyped
        // TODO add your handling code here:
        char enter= evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_tf_unitKeyTyped

    private void cb_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_barangActionPerformed
        // TODO add your handling code here:
        try {
            String a = cb_barang.getSelectedItem().toString();
            java.sql.Connection konek = new Koneksi_mysql().getConnection();
            String sql = "SELECT * FROM menu WHERE `nama_menu`='"+a+"'";
            PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
            ResultSet hasil = stat.executeQuery();
            while(hasil.next()){
                String harga = hasil.getString("harga");
                tf_harga.setText(harga);
            }

        } catch (SQLException e) {
        }

        // tf_harga.setText(a);
    }//GEN-LAST:event_cb_barangActionPerformed

    private void cb_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cb_barangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_barangKeyPressed

    private void bt_keluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_keluarKeyPressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_bt_keluarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_keluar;
    private javax.swing.JButton bt_simpan;
    private javax.swing.JButton bt_tambah;
    private javax.swing.JComboBox<String> cb_barang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jammm;
    private javax.swing.JLabel lb_jam;
    private javax.swing.JLabel lb_pegawai;
    private javax.swing.JLabel lb_tanggal;
    private javax.swing.JLabel lb_transaksi;
    private javax.swing.JLabel tanggal;
    private javax.swing.JTable tb_barang;
    private javax.swing.JButton tb_hapus;
    private javax.swing.JTextField tf_bayar;
    private javax.swing.JTextField tf_harga;
    private javax.swing.JTextField tf_kembali;
    private javax.swing.JTextField tf_total;
    private javax.swing.JTextField tf_total_bayar;
    private javax.swing.JTextField tf_total_tampil;
    private javax.swing.JTextField tf_unit;
    // End of variables declaration//GEN-END:variables
}
