package com.example.proyekpbo;

import Repository.*;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {

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
    private Button create;

    @FXML
    private MenuBar menu;

    @FXML
    private Label notice;

    @FXML
    private DatePicker dateReceipt;

    private OrderRepository orderRepository = new OrderRepository();
    private ProdukRepository prorep = new ProdukRepository();
    private PelangganRepository pelrep = new PelangganRepository();
    private StaffRepository staffrep = new StaffRepository();

    private ObservableList<OrderProperty> orderList = FXCollections.observableArrayList();
    DataSingleton data = DataSingleton.getInstance();

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

    @FXML
    void OnBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void OnLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void OnMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMenu.fxml"));
        Stage stage = HelloApplication.getHelloApplication().getStage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onCreateClick(ActionEvent event) throws SQLException {
        ArrayList<Integer> produkBoughtList = new ArrayList<>();
        ArrayList<String> namaProdukList = new ArrayList<>();
        String name = PelangganBox.getValue();
        String date = dateReceipt.getValue().toString();
        ArrayList<Order> order = orderRepository.GetOrderByPerson(name, date);
        ArrayList<Produk> produkList = prorep.GetProduk();
        ArrayList<Produk> produk = prorep.GetProduk();
        ArrayList<Staff> staff = staffrep.GetStaff();
        String staffName = "";
        for (Staff temp : staff){
            if (temp.getUser().equalsIgnoreCase(data.getLoginDetail())){
                staffName = temp.getFname();
            }
        }
        int total = 0;
        for (Order or : order) {
            for (Produk temp : produk) {
                if (temp.getName().equals(or.getProduk())){
                    total+= temp.getPrice();
                }
            }
        }
        for (Produk pr : produkList) {
            String produkName = "";
            int count = 0;

            for (Order temp : order) {
                if (temp.getProduk().equalsIgnoreCase(pr.getName())) {
                    count++;
                    produkName = temp.getProduk();
                }
            }
            namaProdukList.add(produkName);
            produkBoughtList.add(count);
        }
        System.out.println(namaProdukList);
        System.out.println(produkBoughtList);
        System.out.println(total);
        System.out.print(order.size());
        String receipt = "";
        for(int i = 0 ;i < namaProdukList.size(); i++){
            if (!(namaProdukList.get(i).isEmpty())){
                if(namaProdukList.get(i).length() < 8){
                receipt += namaProdukList.get(i) + "\t\t\t\t" +  produkBoughtList.get(i) + "\n";
            }else receipt += namaProdukList.get(i) + "\t\t\t" +  produkBoughtList.get(i) + "\n";
            }
        }
        System.out.println(receipt);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Nota Pembayaran \n----------------------------" +
                "\nStaff : " + staffName +
                "\n----------------------------" +
                "\nPelanggan : " + PelangganBox.getValue() +
                "\n----------------------------\n" + receipt + "----------------------------"
         + "\nQuantity : " + order.size() +"\nTotal : " + total);


        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ColumnId.setCellValueFactory(f -> f.getValue().idProperty());
        ColumnCus.setCellValueFactory(f -> f.getValue().pelangganProperty());
        ColumnDate.setCellValueFactory(f -> f.getValue().dateProperty());
        ColumnStaff.setCellValueFactory(f -> f.getValue().staffProperty());
        ColumnProduk.setCellValueFactory(f -> f.getValue().produkProperty());
        try {


            ArrayList<Pelanggan> pelanggan = pelrep.GetPelanggan();
            ArrayList<String> pelangganName = new ArrayList<>();
            for (Pelanggan temp : pelanggan) {
                pelangganName.add(temp.getFname());
            }

            ObservableList<String> cust =
                    FXCollections.observableArrayList(
                            pelangganName
                    );
            PelangganBox.setItems(cust);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            DisplayTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String pattern = "yyyy-MM-dd";

        dateReceipt.setPromptText(pattern.toLowerCase());

        dateReceipt.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }
}