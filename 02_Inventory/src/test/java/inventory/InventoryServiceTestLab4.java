package inventory;

import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepositoryFile;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

// Test pentru S (service)
// Test de integrare – Step 2 (S cu R mock-uit)
public class InventoryServiceTestLab4 {
    InventoryService service;
    InventoryRepositoryFile repo;

    // folosim Mockito pentru a crea un mock al repository-ului (R)
    @BeforeEach
    void setUp() {
        repo = mock(InventoryRepositoryFile.class); // mock pentru R
        service = new InventoryService(repo);       // injectam mock-ul in S
    }

    // Test JUnit + Mockito - mock pentru Product + comportament repo.getAllProducts()
    @Test
    void testGetAllProducts() {
        Product prod = mock(Product.class); // mock pentru E
        Mockito.when(repo.getAllProducts()).thenReturn(FXCollections.observableArrayList(prod));
        assertEquals(service.getAllProducts().size(), 1);
    }

    // Test JUnit + Mockito - mock pentru Part + comportament repo.getAllParts()
    @Test
    void testGetAllParts() {
        Part part = mock(Part.class); // mock pentru E
        Mockito.when(repo.getAllParts()).thenReturn(FXCollections.observableArrayList(part));
        assertEquals(service.getAllParts().size(), 1);
    }

    // Test JUnit + Mockito - mock pentru Part + comportament repo.addPart()
    @Test
    void testAddInhousePart() {
        Part part = mock(Part.class); // mock pentru E
        Mockito.when(repo.addPart(part)).thenReturn((Part) FXCollections.observableArrayList(part));
        assertEquals(service.getAllParts().size(), 1);
    }
}
