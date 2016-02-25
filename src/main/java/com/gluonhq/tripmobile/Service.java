package com.gluonhq.tripmobile;

import com.gluonhq.charm.connect.GluonClient;
import com.gluonhq.charm.connect.GluonClientBuilder;
import com.gluonhq.charm.connect.service.CharmObservableObject;
import com.gluonhq.charm.connect.service.StorageService;
import com.gluonhq.charm.connect.service.StorageWhere;
import com.gluonhq.tripmobile.model.Settings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.annotation.PostConstruct;

/**
 *
 * @author JosePereda
 */
public class Service {
    
    private static final String TRIP_SETTINGS = "trip-settings";
    private final ObjectProperty<Settings> settings = new SimpleObjectProperty<>(new Settings());
    
    private GluonClient gluonClient;
    private StorageService userStorage;

    @PostConstruct
    public void postConstruct() {
        gluonClient = GluonClientBuilder.create().build();
        userStorage = gluonClient.getStorageService();
    }
    
    public void retrieveSettings() {
        CharmObservableObject<Settings> charmSettings = userStorage.<Settings>retrieveObject(TRIP_SETTINGS, 
                Settings.class, StorageWhere.DEVICE);
        charmSettings.stateProperty().addListener((obs, ov, nv) -> {
            if (nv.equals(CharmObservableObject.State.INITIALIZED) && charmSettings.get() != null) {
                settingsProperty().set(charmSettings.get());
            }
        });
    }
    
    public void storeSettings() {
        userStorage.<Settings>storeObject(TRIP_SETTINGS, settings.get(), StorageWhere.DEVICE);
    }

    public ObjectProperty<Settings> settingsProperty() {
        return settings;
    }
}
