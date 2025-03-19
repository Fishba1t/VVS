package inventory.validator;

import inventory.model.Part;

public class PartValidator implements Validator<Part> {
    public static String minGreaterErrMsg = "Min has to be lower then INTEGER_MAX_VALUE-1. ";
    public static String minErrMsg = "Min has to be greater then 1. ";
    public static String emptyNameErrMsg = "A name has not been entered. ";
    public static String priceErrMsg = "The price must be greater than 0. ";
    public static String stockErrMsg = "Inventory level must be greater than 0. ";
    public static String minGreaterMaxErrMsg = "The Min value must be less than the Max value. ";
    public static String stockLowerErrMsg = "Inventory level is lower than minimum value. ";
    public static String stockGreaterErrMsg = "Inventory level is higher than the maximum value. ";

    @Override
    public void validate(Part entity) throws ValidationException {
        String errorMessage = "";
        if (entity.getMin() > Integer.MAX_VALUE-1) {
            errorMessage += minGreaterErrMsg;
        }
        if (entity.getMin() < 1) {
            errorMessage += minErrMsg;
        }
        if (entity.getName().equals("")) {
            errorMessage += emptyNameErrMsg;
        }
        if (entity.getPrice() < 0.01) {
            errorMessage += priceErrMsg;
        }
        if (entity.getInStock() < 1) {
            errorMessage += stockErrMsg;
        }
        if (entity.getMin() > entity.getMax()) {
            errorMessage += minGreaterMaxErrMsg;
        }
        if (entity.getInStock() < entity.getMin()) {
            errorMessage += stockLowerErrMsg;
        }
        if (entity.getInStock() > entity.getMax()) {
            errorMessage += stockGreaterErrMsg;
        }
        if (!errorMessage.equals(""))
            throw new ValidationException(errorMessage);
    }
}
