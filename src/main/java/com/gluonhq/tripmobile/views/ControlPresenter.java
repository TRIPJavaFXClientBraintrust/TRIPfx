package com.gluonhq.tripmobile.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.tripmobile.TRIPMobile;
import static com.gluonhq.tripmobile.TRIPMobile.MENU_LAYER;
import com.gluonhq.tripmobile.trip.Camera;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author JosePereda
 */
public class ControlPresenter {
    
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
    
    public void initialize() {
        control.showingProperty().addListener((obs, ov, nv) -> {
            if (nv) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button(e -> MobileApplication.getInstance().goHome()));
                appBar.setTitleText("TRIP Mobile ControlBoard");
                appBar.getActionItems().add(MaterialDesignIcon.CAMERA.button(e -> {
                    if (camFeed == null) {
                        camFeed = new Camera(TRIPMobile.CAMERA_IP);
                        camFeed.getImageProperty().addListener(
                            (obs1, ov1, nv1) -> Platform.runLater(() -> imageView.setImage(nv1)));
                        camFeed.getHasImageProperty().addListener(
                            (obs1, ov1, nv1) -> {
                                if (!nv1) {
                                    Platform.runLater(() -> imageView.setImage(new Image(ControlPresenter.class.getResource("No_Image_Available.png").toExternalForm())));
                                }
                            });
                        start();
                    }
                }));
            }
        });
        
        buttonUp.setOnAction(e -> System.out.println("Up"));
        buttonLeft.setOnAction(e -> System.out.println("Left"));
        buttonRight.setOnAction(e -> System.out.println("Right"));
        buttonDown.setOnAction(e -> System.out.println("Down"));
        buttonStop.setOnAction(e -> System.out.println("Stop"));
    }
    
    public void start() {
        if (scheduler != null) {
            stop();
        }
        scheduler = Executors.newSingleThreadScheduledExecutor();
        schedulerCam = scheduler.scheduleAtFixedRate(camFeed, 0, 200, TimeUnit.MILLISECONDS);
    }
    
    public void stop() {
        if (schedulerCam != null) {
            schedulerCam.cancel(true);
        }
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}
