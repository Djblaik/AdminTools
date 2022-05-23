/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admintools.util;

import app.admintools.gui.SettingsWindowController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 *
 * @author lukak
 */
@SuppressWarnings("SimplifyStreamApiCallChains")
public class WindowLoader {

    public static void loadRcon(AnchorPane rootPane) {
        loadWindow(rootPane, Utill.getPath("/gui/fxml/RconWindow.fxml"));
        Data.isOnStatusWindow = false;
    }

    public static void loadSettings(AnchorPane rootPane) {
        loadWindow(rootPane, Utill.getPath("/gui/fxml/SettingsWindow.fxml"));
        Data.isOnStatusWindow = false;
    }

    public static void loadStatus(AnchorPane rootPane) {
        Data.isOnStatusWindow = true;
        loadWindow(rootPane, Utill.getPath("/gui/fxml/StatusWindow.fxml"));
    }

    public static void loadHome(AnchorPane rootPane) {
        loadWindow(rootPane, Utill.getPath("/gui/fxml/HomeWindow.fxml"));
        Data.isOnStatusWindow = false;
    }

    private static void loadWindow(AnchorPane rootPane, String url) {

        try {
            Data d = Data.getInstance(); //Get instance of data 

            @SuppressWarnings("ConstantConditions") AnchorPane ap = FXMLLoader.load(SettingsWindowController.class.getResource(url)); //Get anchorpane

            //get width and height of current scene
            double scWidth = rootPane.getScene().getWidth();
            double scHeight = rootPane.getScene().getHeight();

            //Set style for selected theme
            Utill.setSelectedTheme(ap);

            //Create stage      yes
            Scene scene2 = new Scene(ap, scWidth, scHeight);
            Stage windowStage = (Stage) rootPane.getScene().getWindow();

            //Switch scene
            windowStage.setScene(scene2);

            //Get fade transition between windows
            //Fade in
            //Foreach just netben
            ap.getChildren().stream().map((child) -> new FadeTransition(Duration.seconds(0.25), child)).map((ft) -> {
                ft.setFromValue(.3d);
                return ft;
            }).map((ft) -> {
                ft.setToValue(1.0d);
                return ft;
            }).forEachOrdered((ft) -> {
                //noinspection Convert2MethodRef
                ft.play();
            });

            //Refresh title
            windowStage.setTitle("Admin Tools - " + d.getSelectedCredentials().getIP() + ":" + d.getSelectedCredentials().getPort());

            //Set width after scene switch
            windowStage.sizeToScene();

        } catch (IOException ex) {
            AtLogger.logger.warning(AtLogger.formatException(ex));
        }

    }
}
