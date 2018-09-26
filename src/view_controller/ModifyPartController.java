package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getProdInv;
import static view_controller.MainScreenController.partToModifyIndex;
import static model.Inventory.getPartInv;

public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton modPartInHouseRadio;
    @FXML
    private RadioButton modPartOutsourcedRadio;
    @FXML
    private TextField modPartIdField;
    @FXML
    private TextField modPartNameField;
    @FXML
    private TextField modPartInvField;
    @FXML
    private TextField modPartPriceField;
    @FXML
    private TextField modPartMaxField;
    @FXML
    private TextField modPartMinField;
    @FXML
    private Label switchModPartLabel;
    @FXML
    private TextField switchModPartField;

    private boolean inHouse;
    private String exception = new String();
    private int partID;
    private int partIndex = partToModifyIndex();

    // RADIO BUTTONS
    @FXML
    void radioModPartInhouse(ActionEvent actionEvent) {
        inHouse = true;
        switchModPartLabel.setText("Machine ID");
        switchModPartField.setPromptText("Mach ID");
        modPartOutsourcedRadio.setSelected(false);
    }

    @FXML
    void radioModPartOutsourced(ActionEvent actionEvent) {
        inHouse = false;
        switchModPartLabel.setText("Company Name");
        switchModPartField.setPromptText("Comp Name");
        modPartInHouseRadio.setSelected(false);
    }


    // SAVE BUTTON
    @FXML
    void modPartSaveBtn(ActionEvent actionEvent) throws IOException {
        String partName = modPartNameField.getText();
        String partInv = modPartInvField.getText();
        String partPrice = modPartPriceField.getText();
        String partMax = modPartMaxField.getText();
        String partMin = modPartMinField.getText();
        String partSwitch = switchModPartField.getText();

        boolean isValid = true;
        StringBuilder err = new StringBuilder();

        String nameVal = Part.valName(partName);
        if (!nameVal.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText(nameVal);
            alert.showAndWait();

            isValid = false;
        }

        String maxMinVal = Part.valMaxMin(partMax, partMin);
        if (!maxMinVal.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText(maxMinVal);
            alert.showAndWait();

            isValid = false;
        }

        String invVal = Part.valInv(partInv, partMax, partMin);
        errorMessage(invVal);

        String priceVal = Part.valPrice(partPrice);
        errorMessage(priceVal);

        if (isValid) {
            if (inHouse == true) {
                Inhouse inPart = new Inhouse();
                inPart.setPartID(partID);
                inPart.setPartName(partName);
                inPart.setPartPrice(Double.parseDouble(partPrice));
                inPart.setPartInStock(Integer.parseInt(partInv));
                inPart.setPartMax(Integer.parseInt(partMax));
                inPart.setPartMin(Integer.parseInt(partMin));
                inPart.setMachineID(Integer.parseInt(partSwitch));
                Inventory.addPart(inPart);
            } else {
                Outsourced outPart = new Outsourced();
                outPart.setPartID(partID);
                outPart.setPartName(partName);
                outPart.setPartPrice(Double.parseDouble(partPrice));
                outPart.setPartInStock(Integer.parseInt(partInv));
                outPart.setPartMax(Integer.parseInt(partMax));
                outPart.setPartMin(Integer.parseInt(partMin));
                outPart.setCompanyName(partSwitch);
                Inventory.addPart(outPart);
            }
            Parent partSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene s = new Scene(partSave);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(s);
            window.show();
        }
    }

    private void errorMessage(String priceVal) {
        boolean isValid;
        if (!priceVal.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText(priceVal);

            isValid = false;
        }
    }


    // CANCEL BUTTON
    @FXML
    void modPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirm Cancellation");
        alert.setContentText("Are you sure you want to cancel adding a new part?");
        Optional<ButtonType> confirm = alert.showAndWait();

        if (confirm.get() == ButtonType.OK) {
            Parent partCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene s = new Scene(partCancel);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(s);
            window.show();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Part part = getPartInv().get(partIndex);
        partID = getPartInv().get(partIndex).getPartID();
        modPartIdField.setText("Auto-Gen: " + partID);
        modPartNameField.setText(part.getPartName());
        modPartInvField.setText(Integer.toString(part.getPartInStock()));
        modPartPriceField.setText(Double.toString(part.getPartPrice()));
        modPartMaxField.setText((Integer.toString(part.getPartMax())));
        modPartMinField.setText((Integer.toString(part.getPartMin())));
    }



}
