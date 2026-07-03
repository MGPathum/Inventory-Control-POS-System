package com.mycompany.inventorycontrolsystem.view;

import com.mycompany.inventorycontrolsystem.db.DAOFactory;
import com.mycompany.inventorycontrolsystem.model.Product;
import com.mycompany.inventorycontrolsystem.model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Main application shell.
 *
 * Fix Netbeans Layout Issue.
 */
public class DashboardFrame extends javax.swing.JFrame {

        private final User currentUser;

    // KPI labels � created in buildKpiRow(), populated by SwingWorker
    private JLabel lblKpiProductsVal;
    private JLabel lblKpiCategoriesVal;
    private JLabel lblKpiSuppliersVal;
    private JLabel lblKpiLowStockVal;
    private JPanel pnlKpiLowStockCard;

    // Low-stock table � promoted to field for SwingWorker access
    private DefaultTableModel lowStockTableModel;
    private JTable lowStockTable;

    // ReportsPanel � promoted to field so the btnReports lambda can call
    // loadInvoiceHistory() safely after the panel is constructed.
    private ReportsPanel reportsPanel;

    // Promoted to fields so ProductFrame can refresh PurchaseOrderPanel's
    // product combo after a new product is saved.
    private ProductFrame productFrame;
    private PurchaseOrderPanel purchaseOrderPanel;

    // Card-name constants � single source of truth for every show() call
    private static final String CARD_HOME            = "HOME";
    private static final String CARD_PRODUCTS        = "PRODUCTS";
    private static final String CARD_CATEGORIES      = "CATEGORIES";
    private static final String CARD_SUPPLIERS       = "SUPPLIERS";
    private static final String CARD_POS             = "POS";
    private static final String CARD_PURCHASE_ORDERS = "PURCHASE_ORDERS";
    private static final String CARD_REPORTS         = "REPORTS";
    private static final String CARD_USERS           = "USERS";
    private static final String CARD_DB              = "DB";
    // CONSTRUCTOR

    public DashboardFrame(User user) {
        this.currentUser = user;

        // Step A � let NetBeans build all GUI widgets (top-bar, sidebar, etc.)
        initComponents();

        // Step B � IMMEDIATELY destroy whatever layout NetBeans assigned to
        // pnlMainContent and replace it with a fresh CardLayout.
        // This line is the definitive fix: it runs BEFORE setupDashboard(), so
        // every subsequent add() call operates on a clean CardLayout container.
        pnlMainContent.removeAll();
        pnlMainContent.setLayout(new java.awt.CardLayout());

        // Step C � maximise window, then wire up all cards and listeners
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setupDashboard();
        setLocationRelativeTo(null);
    }
    // NetBeans GUI Builder � GENERATED CODE
    // WARNING: Do NOT modify this method. The NetBeans Form Editor regenerates
    // it. pnlMainContent is intentionally left with whatever layout NetBeans
    // assigns here � the constructor overwrites it immediately afterwards.

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTopBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        pnlSidebar = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnProducts = new javax.swing.JButton();
        btnCategories = new javax.swing.JButton();
        btnSuppliers = new javax.swing.JButton();
        btnPOS = new javax.swing.JButton();
        btnPurchase = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        btnUser = new javax.swing.JButton();
        btnDB = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jButton9 = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        pnlMainContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inventory Control System");
        setExtendedState(6);

        pnlTopBar.setBackground(new java.awt.Color(30, 41, 59));

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 1, 36)); // NOI18N
        jLabel1.setText("INVENTORY CONTROL SYSTEM");

        jLabel2.setFont(new java.awt.Font("JetBrains Mono", 0, 24)); // NOI18N
        jLabel2.setText("Welcome, User");

        javax.swing.GroupLayout pnlTopBarLayout = new javax.swing.GroupLayout(pnlTopBar);
        pnlTopBar.setLayout(pnlTopBarLayout);
        pnlTopBarLayout.setHorizontalGroup(
            pnlTopBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTopBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(695, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopBarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTopBarLayout.setVerticalGroup(
            pnlTopBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        getContentPane().add(pnlTopBar, java.awt.BorderLayout.NORTH);

        pnlSidebar.setBackground(new java.awt.Color(15, 23, 42));
        pnlSidebar.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Javanese Text", 0, 24)); // NOI18N
        jLabel3.setText("SIDEBAR PANEL");

        btnProducts.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnProducts.setForeground(new java.awt.Color(0, 0, 0));
        btnProducts.setText("Products");
        btnProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductsActionPerformed(evt);
            }
        });

        btnCategories.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnCategories.setForeground(new java.awt.Color(0, 0, 0));
        btnCategories.setText("Categories");

        btnSuppliers.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnSuppliers.setForeground(new java.awt.Color(0, 0, 0));
        btnSuppliers.setText("Suppliers");

        btnPOS.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnPOS.setForeground(new java.awt.Color(0, 0, 0));
        btnPOS.setText("POS Counter");

        btnPurchase.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnPurchase.setForeground(new java.awt.Color(0, 0, 0));
        btnPurchase.setText("Purchase Orders");

        btnReports.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnReports.setForeground(new java.awt.Color(0, 0, 0));
        btnReports.setText("Reports");

        btnUser.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnUser.setForeground(new java.awt.Color(0, 0, 0));
        btnUser.setText("User Manage");

        btnDB.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnDB.setForeground(new java.awt.Color(0, 0, 0));
        btnDB.setText("DB Maintenance");

        jButton9.setBackground(new java.awt.Color(153, 0, 0));
        jButton9.setFont(new java.awt.Font("JetBrains Mono", 1, 18)); // NOI18N
        jButton9.setText("Logout");

        btnDashboard.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(0, 0, 0));
        btnDashboard.setText("Dashboard");
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSidebarLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jButton9))
                    .addGroup(pnlSidebarLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnDB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPOS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSuppliers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCategories, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProducts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPurchase, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReports, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSidebarLayout.setVerticalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDashboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProducts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCategories)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuppliers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPOS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPurchase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReports)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDB)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        getContentPane().add(pnlSidebar, java.awt.BorderLayout.LINE_START);

        pnlMainContent.setLayout(new java.awt.CardLayout());
        getContentPane().add(pnlMainContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // NetBeans-required stub handlers (empty � real listeners wired in setupDashboard)
    private void btnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
    }//GEN-LAST:event_btnProductsActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
    }//GEN-LAST:event_btnDashboardActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCategories;
    private javax.swing.JButton btnDB;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnPOS;
    private javax.swing.JButton btnProducts;
    private javax.swing.JButton btnPurchase;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnSuppliers;
    private javax.swing.JButton btnUser;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel pnlMainContent;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlTopBar;
    // End of variables declaration//GEN-END:variables
    // BACKEND INITIALISATION
    // Called from the constructor AFTER initComponents() AND after the
    // constructor has already forced a clean CardLayout onto pnlMainContent.

    private void setupDashboard() {

        // Defensive guard: even though the constructor already did removeAll()
        // + setLayout(), we repeat it here to make absolutely certain no stale
        // state exists if setupDashboard() is ever called from a different path.
        pnlMainContent.removeAll();
        pnlMainContent.setLayout(new java.awt.CardLayout());        // reportsPanel is assigned to the INSTANCE FIELD before any lambda
        // captures it, so btnReports can safely call loadInvoiceHistory().
        // The constructor no longer auto-loads data � we trigger the first
        // load below after all cards are registered, keeping startup fast.
        reportsPanel = new ReportsPanel();        // ORDER MATTERS for Z-order within CardLayout's internal component list.
        // reportsPanel is registered before the Purchase Orders placeholder so
        // it is never visually adjacent to it in the AWT component stack,
        // eliminating the bleed-through observed when Reports was added after it.
        pnlMainContent.add(buildHomePanel(),                        CARD_HOME);
        // PurchaseOrderPanel must be created first so we can pass it into
        // ProductFrame. That way ProductFrame can refresh the product combo
        // immediately after a successful save — no complex patterns needed.
        purchaseOrderPanel = new PurchaseOrderPanel(currentUser);
        productFrame = new ProductFrame(currentUser, purchaseOrderPanel);
        pnlMainContent.add(productFrame,                            CARD_PRODUCTS);
        pnlMainContent.add(new CategoryFrame(currentUser),          CARD_CATEGORIES);
        pnlMainContent.add(new SupplierPanel(currentUser),          CARD_SUPPLIERS);
        pnlMainContent.add(new POSPanel(),                          CARD_POS);
        pnlMainContent.add(reportsPanel,                             CARD_REPORTS);
        pnlMainContent.add(purchaseOrderPanel,                      CARD_PURCHASE_ORDERS);
        pnlMainContent.add(buildComingSoonPanel("User Management"),  CARD_USERS);
        pnlMainContent.add(buildComingSoonPanel("DB Maintenance"),   CARD_DB);        // btnProducts and btnDashboard already have empty NetBeans stubs;
        // addActionListener() adds our real listeners on top � the stubs do nothing.
        btnDashboard .addActionListener(e -> showCard(CARD_HOME,            btnDashboard));
        btnProducts  .addActionListener(e -> showCard(CARD_PRODUCTS,        btnProducts));
        btnCategories.addActionListener(e -> showCard(CARD_CATEGORIES,      btnCategories));
        btnSuppliers .addActionListener(e -> showCard(CARD_SUPPLIERS,       btnSuppliers));
        btnPOS       .addActionListener(e -> showCard(CARD_POS,             btnPOS));
        btnPurchase  .addActionListener(e -> showCard(CARD_PURCHASE_ORDERS, btnPurchase));
        btnUser      .addActionListener(e -> showCard(CARD_USERS,           btnUser));
        btnDB        .addActionListener(e -> showCard(CARD_DB,              btnDB));
        jButton9     .addActionListener(e -> onLogoutClicked());

        // Reports: show card first, then reload fresh data
        btnReports.addActionListener(e -> {
            showCard(CARD_REPORTS, btnReports);
            reportsPanel.loadInvoiceHistory();
        });        ((java.awt.CardLayout) pnlMainContent.getLayout()).show(pnlMainContent, CARD_HOME);
        setButtonActive(btnDashboard);        jLabel2.setText("Welcome,  " + currentUser.getFullName()
                + "  [" + getRoleName(currentUser.getRoleId()) + "]");        JButton[] navButtons = {
            btnDashboard, btnProducts, btnCategories, btnSuppliers,
            btnPOS, btnPurchase, btnReports, btnUser, btnDB
        };
        for (JButton btn : navButtons) {
            if (btn == null) continue;
            btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btn.setBackground(new Color(34, 45, 65));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
        }

        // Logout button � red
        jButton9.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        jButton9.setBackground(new Color(180, 40, 40));
        jButton9.setForeground(Color.WHITE);
        jButton9.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton9.setFocusPainted(false);
        jButton9.setBorderPainted(false);
        jButton9.setOpaque(true);
        jButton9.setContentAreaFilled(true);        jLabel1.setOpaque(false);
        jLabel1.setForeground(Color.WHITE);

        jLabel2.setOpaque(false);
        jLabel2.setForeground(Color.WHITE);
        jLabel2.setMinimumSize(new Dimension(600, 40));
        jLabel2.setMaximumSize(new Dimension(800, 40));
        jLabel2.setPreferredSize(new Dimension(700, 40));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);

        jLabel3.setOpaque(false);
        jLabel3.setForeground(Color.WHITE);
        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 14));

        pnlTopBar.revalidate();
        pnlTopBar.repaint();        // Dashboard KPIs � runs on a background thread
        loadDashboardKpis();
        // Pre-populate the Reports table so it's ready when the user clicks Reports
        reportsPanel.loadInvoiceHistory();
    }
    // CARD SWITCHING

    /**
     * Switches the visible card in pnlMainContent and updates the active
     * button highlight.  Forces a revalidate/repaint so the new panel
     * fully occupies the content area.
     */
    private void showCard(String cardName, JButton activeButton) {
        java.awt.CardLayout cl = (java.awt.CardLayout) pnlMainContent.getLayout();
        cl.show(pnlMainContent, cardName);
        setButtonActive(activeButton);
        pnlMainContent.revalidate();
        pnlMainContent.repaint();
    }

    /**
     * Resets every sidebar button to the default navy colour and highlights
     * only {@code target} with the accent blue.
     */
    private void setButtonActive(JButton target) {
        for (JButton btn : new JButton[]{
                btnDashboard, btnProducts, btnCategories, btnSuppliers,
                btnPOS, btnPurchase, btnReports, btnUser, btnDB}) {
            if (btn != null) {
                btn.setBackground(new Color(34, 45, 65));
                btn.setForeground(Color.WHITE);
            }
        }
        if (target != null) {
            target.setBackground(new Color(41, 98, 178));
            target.setForeground(Color.WHITE);
        }
    }
    // HOME PANEL (Dashboard Overview)

    /**
     * Builds the home card.  All KPI labels and the low-stock JTable are
     * assigned to instance fields here so the SwingWorker can populate them.
     */
    private JPanel buildHomePanel() {
        JPanel pnlHome = new JPanel(new BorderLayout(0, 20));
        pnlHome.setOpaque(true);
        pnlHome.setBackground(UITheme.BG_CONTENT);
        pnlHome.setBorder(new EmptyBorder(20, 24, 20, 24));

        JLabel lblTitle = new JLabel("Dashboard Overview");
        lblTitle.setFont(UITheme.FONT_HEADING);
        lblTitle.setForeground(UITheme.TEXT_PRIMARY);
        lblTitle.setBorder(new EmptyBorder(0, 0, 4, 0));
        pnlHome.add(lblTitle, BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(0, 20));
        centre.setOpaque(false);
        centre.add(buildKpiRow(),        BorderLayout.NORTH);
        centre.add(buildLowStockPanel(), BorderLayout.CENTER);
        pnlHome.add(centre, BorderLayout.CENTER);

        return pnlHome;
    }

    /** Creates the 4-card KPI row and initialises all KPI value labels. */
    private JPanel buildKpiRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 16, 0));
        row.setOpaque(false);

        lblKpiProductsVal   = makeKpiValueLabel("...");
        lblKpiCategoriesVal = makeKpiValueLabel("...");
        lblKpiSuppliersVal  = makeKpiValueLabel("...");
        lblKpiLowStockVal   = makeKpiValueLabel("...");

        row.add(buildKpiCard("Total Products",   lblKpiProductsVal,
                             "\uD83D\uDCE6",      UITheme.ACCENT));
        row.add(buildKpiCard("Categories",       lblKpiCategoriesVal,
                             "\uD83C\uDFF7\uFE0F", UITheme.SUCCESS));
        row.add(buildKpiCard("Active Suppliers", lblKpiSuppliersVal,
                             "\uD83C\uDFED",      UITheme.SECONDARY));
        pnlKpiLowStockCard = buildKpiCard("Low Stock Items", lblKpiLowStockVal,
                                          "\u26A0\uFE0F",    UITheme.SUCCESS);
        row.add(pnlKpiLowStockCard);
        return row;
    }

    /** Builds a single styled KPI card containing a value label and title. */
    private JPanel buildKpiCard(String title, JLabel valueLabel,
                                String icon,  Color  accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UITheme.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                new EmptyBorder(18, 20, 18, 20)));

        JPanel strip = new JPanel();
        strip.setBackground(accent);
        strip.setPreferredSize(new Dimension(5, 0));
        card.add(strip, BorderLayout.WEST);

        JPanel inner = new JPanel(new BorderLayout());
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(0, 14, 0, 0));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        inner.add(lblIcon, BorderLayout.EAST);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UITheme.FONT_SMALL);
        lblTitle.setForeground(UITheme.TEXT_MUTED);

        JPanel textCol = new JPanel();
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        textCol.setOpaque(false);
        textCol.add(valueLabel);
        textCol.add(Box.createVerticalStrut(4));
        textCol.add(lblTitle);
        inner.add(textCol, BorderLayout.CENTER);

        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    /** Creates a large bold KPI value label. */
    private JLabel makeKpiValueLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UITheme.FONT_KPI);
        lbl.setForeground(UITheme.TEXT_PRIMARY);
        return lbl;
    }

    /**
     * Builds the Low Stock Alerts table card.
     * {@code lowStockTable} and {@code lowStockTableModel} are assigned to
     * instance fields so the SwingWorker can fill them after the DB query.
     */
    private JPanel buildLowStockPanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UITheme.BG_CARD);
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.BG_CARD);
        header.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel lblCardTitle = new JLabel("\u26A0  Low Stock Alerts");
        lblCardTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCardTitle.setForeground(UITheme.DANGER);

        JLabel lblHint = new JLabel("Products at or below reorder level");
        lblHint.setFont(UITheme.FONT_SMALL);
        lblHint.setForeground(UITheme.TEXT_MUTED);

        header.add(lblCardTitle, BorderLayout.WEST);
        header.add(lblHint,      BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        String[] cols = {
            "Product Code", "Product Name", "Category",
            "Stock Qty", "Reorder Level", "Status"
        };
        lowStockTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        lowStockTable = new JTable(lowStockTableModel);
        UITheme.styleTable(lowStockTable);
        lowStockTable.getTableHeader().setReorderingAllowed(false);

        lowStockTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean focus, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        t, v, sel, focus, row, col);
                lbl.setBackground(new Color(24, 35, 54));
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setOpaque(true);
                return lbl;
            }
        });

        lowStockTable.getColumnModel().getColumn(5)
                .setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, focus, row, col);
                if (!sel) {
                    String s = (v == null) ? "" : v.toString();
                    setForeground("OUT OF STOCK".equals(s) ? UITheme.DANGER : UITheme.WARNING);
                }
                setFont(new Font("Segoe UI", Font.BOLD, 11));
                setHorizontalAlignment(CENTER);
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(lowStockTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }
    // SWINGWORKER � KPI data loader

    private record KpiData(int products, int categories, int suppliers,
                           int lowStock, List<Product> lowStockList) {}

    private void loadDashboardKpis() {
        new SwingWorker<KpiData, Void>() {
            @Override
            protected KpiData doInBackground() {
                try {
                    int p  = DAOFactory.getProductDAO().findAll().size();
                    int c  = DAOFactory.getCategoryDAO().findActive().size();
                    int s  = DAOFactory.getSupplierDAO().findActive().size();
                    List<Product> ls = DAOFactory.getProductDAO().findLowStock();
                    return new KpiData(p, c, s, ls.size(), ls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return new KpiData(0, 0, 0, 0, List.of());
                }
            }

            @Override
            protected void done() {
                try {
                    KpiData data = get();

                    lblKpiProductsVal  .setText(String.valueOf(data.products()));
                    lblKpiCategoriesVal.setText(String.valueOf(data.categories()));
                    lblKpiSuppliersVal .setText(String.valueOf(data.suppliers()));
                    lblKpiLowStockVal  .setText(String.valueOf(data.lowStock()));

                    if (data.lowStock() > 0) {
                        lblKpiLowStockVal.setForeground(UITheme.DANGER);
                        pnlKpiLowStockCard.getComponent(0).setBackground(UITheme.DANGER);
                    }

                    lowStockTableModel.setRowCount(0);
                    for (Product p : data.lowStockList()) {
                        String status = (p.getQuantityOnHand() == 0)
                                ? "OUT OF STOCK" : "LOW STOCK";
                        lowStockTableModel.addRow(new Object[]{
                            p.getProductCode(), p.getProductName(), p.getCategoryName(),
                            p.getQuantityOnHand(), p.getReorderLevel(), status
                        });
                    }

                    autoResizeColumns(lowStockTable);
                    lowStockTable.getTableHeader().setVisible(true);
                    lowStockTable.getTableHeader().revalidate();
                    lowStockTable.getTableHeader().repaint();

                } catch (ExecutionException ex) {
                    ex.getCause().printStackTrace();
                    JOptionPane.showMessageDialog(DashboardFrame.this,
                            "Could not load dashboard data.\nCheck database connection.",
                            "Data Load Error", JOptionPane.WARNING_MESSAGE);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }.execute();
    }
    // EVENT HANDLERS

    private void onLogoutClicked() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to log out?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Logged out. Please restart the application to log in again.",
                    "Logged Out", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
    // COMING-SOON PLACEHOLDER

    private JPanel buildComingSoonPanel(String moduleName) {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setOpaque(true);
        outer.setBackground(UITheme.BG_CONTENT);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(true);
        card.setBackground(UITheme.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                new EmptyBorder(40, 60, 40, 60)));

        JLabel lblIcon = new JLabel("\uD83D\uDEA7", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel(moduleName, SwingConstants.CENTER);
        lblTitle.setFont(UITheme.FONT_HEADING);
        lblTitle.setForeground(UITheme.TEXT_PRIMARY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel(
                "Design this screen: New \u2192 JPanel Form \u2192 "
                + moduleName.replace(" ", "") + "Frame",
                SwingConstants.CENTER);
        lblSub.setFont(UITheme.FONT_SMALL);
        lblSub.setForeground(UITheme.TEXT_MUTED);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblIcon);
        card.add(Box.createVerticalStrut(12));
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(8));
        card.add(lblSub);

        outer.add(card);
        return outer;
    }
    // UTILITIES

    private String getRoleName(int roleId) {
        return switch (roleId) {
            case 1  -> "Admin";
            case 2  -> "Manager";
            default -> "Cashier";
        };
    }

    private void autoResizeColumns(JTable table) {
        if (table == null) return;
        javax.swing.table.TableColumnModel cm = table.getColumnModel();
        for (int col = 0; col < table.getColumnCount(); col++) {
            int width = 60;
            javax.swing.table.TableCellRenderer hdr =
                    table.getTableHeader().getDefaultRenderer();
            if (hdr != null) {
                Component hc = hdr.getTableCellRendererComponent(
                        table, cm.getColumn(col).getHeaderValue(),
                        false, false, 0, col);
                width = Math.max(hc.getPreferredSize().width + 25, width);
            }
            for (int row = 0; row < table.getRowCount(); row++) {
                Component dc = table.prepareRenderer(
                        table.getCellRenderer(row, col), row, col);
                width = Math.max(dc.getPreferredSize().width + 15, width);
            }
            cm.getColumn(col).setPreferredWidth(width);
        }
    }
    // MAIN � development / test entry point

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            User testUser = new User(1, 1, "admin",
                    "System Administrator", "admin@inventory.local", true);
            new DashboardFrame(testUser).setVisible(true);
        });
    }

} // end class DashboardFrame


