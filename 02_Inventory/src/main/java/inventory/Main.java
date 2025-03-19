package inventory;

import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import inventory.controller.MainScreenController;
import inventory.validator.PartValidator;
import inventory.validator.ProductValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        InventoryRepository repo= new InventoryRepository();
        PartValidator partValidator = new PartValidator();
        ProductValidator productValidator = new ProductValidator();
        InventoryService service = new InventoryService(repo, partValidator, productValidator);
        System.out.println(service.getAllProducts());
        System.out.println(service.getAllParts());
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));

        Parent root=loader.load();
        MainScreenController ctrl=loader.getController();
        ctrl.setService(service);

        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}