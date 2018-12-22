package kasirkafe;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
/**
 *
 * @author Citra
 */
public class Input_kategori extends javax.swing.JFrame {
    Connection conn = null;
    PreparedStatement pst =null;
    
    /**
     * Creates new form Input_menu
     */
    private DefaultTableModel tabmode;
    
    private void tampil_kategori(){
        Object[]baris={"ID Kategori", "Kategori Menu"};
        tabmode= new DefaultTableModel(null,baris);
        tabel_kategori.setModel(tabmode);
        String sql = "SELECT * FROM kategori  order by id_kategori asc";
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String id_kategori = hasil.getString("id_kategori");
                    String kategori_menu = hasil.getString("kategori_menu");
                    String[]data= {id_kategori,kategori_menu};
                    tabmode.addRow(data);
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "menampilkan data gagal", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    private void ambil_id(){
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                Statement stat= konek.createStatement();
                String sql = "SELECT id_kategori as no FROM kategori";
                ResultSet hasil = stat.executeQuery(sql);
                if(hasil.next()){
                    hasil.last();
                    int set_id = hasil.getInt(1)+1;
                    String no = String.valueOf(set_id);
                    tf_kategori.setText(no);
                }
                else{
                    tf_kategori.setText("1");
                }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "gagal mengambil id kategori", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void reset(){
        tf_kategori.setText("");
        tf_kategori_menu.setText("");
        ambil_id();
    }
    public void tambah_data(){
        
        int harga = 0;
        String id_kategori = tf_kategori.getText();
        String kategori_menu = tf_kategori_menu.getText();
        try{
            try (Connection konek = new Koneksi_mysql().getConnection()) {
                String sql = "INSERT INTO `kategori`(`id_kategori`, `kategori_menu`) VALUES ('"+id_kategori+"','"+kategori_menu+"')";
                PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                tampil_kategori();
                reset();
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
     }
    public void hapus_data(){
         String id_kategori;
        int harga = 0;
        id_kategori = tf_kategori.getText();
       
        harga = Integer.parseInt(tf_kategori.getText());
        try{
             try (Connection konek = new Koneksi_mysql().getConnection()) {
                 String sql = "DELETE FROM `kategori` WHERE `id_kategori` = '"+id_kategori+"'";
                 PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                 stat.executeUpdate();
                 JOptionPane.showMessageDialog(null, "Berhasil Menghapus Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                 tampil_kategori();
                 reset();
             }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
     }
    public void update_data(){
          String id_kategori, kategori_menu;
        id_kategori = tf_kategori.getText();
        kategori_menu = tf_kategori_menu.getText();
        try{
              try (Connection konek = new Koneksi_mysql().getConnection()) {
                  String sql = "UPDATE `kategori` SET `kategori_menu`='"+kategori_menu+"' WHERE `id_kategori`='"+id_kategori+"'";
                  PreparedStatement stat = (PreparedStatement) konek.prepareStatement(sql);
                  stat.executeUpdate();
                  JOptionPane.showMessageDialog(null, "Berhasil Merubah Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                  tampil_kategori();
                  reset();
              }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Merubah Data", "Informasi", JOptionPane.INFORMATION_MESSAGE);    
        }
     }

     public void lebarkolom(){ 
        TableColumn column;
        tabel_kategori.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); 
        column = tabel_kategori.getColumnModel().getColumn(0); 
        column.setPreferredWidth(75);
        column = tabel_kategori.getColumnModel().getColumn(1); 
        column.setPreferredWidth(248); 
    }
  
    public Input_kategori() {
        initComponents();
        tampil_kategori();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
        ambil_id();
        lebarkolom();
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
        jLabel2 = new javax.swing.JLabel();
        tf_kategori = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf_kategori_menu = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_kategori = new javax.swing.JTable();
        bt_simpan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(205, 133, 63));

        jPanel1.setBackground(new java.awt.Color(205, 133, 63));
        jPanel1.setPreferredSize(new java.awt.Dimension(1050, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INPUT KATEGORI MENU");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID_KATEGORI");

        tf_kategori.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_kategori.setText("jLabel4");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("KATEGORI MENU");

        tf_kategori_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_kategori_menuActionPerformed(evt);
            }
        });

        tabel_kategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tabel_kategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_kategoriMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_kategori);
        if (tabel_kategori.getColumnModel().getColumnCount() > 0) {
            tabel_kategori.getColumnModel().getColumn(0).setResizable(false);
            tabel_kategori.getColumnModel().getColumn(1).setResizable(false);
        }

        bt_simpan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Ubah");
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

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Kembali");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_kategori)
                            .addComponent(tf_kategori_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_simpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_kategori)
                            .addComponent(jLabel2))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_kategori_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addComponent(bt_simpan)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(254, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1063, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        form_menu_makanan n = new form_menu_makanan();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
       tambah_data();
    }//GEN-LAST:event_bt_simpanActionPerformed

    private void tabel_kategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_kategoriMouseClicked
        // TODO add your handling code here:
        int tb_menu = tabel_kategori.getSelectedRow();
        tf_kategori.setText(tabel_kategori.getValueAt(tb_menu, 0).toString());
        tf_kategori_menu.setText(tabel_kategori.getValueAt(tb_menu, 1).toString());
    }//GEN-LAST:event_tabel_kategoriMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int hapusselected = JOptionPane.showConfirmDialog(null, "Hapus Data?","Close Message", JOptionPane.YES_NO_OPTION);
         if(hapusselected == JOptionPane.YES_OPTION){
        hapus_data();
         }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         int hapusselected = JOptionPane.showConfirmDialog(null, "Ubah Data?","Close Message", JOptionPane.YES_NO_OPTION);
         if(hapusselected == JOptionPane.YES_OPTION){
             update_data();
         }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bt_simpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_simpanKeyPressed
        // TODO add your handling code here:
          if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             tambah_data();
        }
    }//GEN-LAST:event_bt_simpanKeyPressed

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             hapus_data();
        }
    }//GEN-LAST:event_jButton3KeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             update_data();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             form_menu_makanan n = new form_menu_makanan();
             n.setVisible(true);
             this.setVisible(false);
        }
    }//GEN-LAST:event_jButton2KeyPressed

    private void tf_kategori_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_kategori_menuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_kategori_menuActionPerformed

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
            java.util.logging.Logger.getLogger(Input_kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Input_kategori().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_simpan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_kategori;
    private javax.swing.JLabel tf_kategori;
    private javax.swing.JTextField tf_kategori_menu;
    // End of variables declaration//GEN-END:variables
}
