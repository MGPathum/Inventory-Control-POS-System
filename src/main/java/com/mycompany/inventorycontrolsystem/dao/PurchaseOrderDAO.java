package com.mycompany.inventorycontrolsystem.dao;

import com.mycompany.inventorycontrolsystem.model.PurchaseOrder;
import java.util.List;
import java.util.Optional;

/** DAO contract for {@link PurchaseOrder}. */
public interface PurchaseOrderDAO {
    int insertWithItems(PurchaseOrder po);
    boolean updateStatus(int poId, PurchaseOrder.Status status);
    boolean receiveOrder(int poId);       // marks RECEIVED and updates stock
    Optional<PurchaseOrder> findById(int poId);
    List<PurchaseOrder> findAll();
    List<PurchaseOrder> findByStatus(PurchaseOrder.Status status);
    String generatePoNumber();
}
