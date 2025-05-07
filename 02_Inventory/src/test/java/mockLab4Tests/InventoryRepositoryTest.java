package mockLab4Tests;

import inventory.model.InhousePart;
import inventory.model.Product;
import inventory.repository.InventoryRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

// TEST PENTRU R(REPOSITORY):
// Repo Real si Entitati mockuite
public class InventoryRepositoryTest {

    InventoryRepositoryInMemory repo;
    InventoryRepositoryInMemory emptyListRepo;

    Product prod;
    Product prod2;
    Product prod3;

    InhousePart part1;

    @BeforeEach
    void setUp() {
        repo = new InventoryRepositoryInMemory();

        //MOCK: folosim Mockito pentru a crea 3 produse false
        prod = mock(Product.class);
        prod2 = mock(Product.class);
        prod3 = mock(Product.class);

        //Definim comportamentul mock-urilor cu Mockito
        Mockito.when(prod.getName()).thenReturn("nume_gasit");
        Mockito.when(prod2.getName()).thenReturn("produs_second");
        Mockito.when(prod3.getName()).thenReturn("prod");

        Mockito.when(prod.getProductId()).thenReturn(1);
        Mockito.when(prod2.getProductId()).thenReturn(2);
        Mockito.when(prod3.getProductId()).thenReturn(3);

        //Adaugam produsele mock-uite in repository
        repo.addProduct(prod);
        repo.addProduct(prod2);
        repo.addProduct(prod3);

        //Repo separat pentru cazul cu lista goala
        emptyListRepo = new InventoryRepositoryInMemory();
    }

    // Test JUnit + Mockito; verifica daca un produs poate fi gasit dupa nume
    @Test
    void lookupProductOkByName() {
        Product productFound = repo.lookupProduct("nume_gasit");
        assertEquals(prod, productFound);
    }

    // Test JUnit + Mockito; verifica daca un produs poate fi gasit dupa ID
    @Test
    void lookupProductOkById() {
        Product productFound = repo.lookupProduct("2");
        assertEquals(prod2, productFound);
    }

    // Test JUnit fara mock;  repo intoarce null pentru produs inexistent
    @Test
    void lookupProductNoSearchResults() {
        Product productFound = repo.lookupProduct("nu_se_gaseste");
        assertNull(productFound);
    }

    // Test JUnit fara mock; repo gol intoarce produs gol
    @Test
    void lookupProductEmptyList() {
        Product emptyProduct = new Product(0, null, 0.0, 0, 0, 0, null);
        Product productFound = emptyListRepo.lookupProduct("lista_nepopulata");
        assertEquals(emptyProduct, productFound);
    }

    // Test JUnit + Mockito; verifica inca o data gasirea produsului dupa nume
    @Test
    void lookupProduct4thElem() {
        Product productFound = repo.lookupProduct("produs_second");
        assertEquals(prod2, productFound);
    }
}
