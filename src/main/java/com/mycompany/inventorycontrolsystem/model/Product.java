package com.mycompany.inventorycontrolsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model – maps to the `products` table.
 */
public class Product {

    private int productId;
    private int categoryId;
    private Integer supplierId;          // nullable
    private String productCode;
    private String productName;
    private String description;
    private String unit;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private BigDecimal taxRate;
    private int reorderLevel;
    private String barcode;
    private String imagePath;
    private boolean active;
    private LocalDateTime createdAt;
    private String categoryName;
    private String supplierName;
    private int quantityOnHand;          // from inventory_stock

    public Product() {}
    public int getProductId()                           { return productId; }
    public void setProductId(int id)                    { this.productId = id; }

    public int getCategoryId()                          { return categoryId; }
    public void setCategoryId(int id)                   { this.categoryId = id; }

    public Integer getSupplierId()                      { return supplierId; }
    public void setSupplierId(Integer id)               { this.supplierId = id; }

    public String getProductCode()                      { return productCode; }
    public void setProductCode(String code)             { this.productCode = code; }

    public String getProductName()                      { return productName; }
    public void setProductName(String name)             { this.productName = name; }

    public String getDescription()                      { return description; }
    public void setDescription(String desc)             { this.description = desc; }

    public String getUnit()                             { return unit; }
    public void setUnit(String unit)                    { this.unit = unit; }

    public BigDecimal getCostPrice()                    { return costPrice; }
    public void setCostPrice(BigDecimal price)          { this.costPrice = price; }

    public BigDecimal getSellingPrice()                 { return sellingPrice; }
    public void setSellingPrice(BigDecimal price)       { this.sellingPrice = price; }

    public BigDecimal getTaxRate()                      { return taxRate; }
    public void setTaxRate(BigDecimal rate)             { this.taxRate = rate; }

    public int getReorderLevel()                        { return reorderLevel; }
    public void setReorderLevel(int level)              { this.reorderLevel = level; }

    public String getBarcode()                          { return barcode; }
    public void setBarcode(String barcode)              { this.barcode = barcode; }

    public String getImagePath()                        { return imagePath; }
    public void setImagePath(String path)               { this.imagePath = path; }

    public boolean isActive()                           { return active; }
    public void setActive(boolean active)               { this.active = active; }

    public LocalDateTime getCreatedAt()                 { return createdAt; }
    public void setCreatedAt(LocalDateTime t)           { this.createdAt = t; }

    public String getCategoryName()                     { return categoryName; }
    public void setCategoryName(String name)            { this.categoryName = name; }

    public String getSupplierName()                     { return supplierName; }
    public void setSupplierName(String name)            { this.supplierName = name; }

    public int getQuantityOnHand()                      { return quantityOnHand; }
    public void setQuantityOnHand(int qty)              { this.quantityOnHand = qty; }

    @Override
    public String toString() {
        return "Product{id=" + productId + ", code='" + productCode +
               "', name='" + productName + "'}";
    }
}
