package com.mycompany.inventorycontrolsystem.model;

/**
 * Model – maps to the `categories` table.
 */
public class Category {

    private int categoryId;
    private Integer parentId;       // nullable – null means top-level
    private String categoryName;
    private String description;
    private boolean active;

    public Category() {}

    public Category(int categoryId, String categoryName) {
        this.categoryId   = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId()                      { return categoryId; }
    public void setCategoryId(int id)               { this.categoryId = id; }

    public Integer getParentId()                    { return parentId; }
    public void setParentId(Integer parentId)       { this.parentId = parentId; }

    public String getCategoryName()                 { return categoryName; }
    public void setCategoryName(String name)        { this.categoryName = name; }

    public String getDescription()                  { return description; }
    public void setDescription(String desc)         { this.description = desc; }

    public boolean isActive()                       { return active; }
    public void setActive(boolean active)           { this.active = active; }

    /** Used by JComboBox to display the name automatically. */
    @Override
    public String toString() { return categoryName; }
}
