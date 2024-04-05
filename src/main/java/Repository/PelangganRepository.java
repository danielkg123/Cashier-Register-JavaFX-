package Repository;

import com.example.proyekpbo.Connect;
import entity.Pelanggan;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PelangganRepository {
    private Connection conn;
    public PelangganRepository(){
        conn = Connect.GetConnection();
    }

    public boolean InsertPelanggan(Pelanggan entity) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("INSERT INTO pelanggan(id,first_name,last_name,phone_number,email) VALUES ('%d','%s','%s','%d','%s')",
                entity.getId(),
                entity.getFname(),
                entity.getLname(),
                entity.getNumber(),
                entity.getEmail());
        return (stmt.executeUpdate(sql) > 0);
    }

    public boolean UpdatePelanggan(Pelanggan entity, int id) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("UPDATE pelanggan SET id = '%d', first_name = '%s', last_name = '%s', phone_number = '%d', email = '%s' WHERE id = '%d'",
                entity.getId(),
                entity.getFname(),
                entity.getLname(),
                entity.getNumber(),
                entity.getEmail(),
                id);
        System.out.println(sql);
        return (stmt.executeUpdate(sql) > 0);
    }

    public void updateIdPelanggan() throws SQLException {
        Statement stmt = conn.createStatement();
        int count = 0;
        String sql = ("SELECT * FROM `pelanggan` ORDER BY id;");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            count++;
            EditPelanggan(count,rs.getInt("id"));
        }
        System.out.println(count);
    }

    public boolean RemovePelanggan(int id) throws SQLException{
        Statement stmt = conn.createStatement();
        String sql = String.format("DELETE FROM pelanggan WHERE id = %d", id);
        return (stmt.executeUpdate(sql) > 0);
    }

    public ArrayList<Pelanggan> GetPelanggan() throws SQLException {
        ArrayList<Pelanggan> result = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = ("SELECT * FROM pelanggan");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            result.add(
                    new Pelanggan(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("phone_number"),
                            rs.getString("email")
                    )
            );
        }
        return result;
    }

    public boolean EditPelanggan(int id, int count) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("UPDATE pelanggan SET id = %d WHERE id = %d", id, count);
        return (stmt.executeUpdate(sql) > 0);
    }
}
