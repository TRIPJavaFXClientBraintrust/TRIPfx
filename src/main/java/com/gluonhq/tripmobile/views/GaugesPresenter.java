package com.gluonhq.tripmobile.views;

import com.gluonhq.charm.down.common.JavaFXPlatform;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import eu.hansolo.medusa.FGauge;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.NeedleShape;
import eu.hansolo.medusa.Gauge.NeedleType;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.GaugeDesign;
import eu.hansolo.medusa.LcdDesign;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 * FXML Controller class
 *
 * @author JosePereda
 */
public class GaugesPresenter {

    @FXML
    private View gauges;
    
    @FXML
    private GridPane gridPaneLeft;
    @FXML
    private GridPane gridPaneRight;
    
    private final DoubleProperty temperature = new SimpleDoubleProperty();
    private final DoubleProperty humidity = new SimpleDoubleProperty();
    private final DoubleProperty radiation = new SimpleDoubleProperty();
    private final DoubleProperty direction = new SimpleDoubleProperty();
    private final DoubleProperty distanceLeft = new SimpleDoubleProperty();
    private final DoubleProperty distanceRight = new SimpleDoubleProperty();
    private final DoubleProperty distanceForward = new SimpleDoubleProperty();
    
    public void initialize() {
        
        boolean animate = JavaFXPlatform.isDesktop();
        
        final Color[] colors = LcdDesign.SECTIONS.getColors();
        final Color colorText = LcdDesign.SECTIONS.lcdForegroundColor;
        final LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1,
                true, CycleMethod.NO_CYCLE,
                new Stop(0, colors[0]),
                new Stop(0.03, colors[1]),
                new Stop(0.5, colors[2]),
                new Stop(0.5, colors[3]),
                new Stop(1.0, colors[4]));
        List<Stop> stops = Arrays.asList(new Stop(0.00, Color.TEAL.brighter().brighter()),
                new Stop(0.25, Color.TEAL.brighter()),
                new Stop(0.50, Color.TEAL),
                new Stop(0.75, Color.TEAL.darker()),
                new Stop(1.00, Color.TEAL.darker().darker()));
        
        Gauge gaugeTemp = GaugeBuilder.create()
                             .skinType(SkinType.DASHBOARD)
                             .backgroundPaint(Color.web("#4DB6AC"))
                             .animated(animate)
                             .title("Temperature")
                             .unit("°C")
                             .autoScale(false)
                             .minValue(-15)
                             .maxValue(45)
                             .decimals(1)
                             .barColor(Color.CRIMSON)
                             .valueColor(colorText)
                             .titleColor(colorText)
                             .unitColor(colorText)
                             .shadowsEnabled(true)
                             .gradientBarEnabled(true)
                             .gradientBarStops(stops)
                             .value(-15)
                             .build();
        
        Gauge gaugeHum = GaugeBuilder.create()
                             .skinType(SkinType.DASHBOARD)
                             .backgroundPaint(Color.web("#4DB6AC"))
                             .animated(animate)
                             .title("Humidity")
                             .unit("%")
                             .minValue(0)
                             .maxValue(100)
                             .decimals(1)
                             .barColor(Color.CRIMSON)
                             .valueColor(colorText)
                             .titleColor(colorText)
                             .unitColor(colorText)
                             .shadowsEnabled(true)
                             .gradientBarEnabled(true)
                             .gradientBarStops(stops)
                             .build();
        
        Gauge gaugeRad = GaugeBuilder.create()
                             .skinType(SkinType.DASHBOARD)
                             .backgroundPaint(Color.web("#4DB6AC"))
                             .animated(animate)
                             .title("Radiation")
                             .unit("CPM")
                             .minValue(0)
                             .maxValue(500)
                             .decimals(1)
                             .barColor(Color.CRIMSON)
                             .valueColor(colorText)
                             .titleColor(colorText)
                             .unitColor(colorText)
                             .shadowsEnabled(true)
                             .gradientBarEnabled(true)
                             .gradientBarStops(stops)
                             .build();
        
        Gauge gaugeDir = GaugeBuilder.create()
                            .prefSize(400, 400)
                            .borderPaint(Color.TEAL.darker())
                            .minValue(0)
                            .maxValue(360)
                            .autoScale(false)
                            .startAngle(180)
                            .angleRange(360)
                            .minorTickSpace(15)
                            .minorTickMarksVisible(true)
                            .mediumTickMarksVisible(false)
                            .majorTickSpace(90)
                            .majorTickMarksVisible(true)
                            .customTickLabelsEnabled(true)
                            .customTickLabels("N", "E", "S", "W")
                            .customTickLabelFontSize(48)
                            .knobType(KnobType.FLAT)
                            .knobColor(Color.TEAL.darker())
                            .needleShape(NeedleShape.FLAT)                            
                            .needleType(NeedleType.FAT)
                            .needleColor(Color.TEAL.darker())
                            .tickLabelColor(Color.TEAL.darker())
                            .animated(animate)
                            .valueVisible(false)
                            .backgroundPaint(linearGradient)
                            .needleBehavior(Gauge.NeedleBehavior.OPTIMIZED)
                            .build();
        FGauge framedGaugeDir = new FGauge(gaugeDir, GaugeDesign.METAL, GaugeDesign.GaugeBackground.ANTHRACITE);
                            
        Label compassTitle = new Label("Direction");
        compassTitle.getStyleClass().add("compassTitle");
        compassTitle.setAlignment(Pos.CENTER);
        compassTitle.setPrefWidth(400);
        
        Label compassValue = new Label("0°");
        compassValue.getStyleClass().add("compassValue");
        compassValue.setAlignment(Pos.CENTER);
        compassValue.setPrefWidth(400);
        gaugeDir.currentValueProperty().addListener(o -> {
            compassValue.setText(String.format(Locale.US, "%.0f°", gaugeDir.getValue()));
        });
        VBox pane4 = new VBox(framedGaugeDir, compassValue, compassTitle);
        
        Gauge gaugeDistFo = GaugeBuilder.create()
                              .skinType(SkinType.LCD)
                              .animated(animate)
                              .title("FORWARD")
                              .subTitle("Distance")
                              .unit("cm")
                              .lcdDesign(LcdDesign.SECTIONS)
                              .maxMeasuredValueVisible(false)
                              .minMeasuredValueVisible(false)
                              .maxValue(Double.MAX_VALUE)
                              .threshold(Double.MAX_VALUE)
                              .build();
        
        Gauge gaugeDistLe = GaugeBuilder.create()
                              .skinType(SkinType.LCD)
                              .animated(animate)
                              .title("LEFT")
                              .subTitle("Distance")
                              .unit("cm")
                              .lcdDesign(LcdDesign.SECTIONS)
                              .maxMeasuredValueVisible(false)
                              .minMeasuredValueVisible(false)
                              .maxValue(Double.MAX_VALUE)
                              .threshold(Double.MAX_VALUE)
                              .build();
        
        Gauge gaugeDistRi = GaugeBuilder.create()
                              .skinType(SkinType.LCD)
                              .animated(animate)
                              .title("RIGHT")
                              .subTitle("Distance")
                              .unit("cm")
                              .lcdDesign(LcdDesign.SECTIONS)
                              .maxMeasuredValueVisible(false)
                              .minMeasuredValueVisible(false)
                              .maxValue(Double.MAX_VALUE)
                              .threshold(Double.MAX_VALUE)
                              .build();
        
        gridPaneLeft.add(gaugeTemp, 0, 0);
        gridPaneLeft.add(gaugeHum, 0, 1);
        gridPaneLeft.add(gaugeRad, 0, 2);
            
        gridPaneRight.add(pane4, 0, 0);
        gridPaneRight.add(gaugeDistFo, 0, 1);
        gridPaneRight.add(gaugeDistLe, 0, 2);
        gridPaneRight.add(gaugeDistRi, 0, 3);
        
        gauges.showingProperty().addListener((obs, ov, nv) -> {
            if (nv) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button(e -> MobileApplication.getInstance().goHome()));
                appBar.setTitleText("Monitoring TRIP");
                appBar.getActionItems().addAll(
                    MaterialDesignIcon.PLAY_ARROW.button(e -> {
                        temperature.set(new Random().nextDouble() * 60 -15d);
                        humidity.set(new Random().nextDouble() * 100);
                        radiation.set(new Random().nextDouble() * 500);
                        direction.set(new Random().nextDouble() * 360);
                        distanceLeft.set(new Random().nextDouble() * 500);
                        distanceRight.set(new Random().nextDouble() * 500);
                        distanceForward.set(new Random().nextDouble() * 500);
                    }), 
                    MaterialDesignIcon.STOP.button());
            }
        });
        
        temperature.addListener((obs, ov, nv) -> gaugeTemp.setValue(nv.doubleValue()));
        humidity.addListener((obs, ov, nv) -> gaugeHum.setValue(nv.doubleValue()));
        radiation.addListener((obs, ov, nv) -> gaugeRad.setValue(nv.doubleValue()));
        direction.addListener((obs, ov, nv) -> gaugeDir.setValue(nv.doubleValue()));
        distanceForward.addListener((obs, ov, nv) -> gaugeDistFo.setValue(nv.doubleValue()));
        distanceLeft.addListener((obs, ov, nv) -> gaugeDistLe.setValue(nv.doubleValue()));
        distanceRight.addListener((obs, ov, nv) -> gaugeDistRi.setValue(nv.doubleValue()));
        
        gauges.addEventHandler(LifecycleEvent.HIDDEN, e -> {
            gaugeTemp.stop();
            gaugeHum.stop();
            gaugeRad.stop();
            gaugeDir.stop();
            gaugeDistFo.stop();
            gaugeDistLe.stop();
            gaugeDistRi.stop();
        });
    }    
    
    public DoubleProperty temperatureProperty() {
        return temperature;
    }
 
    public DoubleProperty humidityProperty() {
        return humidity;
    }
    
    public DoubleProperty radiationProperty() {
        return radiation;
    }
    
    public DoubleProperty directionProperty() {
        return direction;
    }
    
    public DoubleProperty distanceLeftProperty() {
        return distanceLeft;
    }
    
    public DoubleProperty distanceRightProperty() {
        return distanceRight;
    }
    
    public DoubleProperty distanceForwardProperty() {
        return distanceForward;
    }
}
