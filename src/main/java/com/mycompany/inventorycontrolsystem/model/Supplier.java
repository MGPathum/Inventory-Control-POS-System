package com.mycompany.inventorycontrolsystem.model;

/**
 * Model – maps to the `suppliers` table.
 */
public class Supplier {

    private int supplierId;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String taxNumber;
    private boolean active;

    public Supplier() {}

    public Supplier(int supplierId, String companyName) {
        this.supplierId  = supplierId;
        this.companyName = companyName;
    }

    public int getSupplierId()                          { return supplierId; }
    public void setSupplierId(int id)                   { this.supplierId = id; }

    public String getCompanyName()                      { return companyName; }
    public void setCompanyName(String name)             { this.companyName = name; }

    public String getContactPerson()                    { return contactPerson; }
    public void setContactPerson(String cp)             { this.contactPerson = cp; }

    public String getEmail()                            { return email; }
    public void setEmail(String email)                  { this.email = email; }

    public String getPhone()                            { return phone; }
    public void setPhone(String phone)                  { this.phone = phone; }

    public String getAddress()                          { return address; }
    public void setAddress(String address)              { this.address = address; }

    public String getCity()                             { return city; }
    public void setCity(String city)                    { this.city = city; }

    public String getCountry()                          { return country; }
    public void setCountry(String country)              { this.country = country; }

    public String getTaxNumber()                        { return taxNumber; }
    public void setTaxNumber(String taxNumber)          { this.taxNumber = taxNumber; }

    public boolean isActive()                           { return active; }
    public void setActive(boolean active)               { this.active = active; }

    /** Used by JComboBox. */
    @Override
    public String toString() { return companyName; }
}
