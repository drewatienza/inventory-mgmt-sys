package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.getPartInventory;
import model.Product;
import model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Part;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private ObservableList<Part> tempPartList = FXCollections.observableArrayList();
    private String isValidException = new String();
    private int productID;

    @FXML
    private TextField addProdNameField;
    @FXML
    private TextField addProdInvField;
    @FXML
    private TextField addProdPriceField;
    @FXML
    private TextField addProdMaxField;
    @FXML
    private TextField addProdMinField;
    @FXML
    private TableView<Part> addProdAddTV;
    @FXML
    private TableColumn<Part, Integer> addProdPartIDCol;
    @FXML
    private TableColumn<Part, String> addProdPartNameCol;
    @FXML
    private TableColumn<Part, Integer> addProdInvCol;
    @FXML
    private TableColumn<Part, Double> addProdPriceCol;
    @FXML
    private TableView<Part> delProdAddTV;
    @FXML
    private TableColumn<Part, Integer> delProdPartIDCol;
    @FXML
    private TableColumn<Part, String> delProdPartNameCol;
    @FXML
    private TableColumn<Part, Integer> delProdInvCol;
    @FXML
    private TableColumn<Part, Double> delProdPriceCol;
    @FXML
    private TextField addProdSearchField;

    // ADD
    @FXML
    void addProdAdd(ActionEvent actionEvent) {
        Part part = addProdAddTV.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateDelProdAddTV();
    }

    public void updateDelProdAddTV() {
        delProdAddTV.setItems(currentParts);
    }

    // DELETE
    @FXML
    void addProdDel(ActionEvent actionEvent) {
        Part part = delProdAddTV.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.get() == ButtonType.OK) {
            currentParts.remove(part);
        }
    }

    // SEARCH
    @FXML
    void addProdSearch(ActionEvent actionEvent) {
        String searchPart = addProdSearchField.getText();
        int partIndex = -1;
        if(Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Search Error");
            alert.setContentText("The part you entered does not match any current parts.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(searchPart);
            Part tempPart = getPartInventory().get(partIndex);
            tempPartList.add(tempPart);
            addProdAddTV.setItems(tempPartList);
        }
    }

    // CANCEL
    @FXML
    void addProdCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CANCEL");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new product?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.get() == ButtonType.OK) {
            Parent cancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene s = new Scene(cancel);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(s);
            window.show();
        }
    }

    // SAVE
    @FXML
    void addProdSave(ActionEvent actionEvent) throws IOException {
        String prodName = addProdNameField.getText();
        String prodInv = addProdInvField.getText();
        String prodPrice = addProdPriceField.getText();
        String prodMax = addProdMaxField.getText();
        String prodMin = addProdMinField.getText();

        try {
            isValidException = Product.isValid(
                    prodName,
                    Integer.parseInt(prodInv),
                    Double.parseDouble(prodPrice),
                    Integer.parseInt(prodMax),
                    Integer.parseInt(prodMin),
                    currentParts,
                    isValidException
                    );
            if (isValidException.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error");
                alert.setContentText(isValidException);
                alert.showAndWait();
                isValidException = "";
            } else {
                Product newProd = new Product();
                newProd.setProductID(productID);
                newProd.setProductName(prodName);
                newProd.setProductInStock(Integer.parseInt(prodInv));
                newProd.setProductPrice(Double.parseDouble(prodPrice));
                newProd.setProductMax(Integer.parseInt(prodMax));
                newProd.setProductMin(Integer.parseInt(prodMin));
                newProd.setProdParts(currentParts);
                Inventory.addProduct(newProd);

                Parent save = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene s = new Scene(save);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(s);
                window.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error");
            alert.setContentText("Form cannot contain black fields.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
