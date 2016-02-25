package com.gluonhq.tripmobile.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.SettingsPane;
import com.gluonhq.charm.glisten.control.settings.DefaultOption;
import com.gluonhq.charm.glisten.control.settings.Option;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.tripmobile.Service;
import com.gluonhq.tripmobile.model.Settings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javax.inject.Inject;

/**
 *
 * @author JosePereda
 */
public class SettingsPresenter {
    
    @Inject private Service service;
    
    @FXML private View settings;
    @FXML private SettingsPane settingsPane;
    
    private Settings configuration;
    
    public void initialize() {
        
        configuration = new Settings();
        updateSettings(service.settingsProperty().get());
        
        configuration.dataIpProperty().addListener((obs, ov, nv) -> updateService());
        configuration.dataPortProperty().addListener((obs, ov, nv) -> updateService());
        configuration.controlIpProperty().addListener((obs, ov, nv) -> updateService());
        configuration.controlPortProperty().addListener((obs, ov, nv) -> updateService());
        configuration.cameraIpProperty().addListener((obs, ov, nv) -> updateService());
        configuration.cameraPortProperty().addListener((obs, ov, nv) -> updateService());
        
        final DefaultOption<String> dataIpOption = new DefaultOption(MaterialDesignIcon.WEB.graphic(),
                "Data IP", "Set the data' IP", null, configuration.dataIpProperty(), true);
        
        final DefaultOption<String> dataPortOption = new DefaultOption(MaterialDesignIcon.WEB.graphic(),
                "Data Port", "Set the data' Port", null, configuration.dataPortProperty(), true);
        
        final DefaultOption<String> controlIpOption = new DefaultOption(MaterialDesignIcon.CONTROL_POINT.graphic(),
                "Control IP", "Set the control' IP", null, configuration.controlIpProperty(), true);
        
        final DefaultOption<String> controlPortOption = new DefaultOption(MaterialDesignIcon.CONTROL_POINT.graphic(),
                "Control Port", "Set the control' Port", null, configuration.controlPortProperty(), true);
        
        final DefaultOption<String> cameraIpOption = new DefaultOption(MaterialDesignIcon.CAMERA_ALT.graphic(),
                "Camera IP", "Set the camera's IP", null, configuration.cameraIpProperty(), true);
        
        final DefaultOption<String> cameraPortOption = new DefaultOption(MaterialDesignIcon.CAMERA_ALT.graphic(),
                "Camera Port", "Set the camera's Port", null, configuration.cameraPortProperty(), true);
        
        settingsPane.getOptions().setAll(FXCollections.<Option>observableArrayList(
                dataIpOption, dataPortOption, 
                controlIpOption, controlPortOption,
                cameraIpOption, cameraPortOption));
        settingsPane.setSearchBoxVisible(false);
        
        service.settingsProperty().addListener((obs, ov, nv) -> updateSettings(nv));
        
        settings.showingProperty().addListener((obs, ov, nv) -> {
            if (nv) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button(e -> 
                        MobileApplication.getInstance().switchToPreviousView()));
                appBar.setTitleText("Settings");
                appBar.getActionItems().add(MaterialDesignIcon.CLOSE.button(e ->
                        MobileApplication.getInstance().switchToPreviousView()));
            }
        });
    }
    
    private void updateSettings(Settings settings) {
        if (settings != null) {
            this.configuration.setDataIp(settings.getDataIp());
            this.configuration.setDataPort(settings.getDataPort());
            this.configuration.setControlIp(settings.getControlIp());
            this.configuration.setControlPort(settings.getControlPort());
            this.configuration.setCameraIp(settings.getCameraIp());
            this.configuration.setCameraPort(settings.getCameraPort());
        }
    }
    
    private void updateService() {
        Settings configuration = new Settings();
        configuration.setDataIp(this.configuration.getDataIp());
        configuration.setDataPort(this.configuration.getDataPort());
        configuration.setControlIp(this.configuration.getControlIp());
        configuration.setControlPort(this.configuration.getControlPort());
        configuration.setCameraIp(this.configuration.getCameraIp());
        configuration.setCameraPort(this.configuration.getCameraPort());
        service.settingsProperty().set(configuration);
        service.storeSettings();
    }
}
