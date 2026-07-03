/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.inventorycontrolsystem.view;

import com.mycompany.inventorycontrolsystem.dao.SalesInvoiceDAO;
import com.mycompany.inventorycontrolsystem.dao.impl.SalesInvoiceDAOImpl;
import com.mycompany.inventorycontrolsystem.model.SalesInvoice;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Reports screen — shows a searchable, refreshable read-only history of all
 * sales invoices.
 *
 * Design: AbsoluteLayout JPanel Form (NetBeans GUI Builder).
 * All backend logic lives below the GEN-BEGIN/GEN-END locked block.
 */
public class ReportsPanel extends javax.swing.JPanel {

    
    // DAO — initialised once in the constructor
    private final SalesInvoiceDAO salesInvoiceDAO;

    // Live table model — replaces the 4-row design-time placeholder
    private DefaultTableModel liveModel;

    // Master copy of all loaded invoices; used for client-side search filtering
    private List<SalesInvoice> masterList = new ArrayList<>();
    // CONSTRUCTOR

    public ReportsPanel() {
        initComponents();   // NetBeans locked block — do not touch

        // Force a fully opaque, solid background so this panel never bleeds
        // through to whatever card sits behind it in the parent CardLayout.
        this.setOpaque(true);
        this.setBackground(new java.awt.Color(245, 245, 245));

        this.salesInvoiceDAO = new SalesInvoiceDAOImpl();

        setupData();      // wire live model, table theme, button listeners
    }
    // NetBeans GUI Builder — GENERATED CODE (do not modify)

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblHeaderTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        scrReports = new javax.swing.JScrollPane();
        tblReports = new javax.swing.JTable();
        btnExportPDF = new javax.swing.JButton();
        btnExportExcel = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(20, 30, 54));

        lblHeaderTitle.setFont(new java.awt.Font("JetBrains Mono", 1, 36)); // NOI18N
        lblHeaderTitle.setText("Invoice Reports History");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeaderTitle)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(912, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeaderTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1470, -1));

        lblSearch.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(0, 0, 0));
        lblSearch.setText("Search");
        add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, -1, 30));

        txtSearch.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 250, -1));

        btnSearch.setFont(new java.awt.Font("JetBrains Mono", 1, 18)); // NOI18N
        btnSearch.setText("Search");
        add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, -1, -1));

        btnRefresh.setFont(new java.awt.Font("JetBrains Mono", 1, 18)); // NOI18N
        btnRefresh.setText("Refresh");
        add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 170, -1, -1));

        tblReports.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        tblReports.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Invoice ID", "Grand Total", "Discount", "Net Total", "Cash Paid", "null", "Status"
            }
        ));
        scrReports.setViewportView(tblReports);

        add(scrReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 1400, 390));

        btnExportPDF.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        btnExportPDF.setText("Export PDF Report");
        add(btnExportPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 640, 280, -1));

        btnExportExcel.setFont(new java.awt.Font("JetBrains Mono", 1, 24)); // NOI18N
        btnExportExcel.setText("Export to Excel");
        add(btnExportExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 640, 280, -1));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportExcel;
    private javax.swing.JButton btnExportPDF;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHeaderTitle;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JScrollPane scrReports;
    private javax.swing.JTable tblReports;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
    // BACKEND INITIALISATION
    // Called once from the constructor after initComponents().

    private void setupData() {
        //    Columns match the GEN block exactly, except "null" is corrected
        //    to "Balance" (a typo in the original design).
        liveModel = new DefaultTableModel(
                new String[]{
                    "Invoice ID", "Grand Total", "Discount",
                    "Net Total",  "Cash Paid",   "Balance",  "Status"
                }, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }

            @Override
            public Class<?> getColumnClass(int col) {
                // Invoice ID is int; monetary columns are String (pre-formatted);
                // Status is String.
                return col == 0 ? Integer.class : String.class;
            }
        };
        tblReports.setModel(liveModel);
        styleTable();
        btnSearch .addActionListener(e -> onSearchClicked());
        btnRefresh.addActionListener(e -> loadInvoiceHistory());

        // Enter key in the search box also triggers a search
        txtSearch.addActionListener(e -> onSearchClicked());

        // Export stubs — wired to their handlers
        btnExportPDF  .addActionListener(e -> onExportPDF());
        btnExportExcel.addActionListener(e -> onExportExcel());
        jPanel1.setOpaque(true);
        lblHeaderTitle.setForeground(java.awt.Color.WHITE);
        scrReports.setOpaque(true);
        scrReports.getViewport().setOpaque(true);
        scrReports.getViewport().setBackground(java.awt.Color.WHITE);
    }
    // TABLE STYLING

    private void styleTable() {
        tblReports.setRowHeight(28);
        tblReports.setShowVerticalLines(false);
        tblReports.setGridColor(new Color(218, 224, 235));
        tblReports.setSelectionBackground(new Color(41, 98, 178));
        tblReports.setSelectionForeground(Color.WHITE);
        tblReports.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblReports.setFillsViewportHeight(true);

        // Dark-navy header — overrides Nimbus completely via a custom renderer
        tblReports.getTableHeader().setReorderingAllowed(false);
        tblReports.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(new Color(20, 30, 54));
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Right-align monetary columns (Grand Total, Discount, Net Total,
        // Cash Paid, Balance → columns 1–5)
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(JLabel.RIGHT);
        for (int col = 1; col <= 5; col++) {
            tblReports.getColumnModel().getColumn(col).setCellRenderer(rightAlign);
        }

        // Status column — colour-coded renderer
        tblReports.getColumnModel().getColumn(6).setCellRenderer(
                new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, focus, row, col);
                if (!sel) {
                    String s = (v == null) ? "" : v.toString();
                    switch (s.toUpperCase()) {
                        case "PAID"   -> setForeground(new Color(39, 174, 96));
                        case "VOID"   -> setForeground(new Color(192, 57, 43));
                        default       -> setForeground(new Color(243, 156, 18));
                    }
                }
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 12));
                return this;
            }
        });
    }
    // DATA LOADING

    /**
     * Fetches every invoice from the database, stores the master list, and
     * renders it in {@code tblReports}.
     *
     * Called:
     *   - Once from {@code DashboardFrame.setupData()} when the app starts.
     *   - Again each time the user clicks the "Reports" sidebar button, so the
     *     table always shows the most recent transactions.
     *   - By the Refresh button to force a manual reload.
     */
    public void loadInvoiceHistory() {
        // Clear search box and disable buttons while loading
        txtSearch.setText("");
        btnSearch .setEnabled(false);
        btnRefresh.setEnabled(false);

        new SwingWorker<List<SalesInvoice>, Void>() {
            @Override
            protected List<SalesInvoice> doInBackground() {
                return salesInvoiceDAO.findAll();
            }

            @Override
            protected void done() {
                try {
                    masterList = get();
                    populateTable(masterList);
                } catch (java.util.concurrent.ExecutionException ex) {
                    ex.getCause().printStackTrace();
                    JOptionPane.showMessageDialog(ReportsPanel.this,
                            "Could not load invoice history.\n"
                            + "Check database connection.\n\n" + ex.getCause().getMessage(),
                            "Data Load Error", JOptionPane.WARNING_MESSAGE);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } finally {
                    btnSearch .setEnabled(true);
                    btnRefresh.setEnabled(true);
                }
            }
        }.execute();
    }

    /**
     * Clears the live model and fills it from {@code invoices}.
     * Always called on the EDT (either directly from done() or from search).
     */
    private void populateTable(List<SalesInvoice> invoices) {
        liveModel.setRowCount(0);
        for (SalesInvoice inv : invoices) {
            liveModel.addRow(new Object[]{
                inv.getInvoiceId(),
                inv.getSubtotal()       != null
                        ? String.format("%.2f", inv.getSubtotal())       : "0.00",
                inv.getDiscountAmount() != null
                        ? String.format("%.2f", inv.getDiscountAmount()) : "0.00",
                inv.getTotalAmount()    != null
                        ? String.format("%.2f", inv.getTotalAmount())    : "0.00",
                inv.getAmountPaid()     != null
                        ? String.format("%.2f", inv.getAmountPaid())     : "0.00",
                inv.getChangeAmount()   != null
                        ? String.format("%.2f", inv.getChangeAmount())   : "0.00",
                inv.getStatus() != null ? inv.getStatus() : ""
            });
        }
        revalidate();
        repaint();
    }
    // SEARCH

    /**
     * Filters {@code masterList} client-side by Invoice ID (starts-with match)
     * and re-renders the table.  No DB round-trip needed.
     *
     * If the search box is blank, the full master list is restored.
     */
    private void onSearchClicked() {
        String keyword = txtSearch.getText().trim();

        if (keyword.isEmpty()) {
            populateTable(masterList);
            return;
        }

        List<SalesInvoice> filtered = new ArrayList<>();
        for (SalesInvoice inv : masterList) {
            // Match on invoice ID (numeric prefix or exact) OR status text
            String idStr = String.valueOf(inv.getInvoiceId());
            String status = inv.getStatus() != null ? inv.getStatus().toLowerCase() : "";
            if (idStr.startsWith(keyword) || status.contains(keyword.toLowerCase())) {
                filtered.add(inv);
            }
        }

        populateTable(filtered);
    }
    // EXPORT STUBS

    /**
     * Exports the currently displayed invoice list to a real PDF file using
     * Apache PDFBox via {@link com.mycompany.inventorycontrolsystem.util.PdfExportUtil}.
     */
    private void onExportPDF() {
        if (liveModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No data to export. Load or search invoices first.",
                    "Nothing to Export", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save PDF Report");
        fc.setSelectedFile(new java.io.File("Invoice_Report.pdf"));
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "PDF Files (*.pdf)", "pdf"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String path = fc.getSelectedFile().getAbsolutePath();
        if (!path.toLowerCase().endsWith(".pdf")) path += ".pdf";

        // Snapshot table data on the EDT before handing to the background worker
        final int rowCount = liveModel.getRowCount();
        final int colCount = liveModel.getColumnCount();
        final java.util.List<Object[]> snap = new java.util.ArrayList<>(rowCount);
        for (int r = 0; r < rowCount; r++) {
            Object[] row = new Object[colCount];
            for (int c = 0; c < colCount; c++) row[c] = liveModel.getValueAt(r, c);
            snap.add(row);
        }
        final String finalPath = path;

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                String[] headers = {"Invoice ID", "Grand Total", "Discount",
                                    "Net Total",  "Cash Paid",   "Balance",  "Status"};
                com.mycompany.inventorycontrolsystem.util.PdfExportUtil.exportTable(
                        "Invoice Reports History",
                        headers,
                        snap,
                        finalPath,
                        "System User");
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(ReportsPanel.this,
                            "PDF exported successfully!\nSaved to: " + finalPath,
                            "Export Complete", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ReportsPanel.this,
                            "PDF export failed:\n" + ex.getMessage(),
                            "Export Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    /**
     * Export to Excel (.xlsx) using Apache POI.
     */
    private void onExportExcel() {
        if (liveModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No data to export. Load or search invoices first.",
                    "Nothing to Export", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save Excel Report");
        fc.setSelectedFile(new java.io.File("Invoice_Report.xlsx"));
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Excel Files (*.xlsx)", "xlsx"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String path = fc.getSelectedFile().getAbsolutePath();
        if (!path.toLowerCase().endsWith(".xlsx")) path += ".xlsx";

        final int rows = liveModel.getRowCount();
        final int cols = liveModel.getColumnCount();
        final Object[][] snap = new Object[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                snap[r][c] = liveModel.getValueAt(r, c);
        final String finalPath = path;

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (org.apache.poi.xssf.usermodel.XSSFWorkbook wb =
                             new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {

                    org.apache.poi.ss.usermodel.Sheet sheet =
                            wb.createSheet("Invoice Report");

                    // Header style — dark navy
                    org.apache.poi.ss.usermodel.CellStyle hStyle = wb.createCellStyle();
                    org.apache.poi.ss.usermodel.Font hFont = wb.createFont();
                    hFont.setBold(true);
                    hFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
                    hFont.setFontHeightInPoints((short) 12);
                    hStyle.setFont(hFont);
                    hStyle.setFillForegroundColor(new org.apache.poi.xssf.usermodel.XSSFColor(
                            new byte[]{(byte) 20, (byte) 30, (byte) 54}, null));
                    hStyle.setFillPattern(
                            org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);

                    String[] headers = {"Invoice ID","Grand Total","Discount",
                                        "Net Total","Cash Paid","Balance","Status"};
                    org.apache.poi.ss.usermodel.Row hRow = sheet.createRow(0);
                    for (int c = 0; c < headers.length; c++) {
                        org.apache.poi.ss.usermodel.Cell cell = hRow.createCell(c);
                        cell.setCellValue(headers[c]);
                        cell.setCellStyle(hStyle);
                    }

                    for (int r = 0; r < snap.length; r++) {
                        org.apache.poi.ss.usermodel.Row row = sheet.createRow(r + 1);
                        for (int c = 0; c < snap[r].length; c++) {
                            org.apache.poi.ss.usermodel.Cell cell = row.createCell(c);
                            Object val = snap[r][c];
                            if (val instanceof Number n)
                                cell.setCellValue(n.doubleValue());
                            else
                                cell.setCellValue(val != null ? val.toString() : "");
                        }
                    }

                    for (int c = 0; c < headers.length; c++) {
                        sheet.autoSizeColumn(c);
                        sheet.setColumnWidth(c, sheet.getColumnWidth(c) + 1024);
                    }

                    try (java.io.FileOutputStream fos = new java.io.FileOutputStream(finalPath)) {
                        wb.write(fos);
                    }
                }
                return null;
            }
            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(ReportsPanel.this,
                            "Export successful!\nSaved to: " + finalPath,
                            "Export Complete", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ReportsPanel.this,
                            "Export failed: " + ex.getMessage(),
                            "Export Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

} // end class ReportsPanel
