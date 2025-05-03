import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class User {
    private String username;
    private String password;
    

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean login() {
       
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM Admin WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }
    public void addUser(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO Admin (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("User added successfully.\n");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
    public void ShowUser() {
    try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT * FROM Admin";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

       
        JFrame frame = new JFrame("Admin Users");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Admin ID");
        model.addColumn("Username");
        model.addColumn("Password"); 

        while (rs.next()) {
            int id = rs.getInt("admin_id");           
            String username = rs.getString("username");
            String password = rs.getString("password"); 

            model.addRow(new Object[]{id, username, password}); 
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error retrieving users: " + e.getMessage());
    }
}

}
