/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.inventorycontrolsystem.view;

import com.mycompany.inventorycontrolsystem.dao.SupplierDAO;
import com.mycompany.inventorycontrolsystem.db.DAOFactory;
import com.mycompany.inventorycontrolsystem.model.Supplier;
import com.mycompany.inventorycontrolsystem.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Supplier Management screen.
 * NetBeans JPanel Form — GUI Builder designed.
 * Backend logic is wired below the locked initComponents() block.
 *
 * Component inventory (GEN-declared):
 *   txtSupplierId    – read-only ID field
 *   txtSupplierName  – company name input
 *   txtContactPerson – contact person input
 *   txtPhone         – phone number input
 *   txtEmail         – email address input
 *   rbActive         – Active radio button  (inside buttonGroup1)
 *   rbInactive       – Inactive radio button
 *   btnSave          – insert or update
 *   btnClear         – reset form
 *   btnDelete        – soft-deactivate the selected supplier
 *
 * tblSuppliers is NOT in the GEN block (not yet added in the NetBeans designer).
 * It is built and injected programmatically in setupData() so that the
 * form editor can be reopened at any time without conflicts.
 */
public class SupplierPanel extends javax.swing.JPanel {
    private final SupplierDAO supplierDAO = DAOFactory.getSupplierDAO();
    private final User currentUser;
    private Supplier editingSupplier = null;
    private DefaultTableModel liveTableModel;
    private JTable tblSuppliers;
    // CONSTRUCTORS

    /**
     * Called by DashboardFrame when the user clicks "Suppliers" in the sidebar.
     */
    public SupplierPanel(User user) {
        this.currentUser = user;
        initComponents();
        setupData();
    }

    /**
     * No-arg constructor for cases where no session user is needed
     * (e.g. design-time preview).
     */
    public SupplierPanel() {
        this.currentUser = null;
        initComponents();
        setupData();
    }
    // NetBeans GUI Builder — GENERATED CODE (do not modify)

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSupplierId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSupplierName = new javax.swing.JTextField();
        txtContactPerson = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rbActive = new javax.swing.JRadioButton();
        rbInactive = new javax.swing.JRadioButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 1, 36)); // NOI18N
        jLabel1.setText("Supplier Management");

        jLabel2.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel2.setText("Supplier ID:");

        txtSupplierId.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel3.setText("Company Name:");

        txtSupplierName.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N

        txtContactPerson.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel4.setText("Contact Person:");

        txtPhone.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel5.setText("Phone Number:");

        jLabel6.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel6.setText("Email Address:");

        txtEmail.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel7.setText("Status:");

        buttonGroup1.add(rbActive);
        rbActive.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        rbActive.setText("Active");

        buttonGroup1.add(rbInactive);
        rbInactive.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        rbInactive.setText("Inactive");

        btnDelete.setBackground(new java.awt.Color(204, 0, 51));
        btnDelete.setFont(new java.awt.Font("JetBrains Mono", 1, 18)); // NOI18N
        btnDelete.setText("Delete");

        btnSave.setBackground(new java.awt.Color(51, 153, 0));
        btnSave.setFont(new java.awt.Font("JetBrains Mono", 1, 18)); // NOI18N
        btnSave.setText("Save");

        btnClear.setFont(new java.awt.Font("JetBrains Mono", 1, 18)); // NOI18N
        btnClear.setText("Clear");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSave))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(336, 336, 336)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtContactPerson, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSupplierName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSupplierId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rbActive)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rbInactive))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClear)
                                .addGap(179, 179, 179)
                                .addComponent(btnDelete)
                                .addGap(107, 107, 107)))))
                .addContainerGap(246, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSupplierId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtContactPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rbActive)
                    .addComponent(rbInactive))
                .addGap(78, 78, 78)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnSave)
                    .addComponent(btnClear))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rbActive;
    private javax.swing.JRadioButton rbInactive;
    private javax.swing.JTextField txtContactPerson;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSupplierId;
    private javax.swing.JTextField txtSupplierName;
    // End of variables declaration//GEN-END:variables
    // BACKEND INITIALISATION
    // Called once from the constructor after initComponents().

    private void setupData() {
        txtSupplierId.setEditable(false);
        txtSupplierId.setBackground(new Color(235, 235, 235));
        txtSupplierId.setToolTipText("Auto-assigned by the database");
        rbActive.setSelected(true);
        //    tblSuppliers is not in the GEN block; we create it here so the
        //    NetBeans form editor can still open the .form file safely.
        buildSuppliersTable();
        btnSave .addActionListener(e -> onSaveClicked());
        btnClear.addActionListener(e -> clearForm());
        btnDelete.addActionListener(e -> onDeleteClicked());
        loadSuppliersAsync();
    }
    // TABLE CONSTRUCTION

    /**
     * Creates {@code tblSuppliers}, wraps it in a {@code JScrollPane}, and
     * appends it below {@code jPanel1} inside this panel's GroupLayout.
     *
     * Columns: ID | Company Name | Contact Person | Phone | Email | Status
     * The ID column is hidden (width=0) but retained so row-click can fetch
     * the supplier_id without an extra DB lookup.
     */
    private void buildSuppliersTable() {
        liveTableModel = new DefaultTableModel(
                new String[]{"ID", "Company Name", "Contact Person",
                             "Phone", "Email", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tblSuppliers = new JTable(liveTableModel);

        // Hide the ID column — kept for data, not for display
        tblSuppliers.getColumnModel().getColumn(0).setMinWidth(0);
        tblSuppliers.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSuppliers.getColumnModel().getColumn(0).setWidth(0);
        tblSuppliers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblSuppliers.setRowHeight(28);
        tblSuppliers.setGridColor(new Color(218, 224, 235));
        tblSuppliers.setSelectionBackground(new Color(41, 98, 178));
        tblSuppliers.setSelectionForeground(Color.WHITE);
        tblSuppliers.setShowVerticalLines(false);
        tblSuppliers.setFillsViewportHeight(true);
        tblSuppliers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Dark-navy header renderer (consistent with every other table in the app)
        tblSuppliers.getTableHeader().setReorderingAllowed(false);
        tblSuppliers.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean focus, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        t, v, sel, focus, row, col);
                lbl.setBackground(new Color(24, 35, 54));
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Colour-coded Status column renderer
        tblSuppliers.getColumnModel().getColumn(5)
                .setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, focus, row, col);
                if (!sel) {
                    String s = (v == null) ? "" : v.toString();
                    setForeground("Active".equals(s)
                            ? new Color(39, 174, 96)
                            : new Color(192, 57, 43));
                }
                setFont(new Font("Segoe UI", Font.BOLD, 12));
                setHorizontalAlignment(CENTER);
                return this;
            }
        });

        // Row click → populate form fields
        tblSuppliers.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onRowSelected();
        });
        JScrollPane scrollPane = new JScrollPane(tblSuppliers);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(new Color(218, 224, 235), 1)));

        // Append the scroll pane below the existing jPanel1 inside a
        // BorderLayout wrapper so it fills all remaining vertical space.
        setLayout(new BorderLayout());
        add(jPanel1,    BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    // DATA LOADING — SwingWorker

    /**
     * Fetches all suppliers on a background thread and populates
     * {@code tblSuppliers} on the EDT.  Called on startup and after every
     * save / delete operation.
     */
    private void loadSuppliersAsync() {
        btnSave .setEnabled(false);
        btnDelete.setEnabled(false);

        new SwingWorker<List<Supplier>, Void>() {
            @Override
            protected List<Supplier> doInBackground() {
                return supplierDAO.findAll();   // includes inactive rows
            }

            @Override
            protected void done() {
                try {
                    List<Supplier> suppliers = get();
                    liveTableModel.setRowCount(0);

                    for (Supplier s : suppliers) {
                        liveTableModel.addRow(new Object[]{
                            s.getSupplierId(),
                            s.getCompanyName(),
                            s.getContactPerson() != null ? s.getContactPerson() : "",
                            s.getPhone()         != null ? s.getPhone()         : "",
                            s.getEmail()         != null ? s.getEmail()         : "",
                            s.isActive() ? "Active" : "Inactive"
                        });
                    }

                    autoResizeColumns(tblSuppliers);

                } catch (ExecutionException ex) {
                    ex.getCause().printStackTrace();
                    JOptionPane.showMessageDialog(SupplierPanel.this,
                            "Could not load suppliers.\nCheck database connection.",
                            "Load Error", JOptionPane.ERROR_MESSAGE);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } finally {
                    btnSave .setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        }.execute();
    }
    // EVENT HANDLERS

    /**
     * Row-click handler.
     * Reads the selected row, populates all form fields, and sets the correct
     * radio button based on the supplier's active status.
     */
    private void onRowSelected() {
        int row = tblSuppliers.getSelectedRow();
        if (row < 0) return;

        int supplierId = (int) liveTableModel.getValueAt(row, 0);

        supplierDAO.findById(supplierId).ifPresent(s -> {
            editingSupplier = s;

            txtSupplierId   .setText(String.valueOf(s.getSupplierId()));
            txtSupplierName .setText(s.getCompanyName()   != null ? s.getCompanyName()   : "");
            txtContactPerson.setText(s.getContactPerson() != null ? s.getContactPerson() : "");
            txtPhone        .setText(s.getPhone()         != null ? s.getPhone()         : "");
            txtEmail        .setText(s.getEmail()         != null ? s.getEmail()         : "");

            if (s.isActive()) {
                rbActive.setSelected(true);
            } else {
                rbInactive.setSelected(true);
            }

        });
    }

    /**
     * Save button handler.
     *
     * Validation rules:
     *   - Company Name must not be blank.
     *   - Phone must not be blank.
     *
     * If {@code editingSupplier} is null, executes an INSERT.
     * If a row was previously selected, executes an UPDATE.
     */
    private void onSaveClicked() {
        String name  = txtSupplierName .getText().trim();
        String phone = txtPhone        .getText().trim();
        String contactPerson = txtContactPerson.getText().trim();
        String email         = txtEmail        .getText().trim();
        if (name.isEmpty()) {
            showWarning("Company Name is required.");
            txtSupplierName.requestFocusInWindow();
            return;
        }
        if (phone.isEmpty()) {
            showWarning("Phone Number is required.");
            txtPhone.requestFocusInWindow();
            return;
        }
        if (!email.isEmpty() && !email.contains("@")) {
            showWarning("Email address does not appear to be valid.");
            txtEmail.requestFocusInWindow();
            return;
        }
        boolean isNew = (editingSupplier == null);
        Supplier s = isNew ? new Supplier() : editingSupplier;
        s.setCompanyName   (name);
        s.setContactPerson (contactPerson.isEmpty() ? null : contactPerson);
        s.setPhone         (phone);
        s.setEmail         (email.isEmpty() ? null : email);
        s.setActive        (rbActive.isSelected());
        // Fields not on this form — preserve existing value on update, default on insert
        if (isNew) {
            s.setAddress  (null);
            s.setCity     (null);
            s.setCountry  ("Malaysia");
            s.setTaxNumber(null);
        }
        boolean success;
        if (isNew) {
            success = supplierDAO.insert(s) > 0;
        } else {
            success = supplierDAO.update(s);
        }

        if (success) {
            String action = isNew ? "added" : "updated";
            String who    = (currentUser != null) ? currentUser.getUsername() : "unknown";
            System.out.println("Supplier '{}' {} by '{}'.");
            JOptionPane.showMessageDialog(this,
                    "Supplier '" + name + "' saved successfully.",
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadSuppliersAsync();
        } else {
            showWarning("Save failed.\n"
                    + "The company name may already exist, or a required field is missing.");
        }
    }

    /**
     * Delete button handler.
     *
     * Performs a soft-delete (sets {@code is_active = 0}) rather than a hard
     * DELETE so purchase order history is preserved.  Requires a row to be
     * selected first.
     */
    private void onDeleteClicked() {
        int row = tblSuppliers.getSelectedRow();
        if (row < 0) {
            showWarning("Please select a supplier from the table first.");
            return;
        }

        int    id   = (int)    liveTableModel.getValueAt(row, 0);
        String name = (String) liveTableModel.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deactivate supplier:\n\"" + name + "\"?\n\n"
                + "The supplier will be hidden but purchase order history is preserved.",
                "Confirm Deactivate",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = supplierDAO.deactivate(id);
            if (ok) {
                String who = (currentUser != null) ? currentUser.getUsername() : "unknown";
                System.out.println("Supplier ID={} '{}' deactivated by '{}'.");
                clearForm();
                loadSuppliersAsync();
            } else {
            }
        }
    }
    // FORM HELPERS

    /**
     * Resets all form fields, radio buttons, and table selection.
     * Sets the panel back to "add new supplier" mode.
     */
    private void clearForm() {
        editingSupplier = null;
        txtSupplierId   .setText("");
        txtSupplierName .setText("");
        txtContactPerson.setText("");
        txtPhone        .setText("");
        txtEmail        .setText("");
        rbActive.setSelected(true);
        tblSuppliers.clearSelection();
        txtSupplierName.requestFocusInWindow();
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Validation", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Auto-sizes every column to fit the widest of its header text and its
     * data cells.  Called from SwingWorker.done() after data is loaded.
     */
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

} // end class SupplierPanel
