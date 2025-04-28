import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

            bookTitles.clear();
            System.out.println("\n--- Books Available ---");
            while (rs.next()) {
                String title = rs.getString("title");
                bookTitles.add(title);
                System.out.printf("%d | %s | %s | %.2f | Qty: %d\n",
                        rs.getInt("book_id"), title,
                        rs.getString("author"), rs.getDouble("price"),
                        rs.getInt("quantity"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error fetching books: " + e.getMessage());
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
