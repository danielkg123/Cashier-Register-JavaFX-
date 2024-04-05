package entity;

public class Person {
    protected int id;
    protected String fname,lname;

    public Person(int id, String fname, String lname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
    }

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }


}
