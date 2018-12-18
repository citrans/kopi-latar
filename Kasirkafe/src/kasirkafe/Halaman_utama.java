/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirkafe;

import javax.swing.JFrame;
import kasirkafe.mejatr.MejaTransaksi;

/**
 *
 * @author Citra
 */
public class Halaman_utama extends javax.swing.JFrame {

    /**
     * Creates new form Halaman_utama
     */
    String user, status;
    
    public Halaman_utama() {
        initComponents();
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    public Halaman_utama(String User, String Status) {
        initComponents();
//         this.setExtendedState(JFrame.MAXIMIZED_BOTH);
         this.user = User;
         this.status=Status;
         jLabel1.setText(status);
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
        jp_kl = new javax.swing.JPanel();
        jlb_kl = new javax.swing.JLabel();
        jlb_gb_kl = new javax.swing.JLabel();
        jp_du = new javax.swing.JPanel();
        jlb_du = new javax.swing.JLabel();
        jlb_gb_du = new javax.swing.JLabel();
        jp_lp = new javax.swing.JPanel();
        jlb_gb_lp = new javax.swing.JLabel();
        jlb_lp = new javax.swing.JLabel();
        jp_tr = new javax.swing.JPanel();
        jlb_gb_tr = new javax.swing.JLabel();
        jlb_tr = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(205, 101, 38));

        jp_kl.setBackground(new java.awt.Color(255, 222, 173));
        jp_kl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jp_klMouseClicked(evt);
            }
        });

        jlb_kl.setText("KELUAR");
        jlb_kl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_klMouseClicked(evt);
            }
        });

        jlb_gb_kl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/outc.png"))); // NOI18N
        jlb_gb_kl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_gb_klMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jp_klLayout = new javax.swing.GroupLayout(jp_kl);
        jp_kl.setLayout(jp_klLayout);
        jp_klLayout.setHorizontalGroup(
            jp_klLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_klLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jlb_gb_kl)
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_klLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlb_kl)
                .addGap(42, 42, 42))
        );
        jp_klLayout.setVerticalGroup(
            jp_klLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_klLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlb_gb_kl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlb_kl)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jp_du.setBackground(new java.awt.Color(255, 222, 173));
        jp_du.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jp_duMouseClicked(evt);
            }
        });

        jlb_du.setText("DATA UTAMA");
        jlb_du.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_duMouseClicked(evt);
            }
        });

        jlb_gb_du.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/master.png"))); // NOI18N
        jlb_gb_du.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_gb_duMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jp_duLayout = new javax.swing.GroupLayout(jp_du);
        jp_du.setLayout(jp_duLayout);
        jp_duLayout.setHorizontalGroup(
            jp_duLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_duLayout.createSequentialGroup()
                .addGroup(jp_duLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_duLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jlb_du))
                    .addGroup(jp_duLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jlb_gb_du)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jp_duLayout.setVerticalGroup(
            jp_duLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_duLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlb_gb_du)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlb_du)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jp_lp.setBackground(new java.awt.Color(255, 222, 173));
        jp_lp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jp_lpMouseClicked(evt);
            }
        });

        jlb_gb_lp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/lapo.png"))); // NOI18N
        jlb_gb_lp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_gb_lpMouseClicked(evt);
            }
        });

        jlb_lp.setText("LAPORAN");
        jlb_lp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_lpMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jp_lpLayout = new javax.swing.GroupLayout(jp_lp);
        jp_lp.setLayout(jp_lpLayout);
        jp_lpLayout.setHorizontalGroup(
            jp_lpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_lpLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jlb_gb_lp)
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_lpLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlb_lp)
                .addGap(35, 35, 35))
        );
        jp_lpLayout.setVerticalGroup(
            jp_lpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_lpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlb_gb_lp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlb_lp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jp_tr.setBackground(new java.awt.Color(255, 222, 173));
        jp_tr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jp_trMouseClicked(evt);
            }
        });
        jp_tr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jp_trKeyPressed(evt);
            }
        });

        jlb_gb_tr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/tran.png"))); // NOI18N
        jlb_gb_tr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_gb_trMouseClicked(evt);
            }
        });
        jlb_gb_tr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jlb_gb_trKeyPressed(evt);
            }
        });

        jlb_tr.setText("TRANSAKSI");
        jlb_tr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_trMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jp_trLayout = new javax.swing.GroupLayout(jp_tr);
        jp_tr.setLayout(jp_trLayout);
        jp_trLayout.setHorizontalGroup(
            jp_trLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_trLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jp_trLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_trLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jlb_tr))
                    .addComponent(jlb_gb_tr))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp_trLayout.setVerticalGroup(
            jp_trLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_trLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlb_gb_tr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlb_tr)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jp_du, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jp_tr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jp_lp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jp_kl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_lp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jp_tr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_du, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jp_kl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(499, 440));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jp_trKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jp_trKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jp_trKeyPressed

    private void jlb_gb_trKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jlb_gb_trKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jlb_gb_trKeyPressed

    private void jp_trMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jp_trMouseClicked
        // TODO add your handling code here:
        MejaTransaksi n = new MejaTransaksi(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jp_trMouseClicked

    private void jlb_gb_trMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_gb_trMouseClicked
        // TODO add your handling code here:
        MejaTransaksi n = new MejaTransaksi(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jlb_gb_trMouseClicked

    private void jlb_trMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_trMouseClicked
        // TODO add your handling code here:
        MejaTransaksi n = new MejaTransaksi(user, status);
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jlb_trMouseClicked

    private void jp_duMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jp_duMouseClicked
        // TODO add your handling code here:
        if("Pegawai".equals(status)){
       form_menu_makanan n = new form_menu_makanan(user, status);
        n.setVisible(true);
        this.setVisible(false);
        }
        else  {
             crud n = new crud(user,status);
        n.setVisible(true);
        this.setVisible(false);
        
        }
    }//GEN-LAST:event_jp_duMouseClicked

    private void jlb_gb_duMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_gb_duMouseClicked
        // TODO add your handling code here:
        if("Pegawai".equals(status)){
       form_menu_makanan n = new form_menu_makanan(user, status);
        n.setVisible(true);
        this.setVisible(false);
        }
        else  {
             crud n = new crud(user,status);
        n.setVisible(true);
        this.setVisible(false);
        
        }
    }//GEN-LAST:event_jlb_gb_duMouseClicked

    private void jlb_duMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_duMouseClicked
        // TODO add your handling code here:
        if("Pegawai".equals(status)){
       form_menu_makanan n = new form_menu_makanan(user, status);
        n.setVisible(true);
        this.setVisible(false);
        }
        else  {
        crud n = new crud(user,status);
        n.setVisible(true);
        this.setVisible(false);
        
        }
    }//GEN-LAST:event_jlb_duMouseClicked

    private void jp_klMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jp_klMouseClicked
        // TODO add your handling code here:
        new login().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jp_klMouseClicked

    private void jlb_gb_klMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_gb_klMouseClicked
        // TODO add your handling code here:
        new login().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jlb_gb_klMouseClicked

    private void jlb_klMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_klMouseClicked
        // TODO add your handling code here:
        new login().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jlb_klMouseClicked

    private void jp_lpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jp_lpMouseClicked
        // TODO add your handling code here:
        if("Pegawai".equals(status)){
            LaporanTransaksiKasir n = new LaporanTransaksiKasir(user, status);
            n.setVisible(true);
            this.setVisible(false);
        }
        else  {
            LaporanTransaksiPemilik n = new LaporanTransaksiPemilik(user, status);
            n.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_jp_lpMouseClicked

    private void jlb_gb_lpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_gb_lpMouseClicked
        // TODO add your handling code here:
        if("Pegawai".equals(status)){
            LaporanTransaksiKasir n = new LaporanTransaksiKasir(user, status);
            n.setVisible(true);
            this.setVisible(false);
        }
        else  {
            LaporanTransaksiPemilik n = new LaporanTransaksiPemilik(user, status);
            n.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_jlb_gb_lpMouseClicked

    private void jlb_lpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_lpMouseClicked
        // TODO add your handling code here:
        if("Pegawai".equals(status)){
            LaporanTransaksiKasir n = new LaporanTransaksiKasir(user, status);
            n.setVisible(true);
            this.setVisible(false);
        }
        else  {
            LaporanTransaksiPemilik n = new LaporanTransaksiPemilik(user, status);
            n.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_jlb_lpMouseClicked

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
            java.util.logging.Logger.getLogger(Halaman_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Halaman_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Halaman_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Halaman_utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Halaman_utama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlb_du;
    private javax.swing.JLabel jlb_gb_du;
    private javax.swing.JLabel jlb_gb_kl;
    private javax.swing.JLabel jlb_gb_lp;
    private javax.swing.JLabel jlb_gb_tr;
    private javax.swing.JLabel jlb_kl;
    private javax.swing.JLabel jlb_lp;
    private javax.swing.JLabel jlb_tr;
    private javax.swing.JPanel jp_du;
    private javax.swing.JPanel jp_kl;
    private javax.swing.JPanel jp_lp;
    private javax.swing.JPanel jp_tr;
    // End of variables declaration//GEN-END:variables
}
