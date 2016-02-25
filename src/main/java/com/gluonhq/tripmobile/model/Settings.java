package com.gluonhq.tripmobile.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author JosePereda
 */
public class Settings {

    private final StringProperty dataIp = new SimpleStringProperty("localhost");
    
    private final StringProperty dataPort = new SimpleStringProperty("8080");

    private final StringProperty controlIp = new SimpleStringProperty("localhost");
    
    private final StringProperty controlPort = new SimpleStringProperty("8081");

    private final StringProperty cameraIp  = new SimpleStringProperty("localhost");
    
    private final StringProperty cameraPort  = new SimpleStringProperty("8090");

    public final void setDataIp(String value) {
        dataIp.set(value);
    }

    public final String getDataIp() {
        return dataIp.get();
    }

    public final StringProperty dataIpProperty() {
        return dataIp;
    }

    public final void setDataPort(String value) {
        dataPort.set(value);
    }

    public final String getDataPort() {
        return dataPort.get();
    }

    public final StringProperty dataPortProperty() {
        return dataPort;
    }

    public final void setControlIp(String value) {
        controlIp.set(value);
    }

    public final String getControlIp() {
        return controlIp.get();
    }

    public final StringProperty controlIpProperty() {
        return controlIp;
    }

    public final void setControlPort(String value) {
        controlPort.set(value);
    }

    public final String getControlPort() {
        return controlPort.get();
    }

    public final StringProperty controlPortProperty() {
        return controlPort;
    }

    public final void setCameraIp(String value) {
        cameraIp.set(value);
    }

    public final String getCameraIp() {
        return cameraIp.get();
    }

    public final StringProperty cameraIpProperty() {
        return cameraIp;
    }

    public final void setCameraPort(String value) {
        cameraPort.set(value);
    }

    public final String getCameraPort() {
        return cameraPort.get();
    }

    public final StringProperty cameraPortProperty() {
        return cameraPort;
    }
    
    public final String getDataURL() {
        return "ws://" + getDataIp() + ":" + getDataPort() + "/data";
    }
    
    public final String getControlURL() {
        return "ws://" + getControlIp() + ":" + getControlPort() + "/control";
    }
    
    public final String getCameraURL() {
        return "http://" + getCameraIp() + ":" + getCameraPort();
    }
    
}
