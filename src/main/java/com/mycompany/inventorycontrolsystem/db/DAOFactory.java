package com.mycompany.inventorycontrolsystem.db;

import com.mycompany.inventorycontrolsystem.dao.*;
import com.mycompany.inventorycontrolsystem.dao.impl.*;

/**
 * Factory that hands out DAO instances to controllers.
 *
 * <p>Why this matters architecturally:
 * <ul>
 *   <li>Controllers depend only on the DAO <em>interfaces</em> — never on
 *       the concrete {@code *Impl} classes.</li>
 *   <li>Swapping implementations (e.g. for unit testing with mocks) only
 *       requires changing this one file.</li>
 *   <li>Every {@code get*DAO()} call returns the same singleton impl, keeping
 *       the number of live objects small.</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 *   UserDAO userDAO = DAOFactory.getUserDAO();
 *   Optional&lt;User&gt; user = userDAO.findByUsername("admin");
 * </pre>
 */
public final class DAOFactory {
    private static final UserDAO          USER_DAO     = new UserDAOImpl();
    private static final CategoryDAO      CATEGORY_DAO = new CategoryDAOImpl();
    private static final SupplierDAO      SUPPLIER_DAO = new SupplierDAOImpl();
    private static final ProductDAO       PRODUCT_DAO  = new ProductDAOImpl();
    private static final SalesInvoiceDAO  INVOICE_DAO  = new SalesInvoiceDAOImpl();
    private static final PurchaseOrderDAO PO_DAO       = new PurchaseOrderDAOImpl();

    private DAOFactory() { /* static-only utility */ }

    public static UserDAO getUserDAO()                  { return USER_DAO; }
    public static CategoryDAO getCategoryDAO()          { return CATEGORY_DAO; }
    public static SupplierDAO getSupplierDAO()          { return SUPPLIER_DAO; }
    public static ProductDAO getProductDAO()            { return PRODUCT_DAO; }
    public static SalesInvoiceDAO getSalesInvoiceDAO()  { return INVOICE_DAO; }
    public static PurchaseOrderDAO getPurchaseOrderDAO(){ return PO_DAO; }
}
