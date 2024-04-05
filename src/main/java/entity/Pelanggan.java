package entity;

import Repository.PelangganRepository;

public class Pelanggan extends Person {

    private int number;
    private String email;

    public Pelanggan(int id, String fname, String lname, int number) {
        super(id, fname, lname);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public Pelanggan(int id, String fname, String lname, int number, String email) {
        super(id, fname, lname);
        this.number = number;
        this.email = email;
    }

    public void print(){
        System.out.println(id);
        System.out.println(fname);
        System.out.println(lname);
        System.out.println(number);
        System.out.println(email);
    }
}