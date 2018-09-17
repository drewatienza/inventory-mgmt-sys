package model;

import javafx.beans.property.IntegerProperty;

public class Inhouse {

    private final IntegerProperty machineID;

    public Inhouse(IntegerProperty machineID) {
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID.get();
    }

    public IntegerProperty machineIDProperty() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
}
