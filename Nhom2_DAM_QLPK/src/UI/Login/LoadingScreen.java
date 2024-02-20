/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Login;

import UI.Login.Login;
import UI.NhanVien.MainJFramesNV;
import UI.QuanLy.MainJFrames;

import javax.swing.JOptionPane;

public class LoadingScreen extends javax.swing.JFrame {

    public LoadingScreen() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        LoadingText = new javax.swing.JLabel();
        LoadingBar = new javax.swing.JProgressBar();
        LoadingValue = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        jlbBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jpnRoot.setPreferredSize(new java.awt.Dimension(600, 400));
        jpnRoot.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LoadingText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LoadingText.setForeground(new java.awt.Color(255, 255, 255));
        LoadingText.setText("Loading...");
        jpnRoot.add(LoadingText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        LoadingBar.setForeground(new java.awt.Color(0, 102, 204));
        jpnRoot.add(LoadingBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 380, 620, 25));

        LoadingValue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LoadingValue.setForeground(new java.awt.Color(255, 255, 255));
        LoadingValue.setText("0%");
        jpnRoot.add(LoadingValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 360, -1, -1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Login/Spinner-1s-36px.gif"))); // NOI18N
        jpnRoot.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(276, 240, 50, -1));

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Login/logo.png"))); // NOI18N
        jpnRoot.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 350, 100));

        jlbBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Login/wepik-export-20230511065131I1Ib.png"))); // NOI18N
        jpnRoot.add(jlbBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(LoadingScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoadingScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoadingScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoadingScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        LoadingScreen ld = new LoadingScreen();
        ld.setVisible(true);
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(20);
                ld.LoadingValue.setText(i + "%");
                
                if(i==20){
                    ld.LoadingText.setText("Loading...");
                }
                if(i==40){
                    ld.LoadingText.setText("Connecting...");
                }
                if(i==60){
                    ld.LoadingText.setText("Preparing...");
                }
                if(i==80){
                    ld.LoadingText.setText("Successfully!");
                }
                if(i==90){
                    ld.LoadingText.setText("Starting!");
                }
                ld.LoadingBar.setValue(i);              
            }
            if(ld.LoadingBar.getValue()==100){
                Login login = new Login();
                login.setVisible(true);
                    MainJFramesNV main= new MainJFramesNV();
                    main.setVisible(true);
//                MainJFrames main=new MainJFrames();
//                main.setVisible(true);
                ld.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JProgressBar LoadingBar;
    public javax.swing.JLabel LoadingText;
    public javax.swing.JLabel LoadingValue;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jlbBackground;
    private javax.swing.JPanel jpnRoot;
    // End of variables declaration//GEN-END:variables
}
