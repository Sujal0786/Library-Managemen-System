import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            while (rs.next()) {
                System.out.println("Username: " + rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
    }
}
