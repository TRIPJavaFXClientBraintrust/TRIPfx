package com.gluonhq.tripmobile;

import com.gluonhq.charm.down.common.JavaFXPlatform;
import com.gluonhq.charm.down.common.PlatformFactory;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.tripmobile.views.ControlPresenter;
import com.gluonhq.tripmobile.views.ControlView;
import com.gluonhq.tripmobile.views.GaugesView;
import com.gluonhq.tripmobile.views.HomeView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 *
 * @author JosePereda
 */
public class TRIPMobile extends MobileApplication {

    public static final String GAUGES_VIEW = "Gauges";
    public static final String CONTROL_VIEW = "Control";
    public static final String MENU_LAYER = "Menu";
    
    public static final String CAMERA_IP = "http://192.168.1.50:8090";
    
    private ControlPresenter controlPresenter;
    
    private final ChangeListener listener = (o, oldItem, newItem) -> {
                    hideLayer(MENU_LAYER);
                    switchView(((NavigationDrawer.Item) newItem).getTitle());
                };
        
    @Override
    public void init() {
        addViewFactory(HOME_VIEW, () -> (View)new HomeView().getView());
        addViewFactory(GAUGES_VIEW, () -> (View)new GaugesView().getView());
        addViewFactory(CONTROL_VIEW, () -> {
            ControlView controlView = new ControlView();
            controlPresenter = (ControlPresenter) controlView.getPresenter();
            return (View) controlView.getView();
        });
        
        NavigationDrawer navigationDrawer = new NavigationDrawer();
        NavigationDrawer.Header header = new NavigationDrawer.Header("TRIP",
                "Test Robotics IoT Platform",
                new Avatar(21, new Image(getClass().getResourceAsStream("views/icon_spring_cloud_services_cf@2x.png"))));
        navigationDrawer.setHeader(header);

        navigationDrawer.selectedItemProperty().addListener(listener);
        
        navigationDrawer.getItems().setAll(
            new NavigationDrawer.Item(HOME_VIEW, MaterialDesignIcon.HOME.graphic()),
            new NavigationDrawer.Item(GAUGES_VIEW, MaterialDesignIcon.NETWORK_CHECK.graphic()),
            new NavigationDrawer.Item(CONTROL_VIEW, MaterialDesignIcon.DIRECTIONS.graphic()));
        
        addLayerFactory(MENU_LAYER, () -> new SidePopupView(navigationDrawer));
        viewProperty().addListener((obs, ov, nv) -> {
            for (Node node : navigationDrawer.getItems()) {
                NavigationDrawer.Item item = (NavigationDrawer.Item) node;
                if (item.getTitle().equals(nv.getName())) {
                    navigationDrawer.selectedItemProperty().removeListener(listener);
                    navigationDrawer.setSelectedItem(node);
                    item.setSelected(true);
                    navigationDrawer.selectedItemProperty().addListener(listener);
                } else {
                    item.setSelected(false);
                }
            }
        });
        
    }
    
    @Override
    public void postInit(Scene scene) {
        Swatch.TEAL.assignTo(scene);
        
        if (JavaFXPlatform.isDesktop()) {
            scene.getWindow().setWidth(350);
            scene.getWindow().setHeight(650);
        }
        
        PlatformFactory.getPlatform().setOnLifecycleEvent(p -> {
            if (controlPresenter != null) {
                switch (p) {
                    case PAUSE: 
                    case STOP: controlPresenter.stop(); break;
                    case RESUME: controlPresenter.start(); break;
                }
            }
            return null;
        });
    }
    
    @Override
    public void stop() {
        if (controlPresenter != null) {
            controlPresenter.stop();
        }
    }
    
}
