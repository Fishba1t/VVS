package inventory;

import inventory.model.Product;
import inventory.repository.InventoryRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InventoryRepoTest {
    private InventoryRepositoryInMemory repository;

    @BeforeEach
    void setUp() {
        repository = new InventoryRepositoryInMemory();
    }

    @Test
    void lookupProduct_F02_WBT_1() {
        assertNull(repository.lookupProduct(""));
    }

    @Test
    void lookupProduct_F02_WBT_2() {
        repository.addProduct(new Product(1, "masina", 22000.59, 1, 3, 4));
        repository.addProduct(new Product(2, "test", 12.8, 2, 1, 3));
        Product productFound = new Product();
        for(Product product : repository.getProducts()) {
            if(product.getName().equals("masina")) {
                productFound = product;
                break;
            }
        }
        assertEquals(productFound, repository.lookupProduct("masina"));
    }

    @Test
    void lookupProduct_F02_WBT_3() {
        repository.addProduct(new Product(1, "masina", 22000.59, 1, 3, 4));
        repository.addProduct(new Product(2, "test", 12.8, 2, 1, 3));
        assertNull(repository.lookupProduct("telefon"));
    }

    @Test
    void lookupProduct_F02_WBT_4() {
        assertEquals(new Product(0, null, 0.0, 0, 0, 0, null),
                repository.lookupProduct("telefon"));
    }
}
