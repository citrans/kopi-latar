/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;

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
import javax.swing.text.JTextComponent;

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
                        buscar(cb_barang.getItemAt(0).toString());
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
            lb_hide.hide();
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
            Connection konek = new Koneksi_mysql().getConnection();
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
            konek.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
       private void tampil_menu(){
        try{
            Connection konek = new Koneksi_mysql().getConnection();
            String sql = "SELECT * FROM menu";
            PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
            ResultSet hasil = stat.executeQuery();
            while(hasil.next()){
                String menu = hasil.getString("nama_menu");
                cb_barang.addItem(menu);
                ambil_id();
              }
            konek.close();
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
       private void tampil_harga(){
           
        try{
            Connection konek = new Koneksi_mysql().getConnection();
            String sql = "SELECT * FROM menu WHERE `id_menu`='"+no+"'";
            PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
            ResultSet hasil = stat.executeQuery();
            while(hasil.next()){
               String harga = hasil.getString("harga");
               tf_harga.setText(harga);
               //ambil_id();
            }
            konek.close();
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
            Connection konek = new Koneksi_mysql().getConnection();
            Statement stat= konek.createStatement();
            String sql = "SELECT id_menu as id FROM menu WHERE `nama_menu`='"+menu+"'";
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
              //  hasil.first();
                int id = hasil.getInt(1);
                no = String.valueOf(id);                           
            }
            konek.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
       public void ambil_id_peg(){
        
        try{
            String peg = lb_pegawai.getText() ;
            Connection konek = new Koneksi_mysql().getConnection();
            Statement stat= konek.createStatement();
            String sql = "SELECT id_pegawai as id FROM pegawai WHERE `nama_pegawai`='"+peg+"'";
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
              //  hasil.first();
                int id = hasil.getInt(1);
                id_pegawa = String.valueOf(id);                       
            }
            konek.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
       public void id_auto(){
        try{
            Connection konek = new Koneksi_mysql().getConnection();
            Statement stat= konek.createStatement();
            String sql = "SELECT id_transaksi as no FROM transaksi order by id_transaksi asc";
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                if(hasil.first() == false){
                    lb_transaksi.setText("1");
                }else{
                    hasil.last();
                    int set_id = hasil.getInt(1)+20;
                    String id = String.valueOf(set_id);
                    lb_transaksi.setText(id);
                }
            }
            konek.close();
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
       public void tampil_total(){
            int total_harga_bayar =0;
  String thb ="0";
        int jumlahBaris = tb_barang.getRowCount();
        int hargaBarang;
        TableModel tabelModel;
        tabelModel = tb_barang.getModel();
        total_harga_bayar = 0;
        for (int i=0; i<jumlahBaris; i++){
            hargaBarang = Integer.parseInt(tabelModel.getValueAt(i, 4).toString());
            total_harga_bayar = total_harga_bayar + hargaBarang;
            thb = Integer.toString(total_harga_bayar);
        }
        thb = Integer.toString(total_harga_bayar);
        tf_total_bayar.setText(thb);
        tf_total_tampil.setText(thb);
       }
       public void hapus_data(){
            ambil_id();
        String id_menu="", no_meja, id_transaksi;
        int harga = 0;
        id_menu = no;
        no_meja = "1";
        id_transaksi = lb_transaksi.getText();
        try{
            Connection konek = new Koneksi_mysql().getConnection();
            String sql = "DELETE FROM `sementara` WHERE `id_menu` = '"+id_menu+"' AND `no_meja` = '"+no_meja+"' AND `id_transaksi` = '"+id_transaksi+"'";
            PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil Menghapus Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            reset();
            konek.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
          tampil_total();
       }
       public void tambah_data(){
           Connection konek = new Koneksi_mysql().getConnection();
        String id_menu="", no_meja="1", id_transaksi="", nama_menu;
        int total = 0, jumlah_pesan =0;
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
                    }catch(Exception e){
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
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "insert Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
                    }
                }
//            }
            konek.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
        tampil_total();
       }
       public void simpan_data(){
           String id_pegawai="", tgl_transaksi="", id_transaksi="";
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
            Connection konek = new Koneksi_mysql().getConnection();
            String sql = "INSERT INTO `transaksi`(`id_transaksi`, `id_pegawai`, `tgl_transaksi`, `jumlah_pesanan`, `total_harga`, `bayar`, `kembalian`) VALUES ('"+id_transaksi+"','"+id_pegawai+"','"+tgl_transaksi+"','"+jumlah_pesanan+"','"+total_harga+"','"+bayar+"','"+kembalian+"')";
            PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            konek.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
        id_auto();
        bersih();
        tampil_pesanan();
        tf_total_bayar.setText("0");
        tf_total_tampil.setText("0");
       }
       public void tanggal_jam_sekarang(){
        Thread p = new Thread(){
            public void run(){
                for(;;){
                    Calendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    tanggal.setText(hari+"-"+(bulan+1)+"-"+tahun);
                    lb_hide.setText(tahun+"-"+(bulan+1)+"-"+hari);
                    
                    int jam = cal.get(Calendar.HOUR);
                    int menit = cal.get(Calendar.MINUTE);
                    int detik = cal.get(Calendar.SECOND);
                    int AM_PM = cal.get(Calendar.AM_PM);
                    
                    String day_night = "";
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
        lb_hide = new javax.swing.JLabel();

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

        lb_hide.setText("jLabel6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
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
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(100, 100, 100)))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(lb_hide)
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(cb_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jLabel3))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(tf_unit, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(bt_tambah)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(tb_hapus))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lb_tanggal)
                                                        .addComponent(lb_jam)
                                                        .addComponent(jLabel5))
                                                    .addGap(41, 41, 41))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel12)
                                                    .addGap(24, 24, 24)))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lb_pegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(jammm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                                                            .addComponent(tanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel7)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(tf_total_tampil, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(lb_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(bt_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(bt_keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(224, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(lb_transaksi)
                                    .addComponent(lb_hide))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(bt_lima, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(bt_sepuluh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(bt_limabelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(bt_duapuluh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                        .addComponent(bt_tujuhbelas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(lb_pegawai)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lb_tanggal)
                                    .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_jam)
                                    .addComponent(jammm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cb_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tf_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tf_total_tampil, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_tambah)
                            .addComponent(tb_hapus))))
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tf_total_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tf_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tf_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_simpan)
                    .addComponent(bt_keluar))
                .addContainerGap(49, Short.MAX_VALUE))
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
            
        } catch (Exception e) {
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
        simpan_data();
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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jammm;
    private javax.swing.JLabel lb_hide;
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
