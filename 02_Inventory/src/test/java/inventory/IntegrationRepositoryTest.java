package inventory;

import inventory.model.Product;
import inventory.repository.InventoryRepositoryFile;
import inventory.service.InventoryService;
import inventory.validator.PartValidator;
import inventory.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

public class IntegrationRepositoryTest {
    InventoryService service;
    InventoryRepositoryFile repo;
    Product prod;
    Product prod2;

    @BeforeEach
    void setUp() {
        // Folosim obiecte reale pentru validatori si repo
        PartValidator partValidator = new PartValidator();
        ProductValidator productValidator = new ProductValidator();
        repo = new InventoryRepositoryFile(partValidator, productValidator); //(R)
        service = new InventoryService(repo); // (S)

        // Folosim Mockito pentru a crea obiecte mock de tip Product
        prod = mock(Product.class);  //(E)
        prod2 = mock(Product.class);  //(E)

        // Adaugam mock-urile in repository
        repo.addProduct2(prod);
        repo.addProduct2(prod2);

        // Definim comportamentul mock-urilor
        Mockito.when(prod.getName()).thenReturn("nume_gasit");
        Mockito.when(prod.getProductId()).thenReturn(1);

        Mockito.when(prod2.getName()).thenReturn("produs_second");
        Mockito.when(prod2.getProductId()).thenReturn(2);
    }

    // Test JUnit + Mockito - verifica ca produsul cu numele "nume_gasit" este returnat corect
    @Test
    void lookupProductOkByName() {
        Product productFound = service.lookupProduct("nume_gasit");
        assertEquals(prod, productFound);
    }

    // Test JUnit + Mockito - verifica ca un produs inexistent NU este returnat ca fiind `prod2`
    @Test
    void lookupProductNotOkByName() {
        Product productFound = service.lookupProduct("nume_negasit");
        assertNotEquals(prod2, productFound);
    }
}
