package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProdukProperty {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty price;
    private final StringProperty category;

    public ProdukProperty() {
        this.id = new SimpleStringProperty(this,"id");
        this.name = new SimpleStringProperty(this,"name");
        this.price = new SimpleStringProperty(this,"price");
        this.category = new SimpleStringProperty(this,"category");
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }
}
