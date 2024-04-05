package Repository;

import com.example.proyekpbo.Connect;
import entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderRepository {
    private Connection conn;
    private PelangganRepository pelrep = new PelangganRepository();
    private ProdukRepository prorep = new ProdukRepository();
    private StaffRepository staffrep = new StaffRepository();

    public OrderRepository() {
        conn = Connect.GetConnection();
    }

    public boolean RemoveOrder(int id) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("DELETE FROM `order` WHERE id = '%d'", id);
        return (stmt.executeUpdate(sql) > 0);
    }


    public boolean InsertOrder(Order entity) throws SQLException {
        ArrayList<Pelanggan> pellist = pelrep.GetPelanggan();
        ArrayList<Produk> prolist = prorep.GetProduk();
        ArrayList<Staff> staffList = staffrep.GetStaff();
        int pelId = 0;
        int proId = 0;
        int staffId = 0;

        for (Pelanggan temp : pellist) {
            if (entity.getPelanggan().equals(temp.getFname())) {
                pelId = temp.getId();
            }
        }
        for (Produk temp : prolist) {
            if (entity.getProduk().equals(temp.getName())) {
                proId = temp.getId();
            }
        }
        for (Staff temp : staffList) {
            if (entity.getStaff().equals(temp.getFname())) {
                staffId = temp.getId();
            }
        }
        Statement stmt = conn.createStatement();
        String sql = String.format("INSERT INTO `order`(id,pelanggan_id,product_id,staff_id,order_date) VALUES ('%d','%s','%s','%s','%s')",
                entity.getId(),
                pelId,
                proId,
                staffId,
                entity.getDate());
        return (stmt.executeUpdate(sql) > 0);
    }

    public ArrayList<Order> GetOrderByPerson(String name, String date) throws SQLException {
        ArrayList<Pelanggan> pellist = pelrep.GetPelanggan();
        ArrayList<Produk> prolist = prorep.GetProduk();
        ArrayList<Staff> staffList = staffrep.GetStaff();
        ArrayList<Order> result = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = String.format("SELECT * FROM `order` WHERE pelanggan_id = (SELECT pelanggan_id FROM pelanggan WHERE first_name = '%s') AND order_date = '%s';", name, date);
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println(sql);
        while (rs.next()) {

            String pelName = null, produkName = null, staffName = null;
            for (Pelanggan temp : pellist) {
                if (rs.getInt("pelanggan_id") == temp.getId()) {
                    pelName = temp.getFname();

                }
            }
            for (Produk temp : prolist) {
                if (rs.getInt("product_id") == temp.getId()) {
                    produkName = temp.getName();
                }
            }
            for (Staff temp : staffList) {
                if (rs.getInt("staff_id") == temp.getId()) {
                    staffName = temp.getFname();
                }
            }
            result.add(
                    new Order(
                            rs.getInt("id"),
                            pelName,
                            produkName,
                            staffName,
                            rs.getString("order_date")
                    )
            );
        }
        return result;
    }

    public ArrayList<Order> GetMonthly() throws SQLException {
        ArrayList<Pelanggan> pellist = pelrep.GetPelanggan();
        ArrayList<Produk> prolist = prorep.GetProduk();
        ArrayList<Staff> staffList = staffrep.GetStaff();
        ArrayList<Order> result = new ArrayList<>();
        Statement stmt = conn.createStatement();
        int month = LocalDate.now().getMonthValue();
        String sql = String.format("SELECT * FROM `order` WHERE MONTH(order_date) = '%d' ORDER BY id;", month);
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String pelName = null, produkName = null, staffName = null;
            for (Pelanggan temp : pellist) {
                if (rs.getInt("pelanggan_id") == temp.getId()) {
                    pelName = temp.getFname();
                }
            }
            for (Produk temp : prolist) {
                if (rs.getInt("product_id") == temp.getId()) {
                    produkName = temp.getName();
                }
            }
            for (Staff temp : staffList) {
                if (rs.getInt("staff_id") == temp.getId()) {
                    staffName = temp.getFname();
                }
            }

            result.add(
                    new Order(
                            rs.getInt("id"),
                            pelName,
                            produkName,
                            staffName,
                            rs.getString("order_date")
                    )
            );
        }
        return result;
    }

    public ArrayList<Order> GetOrder() throws SQLException {
        ArrayList<Pelanggan> pellist = pelrep.GetPelanggan();
        ArrayList<Produk> prolist = prorep.GetProduk();
        ArrayList<Staff> staffList = staffrep.GetStaff();
        ArrayList<Order> result = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = ("SELECT * FROM `order` ORDER BY id;");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String pelName = null, produkName = null, staffName = null;
            for (Pelanggan temp : pellist) {
                if (rs.getInt("pelanggan_id") == temp.getId()) {
                    pelName = temp.getFname();
                }
            }
            for (Produk temp : prolist) {
                if (rs.getInt("product_id") == temp.getId()) {
                    produkName = temp.getName();
                }
            }
            for (Staff temp : staffList) {
                if (rs.getInt("staff_id") == temp.getId()) {
                    staffName = temp.getFname();
                }
            }

            result.add(
                    new Order(
                            rs.getInt("id"),
                            pelName,
                            produkName,
                            staffName,
                            rs.getString("order_date")
                    )
            );
        }
        return result;
    }


    public void updateOrder() throws SQLException {
        Statement stmt = conn.createStatement();
        int count = 0;
        String sql = ("SELECT * FROM `order` ORDER BY id;");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            count++;
            EditOrder(count,rs.getInt("id"));
        }
        System.out.println(count);
    }

    public boolean EditOrder(int id, int count) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("UPDATE `order` SET id = %d WHERE id = %d", id, count);
        System.out.print(sql);
        return (stmt.executeUpdate(sql) > 0);
    }
}

