package com.mycompany.inventorycontrolsystem.dao;

import com.mycompany.inventorycontrolsystem.model.SalesInvoice;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** DAO contract for {@link SalesInvoice} (header + items). */
public interface SalesInvoiceDAO {

    /**
     * Persists the invoice header AND all its line items in one transaction.
     * Also decrements inventory_stock for each sold product.
     * @return generated invoice_id
     */
    int insertWithItems(SalesInvoice invoice);
    
    /**
     * Persists the invoice header AND all its line items in one transaction
     * using the provided connection (for external transaction management).
     * Also decrements inventory_stock for each sold product.
     * @param invoice the invoice to persist
     * @param conn the connection to use (must have autocommit disabled by caller)
     * @return generated invoice_id
     * @throws java.sql.SQLException if database operation fails
     */
    int insertWithItems(SalesInvoice invoice, Connection conn) throws java.sql.SQLException;

    boolean voidInvoice(int invoiceId);

    Optional<SalesInvoice> findById(int invoiceId);
    Optional<SalesInvoice> findByNumber(String invoiceNumber);

    List<SalesInvoice> findAll();
    List<SalesInvoice> findByDateRange(LocalDate from, LocalDate to);
    List<SalesInvoice> findByUserId(int userId);

    /** Returns the next sequential invoice number e.g. INV-2025-00042. */
    String generateInvoiceNumber();

    /**
     * Returns the last (highest) invoice_id currently in the sales_invoices table.
     * Returns 0 if the table is empty.
     * Used by the POS panel to display the next invoice number on load.
     */
    int getLastInvoiceId() throws java.sql.SQLException;
}
