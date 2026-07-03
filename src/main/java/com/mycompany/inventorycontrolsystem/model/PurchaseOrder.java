package com.mycompany.inventorycontrolsystem.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Model – maps to the `purchase_orders` table.
 */
public class PurchaseOrder {

    public enum Status { PENDING, PARTIAL, RECEIVED, CANCELLED }

    private int poId;
    private int supplierId;
    private int userId;
    private String poNumber;
    private LocalDate orderDate;
    private LocalDate expectedDate;
    private LocalDate receivedDate;
    private Status status = Status.PENDING;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private String notes;
    private LocalDateTime createdAt;

    // Joined
    private String supplierName;
    private String raisedByName;

    private List<PurchaseOrderItem> items = new ArrayList<>();

    public PurchaseOrder() {}
    public int getPoId()                                { return poId; }
    public void setPoId(int id)                         { this.poId = id; }

    public int getSupplierId()                          { return supplierId; }
    public void setSupplierId(int id)                   { this.supplierId = id; }

    public int getUserId()                              { return userId; }
    public void setUserId(int id)                       { this.userId = id; }

    public String getPoNumber()                         { return poNumber; }
    public void setPoNumber(String num)                 { this.poNumber = num; }

    public LocalDate getOrderDate()                     { return orderDate; }
    public void setOrderDate(LocalDate d)               { this.orderDate = d; }

    public LocalDate getExpectedDate()                  { return expectedDate; }
    public void setExpectedDate(LocalDate d)            { this.expectedDate = d; }

    public LocalDate getReceivedDate()                  { return receivedDate; }
    public void setReceivedDate(LocalDate d)            { this.receivedDate = d; }

    public Status getStatus()                           { return status; }
    public void setStatus(Status status)                { this.status = status; }

    public BigDecimal getTotalAmount()                  { return totalAmount; }
    public void setTotalAmount(BigDecimal v)            { this.totalAmount = v; }

    public String getNotes()                            { return notes; }
    public void setNotes(String notes)                  { this.notes = notes; }

    public LocalDateTime getCreatedAt()                 { return createdAt; }
    public void setCreatedAt(LocalDateTime t)           { this.createdAt = t; }

    public String getSupplierName()                     { return supplierName; }
    public void setSupplierName(String name)            { this.supplierName = name; }

    public String getRaisedByName()                     { return raisedByName; }
    public void setRaisedByName(String name)            { this.raisedByName = name; }

    public List<PurchaseOrderItem> getItems()           { return items; }
    public void setItems(List<PurchaseOrderItem> items) { this.items = items; }
    public void addItem(PurchaseOrderItem item)         { this.items.add(item); }
}
