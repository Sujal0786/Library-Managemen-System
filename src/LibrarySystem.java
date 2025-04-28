import java.util.Scanner;
import java.util.Stack;

public class LibrarySystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Stack<String> recentActions = new Stack<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Bookstore Console App!\n");

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. View Books");
            System.out.println("3. View Staff");
            System.out.println("4. Add Book");
            System.out.println("5. Remove Book");
            System.out.println("6. Add Staff");
            System.out.println("7. Remove Staff");
            System.out.println("8. Show Last Change");
            System.out.println("9. History");
            System.out.println("10. Exit");
            System.out.println("11 .New User");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    Book.viewBooks();
                    break;
                case 3:
                    Staff.viewStaff();
                    break;
                case 4:
                    addBook();
                    break;
                case 5:
                    removeBook();
                    break;
                case 6:
                    addStaff();
                    break;
                case 7:
                    removeStaff();
                    break;
                case 8:
                    showRecentActions();
                    break;
                case 9:
                    showAllRecentActions();
                    break;
                case 10:
                    System.out.println("Goodbye!");
                    return;
                case 11:
                    addUser();
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void showRecentActions() {
        if (!recentActions.isEmpty()) {
            System.out.println("\n--- Most Recent Action ---");
            String action = recentActions.peek();
            System.out.println(action);
        } else {
            System.out.println("\nNo recent actions available.");
        }
    }

    private static void showAllRecentActions() {
        if (!recentActions.isEmpty()) {
            System.out.println("\n--- Recent Actions ---");

            Stack<String> tempStack = new Stack<>();
            tempStack.addAll(recentActions);

            while (!tempStack.isEmpty()) {
                System.out.println(tempStack.pop());
            }

        } else {
            System.out.println("\nNo actions in history.");
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        if (user.login()) {
            System.out.println("Login successful!\n");
            recentActions.push("Login successful for user: " + username);
        } else {
            System.out.println("Login failed.\n");
            recentActions.push("Login failed for user: " + username);
        }
    }

    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Book.addBook(title, author, price, quantity);
        recentActions.push("Added book: " + title);
    }

    private static void removeBook() {
        System.out.print("Enter book ID to remove: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Book.removeBook(bookId);
        recentActions.push("Removed book with ID: " + bookId);
    }

    private static void addStaff() {
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        System.out.print("Enter contact: ");
        String contact = scanner.nextLine();

        Staff.addStaff(name, role, contact);
        recentActions.push("Added staff: " + name);
    }

    private static void addUser() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        user.addUser(username, password);
        recentActions.push("Added new user: " + username);
    }

    private static void removeStaff() {
        System.out.print("Enter staff ID to remove: ");
        int staffId = scanner.nextInt();
        scanner.nextLine();

        Staff.removeStaff(staffId);
        recentActions.push("Removed staff with ID: " + staffId);
    }
}