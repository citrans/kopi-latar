/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;

import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Citra
 */
public class Transaksi extends javax.swing.JFrame {
    private DefaultTableModel tabmode;
  String no = null;
  String id_pegawa ="";
  Operasi_autocomplete operasi =new Operasi_autocomplete();
 
    /**
     * Creates new form 
     */
       public Transaksi() {
            initComponents();
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            tanggal_jam_sekarang();
            cb_barang.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

               @Override
                public void keyReleased(KeyEvent evt) {

                    String cadenaEscrita = cb_barang.getEditor().getItem().toString();

                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                       if(comparar(cadenaEscrita)){// compara si el texto escrito se ecuentra en la lista
                           // busca el texto escrito en la base de datos
                           buscar(cadenaEscrita);
                       }else{// en caso contrario toma como default el elemento 0 o sea el primero de la lista y lo busca.
                        buscar(cb_barang.getItemAt(0));
                        cb_barang.setSelectedIndex(0);
                        }
                    }


                    if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105 || evt.getKeyCode() == 8) {
                        cb_barang.setModel(operasi.getLista(cadenaEscrita));
                        if (cb_barang.getItemCount() > 0) {
                            cb_barang.getEditor().setItem(cadenaEscrita);
                            cb_barang.showPopup();                     

                        } else {
                            cb_barang.addItem(cadenaEscrita);
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
       public void buscar(String nombre) {
        String datos[] = operasi.buscar(nombre);
        if (datos[0] != null) {
            tf_harga.setText(datos[2]);
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se encontro ningun archivo", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
       private boolean comparar(String cadena){
    Object [] lista=cb_barang.getComponents();
    boolean encontrado=false;
        for (Object object : lista) {
            if(cadena.equals(object)){
                encontrado=true;
              break;
            }
        }
       return encontrado;
    }
       private void tampil_pesanan(){
        String id_tr = lb_transaksi.getText();
        Object[]baris={"no transaksi","nama menu", "harga","jumlah barang", "total",};
        tabmode= new DefaultTableModel(null,baris);
        tb_barang.setModel(tabmode);
        String sql = "SELECT menu.nama_menu, menu.harga, sementara.id_transaksi, sementara.jumlah_pesanan, sementara.total FROM menu, sementara WHERE menu.id_menu=sementara.id_menu AND sementara.id_transaksi = '"+id_tr+"'";
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
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
       private void tampil_menu(){
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "SELECT * FROM menu";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                ResultSet hasil = stat.executeQuery();
                while(hasil.next()){
                    String menu = hasil.getString("nama_menu");
                    cb_barang.addItem(menu);
                    ambil_id();
                }
            }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "menampilkan data menu gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
       private void tampil_harga(){
           
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "SELECT * FROM menu WHERE `id_menu`='"+no+"'";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                ResultSet hasil = stat.executeQuery();
                while(hasil.next()){
                    String harga = hasil.getString("harga");
                    tf_harga.setText(harga);
                    //ambil_id();
                }
            }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
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
         int bay = Integer.parseInt(tf_bayar.getText());
         int tot = Integer.parseInt(tf_total_bayar.getText());
         int kem = bay - tot;
         String k = Integer.toString(kem);
         tf_kembali.setText(k);
       }
       public void ambil_id(){
        
        try{
            String menu = cb_barang.getSelectedItem().toString();
            try (Connection konek = new Koneksi_mysql().getConnection()) {
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
            try (Connection konek = new Koneksi_mysql().getConnection()) {
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
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT id_transaksi as no FROM transaksi order by id_transaksi asc";
                ResultSet hasil = stat.executeQuery(sql);
                if(hasil.next()){
                    hasil.last();
                    int set_id = hasil.getInt(1)+20;
                    String id = String.valueOf(set_id);
                    lb_transaksi.setText(id);   
                }else{
                    lb_transaksi.setText("1");
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
        no_meja = "1";
        id_transaksi = lb_transaksi.getText();
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
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
        Connection konek = new Koneksi_mysql().getConnection();
        String id_menu, no_meja = "1", id_transaksi, nama_menu;
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
                    //konek.close();
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
               try (Connection konek = new Koneksi_mysql().getConnection()) {
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
                    String waktu = jam + ":" + menit + ":" + detik + "" + day_night;
                    jammm.setText(waktu);
                    
                    try{
                        sleep(1000);
                    }catch(InterruptedException ex){
                        Logger.getLogger(Transaksi.class.getName()).log(Level.SEVERE, null, ex);
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

        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bt_satu = new javax.swing.JButton();
        bt_enam = new javax.swing.JButton();
        bt_sebelas = new javax.swing.JButton();
        bt_enambelas = new javax.swing.JButton();
        lb_tanggal = new javax.swing.JLabel();
        bt_DUA = new javax.swing.JButton();
        bt_tujuh = new javax.swing.JButton();
        bt_duabelas = new javax.swing.JButton();
        bt_tujuhbelas = new javax.swing.JButton();
        bt_tiga = new javax.swing.JButton();
        bt_delapan = new javax.swing.JButton();
        bt_tigabelas = new javax.swing.JButton();
        bt_delapanbelas = new javax.swing.JButton();
        bt_empat = new javax.swing.JButton();
        bt_sembilan = new javax.swing.JButton();
        bt_empatbelas = new javax.swing.JButton();
        bt_sembilanbelas = new javax.swing.JButton();
        bt_lima = new javax.swing.JButton();
        bt_sepuluh = new javax.swing.JButton();
        bt_limabelas = new javax.swing.JButton();
        bt_duapuluh = new javax.swing.JButton();
        lb_jam = new javax.swing.JLabel();
        tanggal = new javax.swing.JLabel();
        jammm = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cb_barang = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        tf_unit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lb_pegawai = new javax.swing.JLabel();
        tf_harga = new javax.swing.JTextField();
        tf_total = new javax.swing.JTextField();
        bt_tambah = new javax.swing.JButton();
        tb_hapus = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        tf_total_tampil = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_barang = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tf_total_bayar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tf_bayar = new javax.swing.JTextField();
        tf_kembali = new javax.swing.JTextField();
        bt_simpan = new javax.swing.JButton();
        bt_keluar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        lb_transaksi = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mb_transaksi = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        mb_data_menu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        mb_tr_today = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        mb_keluar = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TRANSAKSI MEJA 1");

        jLabel2.setText("NOMOR MEJA");

        bt_satu.setText("1");
        bt_satu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_satuActionPerformed(evt);
            }
        });

        bt_enam.setText("6");

        bt_sebelas.setText("11");

        bt_enambelas.setText("16");

        lb_tanggal.setText("Tanggal : ");

        bt_DUA.setText("2");
        bt_DUA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_DUAActionPerformed(evt);
            }
        });

        bt_tujuh.setText("7");

        bt_duabelas.setText("12");

        bt_tujuhbelas.setText("17");
        bt_tujuhbelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tujuhbelasActionPerformed(evt);
            }
        });

        bt_tiga.setText("3");
        bt_tiga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tigaActionPerformed(evt);
            }
        });

        bt_delapan.setText("8");

        bt_tigabelas.setText("13");

        bt_delapanbelas.setText("18");
        bt_delapanbelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_delapanbelasActionPerformed(evt);
            }
        });

        bt_empat.setText("4");
        bt_empat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_empatActionPerformed(evt);
            }
        });

        bt_sembilan.setText("9");

        bt_empatbelas.setText("14");

        bt_sembilanbelas.setText("19");
        bt_sembilanbelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_sembilanbelasActionPerformed(evt);
            }
        });

        bt_lima.setText("5");
        bt_lima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_limaActionPerformed(evt);
            }
        });

        bt_sepuluh.setText("10");

        bt_limabelas.setText("15");

        bt_duapuluh.setText("20");
        bt_duapuluh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_duapuluhActionPerformed(evt);
            }
        });

        lb_jam.setText("Jam :");

        jLabel3.setText("Data Barang :");

        cb_barang.setEditable(true);
        cb_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_barangActionPerformed(evt);
            }
        });

        jLabel4.setText("Jumlah");

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

        jLabel5.setText("Kasir :");

        lb_pegawai.setText("citra");

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

        jLabel7.setText("TOTAL :");

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
        jScrollPane1.setViewportView(tb_barang);

        jLabel8.setText("Detail Data Barang");

        jLabel10.setText("Total :");

        tf_total_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_total_bayarActionPerformed(evt);
            }
        });

        jLabel11.setText("bayar:");

        jLabel9.setText("Kembalian:");

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

        jLabel12.setText("No Transaksi:");

        lb_transaksi.setText("1");

        jButton1.setText("jButton1");

        mb_transaksi.setIcon(new javax.swing.ImageIcon("C:\\Users\\Citra\\Desktop\\tr.png")); // NOI18N
        mb_transaksi.setText("Transaksi");
        mb_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mb_transaksiActionPerformed(evt);
            }
        });

        jMenuItem7.setText("Transaksi Meja 1");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        mb_transaksi.add(jMenuItem7);

        jMenuItem9.setText("Transaksi Meja 2");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        mb_transaksi.add(jMenuItem9);

        jMenuBar1.add(mb_transaksi);

        mb_data_menu.setIcon(new javax.swing.ImageIcon("C:\\Users\\Citra\\Desktop\\menu.png")); // NOI18N
        mb_data_menu.setText("Data Menu");
        mb_data_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mb_data_menuActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Menu Kafe");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mb_data_menu.add(jMenuItem1);

        jMenuItem6.setText("Kategori Menu");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        mb_data_menu.add(jMenuItem6);

        jMenuBar1.add(mb_data_menu);

        mb_tr_today.setIcon(new javax.swing.ImageIcon("C:\\Users\\Citra\\Desktop\\laporan.png")); // NOI18N
        mb_tr_today.setText("Laporan");

        jMenuItem3.setText("Laporan Hari ini");
        mb_tr_today.add(jMenuItem3);

        jMenuItem4.setText("Laporan Favorit");
        mb_tr_today.add(jMenuItem4);

        jMenuItem5.setText("Laporan Transaksi");
        mb_tr_today.add(jMenuItem5);

        jMenuBar1.add(mb_tr_today);

        mb_keluar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Citra\\Desktop\\keluar.png")); // NOI18N
        mb_keluar.setText("Keluar");
        mb_keluar.add(jSeparator1);

        jMenuItem8.setText("jMenuItem8");
        mb_keluar.add(jMenuItem8);

        jMenuBar1.add(mb_keluar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bt_enambelas)
                            .addComponent(bt_sebelas)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(bt_enam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bt_satu, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bt_tujuhbelas)
                            .addComponent(bt_duabelas)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(bt_tujuh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bt_DUA, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_delapanbelas)
                                    .addComponent(bt_tigabelas)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bt_delapan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bt_tiga, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_sembilanbelas)
                                    .addComponent(bt_empatbelas)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bt_sembilan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bt_empat, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_duapuluh)
                                    .addComponent(bt_limabelas)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bt_sepuluh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bt_lima, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(153, 153, 153)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cb_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(tf_unit, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_tambah)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tb_hapus))
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
                                        .addComponent(tf_total_tampil, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                                    .addComponent(tf_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))))))))
                .addContainerGap(340, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb_transaksi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_pegawai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jammm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(17, 17, 17)))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bt_empat, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_sembilan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_empatbelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_sembilanbelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bt_tiga, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_delapan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_tigabelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_delapanbelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bt_satu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_enam, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_sebelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_enambelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bt_DUA, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_tujuh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_duabelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_tujuhbelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(bt_lima, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(bt_sepuluh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(bt_limabelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(bt_duapuluh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cb_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tb_hapus)
                                            .addComponent(bt_tambah)
                                            .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tf_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                    .addComponent(tf_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                        .addGap(11, 11, 11)
                        .addComponent(tf_total_tampil, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_simpan)
                    .addComponent(bt_keluar))
                .addContainerGap(286, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_satuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_satuActionPerformed
         // TODO add your handling code here:
    }//GEN-LAST:event_bt_satuActionPerformed

    private void bt_DUAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_DUAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_DUAActionPerformed

    private void bt_tujuhbelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tujuhbelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_tujuhbelasActionPerformed

    private void bt_tigaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tigaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_tigaActionPerformed

    private void bt_delapanbelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_delapanbelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_delapanbelasActionPerformed

    private void bt_empatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_empatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_empatActionPerformed

    private void bt_sembilanbelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_sembilanbelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_sembilanbelasActionPerformed

    private void bt_limaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_limaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_limaActionPerformed

    private void bt_duapuluhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_duapuluhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_duapuluhActionPerformed

    private void bt_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tambahActionPerformed
        // TODO add your handling code here:       
        tambah_data();
    }//GEN-LAST:event_bt_tambahActionPerformed

    private void tf_total_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_total_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_total_bayarActionPerformed
    
    private void cb_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_barangActionPerformed
        // TODO add your handling code here:
        try {
            String a = cb_barang.getSelectedItem().toString();
            Connection konek = new Koneksi_mysql().getConnection();
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

    private void tf_unitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_unitActionPerformed
        // TODO add your handling code here:
      
        
        
    }//GEN-LAST:event_tf_unitActionPerformed

    private void tb_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb_hapusActionPerformed
         // TODO add your handling code here:
         int hapusselected = JOptionPane.showConfirmDialog(null, "Hapus Data?","Close Message", JOptionPane.YES_NO_OPTION);
         if(hapusselected == JOptionPane.YES_OPTION){
        hapus_data();
         }
    }//GEN-LAST:event_tb_hapusActionPerformed

    private void tb_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_barangMouseClicked
         // TODO add your handling code here:
          int tb_menu = tb_barang.getSelectedRow();
        tf_harga.setText(tb_barang.getValueAt(tb_menu, 2).toString());
        tf_unit.setText(tb_barang.getValueAt(tb_menu, 3).toString());
        tf_total.setText(tb_barang.getValueAt(tb_menu, 4).toString());        
        cb_barang.setSelectedItem(tb_barang.getValueAt(tb_menu, 1).toString());
                                          
    }//GEN-LAST:event_tb_barangMouseClicked

    private void tf_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_bayarActionPerformed
         // TODO add your handling code here:
         
    }//GEN-LAST:event_tf_bayarActionPerformed

    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
          // TODO add your handling code here:
         int hapusselected = JOptionPane.showConfirmDialog(null, "Simpan Data Transaksi?","Close Message", JOptionPane.YES_NO_OPTION);
         if(hapusselected == JOptionPane.YES_OPTION){
        simpan_data();
         }
    }//GEN-LAST:event_bt_simpanActionPerformed

    private void tf_unitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_unitKeyPressed
         // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             total_h_b();
        }
    }//GEN-LAST:event_tf_unitKeyPressed

    private void tf_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_bayarKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             kembalian();
        }
    }//GEN-LAST:event_tf_bayarKeyPressed

    private void bt_tambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_tambahKeyPressed
         // TODO add your handling code here:
          if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             tambah_data();
        }
    }//GEN-LAST:event_bt_tambahKeyPressed

    private void bt_simpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_simpanKeyPressed
         // TODO add your handling code here:
          if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             simpan_data();
        }
    }//GEN-LAST:event_bt_simpanKeyPressed

    private void tb_hapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_hapusKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             hapus_data();
        }
    }//GEN-LAST:event_tb_hapusKeyPressed

    private void tf_unitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_unitKeyTyped
         // TODO add your handling code here:
         char enter= evt.getKeyChar();
         if(!(Character.isDigit(enter))){
             evt.consume();
         }
    }//GEN-LAST:event_tf_unitKeyTyped

    private void tf_bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_bayarKeyTyped
         // TODO add your handling code here:
         char enter= evt.getKeyChar();
         if(!(Character.isDigit(enter))){
             evt.consume();
         }
    }//GEN-LAST:event_tf_bayarKeyTyped

    private void mb_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mb_transaksiActionPerformed
        // TODO add your handling code here:
        Transaksi a = new Transaksi();
        a.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mb_transaksiActionPerformed

    private void mb_data_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mb_data_menuActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_mb_data_menuActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
          form_menu_makanan n = new form_menu_makanan();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
          Input_kategori n = new Input_kategori();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Transaksi().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_DUA;
    private javax.swing.JButton bt_delapan;
    private javax.swing.JButton bt_delapanbelas;
    private javax.swing.JButton bt_duabelas;
    private javax.swing.JButton bt_duapuluh;
    private javax.swing.JButton bt_empat;
    private javax.swing.JButton bt_empatbelas;
    private javax.swing.JButton bt_enam;
    private javax.swing.JButton bt_enambelas;
    private javax.swing.JButton bt_keluar;
    private javax.swing.JButton bt_lima;
    private javax.swing.JButton bt_limabelas;
    private javax.swing.JButton bt_satu;
    private javax.swing.JButton bt_sebelas;
    private javax.swing.JButton bt_sembilan;
    private javax.swing.JButton bt_sembilanbelas;
    private javax.swing.JButton bt_sepuluh;
    private javax.swing.JButton bt_simpan;
    private javax.swing.JButton bt_tambah;
    private javax.swing.JButton bt_tiga;
    private javax.swing.JButton bt_tigabelas;
    private javax.swing.JButton bt_tujuh;
    private javax.swing.JButton bt_tujuhbelas;
    private javax.swing.JComboBox<String> cb_barang;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel jammm;
    private javax.swing.JLabel lb_jam;
    private javax.swing.JLabel lb_pegawai;
    private javax.swing.JLabel lb_tanggal;
    private javax.swing.JLabel lb_transaksi;
    private javax.swing.JMenu mb_data_menu;
    private javax.swing.JMenu mb_keluar;
    private javax.swing.JMenu mb_tr_today;
    private javax.swing.JMenu mb_transaksi;
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
