package com.mycompany.inventorycontrolsystem.dao;

import com.mycompany.inventorycontrolsystem.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object contract for {@link Product} entities.
 */
public interface ProductDAO {

    int insert(Product product);
    boolean update(Product product);
    boolean deactivate(int productId);
    boolean delete(int productId);

    Optional<Product> findById(int productId);
    Optional<Product> findByCode(String productCode);

    /**
     * Finds a product by ID regardless of its active/inactive status.
     * Use this in management screens (edit, deactivate, audit).
     * The POS must use {@link #findById(int)} which only returns active products.
     */
    Optional<Product> findByIdAny(int productId);

    /** Full list with category name, supplier name, and stock qty joined. */
    List<Product> findAll();

    /** Search by name or code (LIKE query). */
    List<Product> search(String keyword);

    /** Products where quantity_on_hand <= reorder_level. */
    List<Product> findLowStock();

    /** Products belonging to a specific category. */
    List<Product> findByCategory(int categoryId);

    /**
     * Returns the next available product code in the format {@code PROD-XXXX}.
     *
     * <p>Finds the highest numeric suffix among existing {@code PROD-NNNN}
     * codes, increments it by 1, and returns it zero-padded to 4 digits.
     * Falls back to {@code "PROD-0001"} when the table is empty or contains
     * no {@code PROD-} prefixed codes.
     *
     * <p>This method is intentionally lightweight (single indexed query) so
     * it is safe to call from a {@code SwingWorker} background thread every
     * time the form is cleared for a new entry.
     */
    String generateNextProductCode();
}
