package com.example.proyekpbo;

import Repository.PelangganRepository;
import Repository.StaffRepository;
import entity.Pelanggan;
import entity.PelangganProperty;
import entity.Staff;
import entity.StaffProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    @FXML
    private TableColumn<StaffProperty, String> ColumnFname;

    @FXML
    private TableColumn<StaffProperty, String> ColumnId;

    @FXML
    private TableColumn<StaffProperty, String> ColumnLname;

    @FXML
    private TableColumn<StaffProperty, String> ColumnPnum;

    @FXML
    private TableColumn<StaffProperty, String> ColumnUser;

    @FXML
    private TableView<StaffProperty> tabelStaff;

    @FXML
    private Label select;

    private StaffRepository staffRep = new StaffRepository();

    private int id_select;

    private ObservableList<StaffProperty> staff = FXCollections.observableArrayList();

    @FXML
    protected void onLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) tabelStaff.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        Stage stage = (Stage) tabelStaff.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
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
        tabelStaff.setItems(staff);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColumnId.setCellValueFactory(f -> f.getValue().idProperty());
        ColumnFname.setCellValueFactory(f -> f.getValue().fNameProperty());
        ColumnLname.setCellValueFactory(f -> f.getValue().lNameProperty());
        ColumnPnum.setCellValueFactory(f -> f.getValue().numberProperty());
        ColumnUser.setCellValueFactory(f -> f.getValue().userProperty());
        try {
            DisplayTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClearClick() throws SQLException, IOException {
        try {
            StaffProperty staff = tabelStaff.getSelectionModel().getSelectedItem();
            int count = tabelStaff.getItems().size();
            // pas mencet clear nanti ngambil data dari row yang diclick
            // ambil id terus di remove

            int id = Integer.parseInt(staff.getId());
            boolean removed = staffRep.RemoveStaff(id);
            // fungsi remove pake parameter integer (id)
            // remove menggunakan repository lagi trus boolean untuk liat berasil ato tidak.
            DisplayTable();

            staffRep.updateIdStaff();
            DisplayTable();

            if (removed) {
                select.setText("Remove Sukses");
                select.setTextFill(Color.GREEN);
            }
            else {
                select.setText("Gagal Remove");
                select.setTextFill(Color.RED);
            }
        }
        catch (Exception e) {
            select.setText("No Selection !");
            select.setTextFill(Color.RED);
        }
    }

    @FXML
    protected void onEditClick() throws IOException {
        StaffProperty staff = tabelStaff.getSelectionModel().getSelectedItem();
        id_select = Integer.parseInt(staff.getId());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditStaff.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

        EditController ctrl = loader.getController();
        ctrl.setText(id_select);
        ctrl.setFname(staff.getfName());
        ctrl.setLname(staff.getlName());
        ctrl.setPhone(staff.getNumber());
    }

    public int getId_select() {
        return id_select;
    }
}
