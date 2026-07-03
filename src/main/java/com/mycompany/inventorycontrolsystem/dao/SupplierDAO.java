package com.mycompany.inventorycontrolsystem.dao;

import com.mycompany.inventorycontrolsystem.model.Supplier;
import java.util.List;
import java.util.Optional;

/** DAO contract for {@link Supplier}. */
public interface SupplierDAO {
    int insert(Supplier supplier);
    boolean update(Supplier supplier);
    boolean deactivate(int supplierId);
    boolean delete(int supplierId);
    Optional<Supplier> findById(int supplierId);
    List<Supplier> findAll();
    List<Supplier> findActive();
    List<Supplier> search(String keyword);
}
