package entity;

public class Staff extends Person {
    private int number;
    private String user, pass;

    public Staff(int id, String fname, String lname, int number, String user, String pass) {
        super(id, fname, lname);
        this.number = number;
        this.user = user;
        this.pass = pass;
    }

    public Staff(int id, String fname, String lname, int number) {
        super(id, fname, lname);
        this.number = number;
    }

    public Staff(int id, String user, String pass) {
        super(id);
        this.user = user;
        this.pass = pass;
    }

    public int getNumber() {
        return number;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
