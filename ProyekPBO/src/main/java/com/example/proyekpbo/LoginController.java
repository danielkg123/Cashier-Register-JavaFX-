package com.example.proyekpbo;

import Repository.DataSingleton;
import Repository.StaffRepository;
import entity.Pelanggan;
import entity.Staff;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label verification;

    @FXML
    private ImageView user;

    @FXML
    private ImageView pass;
    @FXML
    private Button login;



    boolean kosong = false;
    DataSingleton data = DataSingleton.getInstance();

    private StaffRepository staffRep = new StaffRepository();

    @FXML
    protected void OnLoginClick() throws IOException, SQLException {
        LoginVerification();
        data.setLoginDetail(username.getText());

        if (HelloApplication.isAdmin()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Stage stage = (Stage) password.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } else {
            if (kosong) {
                verification.setText("Gagal Login");
                verification.setTextFill(Color.RED);
            } else {
                String user = username.getText();
                String pass = password.getText();
                ArrayList<Staff> data = staffRep.GetStaff();
                for (Staff temp : data) {
                    if (user.equals(temp.getUser())) {
                        if (pass.equals(temp.getPass())) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMenu.fxml"));
                            Stage stage = (Stage) password.getScene().getWindow();
                            Scene scene = new Scene(loader.load());
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            verification.setText("Password Salah");
                            verification.setTextFill(Color.RED);
                        }
                    } else {
                        verification.setText("Username tidak ditemukan");
                        verification.setTextFill(Color.RED);
                    }
                }
            }
        }
    }

    @FXML
    protected void LoginVerification() {
        if (username.getText().equalsIgnoreCase("Admin") && password.getText().equalsIgnoreCase("Admin")) {
            HelloApplication.setAdmin(true);
        } else if (username.getText().equals("") || password.getText().equals("")) {
            kosong = true;
        }
    }

    @FXML
    protected void onRegClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Stage stage = (Stage) password.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setStyle((
                "-fx-font-size: 12px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-color: lightgreen;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-color: blue;"
        ));
    }
}
