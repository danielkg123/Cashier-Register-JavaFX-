package com.example.proyekpbo;

import Repository.PelangganRepository;
import entity.Pelanggan;
import entity.PelangganProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class PelangganController implements Initializable {
    @FXML
    private TableView<PelangganProperty> tabelPelanggan;


    @FXML
    private TableColumn<PelangganProperty, String> ColumnEmail;

    @FXML
    private TableColumn<PelangganProperty, String> ColumnFname;

    @FXML
    private TableColumn<PelangganProperty, String> ColumnId;

    @FXML
    private TableColumn<PelangganProperty, String> ColumnLname;

    @FXML
    private TableColumn<PelangganProperty, String> ColumnPnum;
    @FXML
    private TextField email;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField phone;

    @FXML
    private Label lol;

    @FXML
    private Button clear;

    @FXML
    private Button add;
    @FXML
    private Button updateBTN;


    static int count = 1;


    private PelangganRepository pelrep = new PelangganRepository();

    private ObservableList<PelangganProperty> pelanggan = FXCollections.observableArrayList();

    private boolean update = false;

    @FXML
    protected void onLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) tabelPelanggan.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onMenu() throws IOException {
        if (HelloApplication.isAdmin()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Stage stage = HelloApplication.getHelloApplication().getStage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMenu.fxml"));
            Stage stage = HelloApplication.getHelloApplication().getStage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    private void DisplayTable() throws SQLException {
        pelanggan = FXCollections.observableArrayList();
        try {
            ArrayList<Pelanggan> results = pelrep.GetPelanggan(); // getPelanggan ambil dari repository berupa arraylist
            results.forEach((s) -> {
                PelangganProperty pt = new PelangganProperty(); //property digunakan untuk data bisa dimasukkan di observeablelist
                pt.setId(String.valueOf(s.getId()));
                pt.setfName(s.getFname());
                pt.setlName(s.getLname());
                pt.setNumber(String.valueOf(s.getNumber()));
                pt.setEmail(s.getEmail());
                pelanggan.add(pt);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelPelanggan.setItems(pelanggan);
    }

    @FXML
    public void onClearClick() throws SQLException, IOException {
        try {
            PelangganProperty pelanggan = tabelPelanggan.getSelectionModel().getSelectedItem();
            // pas mencet clear nanti ngambil data dari row yang diclick
            // ambil id terus di remove

            int id = Integer.parseInt(pelanggan.getId());
            boolean removed = pelrep.RemovePelanggan(id);
            // fungsi remove pake parameter integer (id)
            // remove menggunakan repository lagi trus boolean untuk liat berasil ato tidak.
            pelrep.updateIdPelanggan();
            System.out.print(id);
            System.out.println(count);
            DisplayTable();
            // habis remove pake fungsi displaytable buat display kembali
            if (removed) {
                lol.setText("Remove Success");
                lol.setTextFill(Color.LIGHTGREEN);
            } else {
                lol.setText("Remove Failed");
                lol.setTextFill(Color.RED);
            }
            if (!update) {
                clear.setVisible(false);
                this.fname.clear();
                this.lname.clear();
                this.email.clear();
                this.phone.clear();
                add.setText("Add");
            }
        }
        catch (RuntimeException e) {
            lol.setText("No Selection");
            lol.setTextFill(Color.RED);
        }
    }

    @FXML
    public void getSelected() {
        add.setVisible(false);
        updateBTN.setVisible(true);
        try {
            PelangganProperty pelanggan = tabelPelanggan.getSelectionModel().getSelectedItem();
            fname.setText(pelanggan.getfName());
            lname.setText(pelanggan.getlName());
            phone.setText(pelanggan.getNumber());
            email.setText(pelanggan.getEmail());
            add.setText("Save");
            clear.setVisible(true);
        }
        catch (RuntimeException e) {
            lol.setText("No Selection !");
            lol.setTextFill(Color.RED);
        }
        // saat click data di tabel akan mengambil semua data dimasukkan ke textfield biar bisa diubah
        // click juga buat tombol clear nyala
    }

    @FXML
    public void onAddClick() throws SQLException, IOException {
        try {
            if (clear.isVisible()) {
                update = true;
                clear.setVisible(false);
                onClearClick();
                lol.setText("Update Success");
                add.setText("Add");
                System.out.println("test");
            }
            else lol.setText("Insert Success");
            // disiini ngecek kalo clear nyala (berarti ada row yang di click) bakal ngekeluarin kata yang beda

            boolean idDetector = false;
            update = false;
            // buat iddetector buat cek kalau input dengan id yang sama tidak akan tetambah

            if (pelanggan.size() == 0) {
                System.out.println(pelanggan.size());
                count = 1;
            }
            else {
                count = pelanggan.size() + 1;
            }

            int id = count;
            String fname = this.fname.getText();
            String lname = this.lname.getText();
            int number = Integer.parseInt(phone.getText());
            String email = this.email.getText();
            //ngambil data dari textfield


            ArrayList<Pelanggan> data = pelrep.GetPelanggan();
            for (Pelanggan temp : data) {
                if (id == temp.getId()) {
                    idDetector = true;
                    lol.setText("ID sudah dipakai");
                    lol.setTextFill(Color.RED);

                    break;
                }
            }

            // disini ngecek apakah data ada id yang sama


            if (!(idDetector)) {
                // kalau id !true atau false jadinya
                boolean insert = pelrep.InsertPelanggan(new Pelanggan(id, fname, lname, number, email));
                // akan insert tabel
                DisplayTable();
                // displaytable() untuk update tabel

                if (insert) {
                    lol.setTextFill(Color.LIGHTGREEN);
                    System.out.println("ow");
                } else {
                    lol.setText("Insert Failed");
                }
                //untuk merubah warna di label notif
            }

            this.fname.clear();
            this.lname.clear();
            this.email.clear();
            this.phone.clear();
        }
        catch (NumberFormatException e) {
            lol.setText("INVALID");
            lol.setTextFill(Color.RED);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clear.setVisible(false);
        updateBTN.setVisible(false);
        // di awal tombol clear selalu invisible

        ColumnId.setCellValueFactory(f -> f.getValue().idProperty());
        ColumnFname.setCellValueFactory(f -> f.getValue().fNameProperty());
        ColumnLname.setCellValueFactory(f -> f.getValue().lNameProperty());
        ColumnPnum.setCellValueFactory(f -> f.getValue().numberProperty());
        ColumnEmail.setCellValueFactory(f -> f.getValue().emailProperty());
        // buat nampilin data dengan value yan bener

        try {
            DisplayTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onUpdateClick(ActionEvent event) throws SQLException {
        updateBTN.setVisible(false);
        add.setVisible(true);
        clear.setVisible(false);
        PelangganProperty pel = tabelPelanggan.getSelectionModel().getSelectedItem();


        boolean idDetector = false;
        ArrayList<Pelanggan> data = pelrep.GetPelanggan();
        for (Pelanggan temp : data) {
            if (Integer.parseInt(pel.getId()) == temp.getId()) {
                idDetector = true;
                lol.setText("");
                lol.setTextFill(Color.RED);

                break;
            }
        }
        boolean update = pelrep.UpdatePelanggan(new Pelanggan(Integer.parseInt(pel.getId()), fname.getText(),lname.getText(),Integer.parseInt(phone.getText()),email.getText()),Integer.parseInt(pel.getId()));
        this.fname.clear();
        this.lname.clear();
        this.email.clear();
        this.phone.clear();
        DisplayTable();
    }
}
