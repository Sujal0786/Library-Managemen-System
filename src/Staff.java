import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.*;
public class Staff {
    private int staffId;
    private String name;
    private String role;
    private String contact;

    private static HashMap<Integer, String> staffMap = new HashMap<>();

    public Staff(int staffId, String name, String role, String contact) {
        this.staffId = staffId;
        this.name = name;
        this.role = role;
        this.contact = contact;
    }

    public static void viewStaff() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM Staff";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Create JFrame
            JFrame frame = new JFrame("Staff Details");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);

            // Table model
            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);

            // Add column names
            model.addColumn("Staff ID");
            model.addColumn("Name");
            model.addColumn("Role");
            model.addColumn("Contact");

            // Clear map and populate data
            staffMap.clear();
            while (rs.next()) {
                int id = rs.getInt("staff_id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String contact = rs.getString("contact");

                staffMap.put(id, name);
                model.addRow(new Object[]{id, name, role, contact});
            }

            // Scroll pane and add to frame
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching staff: " + e.getMessage());
        }
    }

    public static void addStaff(String name, String role, String contact) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO Staff (name, role, contact) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, role);
            stmt.setString(3, contact);
            stmt.executeUpdate();
            System.out.println("Staff added successfully.\n");
        } catch (SQLException e) {
            System.out.println("Error adding staff: " + e.getMessage());
        }
    }

    public static void removeStaff(int staffId) {
        try (Connection conn = DBConnection.getConnection()) {
            if (staffMap.containsKey(staffId)) {
                String name = staffMap.get(staffId);
                String query = "DELETE FROM Staff WHERE staff_id=?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, staffId);
                stmt.executeUpdate();
                System.out.println("Staff removed.\n");
            } else {
                System.out.println("Staff ID not found. Try viewing staff first.\n");
            }
        } catch (SQLException e) {
            System.out.println("Error removing staff: " + e.getMessage());
        }
    }
}
