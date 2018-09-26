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
import model.Product;
import static model.Inventory.getPartInv;
import static model.Inventory.getProdInv;
import static view_controller.MainScreenController.prodToModifyIndex;



import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    private ObservableList<Part> presentParts = FXCollections.observableArrayList();
    private ObservableList<Part> tempParts = FXCollections.observableArrayList();
    private String exception = new String();
    private int productID;
    private int prodIndex = prodToModifyIndex();

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
    private TableColumn<Part, Integer> addModProdPartIDCol;
    @FXML
    private TableColumn<Part, String> addModProdPartNameCol;
    @FXML
    private TableColumn<Part, Integer> addModProdInvCol;
    @FXML
    private TableColumn<Part, Double> addModProdPriceCol;
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

    // SEARCH BUTTON
    @FXML
    void modProdSearch(ActionEvent actionEvent) {
        String searchPart = modProdSearchField.getText();
        prodSearchHelper(searchPart, tempParts, addProdModTV);
    }

    static void prodSearchHelper(String searchPart, ObservableList<Part> tempPartList, TableView<Part> addProdModTV) {
        int partIndex;
        if(Inventory.searchPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Search Error");
            alert.setContentText("The part you searched was not found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.searchPart(searchPart);
            Part tempPart = getPartInv().get(partIndex);
            tempPartList.add(tempPart);
            addProdModTV.setItems(tempPartList);
        }
    }

    // ADD BUTTON
    @FXML
    void modProdAdd(ActionEvent actionEvent) {
        Part part = addProdModTV.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Part Addition Error");
            alert.setContentText("No part was selected.");
        } else {
            presentParts.add(part);
            updateDelProdModTV();
        }
    }

    public void updateDelProdModTV() {
        delProdModTV.setItems(presentParts);
    }

    // DELETE BUTTON
    @FXML
    void modProdDel(ActionEvent actionEvent) {
        Part part = delProdModTV.getSelectionModel().getSelectedItem();
        AddProductController.prodDelAlert(part, presentParts);
    }

    // CANCEL BUTTON
    @FXML
    void modProdCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CANCEL");
        alert.setHeaderText("Confirm Cancellation");
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
            exception = Product.isValid(
                    prodName,
                    Integer.parseInt(prodInv),
                    Double.parseDouble(prodPrice),
                    Integer.parseInt(prodMax),
                    Integer.parseInt(prodMin),
                    presentParts,
                    exception
            );
            if (exception.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Validation Error");
                alert.setContentText(exception);
                alert.showAndWait();
                exception = "";
            } else {
                Product newProd = new Product();
                newProd.setProductID(productID);
                newProd.setProductName(prodName);
                newProd.setProductInStock(Integer.parseInt(prodInv));
                newProd.setProductPrice(Double.parseDouble(prodPrice));
                newProd.setProductMax(Integer.parseInt(prodMax));
                newProd.setProductMin(Integer.parseInt(prodMin));
                newProd.setProdParts(presentParts);
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
            alert.setContentText("Form cannot contain blank fields.");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateDelProdModTV();
        modProdIdNumField.setText("Auto-gen: " + productID);

        Product product = getProdInv().get(prodIndex);
        productID = getProdInv().get(prodIndex).getProductID();
        modProdNameField.setText(product.getProductName());

        updateAddPartTV();
        updateDeletePartTV();

        addModProdPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addModProdPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addModProdInvCol.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        addModProdPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        delModProdPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        delModProdPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        delModProdInvCol.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        delModProdPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

    }

    public void updateAddPartTV() {
        addProdModTV.setItems(getPartInv());
    }

    public void updateDeletePartTV() {
        delProdModTV.setItems(presentParts);
    }
}
