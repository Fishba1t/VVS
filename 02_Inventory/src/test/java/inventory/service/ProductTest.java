package inventory.service;

import inventory.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TEST PENTRU E(ENTITATE - Product)
public class ProductTest {
    Product prod;

    //Test JUnit - folosim instanta reala de Product
    @BeforeEach
    void setUp() {
        prod = new Product(1, "name", 100, 10, 2, 3, null);
    }

    @Test
    void getNameTest() {
        assert(prod.getName().equals("name"));
    }

    @Test
    void setNameTest() {
        prod.setName("test");
        assert(prod.getName().equals("test"));
    }
}
