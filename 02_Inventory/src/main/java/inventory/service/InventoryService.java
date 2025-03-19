package inventory.service;

import inventory.exceptions.IsValidProductException;
import inventory.model.*;
import inventory.repository.InventoryRepository;
import inventory.validator.PartValidator;
import inventory.validator.ProductValidator;
import inventory.validator.ValidationException;
import javafx.collections.ObservableList;

public class InventoryService {

    private InventoryRepository repo;
    private PartValidator partValidator;

    private ProductValidator productValidator;
    public InventoryService(InventoryRepository repo, PartValidator partValidator, ProductValidator productValidator) {
        this.repo = repo;
        this.partValidator = partValidator;
        this.productValidator = productValidator;
    }

    public void addInhousePart(String name, double price, int inStock, int min, int max, int partDynamicValue) throws ValidationException {
        // 1. Create a part object
        InhousePart inhousePart = new InhousePart(repo.getAutoPartId(), name, price, inStock, min, max, partDynamicValue);

        // 2. Validate before saving
        partValidator.validate(inhousePart);

        // 3. Save if valid
        repo.addPart(inhousePart);
    }
    public void addOutsourcePart(String name, double price, int inStock, int min, int max, String partDynamicValue) throws ValidationException {
        OutsourcedPart outsourcedPart = new OutsourcedPart(repo.getAutoPartId(), name, price, inStock, min, max, partDynamicValue);

        partValidator.validate(outsourcedPart);

        repo.addPart(outsourcedPart);
    }

    public void addProduct(String name, double price, int inStock, int min, int  max, ObservableList<Part> addParts){
        Product product = new Product(repo.getAutoProductId(), name, price, inStock, min, max, addParts);
        productValidator.validate(product);
        repo.addProduct(product);
    }

    public ObservableList<Part> getAllParts() {
        return repo.getAllParts();
    }

    public ObservableList<Product> getAllProducts() {
        return repo.getAllProducts();
    }

    public Part lookupPart(String search) {
        return repo.lookupPart(search);
    }

    public Product lookupProduct(String search) {
        return repo.lookupProduct(search);
    }

    public void updateInhousePart(int partIndex, int partId, String name, double price, int inStock, int min, int max, int partDynamicValue) throws ValidationException {
        // 1. Create the part object
        InhousePart inhousePart = new InhousePart(partId, name, price, inStock, min, max, partDynamicValue);

        // 2. Validate before updating
        partValidator.validate(inhousePart);

        // 3. Update if valid
        repo.updatePart(partIndex, inhousePart);
    }

    public void updateOutsourcedPart(int partIndex, int partId, String name, double price, int inStock, int min, int max, String partDynamicValue) throws ValidationException {
        // 1. Create the part object
        OutsourcedPart outsourcedPart = new OutsourcedPart(partId, name, price, inStock, min, max, partDynamicValue);

        // 2. Validate before updating
        partValidator.validate(outsourcedPart);

        // 3. Update if valid
        repo.updatePart(partIndex, outsourcedPart);
    }


    public void updateProduct(int productIndex, int productId, String name, double price, int inStock, int min, int max, ObservableList<Part> addParts){
        Product product = new Product(productId, name, price, inStock, min, max, addParts);
        productValidator.validate(product);
        repo.updateProduct(productIndex, product);
    }

    public void deletePart(Part part){
        repo.deletePart(part);
    }

    public void deleteProduct(Product product){
        repo.deleteProduct(product);
    }

    /**
     * Generate an error message for invalid values in a product
     * and evaluate whether the sum of the price of associated parts
     * is less than the price of the resulting product.
     * A valid product will return an empty error message string.
     * @param name
     * @param min
     * @param max
     * @param inStock
     * @param price
     * @param parts
     * @param errorMessage
     * @return
     */
    public static String isValidProduct(String name, double price, int inStock, int min, int max, ObservableList<Part> parts, String errorMessage) throws IsValidProductException {
        double sumOfParts = 0.00;
        for (int i = 0; i < parts.size(); i++) {
            sumOfParts += parts.get(i).getPrice();
        }
        if (name.equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if (min < 0) {
            errorMessage += "The inventory level must be greater than 0. ";
        }
        if (price < 0.01) {
            errorMessage += "The price must be greater than $0. ";
        }
        if (min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        if (parts.size() < 1) {
            errorMessage += "Product must contain at least 1 part. ";
        }
        if (sumOfParts > price) {
            errorMessage += "Product price must be greater than cost of parts. ";
        }
        throw new IsValidProductException(errorMessage);
    }
}