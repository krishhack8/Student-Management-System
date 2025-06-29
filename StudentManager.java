import java.sql.*;
import java.util.Scanner;

public class StudentManager {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB"; // your DB name
    private static final String USER = "root";
    private static final String PASS = "Krish@sq4"; // replace with your password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to MySQL");

            while (true) {
                System.out.println("\n1. Add Student\n2. View Students\n3. Exit\nChoose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        String insertQuery = "INSERT INTO students (name, email, course) VALUES (?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                        pstmt.setString(1, name);
                        pstmt.setString(2, email);
                        pstmt.setString(3, course);
                        pstmt.executeUpdate();
                        System.out.println("Student added successfully.");
                        break;

                    case 2:
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM students");

                        System.out.println("\nID | Name | Email | Course");
                        while (rs.next()) {
                            System.out.println(rs.getInt("id") + " | " +
                                    rs.getString("name") + " | " +
                                    rs.getString("email") + " | " +
                                    rs.getString("course"));
                        }
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        conn.close();
                        return;

                    default:
                        System.out.println("Invalid Option. Try again.");
                }
            }

        } catch (Exception e) {
            System.out.println(" Error occurred:");
            e.printStackTrace();
        }
        sc.close();
    }
}


