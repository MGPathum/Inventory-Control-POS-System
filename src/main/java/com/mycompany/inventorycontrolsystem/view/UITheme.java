package com.mycompany.inventorycontrolsystem.view;

import java.awt.*;

/**
 * Centralised UI constants for the Inventory Control System.
 *
 * Every screen imports this class to ensure a consistent colour palette,
 * typography, and spacing across all 10 modules.  Change a value here and
 * it propagates to the entire application automatically.
 */
public final class UITheme {
    /** Deep navy – used for the sidebar, title-bars, and primary buttons. */
    public static final Color PRIMARY       = new Color(22,  43,  74);
    /** Brighter accent blue – hover states, active nav items. */
    public static final Color ACCENT        = new Color(41,  98, 178);
    /** Soft sky for secondary buttons and badges. */
    public static final Color SECONDARY     = new Color(66, 153, 225);
    /** Success green – save confirmations, stock OK badges. */
    public static final Color SUCCESS       = new Color(39, 174,  96);
    /** Warning amber – low-stock badges, pending-status indicators. */
    public static final Color WARNING       = new Color(243, 156,  18);
    /** Danger red – delete buttons, void actions, error messages. */
    public static final Color DANGER        = new Color(192,  57,  43);
    /** Main content-area background. */
    public static final Color BG_CONTENT    = new Color(245, 247, 250);
    /** Card / panel background. */
    public static final Color BG_CARD       = Color.WHITE;
    /** Table header background. */
    public static final Color BG_TABLE_HDR  = new Color(52,  73, 102);
    /** Light border / separator lines. */
    public static final Color BORDER        = new Color(218, 224, 235);
    /** Primary text colour. */
    public static final Color TEXT_PRIMARY  = new Color( 30,  39,  46);
    /** Muted / secondary text. */
    public static final Color TEXT_MUTED    = new Color(127, 140, 141);
    /** Text on dark (primary/accent) backgrounds. */
    public static final Color TEXT_ON_DARK  = Color.WHITE;
    /** Heading used in section titles and card headers. */
    public static final Font FONT_HEADING   = new Font("Segoe UI", Font.BOLD,  18);
    /** Standard label / body text. */
    public static final Font FONT_BODY      = new Font("Segoe UI", Font.PLAIN, 13);
    /** Small muted label (timestamps, hints). */
    public static final Font FONT_SMALL     = new Font("Segoe UI", Font.PLAIN, 11);
    /** Button text. */
    public static final Font FONT_BUTTON    = new Font("Segoe UI", Font.BOLD,  13);
    /** Table cell text. */
    public static final Font FONT_TABLE     = new Font("Segoe UI", Font.PLAIN, 12);
    /** Table column header text. */
    public static final Font FONT_TABLE_HDR = new Font("Segoe UI", Font.BOLD,  12);
    /** Large numeric display (totals, KPI cards). */
    public static final Font FONT_KPI       = new Font("Segoe UI", Font.BOLD,  26);
    /** Sidebar navigation item text. */
    public static final Font FONT_NAV       = new Font("Segoe UI", Font.BOLD,  13);
    /** Application title in the login screen. */
    public static final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD,  22);
    public static final int SIDEBAR_WIDTH   = 220;
    public static final int TOPBAR_HEIGHT   = 60;
    public static final int ROW_HEIGHT      = 28;
    public static final int BTN_HEIGHT      = 34;
    public static final Insets PADDING_CARD = new Insets(16, 20, 16, 20);

    private UITheme() { /* constants only */ }

    /**
     * Creates a styled, flat {@link JButton} with the given background colour.
     *
     * @param text  button label
     * @param bg    background colour (use UITheme constants)
     * @return a ready-to-use styled button
     */
    public static javax.swing.JButton createButton(String text, Color bg) {
        javax.swing.JButton btn = new javax.swing.JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(bg);
        btn.setForeground(TEXT_ON_DARK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, BTN_HEIGHT));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(bg.darker());
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    /**
     * Styles a {@link javax.swing.JTextField} consistently.
     */
    public static void styleTextField(javax.swing.JTextField field) {
        field.setFont(FONT_BODY);
        field.setBackground(BG_CARD);
        field.setForeground(TEXT_PRIMARY);
        field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER, 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    /**
     * Applies standard column-header styling to a {@link javax.swing.JTable}.
     */
    public static void styleTable(javax.swing.JTable table) {
        table.setFont(FONT_TABLE);
        table.setRowHeight(ROW_HEIGHT);
        table.setGridColor(BORDER);
        table.setSelectionBackground(SECONDARY);
        table.setSelectionForeground(TEXT_ON_DARK);
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(FONT_TABLE_HDR);
        table.getTableHeader().setBackground(BG_TABLE_HDR);
        table.getTableHeader().setForeground(TEXT_ON_DARK);
        table.getTableHeader().setBorder(
            javax.swing.BorderFactory.createEmptyBorder());
    }
}
