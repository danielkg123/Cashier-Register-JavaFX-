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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditController implements Initializable {

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField phone;

    @FXML
    private Label text;

    @FXML
    ImageView set;


    private static int id;

    private StaffRepository staffRep = new StaffRepository();

    private ObservableList<StaffProperty> staff = FXCollections.observableArrayList();

    @FXML
    protected void onReturnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Staff.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void onConfirmClick() throws SQLException, IOException {
        Display();

        String fname = this.fname.getText();
        String lname = this.lname.getText();
        int number = Integer.parseInt(phone.getText());

        boolean insert = staffRep.EditAll(new Staff(id,fname,lname,number));

        if (insert) {
            Alert al = new Alert(Alert.AlertType.NONE,"Insert Sukses", ButtonType.CLOSE);
            al.showAndWait();
        }
        else {
            Alert al = new Alert(Alert.AlertType.NONE,"Insert Gagal", ButtonType.CLOSE);
            al.showAndWait();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Staff.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    private void Display() {
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

    public void setText(int text) {
        id = text;
        this.text.setText("ID Selected : " + text);
    }

    public void setFname(String fname) {
        this.fname.setText(fname);
    }

    public void setLname(String lname) {
        this.lname.setText(lname);
    }

    public void setPhone(String phone) {
        this.phone.setText(String.valueOf(phone));
    }
}
