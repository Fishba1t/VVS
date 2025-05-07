package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepositoryFile;
import inventory.validator.PartValidator;
import inventory.validator.ProductValidator;
import inventory.validator.ValidationException;
import org.junit.jupiter.api.*;

import static inventory.validator.PartValidator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryServiceTest {
    InventoryService service;

    @BeforeEach
    @Timeout(1000 * 60)
    void setUp() {
        PartValidator partValidator = new PartValidator();
        ProductValidator productValidator = new ProductValidator();
        InventoryRepositoryFile repo = new InventoryRepositoryFile(partValidator, productValidator);
        service = new InventoryService(repo);
    }

    @AfterEach
    @Disabled
    void tearDown() {
    }

    @Test
    @DisplayName("ECP_1 - OK")
    void addInhousePart_validData_OK_ECP_1() {
        try {
            //arrange
            Part part=new InhousePart(1,"Piulita", 0.5, 200, 5, 1000, 1);

            //act
            Part part_service=service.addInhousePart("Piulita", 0.5, 200, 5, 1000, 1);
            part.setPartId(part_service.getPartId());

            //assert
            System.out.println(part.getPartId());
            assert(part_service.equals(part));
        } catch (ValidationException err) {
            assert (false);
        }
    }

    @Test
    //@RepeatedTest(2)
    void addInhousePart_validData_OK_ECP_2() {
        try {
            //arrange
            Part part=new InhousePart(1,"Amortizare", 200, 20, 4, 1000, 5);

            //act
            Part part_service=service.addInhousePart("Amortizare", 200, 20, 4, 1000, 5);
            part.setPartId(part_service.getPartId());

            //assert
            System.out.println(part.getPartId());
            assert(part_service.equals(part));
        } catch (ValidationException err) {
            assert (false);
        }
    }

    @Test
    @Tag("Invalid")
    void addInhousePart_InvalidMin_Exception_ECP_1() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            service.addInhousePart("Surub", 1, 200, -5, 1000, 34);
        });

        String expectedMessage = minErrMsg;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void addInhousePart_InvalidMin_Exception_ECP_2() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            service.addInhousePart("Roata", 500, 200, Integer.MAX_VALUE, 1000, 34);
        });

        String expectedMessage = minGreaterErrMsg+minGreaterMaxErrMsg+stockLowerErrMsg;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void addInhousePart_validData_OK_BVA_1() {
        try {
            //setup
            Part part=new InhousePart(1,"M", 67, 20, 10, 30, 34);

            //act
            Part part_service=service.addInhousePart("M", 67, 20, 10, 30, 34);
            part.setPartId(part_service.getPartId());

            //assert
            assert(part_service.equals(part));
        } catch (ValidationException err) {
            assert (false);
        }
    }

    @Test
    void addInhousePart_validData_OK_BVA_2() {
        try {
            //setup
            Part part=new InhousePart(1,"M", 67, 20, 1, 30, 34);

            //act
            Part part_service=service.addInhousePart( "M", 67, 20, 1, 30, 34);
            part.setPartId(part_service.getPartId());

            //assert
            assert(part_service.equals(part));
        } catch (ValidationException err) {
            assert (false);
        }
    }

    @Test
    void addInhousePart_InvalidMin_Exception_BVA() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            service.addInhousePart("M", 67, 20, 0, 30, 34);
        });

        String expectedMessage = minErrMsg;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void addInhousePart_InvalidName_Exception_BVA() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            service.addInhousePart("", 67, 20, 1, 30, 34);
        });

        String expectedMessage = emptyNameErrMsg;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
