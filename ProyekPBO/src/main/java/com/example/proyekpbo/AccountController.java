package com.example.proyekpbo;

import Repository.DataSingleton;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    private Label notice;

    @FXML
    private TextField username;

    @FXML
    private PasswordField oldPass;

    @FXML
    private PasswordField newPass;

    @FXML
    ImageView set;


    private StaffRepository staffRep = new StaffRepository();

    private int id;

    DataSingleton data = DataSingleton.getInstance();

    private ObservableList<StaffProperty> staff = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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


            String staffUser = null;
            for (Staff st : results) {
                if (st.getUser().equalsIgnoreCase(data.getLoginDetail())) {
                    staffUser = st.getUser();
                    id = st.getId();
                }
            }
            username.setText(staffUser);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onConfirmClick() throws SQLException, IOException {
        System.out.println(id);
        if (oldPass.getText().equals(staffRep.GetStaff().get(id-1).getPass())) {
            if (newPass.getText().equals("")) {
                notice.setText("Gagal");
                notice.setTextFill(Color.RED);
            }
            else {
                String us;
                if (username.getText().equals("")) {
                    us = staff.get(id-1).getUser();
                }
                else {
                    us = username.getText();
                }

                String ps = newPass.getText();

                staffRep.EditAcc(new Staff(id,us,ps));

                Alert alert = new Alert(Alert.AlertType.NONE, "Sukses", ButtonType.CLOSE);
                alert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMenu.fxml"));
                Stage stage = HelloApplication.getHelloApplication().getStage();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            }
        }
        else {
            notice.setText("Password Lama Salah");
            notice.setTextFill(Color.RED);
        }
    }

    @FXML
    protected void onReturnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMenu.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
