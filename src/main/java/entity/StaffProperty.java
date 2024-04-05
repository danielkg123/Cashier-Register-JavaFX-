package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StaffProperty {
    private final StringProperty id;
    private final StringProperty fName;
    private final StringProperty lName;
    private final StringProperty number;

    private final StringProperty user;
    private final StringProperty pass;

    public StaffProperty() {
        this.id = new SimpleStringProperty(this,"id");
        this.fName = new SimpleStringProperty(this,"first name");
        this.lName = new SimpleStringProperty(this,"last name");
        this.number = new SimpleStringProperty(this,"number");
        this.user = new SimpleStringProperty(this,"user");
        this.pass = new SimpleStringProperty(this,"pass");
    }


    public void setId(String id) {
        this.id.set(id);
    }

    public void setfName(String fName) {
        this.fName.set(fName);
    }

    public void setlName(String lName) {
        this.lName.set(lName);
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getfName() {
        return fName.get();
    }

    public StringProperty fNameProperty() {
        return fName;
    }

    public String getlName() {
        return lName.get();
    }

    public StringProperty lNameProperty() {
        return lName;
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public String getPass() {
        return pass.get();
    }

    public StringProperty passProperty() {
        return pass;
    }
}
