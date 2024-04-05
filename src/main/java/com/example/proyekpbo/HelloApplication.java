package com.example.proyekpbo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
//    String iconPath = "@../../../ImageResource/icon.png";
//    Image icon = new Image(iconPath);

    static boolean admin = false;

    private static HelloApplication applicationInstances;


    public HelloApplication() {
        applicationInstances = this;
    }

    private Stage stage;

    public static HelloApplication getHelloApplication() {
        return applicationInstances;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
//        stage.getIcons().add(icon);
        stage.setTitle("Fast Food");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Stage getStage() {
        return stage;
    }

    public static boolean isAdmin() {
        return admin;
    }

    public static void setAdmin(boolean admin) {
        HelloApplication.admin = admin;
    }
}