package Repository;

import com.example.proyekpbo.Connect;
import entity.Pelanggan;
import entity.Staff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StaffRepository {
    private Connection conn;

    public StaffRepository() {
        conn = Connect.GetConnection();
    }

    public boolean InsertStaff(Staff entity) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("INSERT INTO staff(id,first_name,last_name,phone_number,user,pass) VALUES ('%d','%s','%s','%d','%s','%s')",
                entity.getId(),
                entity.getFname(),
                entity.getLname(),
                entity.getNumber(),
                entity.getUser(),
                entity.getPass());
        return (stmt.executeUpdate(sql) > 0);
    }

    public ArrayList<Staff> GetStaff() throws SQLException {
        ArrayList<Staff> result = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = ("SELECT * FROM staff");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            result.add(
                    new Staff(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("phone_number"),
                            rs.getString("user"),
                            rs.getString("pass")
                    )
            );
        }

        return result;
    }

    public boolean RemoveStaff(int id) throws SQLException{
        Statement stmt = conn.createStatement();
        String sql = String.format("DELETE FROM staff WHERE id = %d", id);
        return (stmt.executeUpdate(sql) > 0);
    }

    public void updateIdStaff() throws SQLException {
        Statement stmt = conn.createStatement();
        int count = 0;
        String sql = ("SELECT * FROM `staff` ORDER BY id;");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            count++;
            EditStaff(count,rs.getInt("id"));
        }
        System.out.println(count);
    }

    public boolean EditStaff(int id, int count) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("UPDATE staff SET id = %d WHERE id = %d", id, count);
        return (stmt.executeUpdate(sql) > 0);
    }

    public boolean EditAll(Staff entity) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("UPDATE staff SET first_name = '%s', last_name = '%s', phone_number = '%d' WHERE id = %d",entity.getFname(),entity.getLname(),entity.getNumber(),entity.getId());
        return (stmt.executeUpdate(sql) > 0);
    }

    public boolean EditAcc(Staff entity) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("UPDATE staff SET user = '%s', pass = '%s' WHERE id = %d",entity.getUser(),entity.getPass(),entity.getId());
        return (stmt.executeUpdate(sql) > 0);
    }
}
