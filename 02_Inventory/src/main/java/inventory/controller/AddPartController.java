package inventory.controller;

import inventory.model.Part;
import inventory.service.InventoryService;
import inventory.validator.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable, Controller {

    // Declare fields
    private Stage stage;
    private Parent scene;
    private boolean isOutsourced = true;
    private String errorMessage = new String();

    private InventoryService service;
    
    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;
    
    @FXML
    private Label addPartDynamicLbl;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;
    
    @FXML
    private TextField addPartDynamicTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    public AddPartController(){}

    @Override
    public void setService(InventoryService service){

        this.service=service;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        outsourcedRBtn.setSelected(true);
    }
    /**
     * Method to add to button handler to switch to scene passed as source
     * @param event
     * @param source
     * @throws IOException
     */
    @FXML
    private void displayScene(ActionEvent event, String source) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource(source));
        //scene = FXMLLoader.load(getClass().getResource(source));
        scene = loader.load();
        Controller ctrl=loader.getController();
        ctrl.setService(service);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Ask user for confirmation before canceling part addition
     * and switching scene to Main Screen
     * @param event
     * @throws IOException
     */
    @FXML
    void handleAddPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Cancelation");
        alert.setContentText("Are you sure you want to cancel adding part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.out.println("Ok selected. Part addition canceled.");
            displayScene(event, "/fxml/MainScreen.fxml");
        } else {
            System.out.println("Cancel clicked.");
        }
    }

    /**
     * If in-house radio button is selected set isOutsourced boolean
     * to false and modify dynamic label to Machine ID
     * @param event 
     */
    @FXML
    void handleInhouseRBtn(ActionEvent event) {
        isOutsourced = false;
        addPartDynamicLbl.setText("Machine ID");
    }

    /**
     * If outsourced radio button is selected set isOutsourced boolean
     * to true and modify dynamic label to Company Name
     * @param event 
     */
    @FXML
    void handleOutsourcedRBtn(ActionEvent event) {
        isOutsourced = true;
        addPartDynamicLbl.setText("Company Name");
    }

    /**
     * Validate given part parameters.  If valid, add part to inventory,
     * else give user an error message explaining why the part is invalid.
     * @param event
     * @throws IOException
     */
    @FXML
    void handleAddPartSave(ActionEvent event) {
        String name = nameTxt.getText();
        String price = priceTxt.getText();
        String inStock = inventoryTxt.getText();
        String min = minTxt.getText();
        String max = maxTxt.getText();
        String partDynamicValue = addPartDynamicTxt.getText();

        try {

            double parsedPrice = Double.parseDouble(price);
            int parsedStock = Integer.parseInt(inStock);
            int parsedMin = Integer.parseInt(min);
            int parsedMax = Integer.parseInt(max);


            if (isOutsourced) {
                service.addOutsourcePart(name, parsedPrice, parsedStock, parsedMin, parsedMax, partDynamicValue);
            } else {
                service.addInhousePart(name, parsedPrice, parsedStock, parsedMin, parsedMax, Integer.parseInt(partDynamicValue));
            }

            displayScene(event, "/fxml/MainScreen.fxml");

        } catch (ValidationException ve) {
            // Show validation errors in an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding Part!");
            alert.setHeaderText("Validation Error");
            alert.setContentText(ve.getMessage());
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Handle incorrect input formats
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding Part!");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter valid numerical values.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
