package com.gluonhq.tripmobile;

import com.gluonhq.connect.ConnectState;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.gluoncloud.GluonClient;
import com.gluonhq.connect.gluoncloud.GluonClientBuilder;
import com.gluonhq.connect.gluoncloud.OperationMode;
import com.gluonhq.connect.provider.DataProvider;
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

    @PostConstruct
    public void postConstruct() {
        gluonClient = GluonClientBuilder.create()
                .operationMode(OperationMode.LOCAL_ONLY)
                .build();
    }
    
    public void retrieveSettings() {
        GluonObservableObject<Settings> gluonSettings = DataProvider.<Settings>retrieveObject(
                gluonClient.createObjectDataReader(TRIP_SETTINGS, Settings.class));
        gluonSettings.stateProperty().addListener((obs, ov, nv) -> {
            if (ConnectState.SUCCEEDED.equals(nv) && gluonSettings.get() != null) {
                settings.set(gluonSettings.get());
            }
        });
    }
    
    public void storeSettings() {
        DataProvider.<Settings>storeObject(settings.get(), 
                gluonClient.createObjectDataWriter(TRIP_SETTINGS, Settings.class));
    }

    public ObjectProperty<Settings> settingsProperty() {
        return settings;
    }
}
