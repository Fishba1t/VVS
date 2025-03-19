package inventory.validator;

import inventory.model.Product;

public class ProductValidator implements Validator<Product>{
    @Override
    public void validate(Product entity) throws ValidationException {
        StringBuilder errorMessage = new StringBuilder();

        if (entity.getAssociatedParts() == null || entity.getAssociatedParts().isEmpty()) {
            errorMessage.append("Product must contain at least 1 part. ");
        }

        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
            errorMessage.append("A name has not been entered. ");
        }

        if (entity.getMin() < 0) {
            errorMessage.append("The inventory level must be greater than or equal to 0. ");
        }

        if (entity.getMin() > entity.getMax()) {
            errorMessage.append("The Min value must be less than the Max value. ");
        }

        if (entity.getInStock() < entity.getMin()) {
            errorMessage.append("Inventory level is lower than the minimum value. ");
        }

        if (entity.getInStock() > entity.getMax()) {
            errorMessage.append("Inventory level is higher than the maximum value. ");
        }

        if (errorMessage.length() > 0) {
            throw new ValidationException(errorMessage.toString());
        }
    }

}
