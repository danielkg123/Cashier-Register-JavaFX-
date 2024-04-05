package Repository;

import com.example.proyekpbo.Connect;
import entity.Pelanggan;
import entity.Produk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProdukRepository {
    private Connection conn;

    public ProdukRepository(){
        conn = Connect.GetConnection();
    }

    public boolean InsertProduk(Produk entity) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("INSERT INTO produk(id,name,price,category) VALUES ('%d','%s','%s','%s')",
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getCategory());
        return (stmt.executeUpdate(sql) > 0);
    }

    public boolean UpdateProduk(Produk entity, int id) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("UPDATE produk SET id = '%d', name = '%s', price = '%d', category = '%s'  WHERE id = '%d'",
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getCategory(),
                id);
        System.out.println(sql);
        return (stmt.executeUpdate(sql) > 0);
    }

    public boolean RemoveProduk(int id) throws SQLException{
        Statement stmt = conn.createStatement();
        String sql = String.format("DELETE FROM produk WHERE id = %d", id);
        return (stmt.executeUpdate(sql) > 0);
    }

    public ArrayList<Produk> GetProduk() throws SQLException {
        ArrayList<Produk> result = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = ("SELECT * FROM produk");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            result.add(
                    new Produk(
                            rs.getInt("id"),
                            rs.getInt("price"),
                            rs.getString("name"),
                            rs.getString("category")
                    )
            );
        }
        return result;
    }

    public void updateIdProduk() throws SQLException {
        Statement stmt = conn.createStatement();
        int count = 0;
        String sql = ("SELECT * FROM `produk` ORDER BY id;");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            count++;
            EditProduk(count,rs.getInt("id"));
        }
        System.out.println(count);
    }

    public boolean EditProduk(int id, int count) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("UPDATE produk SET id = %d WHERE id = %d", id, count);
        return (stmt.executeUpdate(sql) > 0);
    }
}
