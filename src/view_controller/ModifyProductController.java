package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Part;
import model.Inventory;
import static model.Inventory.getPartInventory;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private ObservableList<Part> tempPartList = FXCollections.observableArrayList();
    private String isValidException = new String();
    private int productID;

    @FXML
    private TextField modProdIdNumField;
    @FXML
    private TextField modProdNameField;
    @FXML
    private TextField modProdInvField;
    @FXML
    private TextField modProdPriceField;
    @FXML
    private TextField modProdMaxField;
    @FXML
    private TextField modProdMinField;
    @FXML
    private TableView<Part> addProdModTV;
    @FXML
    private TableColumn<Part, Integer> modProdPartIDCol;
    @FXML
    private TableColumn<Part, String> modProdPartNameCol;
    @FXML
    private TableColumn<Part, Integer> modProdInvCol;
    @FXML
    private TableColumn<Part, Double> modProdPriceCol;
    @FXML
    private TableView<Part> delProdModTV;
    @FXML
    private TableColumn<Part, Integer> delModProdPartIDCol;
    @FXML
    private TableColumn<Part, String> delModProdPartNameCol;
    @FXML
    private TableColumn<Part, Integer> delModProdInvCol;
    @FXML
    private TableColumn<Part, Double> delModProdPriceCol;
    @FXML
    private TextField modProdSearchField;

    // ADD BUTTON
    @FXML
    void modProdAdd(ActionEvent actionEvent) {
        Part part = addProdModTV.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateDelProdModTV();
    }

    public void updateDelProdModTV() {
        delProdModTV.setItems(currentParts);
    }

    // DELETE BUTTON
    @FXML
    void modProdDel(ActionEvent actionEvent) {
        confirmDelAlert(delProdModTV, currentParts);
    }

    static void confirmDelAlert(TableView<Part> delProdModTV, ObservableList<Part> currentParts) {
        Part part = delProdModTV.getSelectionModel().getSelectedItem();
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

    // SEARCH BUTTON
    @FXML
    void modProdSearch(ActionEvent actionEvent) {
        String searchPart = modProdSearchField.getText();
        int partIndex = -1;
        prodSearchHelper(searchPart, tempPartList, addProdModTV);
    }

    static void prodSearchHelper(String searchPart, ObservableList<Part> tempPartList, TableView<Part> addProdModTV) {
        int partIndex;
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
            addProdModTV.setItems(tempPartList);
        }
    }

    // CANCEL BUTTON
    @FXML
    void modProdCancel(ActionEvent actionEvent) throws IOException {
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

    // SAVE BUTTON
    @FXML
    void modProdSave(ActionEvent actionEvent) throws IOException {
        String prodName = modProdNameField.getText();
        String prodInv = modProdInvField.getText();
        String prodPrice = modProdPriceField.getText();
        String prodMax = modProdMaxField.getText();
        String prodMin = modProdMinField.getText();

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
        updateDelProdModTV();
        productID = Inventory.getProductIdCount();
        modProdIdNumField.setText("Auto-gen: " + productID);
    }
}
