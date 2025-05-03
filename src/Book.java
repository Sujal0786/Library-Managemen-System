import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.*;
public class Book {
    private int bookId;
    private String title;
    private String author;
    private double price;
    private int quantity;

    private static ArrayList<String> bookTitles = new ArrayList<>();

    public Book(int bookId, String title, String author, double price, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public static void viewBooks() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM Books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            JFrame frame = new JFrame("Available Books");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(700, 400);

            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);

            model.addColumn("Book ID");
            model.addColumn("Title");
            model.addColumn("Author");
            model.addColumn("Price");
            model.addColumn("Quantity");

            bookTitles.clear();
            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                bookTitles.add(title);
                model.addRow(new Object[] { id, title, author, price, quantity });
            }

            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching books: " + e.getMessage());
        }
    }

    public static void addBook(String title, String author, double price, int quantity) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO Books (title, author, price, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
            System.out.println("Book added successfully.\n");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public static void removeBook(int bookId) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM Books WHERE book_id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
            System.out.println("Book removed.\n");
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }
}
