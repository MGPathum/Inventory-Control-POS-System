package com.mycompany.inventorycontrolsystem.dao;

import com.mycompany.inventorycontrolsystem.model.Category;
import java.util.List;
import java.util.Optional;

/** DAO contract for {@link Category}. */
public interface CategoryDAO {
    int insert(Category category);
    boolean update(Category category);
    boolean delete(int categoryId);
    Optional<Category> findById(int categoryId);
    List<Category> findAll();
    List<Category> findActive();
}
