import java.sql.*;
import java.util.ArrayList;

// This class is responsible for connecting to the database and executing queries.
// Database course_registration_db has the following tables:
// students table : student_number, password
// admin table : admin_id, username, password
// courses table : course_id, instructor_name, course_name, capacity, number_of_credits, exam_start_time, course_type,  department_id
// department table : department_id, department_name
// registrations table : student_number, course_id
// class_times table : course_id, day, start_time, end_time

public class DataBaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/course_registration_db";
    private static final String USER = "root";
    private static final String PASSWORD = "lmqsy.Arinoor_8053";

    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getCoursesRegisteredByStudent(int studentId) throws SQLException {
        String query = "SELECT course_id FROM registrations WHERE student_number = ?";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Integer> courses = new ArrayList<>();
        while (resultSet.next()) {
            courses.add(resultSet.getInt("course_id"));
        }
        close(connection);
        return courses;
    }
}
