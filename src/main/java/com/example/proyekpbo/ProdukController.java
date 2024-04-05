package com.example.proyekpbo;

import Repository.ProdukRepository;
import entity.Pelanggan;
import entity.PelangganProperty;
import entity.Produk;
import entity.ProdukProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProdukController implements Initializable {

    @FXML
    private Label verif;
    @FXML
    private TableView<ProdukProperty> tabelProduk;

    @FXML
    private Button add;

    @FXML
    private ComboBox<String> category;

    @FXML
    private Button clear;

    @FXML
    private TableColumn<ProdukProperty, String> columnCategory;

    @FXML
    private TableColumn<ProdukProperty, String> columnId;

    @FXML
    private TableColumn<ProdukProperty, String> columnName;

    @FXML
    private TableColumn<ProdukProperty, String> columnPrice;

    @FXML
    private TextField price;

    @FXML
    private TextField nama;
    @FXML
    private Button updateBTN;

    static int count = 1;

    private ProdukRepository prorep = new ProdukRepository();
    private boolean update = false;



    private ObservableList<ProdukProperty> produkList = FXCollections.observableArrayList();

    @FXML
    protected void onLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) tabelProduk.getScene().getWindow();
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
        produkList = FXCollections.observableArrayList();
        try {
            ArrayList<Produk> results = prorep.GetProduk(); // getPelanggan ambil dari repository berupa arraylist
            results.forEach((s) -> {
                ProdukProperty pt = new ProdukProperty(); //property digunakan untuk data bisa dimasukkan di observeablelist
                pt.setId(String.valueOf(s.getId()));
                pt.setName(s.getName());
                pt.setCategory(s.getCategory());
                pt.setPrice(String.valueOf(s.getPrice()));
                produkList.add(pt);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelProduk.setItems(produkList);
    }
    @FXML
    public void test() {
        System.out.println(category.getValue());
        category.valueProperty().set(null);
    }

    @FXML
    public void onClearClick() throws SQLException, IOException {
        updateBTN.setVisible(false);
        add.setVisible(true);
        try {
            ProdukProperty produk = tabelProduk.getSelectionModel().getSelectedItem();
            // pas mencet clear nanti ngambil data dari row yang diclick
            // ambil id terus di remove

            int id = Integer.parseInt(produk.getId());
            boolean removed = prorep.RemoveProduk(id);
            // fungsi remove pake parameter integer (id)
            // remove menggunakan repository lagi trus boolean untuk liat berasil ato tidak.
            prorep.updateIdProduk();
            DisplayTable();
            // habis remove pake fungsi displaytable buat display kembali
            if (removed) {
                verif.setText("Remove Success");
                verif.setTextFill(Color.LIGHTGREEN);
            } else {
                verif.setText("Remove Failed");
                verif.setTextFill(Color.RED);
            }
            System.out.println("Bool :" + update);
            if (!update) {
                clear.setVisible(false);
                nama.clear();
                this.price.clear();
                category.valueProperty().set(null);
                add.setText("Add");
            }
        }
        catch (RuntimeException e) {
            verif.setText("Invalid Selection");
            verif.setTextFill(Color.RED);
        }
    }

    @FXML
    public void getSelected() {
        add.setVisible(false);
        updateBTN.setVisible(true);
        try {
            ProdukProperty produk = tabelProduk.getSelectionModel().getSelectedItem();
            nama.setText(produk.getName());
            price.setText(produk.getPrice());
            category.setValue(produk.getCategory());
            add.setText("Save");
            clear.setVisible(true);
        } catch (RuntimeException ignored) {

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
                verif.setText("Update Success");
                add.setText("Add");
            } else verif.setText("Insert Success");
            // disiini ngecek kalo clear nyala (berarti ada row yang di click) bakal ngekeluarin kata yang beda

            boolean idDetector = false;
            update = false;

            if (produkList.size() == 0) {
                count = 1;
            }
            else {
                count = produkList.size() + 1;
            }

            int id = count;
            String name = this.nama.getText();
            String category = this.category.getValue();
            int price = Integer.parseInt(this.price.getText());

            //ngambil data dari textfield


            ArrayList<Produk> data = prorep.GetProduk();
            for (Produk temp : data) {
                if (id == temp.getId()) {
                    idDetector = true;
                    verif.setText("ID sudah dipakai");
                    verif.setTextFill(Color.RED);
                    break;
                }
            }
            // disini ngecek apakah data ada id yang sama


            if (!(idDetector)) {
                // kalau id !true atau false jadinya
                boolean insert = prorep.InsertProduk(new Produk(id, price, name, category));
                // akan insert tabel
                DisplayTable();
                // displaytable() untuk update tabel

                if (insert) {
                    verif.setTextFill(Color.LIGHTGREEN);
//                    System.out.println("ow");
                } else {
                    verif.setText("Insert Failed");
                }
                //untuk merubah warna di label notif
            }

            this.nama.clear();
            this.price.clear();
            this.category.valueProperty().set(null);

        } catch (NumberFormatException e) {
            verif.setText("INVALID");
            verif.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clear.setVisible(false);
        updateBTN.setVisible(false);
        columnId.setCellValueFactory(f -> f.getValue().idProperty());
        columnName.setCellValueFactory(f -> f.getValue().nameProperty());
        columnCategory.setCellValueFactory(f -> f.getValue().categoryProperty());
        columnPrice.setCellValueFactory(f -> f.getValue().priceProperty());
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Food",
                        "Beverage",
                        "Dessert"
                );
        category.setItems(options);

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
        ProdukProperty produk = tabelProduk.getSelectionModel().getSelectedItem();
        boolean idDetector = false;
        ArrayList<Produk> data = prorep.GetProduk();
        for (Produk temp : data) {
            if (Integer.parseInt(produk.getId()) == temp.getId()) {
                idDetector = true;
                verif.setText("");
                verif.setTextFill(Color.RED);
                break;
            }
        }
        boolean update = prorep.UpdateProduk(new Produk(Integer.parseInt(produk.getId()),Integer.parseInt(price.getText()),nama.getText(),category.getValue()), Integer.parseInt(produk.getId()));
        this.nama.clear();
        this.price.clear();
        this.category.valueProperty().set(null);
        DisplayTable();
    }
}