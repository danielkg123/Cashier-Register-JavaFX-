package com.example.proyekpbo;

import Repository.StaffRepository;
import entity.Staff;
import entity.StaffProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterController {

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField phone;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmationPass;

    @FXML
    private Label notice;

    static int count = 1;

    private StaffRepository staffRep = new StaffRepository();

    private ObservableList<StaffProperty> staff = FXCollections.observableArrayList();

    @FXML
    protected void onReturnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onConfirmClick() throws SQLException, IOException {
        try {

            if (password.getText().equals(confirmationPass.getText())) {
                DisplayTable();

                if (staff.size() == 0) {
                    System.out.println(staff.size());
                    count = 1;
                } else {
                    count = staff.size() + 1;
                }

                System.out.println(count);
                int id = count;
                String fname = this.fname.getText();
                String lname = this.lname.getText();
                int number = Integer.parseInt(phone.getText());
                String username = this.username.getText();
                String pass = this.password.getText();

                staffRep.InsertStaff(new Staff(id, fname, lname, number, username, pass));

                Alert alert = new Alert(Alert.AlertType.NONE, "Registrasi Sukses", ButtonType.CLOSE);
                alert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Stage stage = HelloApplication.getHelloApplication().getStage();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            } else {
                notice.setText("Password tidak sama !");
                notice.setTextFill(Color.RED);
            }
        }
        catch (RuntimeException e) {
            notice.setText("GAGAL");
            notice.setTextFill(Color.RED);
        }
    }

    private void DisplayTable() throws SQLException {
        staff = FXCollections.observableArrayList();
        try {
            ArrayList<Staff> results = staffRep.GetStaff(); // getPelanggan ambil dari repository berupa arraylist

            results.forEach((s) -> {
                StaffProperty st = new StaffProperty(); //property digunakan untuk data bisa dimasukkan di observeablelist
                st.setId(String.valueOf(s.getId()));
                st.setfName(s.getFname());
                st.setlName(s.getLname());
                st.setNumber(String.valueOf(s.getNumber()));
                st.setUser(s.getUser());
                st.setPass(s.getPass());
                staff.add(st);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
