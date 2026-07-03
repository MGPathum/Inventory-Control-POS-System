package com.mycompany.inventorycontrolsystem.controller;

import com.mycompany.inventorycontrolsystem.dao.SalesInvoiceDAO;
import com.mycompany.inventorycontrolsystem.db.DAOFactory;
import com.mycompany.inventorycontrolsystem.model.InvoiceItem;
import com.mycompany.inventorycontrolsystem.model.Product;
import com.mycompany.inventorycontrolsystem.model.SalesInvoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for POS billing and invoice management.
 *
 * <p>The GUI builds a {@link SalesInvoice} with line items, then calls
 * {@link #finaliseInvoice(SalesInvoice, BigDecimal)} to calculate totals
 * and persist everything in one atomic transaction.
 */
public class SalesController {

    
    private final SalesInvoiceDAO invoiceDAO;

    public SalesController() {
        this.invoiceDAO = DAOFactory.getSalesInvoiceDAO();
    }

    /**
     * Creates a new, empty {@link SalesInvoice} pre-populated with a
     * generated invoice number and today's date-time.
     *
     * @param userId the cashier's user_id
     */
    public SalesInvoice newInvoice(int userId) {
        SalesInvoice inv = new SalesInvoice();
        inv.setUserId       (userId);
        inv.setInvoiceNumber(invoiceDAO.generateInvoiceNumber());
        inv.setInvoiceDate  (LocalDateTime.now());
        inv.setCustomerName ("Walk-in Customer");
        inv.setPaymentMethod("CASH");
        inv.setStatus       ("PAID");
        return inv;
    }

    /**
     * Adds a product to an in-progress invoice as a line item.
     *
     * @param invoice     the current invoice being built
     * @param product     product to add (must have stock available)
     * @param quantity    quantity to sell
     * @param discountPct per-item discount percentage (0 if none)
     */
    public void addLineItem(SalesInvoice invoice, Product product,
                            int quantity, BigDecimal discountPct) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        if (product.getQuantityOnHand() < quantity)
            throw new IllegalArgumentException(
                "Insufficient stock for '" + product.getProductName() +
                "'. Available: " + product.getQuantityOnHand());

        InvoiceItem item = new InvoiceItem(
            product.getProductId(),
            product.getProductCode(),
            product.getProductName(),
            quantity,
            product.getSellingPrice(),
            product.getTaxRate()
        );
        item.setDiscountPct(discountPct != null ? discountPct : BigDecimal.ZERO);
        item.recalculate();

        invoice.addItem(item);
        recalculateTotals(invoice);
    }

    /**
     * Removes a line item from the invoice and recalculates totals.
     */
    public void removeLineItem(SalesInvoice invoice, int itemIndex) {
        invoice.getItems().remove(itemIndex);
        recalculateTotals(invoice);
    }

    /**
     * Recalculates subtotal, tax, discount and total on the invoice header.
     * Call this whenever items are added or removed.
     */
    public void recalculateTotals(SalesInvoice invoice) {
        BigDecimal subtotal   = BigDecimal.ZERO;
        BigDecimal taxTotal   = BigDecimal.ZERO;
        BigDecimal discTotal  = BigDecimal.ZERO;

        for (InvoiceItem item : invoice.getItems()) {
            BigDecimal rawLine  = item.getUnitPrice()
                                      .multiply(BigDecimal.valueOf(item.getQuantity()));
            BigDecimal disc     = rawLine.multiply(
                item.getDiscountPct().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
            BigDecimal afterDisc = rawLine.subtract(disc);
            BigDecimal tax      = afterDisc.multiply(
                item.getTaxRate().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));

            subtotal  = subtotal.add(afterDisc);
            taxTotal  = taxTotal.add(tax);
            discTotal = discTotal.add(disc);
        }

        BigDecimal total = subtotal.add(taxTotal).setScale(2, RoundingMode.HALF_UP);

        invoice.setSubtotal      (subtotal .setScale(2, RoundingMode.HALF_UP));
        invoice.setTaxAmount     (taxTotal .setScale(2, RoundingMode.HALF_UP));
        invoice.setDiscountAmount(discTotal.setScale(2, RoundingMode.HALF_UP));
        invoice.setTotalAmount   (total);
    }

    /**
     * Finalises and persists the invoice.
     *
     * <ul>
     *   <li>Validates that the invoice has at least one item.</li>
     *   <li>Calculates change amount from amountPaid.</li>
     *   <li>Calls {@link SalesInvoiceDAO#insertWithItems(SalesInvoice)} which
     *       is atomic (header + items + stock decrement in one transaction).</li>
     * </ul>
     *
     * @param invoice    the completed invoice
     * @param amountPaid cash/card amount tendered by the customer
     * @return the saved invoice_id
     * @throws IllegalArgumentException if validation fails
     */
    public int finaliseInvoice(SalesInvoice invoice, BigDecimal amountPaid) {
        if (invoice.getItems().isEmpty())
            throw new IllegalArgumentException("Cannot finalise an invoice with no items.");
        if (amountPaid.compareTo(invoice.getTotalAmount()) < 0)
            throw new IllegalArgumentException(
                "Amount paid (RM " + amountPaid + ") is less than total (RM " +
                invoice.getTotalAmount() + ").");

        invoice.setAmountPaid  (amountPaid);
        invoice.setChangeAmount(amountPaid.subtract(invoice.getTotalAmount())
                                          .setScale(2, RoundingMode.HALF_UP));
        invoice.setInvoiceDate (LocalDateTime.now());

        int id = invoiceDAO.insertWithItems(invoice);
        if (id <= 0) throw new RuntimeException(
            "Failed to save invoice. Check stock levels and database connection.");

        return id;
    }

    /**
     * Voids a previously saved invoice (does NOT restore stock — requires
     * a manual adjustment).
     */
    public boolean voidInvoice(int invoiceId) {
        System.out.println("Voiding invoice ID={}");
        return invoiceDAO.voidInvoice(invoiceId);
    }

    public List<SalesInvoice> getAllInvoices()                              { return invoiceDAO.findAll(); }
    public List<SalesInvoice> getInvoicesByDate(LocalDate from, LocalDate to){ return invoiceDAO.findByDateRange(from, to); }
    public Optional<SalesInvoice> getInvoiceByNumber(String number)        { return invoiceDAO.findByNumber(number); }
    public String generateNextInvoiceNumber()                              { return invoiceDAO.generateInvoiceNumber(); }
}
