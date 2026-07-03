/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.inventorycontrolsystem.view;

import com.mycompany.inventorycontrolsystem.controller.LoginController;
import com.mycompany.inventorycontrolsystem.controller.LoginController.AuthException;
import com.mycompany.inventorycontrolsystem.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

/**
 * Login screen for the Inventory Control System.
 *
 * Design: GroupLayout JFrame (NetBeans GUI Builder).
 * All backend logic lives below the GEN-BEGIN / GEN-END locked block.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * IMAGE LOADING STRATEGY
 * ─────────────────────────────────────────────────────────────────────────────
 * Three separate problems must all be solved together:
 *
 *  1. RESOURCE LOCATION — Maven copies src/main/resources/** to the root of
 *     the classpath.  The image is at resources/images/login_bg.png, so at
 *     runtime it lives at /images/login_bg.png on the classpath.  We try
 *     three independent class-loader strategies so the lookup works regardless
 *     of whether the app is run from NetBeans, from a fat JAR, or from the
 *     Maven exec plugin.
 *
 *  2. DIMENSION TIMING — pack() is called by initComponents() and gives the
 *     JFrame its size, but GroupLayout performs its first measure/layout pass
 *     only when the frame is made visible.  Inside the constructor, lblImage
 *     reports getWidth()=404 (from the GroupLayout PREFERRED_SIZE constraint)
 *     but getHeight()=0 because its vertical size is "fill remaining space"
 *     and that fill hasn't been computed yet.
 *
 *     The fix: image loading is triggered from main() with a nested
 *     SwingUtilities.invokeLater that runs AFTER setVisible(true) returns.
 *     By that point the EDT has completed the first layout and paint pass, so
 *     both width and height are fully resolved.
 *
 *  3. FALLBACK CHAIN — if every class-loader strategy returns null (the image
 *     was never copied to the output directory, e.g. a clean checkout with no
 *     Maven build yet), a styled navy placeholder is rendered instead of a
 *     blank gray box so the login screen always looks intentional.
 * ─────────────────────────────────────────────────────────────────────────────
 */
public class LoginFrame extends javax.swing.JFrame {

    
    private final LoginController loginController = new LoginController();

    // Masking buffer — holds the real characters behind the ● echo
    private final StringBuilder realPasswordBuffer = new StringBuilder();
    // CONSTRUCTOR

    public LoginFrame() {
        initComponents();   // NetBeans locked block — do not touch
        setupData();      // wire listeners, password mask, window positioning
        // Image loading intentionally deferred — see triggerImageLoad()
    }
    // NetBeans GUI Builder — GENERATED CODE (do not modify)

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblImage = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(20, 30, 54));

        lblTitle.setFont(new java.awt.Font("JetBrains Mono", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("SYSTEM LOGIN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        jLabel3.setText("Username");

        txtUsername.setFont(new java.awt.Font("JetBrains Mono", 0, 24)); // NOI18N

        txtPassword.setFont(new java.awt.Font("JetBrains Mono", 0, 24)); // NOI18N

        jLabel4.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        jLabel4.setText("Password");

        btnCancel.setBackground(new java.awt.Color(204, 0, 0));
        btnCancel.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(0, 51, 153));
        btnLogin.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        btnLogin.setText("Login");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(94, 94, 94)
                        .addComponent(txtUsername))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(94, 94, 94)
                        .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(btnLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(95, 95, 95))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(71, 71, 71)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnLogin))
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        onLoginClicked();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        System.exit(0);
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
    // BACKEND INITIALISATION

    /**
     * Called once from the constructor after initComponents().
     * Wires event listeners, password masking, and window centering.
     * Image loading is NOT done here — it requires real pixel dimensions
     * that are only available after setVisible(true) completes (see main()).
     */
    private void setupData() {

        // Centre on screen
        setLocationRelativeTo(null);

        // Enter key in either field fires login
        txtUsername.addActionListener((ActionEvent e) -> onLoginClicked());
        txtPassword.addActionListener((ActionEvent e) -> onLoginClicked());

        // Mask the password field (txtPassword is JTextField in the GEN block;
        // a DocumentFilter gives us the same echo-character behaviour as
        // JPasswordField without touching the locked variable declaration)
        applyPasswordMask();
    }
    // IMAGE LOADING — called from main() AFTER setVisible(true)

    /**
     * Entry point for image loading. Called from main() via a second
     * {@code SwingUtilities.invokeLater} that executes after
     * {@code setVisible(true)} has returned and the EDT has completed its
     * first full layout + paint pass.  At that point {@code lblImage} has
     * real, non-zero pixel dimensions.
     *
     * <p>This is the only moment where both {@code getWidth()} and
     * {@code getHeight()} on {@code lblImage} are guaranteed to be > 0.
     */
    public void triggerImageLoad() {
        int w = lblImage.getWidth();
        int h = lblImage.getHeight();

        // If dimensions are still somehow 0 (extreme edge-case: HiDPI + slow
        // compositor), derive them from the frame size as a safe fallback.
        if (w <= 0) w = 404;                       // matches GroupLayout constraint
        if (h <= 0) h = getContentPane().getHeight()
                         - jPanel1.getPreferredSize().height - 20; // 20 = top+bottom gaps
        if (h <= 0) h = 400;                       // absolute last-resort default

        scaleLoginImage(w, h);
    }
    // IMAGE SCALING ENGINE

    /**
     * Bulletproof resource lookup + smooth scaling engine.
     *
     * <p>Lookup strategy (three independent class-loader paths):
     * <ol>
     *   <li>{@code getClass().getResource("/images/login_bg.png")} — standard
     *       classpath lookup; works in NetBeans Run and fat-JAR execution.</li>
     *   <li>{@code Thread.currentThread().getContextClassLoader()} — works
     *       when the app class-loader is not the bootstrap loader (OSGi, app
     *       servers, some IDE configurations).</li>
     *   <li>{@code LoginFrame.class.getClassLoader()} — direct reference to the
     *       class's own loader; works when the context class-loader has been
     *       replaced by a framework.</li>
     * </ol>
     *
     * <p>If all three return {@code null} the image was never copied to the
     * build output directory (Maven clean + no build).  A styled navy
     * placeholder is rendered instead.
     *
     * @param w  target width  in pixels (rendered bounds of lblImage)
     * @param h  target height in pixels (rendered bounds of lblImage)
     */
    private void scaleLoginImage(int w, int h) {
        URL imgURL = getClass().getResource("/images/login_bg.png");

        if (imgURL == null) {
            imgURL = Thread.currentThread()
                           .getContextClassLoader()
                           .getResource("images/login_bg.png");
        }

        if (imgURL == null) {
            imgURL = LoginFrame.class.getClassLoader()
                                     .getResource("images/login_bg.png");
        }
        if (imgURL == null) {
            System.out.println("login_bg.png not found on classpath via any class-loader. "
                   + "Run 'mvn compile' or 'Build Project' to copy resources.");
            applyImageFallback();
            return;
        }
        try {
            // Use Toolkit to load so the image is fully decoded before scaling
            // (avoids partially-loaded image artefacts with getScaledInstance)
            Image original = Toolkit.getDefaultToolkit().createImage(imgURL);

            // Force synchronous, complete load via MediaTracker
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(original, 0);
            try {
                tracker.waitForID(0);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            if ((tracker.statusID(0, false) & MediaTracker.ERRORED) != 0) {
                System.out.println("MediaTracker reported an error loading login_bg.png.");
                applyImageFallback();
                return;
            }

            // Scale to exact pixel bounds of lblImage using bilinear interpolation
            Image scaled = original.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);

            lblImage.setIcon(new ImageIcon(scaled));
            lblImage.setText("");
            lblImage.setHorizontalAlignment(SwingConstants.CENTER);
            lblImage.setVerticalAlignment(SwingConstants.CENTER);

            System.out.println("Login image loaded and scaled to {}x{}.");

        } catch (Exception ex) {
            ex.printStackTrace();
            applyImageFallback();
        }
    }

    /**
     * Applied when the image cannot be loaded for any reason.
     * Produces a styled navy panel so the login screen never shows a blank
     * gray box — the layout still looks intentional.
     */
    private void applyImageFallback() {
        lblImage.setOpaque(true);
        lblImage.setBackground(new Color(20, 30, 54));
        lblImage.setForeground(Color.WHITE);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setVerticalAlignment(SwingConstants.CENTER);
        lblImage.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblImage.setText(
            "<html><center><br><br>"
            + "<span style='font-size:22pt'>&#128230;</span><br><br>"
            + "Inventory Control<br>System"
            + "</center></html>");
    }
    // PASSWORD MASKING

    /**
     * Installs a {@link javax.swing.text.DocumentFilter} on {@code txtPassword}
     * that stores real characters in {@code realPasswordBuffer} and echoes
     * the ● character on screen — identical behaviour to JPasswordField without
     * requiring a type change to the locked GEN variable.
     */
    private void applyPasswordMask() {
        ((javax.swing.text.AbstractDocument) txtPassword.getDocument())
            .setDocumentFilter(new javax.swing.text.DocumentFilter() {

            @Override
            public void insertString(FilterBypass fb, int offset,
                                     String text,
                                     javax.swing.text.AttributeSet attr)
                    throws javax.swing.text.BadLocationException {
                if (text == null) return;
                realPasswordBuffer.insert(offset, text);
                super.insertString(fb, offset, "●".repeat(text.length()), attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length,
                                String text,
                                javax.swing.text.AttributeSet attr)
                    throws javax.swing.text.BadLocationException {
                if (length > 0) realPasswordBuffer.delete(offset, offset + length);
                if (text != null && !text.isEmpty()) {
                    realPasswordBuffer.insert(offset, text);
                    super.replace(fb, offset, length, "●".repeat(text.length()), attr);
                } else {
                    super.replace(fb, offset, length, text, attr);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length)
                    throws javax.swing.text.BadLocationException {
                if (length > 0) realPasswordBuffer.delete(offset, offset + length);
                super.remove(fb, offset, length);
            }
        });
    }

    /** Returns the actual (unmasked) password typed by the user. */
    private String getRealPassword() {
        return realPasswordBuffer.toString();
    }
    // LOGIN HANDLER

    /**
     * Validates fields, authenticates via {@link LoginController} on a worker
     */
    private void onLoginClicked() {
        String username = txtUsername.getText().trim();
        String password = getRealPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password.",
                    "Input Required", JOptionPane.WARNING_MESSAGE);
            if (username.isEmpty()) txtUsername.requestFocusInWindow();
            else                    txtPassword.requestFocusInWindow();
            return;
        }

        btnLogin.setEnabled(false);
        btnLogin.setText("Authenticating...");

        new SwingWorker<User, Void>() {
            @Override
            protected User doInBackground() throws AuthException {
                return loginController.authenticate(username, password);
            }

            @Override
            protected void done() {
                btnLogin.setEnabled(true);
                btnLogin.setText("Login");

                try {
                    User authenticatedUser = get();

                    System.out.println("UI: Login successful for '{}'.");
                    DashboardFrame dashboard = new DashboardFrame(authenticatedUser);
                    dashboard.setVisible(true);
                    LoginFrame.this.dispose();

                } catch (java.util.concurrent.ExecutionException ex) {
                    Throwable cause = ex.getCause();
                    if (cause instanceof AuthException) {
                        // Expected auth failure — show the clean message
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                cause.getMessage(), "Login Failed",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Unexpected error (e.g. DB connection failure) —
                        // print the full stack trace to the console AND show
                        // the real cause message in the dialog so it is visible
                        cause.printStackTrace();
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Login Failed: " + cause.getMessage(),
                                "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                    txtUsername.setText("");
                    txtPassword.setText("");
                    realPasswordBuffer.setLength(0);
                    txtUsername.requestFocusInWindow();

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }.execute();
    }
    // MAIN — application entry point

    /**
     * Launches the login screen under Nimbus Look and Feel.
     *
     * <p>Image loading is deliberately deferred using a double-invokeLater:
     * <pre>
     *   invokeLater #1 → creates and shows LoginFrame (setVisible)
     *   invokeLater #2 → calls triggerImageLoad() after the first paint pass
     * </pre>
     * This guarantees that when triggerImageLoad() reads lblImage.getWidth()
     * and lblImage.getHeight(), the GroupLayout has already performed its first
     * measure+layout pass and both values are fully resolved non-zero integers.
     */
    public static void main(String[] args) {
        // Set Nimbus before any Swing component is constructed
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);

            // Second invokeLater: runs after setVisible() has been processed
            // by the EDT, i.e. after the first layout + paint pass completes.
            // At this point lblImage has its true rendered dimensions.
            SwingUtilities.invokeLater(frame::triggerImageLoad);
        });
    }

} // end class LoginFrame
