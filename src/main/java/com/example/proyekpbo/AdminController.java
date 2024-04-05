package com.example.proyekpbo;

import Repository.OrderRepository;
import Repository.PelangganRepository;
import Repository.ProdukRepository;
import entity.Order;
import entity.OrderProperty;
import entity.Pelanggan;
import entity.Produk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TableColumn<OrderProperty, String> ColumnCus;

    @FXML
    private TableColumn<OrderProperty, String> ColumnDate;

    @FXML
    private TableColumn<OrderProperty, String> ColumnId;

    @FXML
    private TableColumn<OrderProperty, String> ColumnProduk;

    @FXML
    private TableColumn<OrderProperty, String> ColumnStaff;
    @FXML
    private TableView<OrderProperty> TabelOrder;

    @FXML
    private Label custModus;

    @FXML
    private TableColumn<?, ?> produkColumn;

    @FXML
    private Label produkModus;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private Label sales;

    @FXML
    private TableColumn<?, ?> salesColumn;

    @FXML
    private TableView<?> tabelSales;

    @FXML
    private Label totalOrder;
    private OrderRepository orderRepository = new OrderRepository();
    private ProdukRepository prorep = new ProdukRepository();
    private PelangganRepository pelrep = new PelangganRepository();
    private ObservableList<OrderProperty> orderList = FXCollections.observableArrayList();


    @FXML
    protected void onLogOutClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        HelloApplication.setAdmin(false);
    }

    @FXML
    protected void onPelangganClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pelanggan.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onStaffClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Staff.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onProdukCLick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onOrderClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    private void DisplayTable() throws SQLException {
        orderList = FXCollections.observableArrayList();
        try {
            ArrayList<Order> results = orderRepository.GetOrder(); // getPelanggan ambil dari repository berupa arraylist
            results.forEach((s) -> {
                OrderProperty ot = new OrderProperty(); //property digunakan untuk data bisa dimasukkan di observeablelist
                ot.setId(String.valueOf(s.getId()));
                ot.setPelanggan(s.getPelanggan());
                ot.setProduk(s.getProduk());
                ot.setStaff(s.getStaff());
                ot.setDate(s.getDate());
                orderList.add(ot);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TabelOrder.setItems(orderList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (HelloApplication.isAdmin()) {
            ColumnId.setCellValueFactory(f -> f.getValue().idProperty());
            ColumnCus.setCellValueFactory(f -> f.getValue().pelangganProperty());
            ColumnDate.setCellValueFactory(f -> f.getValue().dateProperty());
            ColumnStaff.setCellValueFactory(f -> f.getValue().staffProperty());
            ColumnProduk.setCellValueFactory(f -> f.getValue().produkProperty());
            try {
                DisplayTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            int sale = 0;
            int order = 0;
            String prodMod;


            try {
                ArrayList<Integer> produkModList = new ArrayList<>();
                ArrayList<Integer> pelanggan = new ArrayList<>();
                ArrayList<Order> orderList = orderRepository.GetMonthly();
                ArrayList<Produk> produkList = prorep.GetProduk();
                ArrayList<Pelanggan> pelList = pelrep.GetPelanggan();
                order = orderList.size();
                for (Pelanggan pel : pelList) {
                    int count = 0;

                    for (Order temp : orderList) {
                        if (temp.getPelanggan().equalsIgnoreCase(pel.getFname())) {
                            count++;
                        }
                    }
                    pelanggan.add(count);
                }

                int obj1;
                if (pelList.size() == 0) {
                    custModus.setText("-");
                }
                else {
                    obj1 = Collections.max(pelanggan);
                    int index1 = pelanggan.indexOf(obj1);
                    custModus.setText(pelList.get(index1).getFname());
                }


                for (Produk pr : produkList) {
                    int count = 0;

                    for (Order temp : orderList) {
                        if (temp.getProduk().equalsIgnoreCase(pr.getName())) {
                            count++;
                        }
                    }
                    produkModList.add(count);
                }
                for (int i = 0; i < produkList.size(); i++) {
                    sale += (produkList.get(i).getPrice() * produkModList.get(i));
                }

                int obj;
                if (produkModList.size() == 0) {
                    produkModus.setText("-");
                    totalOrder.setText("0");
                    sales.setText("-");
                }
                else {
                    obj = Collections.max(produkModList);
                    int index = produkModList.indexOf(obj);

                    produkModus.setText(produkList.get(index).getName());
                    totalOrder.setText(String.valueOf(order));
                    sales.setText(String.valueOf(sale));
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void onAccClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
