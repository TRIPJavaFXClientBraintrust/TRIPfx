package com.gluonhq.tripmobile.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CardPane;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static com.gluonhq.tripmobile.TRIPMobile.MENU_LAYER;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author JosePereda
 */
public class HomePresenter {

    @FXML
    private View home;
    
    @FXML
    private CardPane cardPane;
    
    public void initialize() {
        final Label labelTrip = new Label("TRIP");
        labelTrip.getStyleClass().add("title");
        final Label labelSubtitle = new Label("Test Robotics IoT Platform");
        labelSubtitle.getStyleClass().add("subtitle");
        
        VBox vBox1 = new VBox(20, labelTrip, new ImageView(new Image(getClass().getResourceAsStream("FullSizeRender.jpg"))), labelSubtitle);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPadding(new Insets(10));
        
        HBox hBox = new HBox(20, 
                new ImageView(new Image(getClass().getResourceAsStream("icon_spring_cloud_services_cf@2x.png"), 64, 64, true, true)), 
                new ImageView(new Image(getClass().getResourceAsStream("/icon.png"), 64, 64, true, true)));
        hBox.setAlignment(Pos.CENTER);
        final Label labelAuthors = new Label("M. Heckler, J. Weaver & J.Pereda");
        labelAuthors.getStyleClass().add("authors");
        VBox vBox2 = new VBox(20, hBox, labelAuthors);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setPadding(new Insets(10));
        
        cardPane.getCards().addAll(vBox1, vBox2);
        
        home.showingProperty().addListener((obs, ov, nv) -> {
            if (nv) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> MobileApplication.getInstance().showLayer(MENU_LAYER)));
                appBar.setTitleText("TRIP Mobile Dashboard");
            }
        });
        
    }    
    
}
