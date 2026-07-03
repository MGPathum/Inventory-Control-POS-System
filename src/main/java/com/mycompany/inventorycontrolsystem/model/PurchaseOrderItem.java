package com.mycompany.inventorycontrolsystem.model;

import java.math.BigDecimal;

/**
 * Model – maps to the `purchase_order_items` table.
 */
public class PurchaseOrderItem {

    private int poItemId;
    private int poId;
    private int productId;
    private int quantityOrdered;
    private int quantityReceived;
    private BigDecimal unitCost;

    // Joined
    private String productCode;
    private String productName;

    public PurchaseOrderItem() {}

    public BigDecimal getLineTotal() {
        if (unitCost == null) return BigDecimal.ZERO;
        return unitCost.multiply(BigDecimal.valueOf(quantityOrdered))
                       .setScale(2, java.math.RoundingMode.HALF_UP);
    }
    public int getPoItemId()                            { return poItemId; }
    public void setPoItemId(int id)                     { this.poItemId = id; }

    public int getPoId()                                { return poId; }
    public void setPoId(int id)                         { this.poId = id; }

    public int getProductId()                           { return productId; }
    public void setProductId(int id)                    { this.productId = id; }

    public int getQuantityOrdered()                     { return quantityOrdered; }
    public void setQuantityOrdered(int qty)             { this.quantityOrdered = qty; }

    public int getQuantityReceived()                    { return quantityReceived; }
    public void setQuantityReceived(int qty)            { this.quantityReceived = qty; }

    public BigDecimal getUnitCost()                     { return unitCost; }
    public void setUnitCost(BigDecimal cost)            { this.unitCost = cost; }

    public String getProductCode()                      { return productCode; }
    public void setProductCode(String code)             { this.productCode = code; }

    public String getProductName()                      { return productName; }
    public void setProductName(String name)             { this.productName = name; }
}
