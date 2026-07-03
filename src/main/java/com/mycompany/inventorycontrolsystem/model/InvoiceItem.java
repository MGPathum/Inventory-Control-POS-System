package com.mycompany.inventorycontrolsystem.model;

import java.math.BigDecimal;

/**
 * Model – maps to the `invoice_items` table.
 */
public class InvoiceItem {

    private int itemId;
    private int invoiceId;
    private int productId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal discountPct  = BigDecimal.ZERO;
    private BigDecimal taxRate      = BigDecimal.ZERO;
    private BigDecimal lineTotal    = BigDecimal.ZERO;

    // Joined
    private String productCode;
    private String productName;

    public InvoiceItem() {}

    public InvoiceItem(int productId, String productCode, String productName,
                       int quantity, BigDecimal unitPrice, BigDecimal taxRate) {
        this.productId   = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.quantity    = quantity;
        this.unitPrice   = unitPrice;
        this.taxRate     = taxRate;
        recalculate();
    }

    /** Recalculates lineTotal: qty * unitPrice * (1 - discount%) * (1 + tax%) */
    public void recalculate() {
        if (unitPrice == null) return;
        BigDecimal base = unitPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal afterDiscount = base.multiply(
            BigDecimal.ONE.subtract(discountPct.divide(BigDecimal.valueOf(100)))
        );
        BigDecimal taxMult = BigDecimal.ONE.add(
            taxRate.divide(BigDecimal.valueOf(100))
        );
        this.lineTotal = afterDiscount.multiply(taxMult)
                                      .setScale(2, java.math.RoundingMode.HALF_UP);
    }
    public int getItemId()                              { return itemId; }
    public void setItemId(int id)                       { this.itemId = id; }

    public int getInvoiceId()                           { return invoiceId; }
    public void setInvoiceId(int id)                    { this.invoiceId = id; }

    public int getProductId()                           { return productId; }
    public void setProductId(int id)                    { this.productId = id; }

    public int getQuantity()                            { return quantity; }
    public void setQuantity(int qty)                    { this.quantity = qty; recalculate(); }

    public BigDecimal getUnitPrice()                    { return unitPrice; }
    public void setUnitPrice(BigDecimal p)              { this.unitPrice = p; recalculate(); }

    public BigDecimal getDiscountPct()                  { return discountPct; }
    public void setDiscountPct(BigDecimal d)            { this.discountPct = d; recalculate(); }

    public BigDecimal getTaxRate()                      { return taxRate; }
    public void setTaxRate(BigDecimal r)                { this.taxRate = r; recalculate(); }

    public BigDecimal getLineTotal()                    { return lineTotal; }
    public void setLineTotal(BigDecimal lt)             { this.lineTotal = lt; }

    public String getProductCode()                      { return productCode; }
    public void setProductCode(String code)             { this.productCode = code; }

    public String getProductName()                      { return productName; }
    public void setProductName(String name)             { this.productName = name; }
}
