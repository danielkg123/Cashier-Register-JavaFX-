package com.example.proyekpbo;

import Repository.*;
import entity.*;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderController implements Initializable {
    @FXML
    private MenuBar menu;

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
    private ComboBox<String> PelangganBox;

    @FXML
    private ComboBox<String> ProductBox;

    @FXML
    private TableView<OrderProperty> TabelOrder;

    @FXML
    private Label notice;

    @FXML
    private TextField quantity;
    @FXML
    private Button clear;
    @FXML
    private Button add;
    private OrderRepository orderRepository = new OrderRepository();
    private ProdukRepository prorep = new ProdukRepository();
    private PelangganRepository pelrep = new PelangganRepository();
    private StaffRepository staffrep = new StaffRepository();

    private ObservableList<OrderProperty> orderList = FXCollections.observableArrayList();
    DataSingleton data = DataSingleton.getInstance();
    static int count = 1;

    @FXML
    protected void onAddClick() throws SQLException {
        try {
            String staffName = null;
            ArrayList<Staff> staffList = staffrep.GetStaff();
            for (Staff st : staffList) {
                if (st.getUser().equalsIgnoreCase(data.getLoginDetail())) {
                    staffName = st.getFname();
                }
            }

            if (orderList.size() == 0) {
                System.out.println(orderList.size());
                count = 1;
            } else {
                count = orderList.size() + 1;
            }

            int id = count;
            String pelName = PelangganBox.getValue();
            String produk = ProductBox.getValue();
            String date = LocalDate.now().toString();
            int jumlah = Integer.parseInt(quantity.getText());
            while (jumlah > 0) {
                orderRepository.InsertOrder(new Order(id, pelName, produk, staffName, date));
                id += 1;
                jumlah -= 1;
            }
            PelangganBox.setValue(null);
            ProductBox.setValue(null);
            quantity.setText(null);
            clear.setVisible(true);
            DisplayTable();
        }
        catch (NumberFormatException e) {
            notice.setText("INVALID");
            notice.setTextFill(Color.RED);
        }
    }

    @FXML
    protected void onClearClick() throws SQLException {
        try {
            OrderProperty order = TabelOrder.getSelectionModel().getSelectedItem();
            int id = Integer.parseInt(order.getId());
            int count = TabelOrder.getItems().size();
            boolean update = orderRepository.RemoveOrder(id);
            for (int i = id; i < count; i++) {
                orderRepository.EditOrder(i, i + 1);
            }

            if (orderList.size() == 1) {
                clear.setVisible(false);
            }

            DisplayTable();
        }
        catch (RuntimeException e) {
            notice.setText("No Selection");
            notice.setTextFill(Color.RED);
        }
    }


    @FXML
    protected void OnLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
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
        try {
            orderRepository.updateOrder();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (orderRepository.GetOrder().size() == 0) {
                this.clear.setVisible(false);
            }
            else {
                this.clear.setVisible(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(data.getLoginDetail());
        ColumnId.setCellValueFactory(f -> f.getValue().idProperty());
        ColumnCus.setCellValueFactory(f -> f.getValue().pelangganProperty());
        ColumnDate.setCellValueFactory(f -> f.getValue().dateProperty());
        ColumnStaff.setCellValueFactory(f -> f.getValue().staffProperty());
        ColumnProduk.setCellValueFactory(f -> f.getValue().produkProperty());
        try {
            ArrayList<Produk> produk = prorep.GetProduk();
            ArrayList<String> produkName = new ArrayList<>();
            for (Produk temp : produk) {
                produkName.add(temp.getName());
            }


            ArrayList<Pelanggan> pelanggan = pelrep.GetPelanggan();
            ArrayList<String> pelangganName = new ArrayList<>();
            for (Pelanggan temp : pelanggan) {
                pelangganName.add(temp.getFname());
            }
            ObservableList<String> products =
                    FXCollections.observableArrayList(
                            produkName
                    );

            ObservableList<String> cust =
                    FXCollections.observableArrayList(
                            pelangganName
                    );
            ProductBox.setItems(products);
            PelangganBox.setItems(cust);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            DisplayTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void OnMenu() throws IOException {
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

    public void OnStruk(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
