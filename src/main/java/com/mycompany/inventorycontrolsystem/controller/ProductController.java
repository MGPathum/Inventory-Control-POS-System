package com.mycompany.inventorycontrolsystem.controller;

import com.mycompany.inventorycontrolsystem.dao.ProductDAO;
import com.mycompany.inventorycontrolsystem.db.DAOFactory;
import com.mycompany.inventorycontrolsystem.model.Product;
import com.mycompany.inventorycontrolsystem.util.BarcodeUtil;

import javax.swing.JLabel;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for product catalogue management.
 * Validates input, delegates persistence to {@link ProductDAO},
 * and coordinates barcode generation via {@link BarcodeUtil}.
 */
public class ProductController {

        private final ProductDAO productDAO;

    public ProductController() {
        this.productDAO = DAOFactory.getProductDAO();
    }

    /** Inserts a new product. Returns the generated product_id or -1 on failure. */
    public int addProduct(Product product) {
        validateProduct(product);
        if (product.getBarcode() == null || product.getBarcode().isBlank()) {
            product.setBarcode(product.getProductCode());
        }
        int id = productDAO.insert(product);
        return id;
    }

    /** Updates an existing product. Returns true on success. */
    public boolean updateProduct(Product product) {
        validateProduct(product);
        boolean ok = productDAO.update(product);
        return ok;
    }

    /** Soft-deletes a product (sets is_active = 0). */
    public boolean deactivateProduct(int productId) {
        return productDAO.deactivate(productId);
    }

    public List<Product> getAllProducts()            { return productDAO.findAll(); }
    public List<Product> searchProducts(String kw)  { return productDAO.search(kw); }
    public List<Product> getLowStockProducts()       { return productDAO.findLowStock(); }
    public List<Product> getByCategory(int catId)   { return productDAO.findByCategory(catId); }
    public Optional<Product> getById(int id)        { return productDAO.findById(id); }
    public Optional<Product> getByCode(String code) { return productDAO.findByCode(code); }

    /**
     * Returns the next available product code in {@code PROD-XXXX} format.
     * Delegates to the DAO.  Safe to call from a SwingWorker background thread.
     */
    public String generateNextProductCode() {
        return productDAO.generateNextProductCode();
    }

    /** Renders a CODE-128 barcode onto a JLabel. */
    public void renderBarcodeOnLabel(JLabel label, String productCode) {
        BarcodeUtil.applyBarcodeToLabel(label, productCode);
    }

    private void validateProduct(Product p) {
        if (p.getProductCode() == null || p.getProductCode().isBlank())
            throw new IllegalArgumentException("Product code is required.");
        if (p.getProductName() == null || p.getProductName().isBlank())
            throw new IllegalArgumentException("Product name is required.");
        if (p.getSellingPrice() == null || p.getSellingPrice().signum() < 0)
            throw new IllegalArgumentException("Selling price must be zero or greater.");
        if (p.getCostPrice() == null || p.getCostPrice().signum() < 0)
            throw new IllegalArgumentException("Cost price must be zero or greater.");
    }
}
