package view_controller;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    // PARTS TABLEVIEW
    @FXML
    private TableView<Part>  MainParts;
    @FXML
    private TableColumn<Part, Integer> MaintPartIdColumn;
    @FXML
    private TableColumn<Part, String> MainPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> MainPartInvColumn;
    @FXML
    private TableColumn<Part, Double> MainPartPriceColumn;
    @FXML
    private TextField PartSearchField;

    // PRODUCT TABLEVIEW
    @FXML
    private TableView<Product>  MainProducts;
    @FXML
    private TableColumn<Product, Integer> MaintProductIdColumn;
    @FXML
    private TableColumn<Product, String> MainProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> MainProductInvColumn;
    @FXML
    private TableColumn<Product, Double> MainProductPriceColumn;
    @FXML
    private TextField ProductSearchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
