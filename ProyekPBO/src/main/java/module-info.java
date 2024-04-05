module com.example.proyekpbo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.proyekpbo to javafx.fxml;
    exports com.example.proyekpbo;
}