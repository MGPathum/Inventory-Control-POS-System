package com.mycompany.inventorycontrolsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Model – maps to the `sales_invoices` table.
 * Holds a list of {@link InvoiceItem} as its line items.
 */
public class SalesInvoice {

    private int invoiceId;
    private int userId;
    private String invoiceNumber;
    private LocalDateTime invoiceDate;
    private String customerName;
    private String customerPhone;
    private BigDecimal subtotal      = BigDecimal.ZERO;
    private BigDecimal taxAmount     = BigDecimal.ZERO;
    private BigDecimal discountAmount= BigDecimal.ZERO;
    private BigDecimal totalAmount   = BigDecimal.ZERO;
    private BigDecimal amountPaid    = BigDecimal.ZERO;
    private BigDecimal changeAmount  = BigDecimal.ZERO;
    private String paymentMethod;
    private String status;
    private String notes;

    // Joined
    private String cashierName;

    // Child collection
    private List<InvoiceItem> items = new ArrayList<>();

    public SalesInvoice() {}
    public int getInvoiceId()                           { return invoiceId; }
    public void setInvoiceId(int id)                    { this.invoiceId = id; }

    public int getUserId()                              { return userId; }
    public void setUserId(int id)                       { this.userId = id; }

    public String getInvoiceNumber()                    { return invoiceNumber; }
    public void setInvoiceNumber(String num)            { this.invoiceNumber = num; }

    public LocalDateTime getInvoiceDate()               { return invoiceDate; }
    public void setInvoiceDate(LocalDateTime dt)        { this.invoiceDate = dt; }

    public String getCustomerName()                     { return customerName; }
    public void setCustomerName(String name)            { this.customerName = name; }

    public String getCustomerPhone()                    { return customerPhone; }
    public void setCustomerPhone(String phone)          { this.customerPhone = phone; }

    public BigDecimal getSubtotal()                     { return subtotal; }
    public void setSubtotal(BigDecimal v)               { this.subtotal = v; }

    public BigDecimal getTaxAmount()                    { return taxAmount; }
    public void setTaxAmount(BigDecimal v)              { this.taxAmount = v; }

    public BigDecimal getDiscountAmount()               { return discountAmount; }
    public void setDiscountAmount(BigDecimal v)         { this.discountAmount = v; }

    public BigDecimal getTotalAmount()                  { return totalAmount; }
    public void setTotalAmount(BigDecimal v)            { this.totalAmount = v; }

    public BigDecimal getAmountPaid()                   { return amountPaid; }
    public void setAmountPaid(BigDecimal v)             { this.amountPaid = v; }

    public BigDecimal getChangeAmount()                 { return changeAmount; }
    public void setChangeAmount(BigDecimal v)           { this.changeAmount = v; }

    public String getPaymentMethod()                    { return paymentMethod; }
    public void setPaymentMethod(String m)              { this.paymentMethod = m; }

    public String getStatus()                           { return status; }
    public void setStatus(String status)                { this.status = status; }

    public String getNotes()                            { return notes; }
    public void setNotes(String notes)                  { this.notes = notes; }

    public String getCashierName()                      { return cashierName; }
    public void setCashierName(String name)             { this.cashierName = name; }

    public List<InvoiceItem> getItems()                 { return items; }
    public void setItems(List<InvoiceItem> items)       { this.items = items; }
    public void addItem(InvoiceItem item)               { this.items.add(item); }
}
