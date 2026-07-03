package com.mycompany.inventorycontrolsystem;

import com.mycompany.inventorycontrolsystem.model.InvoiceItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for invoice line-item calculations.
 * Full test suite will be expanded in STEP 4.
 */
@DisplayName("Invoice Item Calculation Tests")
class InvoiceCalculationTest {

    @Test
    @DisplayName("Line total without discount or tax")
    void testLineTotalNoDiscountNoTax() {
        InvoiceItem item = new InvoiceItem();
        item.setQuantity(3);
        item.setUnitPrice(new BigDecimal("10.00"));
        item.setDiscountPct(BigDecimal.ZERO);
        item.setTaxRate(BigDecimal.ZERO);
        item.recalculate();

        assertEquals(new BigDecimal("30.00"), item.getLineTotal());
    }

    @Test
    @DisplayName("Line total with 10% discount")
    void testLineTotalWithDiscount() {
        InvoiceItem item = new InvoiceItem();
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("50.00"));
        item.setDiscountPct(new BigDecimal("10.00"));
        item.setTaxRate(BigDecimal.ZERO);
        item.recalculate();

        // 2 * 50.00 = 100.00, minus 10% = 90.00
        assertEquals(new BigDecimal("90.00"), item.getLineTotal());
    }

    @Test
    @DisplayName("Line total with 6% tax and no discount")
    void testLineTotalWithTax() {
        InvoiceItem item = new InvoiceItem();
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("100.00"));
        item.setDiscountPct(BigDecimal.ZERO);
        item.setTaxRate(new BigDecimal("6.00"));
        item.recalculate();

        // 100.00 * 1.06 = 106.00
        assertEquals(new BigDecimal("106.00"), item.getLineTotal());
    }

    @ParameterizedTest(name = "qty={0}, price={1}, disc={2}%, tax={3}% => {4}")
    @CsvSource({
        "1, 100.00, 0.00,  0.00, 100.00",
        "2,  50.00, 0.00,  0.00, 100.00",
        "1, 100.00, 10.00, 0.00,  90.00",
        "2, 100.00, 0.00,  6.00, 212.00",
        "3,  10.00, 5.00,  6.00,  30.21"
    })
    @DisplayName("Parameterised line total calculations")
    void testParameterisedLineTotal(int qty, String price,
                                    String disc, String tax,
                                    String expected) {
        InvoiceItem item = new InvoiceItem();
        item.setQuantity(qty);
        item.setUnitPrice(new BigDecimal(price));
        item.setDiscountPct(new BigDecimal(disc));
        item.setTaxRate(new BigDecimal(tax));
        item.recalculate();

        assertEquals(new BigDecimal(expected), item.getLineTotal(),
            "Line total mismatch for inputs: qty=" + qty +
            " price=" + price + " disc=" + disc + " tax=" + tax);
    }
}
