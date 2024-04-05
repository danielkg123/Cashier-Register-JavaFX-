package entity;

import Repository.PelangganRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderProperty {
    private final StringProperty id;
    private final StringProperty pelanggan;
    private final StringProperty produk;
    private final StringProperty staff;
    private final StringProperty date;


    public OrderProperty() {
        this.id = new SimpleStringProperty(this,"id");
        this.pelanggan = new SimpleStringProperty(this,"pelanggan");
        this.produk = new SimpleStringProperty(this,"produk");
        this.staff = new SimpleStringProperty(this,"staff");
        this.date = new SimpleStringProperty(this,"date");
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getPelanggan() {
        return pelanggan.get();
    }

    public StringProperty pelangganProperty() {
        return pelanggan;
    }

    public void setPelanggan(String pelanggan) {
        this.pelanggan.set(pelanggan);
    }

    public String getProduk() {
        return produk.get();
    }

    public StringProperty produkProperty() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk.set(produk);
    }

    public String getStaff() {
        return staff.get();
    }

    public StringProperty staffProperty() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff.set(staff);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
