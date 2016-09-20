package com.gluonhq.tripmobile.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.tripmobile.Service;
import com.gluonhq.tripmobile.trip.Camera;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gluonhq.tripmobile.websocketclient.ControlMessageEndpoint;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 *
 * @author JosePereda
 */
public class ControlPresenter {

    @Inject private Service service;
    
    @FXML
    private View control;

    @FXML
    private ImageView imageView;

    @FXML
    private Button buttonUp;

    @FXML
    private Button buttonDown;

    @FXML
    private Button buttonLeft;

    @FXML
    private Button buttonRight;

    @FXML
    private Button buttonStop;

    private ScheduledExecutorService scheduler = null;
    private ScheduledFuture<?> schedulerCam = null;
    private Camera camFeed;

    private final ControlMessageEndpoint endpoint = new ControlMessageEndpoint();
    private final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    private Session connectToServer;

    public void initialize() {

        imageView.setRotate(90);
        control.showingProperty().addListener((obs, ov, nv) -> {
            if (nv) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button(e -> MobileApplication.getInstance().goHome()));
                appBar.setTitleText("TRIP Mobile ControlBoard");
                appBar.getActionItems().addAll(
                        MaterialDesignIcon.CAMERA.button(e -> start()),
                        MaterialDesignIcon.STOP.button(e -> stop()));
            }
        });

        buttonUp.setOnAction(e -> {
            System.out.println("Up");
            endpoint.sendMessage("F");
        });
        buttonLeft.setOnAction(e -> {
            System.out.println("Left");
            endpoint.sendMessage("L");
        });
        buttonRight.setOnAction(e -> {
            System.out.println("Right");
            endpoint.sendMessage("R");
        });
        buttonDown.setOnAction(e -> {
            System.out.println("Down");
            endpoint.sendMessage("B");
        });
        buttonStop.setOnAction(e -> {
            System.out.println("Stop");
            endpoint.sendMessage("S");
        });
    }

    @PostConstruct
    public void start() {
        System.out.println("Start camera");
        if (camFeed == null) {
            camFeed = new Camera(service.settingsProperty().get().getCameraURL());
            camFeed.getImageProperty().addListener(
                (obs, ov, nv) -> Platform.runLater(() -> imageView.setImage(nv)));
            camFeed.getHasImageProperty().addListener(
                (obs, ov, nv) -> {
                    if (!nv) {
                        Platform.runLater(() -> imageView.setImage(new Image(ControlPresenter.class.getResource("No_Image_Available.png").toExternalForm())));
                    }
                });
        }
        
        if (scheduler != null) {
            stop();
        }
        scheduler = Executors.newSingleThreadScheduledExecutor();
        schedulerCam = scheduler.scheduleAtFixedRate(camFeed, 0, 200, TimeUnit.MILLISECONDS);
        
        System.out.println("Start control");
        // Connect to the GroundControl server
        try {
            if (connectToServer != null) {
                connectToServer.close();
            }
            connectToServer = container.connectToServer(endpoint, new URI(service.settingsProperty().get().getControlURL()));
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            Logger.getLogger(ControlPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    private void stop() {
        System.out.println("stop camera");
        if (schedulerCam != null) {
            schedulerCam.cancel(true);
        }
        if (scheduler != null) {
            scheduler.shutdown();
        }
        
        try {
            System.out.println("closing connection");
            if (connectToServer != null) {
                connectToServer.close();
            }
        } catch(IOException ex) {
            Logger.getLogger(ControlPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
