package System;

import java.sql.*;
import java.util.ArrayList;

import Config.Config;
import Model.*;
import Exception.*;

// This class is responsible for connecting to the database and executing queries.
// Database course_registration_db has the following tables:
// students table : student_id, password
// admin table : admin_id, username, password
// courses table : course_id, instructor_name, course_name, capacity, number_of_credits, exam_start_time, course_type,  department_id
// department table : department_id, department_name
// registrations table : student_id, course_id
// class_times table : course_id, day, start_time, end_time

public class DataBaseManager {

    private DataBaseManager() {

    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USER, Config.DATABASE_PASSWORD);
    }

    private static void close(Connection connection) throws SQLException {
        connection.close();
    }

    public static ArrayList<Course> getRegisteredCourses(int studentId) throws SQLException, DataBaseException {
        check(studentId, "student_id", "students");
        String query = "SELECT course_id FROM registrations WHERE student_id = ?";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentId);
        ResultSet courseResultSet = preparedStatement.executeQuery();
        ArrayList<Course> courses = new ArrayList<>();
        while (courseResultSet.next()) {
            courses.add(getCourse(courseResultSet));
        }
        close(connection);
        return courses;
    }

    private static ArrayList<ClassTime> getClassTimes(int courseId) throws SQLException {
        String query = "SELECT * FROM class_times WHERE course_id = ?";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, courseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<ClassTime> classTimes = new ArrayList<>();
        while (resultSet.next()) {
            classTimes.add(new ClassTime(resultSet));
        }
        close(connection);
        return classTimes;
    }

    public static boolean isRegistered(int studentId, int courseId) throws SQLException, DataBaseException {
        check(studentId, "student_id", "students");
        check(courseId, "course_id", "courses");
        String query = "SELECT EXISTS(SELECT 1 FROM registrations WHERE student_id = ? AND course_id = ?)";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentId);
        preparedStatement.setInt(2, courseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean isRegistered = resultSet.getBoolean(1);
        close(connection);
        return isRegistered;
    }

    public static Course getCourseById(int courseId) throws SQLException, DataBaseException {
        String query = "SELECT * FROM courses WHERE course_id = ?";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, courseId);
        ResultSet courseResultSet = preparedStatement.executeQuery();
        try {
            courseResultSet.next();
        } catch (SQLException e) {
            throw new DataBaseException(String.format("Course with id %d not found", courseId));
        }
        Course course = getCourse(courseResultSet);
        close(connection);
        return course;
    }

    public static ArrayList<Course> getAvailableCourses(int studentId, int departmentId) throws SQLException, DataBaseException {
        check(studentId, "student_id", "students");
        check(departmentId, "department_id", "departments");
        String query = "SELECT * FROM courses WHERE department_id = ? AND course_id NOT IN (SELECT course_id FROM registrations WHERE student_id = ?)";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, departmentId);
        preparedStatement.setInt(2, studentId);
        ResultSet courseResultSet = preparedStatement.executeQuery();
        ArrayList<Course> courses = new ArrayList<>();
        while (courseResultSet.next()) {
            courses.add(getCourse(courseResultSet));
        }
        close(connection);
        return courses;
    }

    public static String getDepartmentName(int departmentId) throws SQLException, DataBaseException {
        String query = "SELECT department_name FROM department WHERE department_id = ?";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, departmentId);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            resultSet.next();
        } catch (SQLException e) {
            throw new DataBaseException(String.format("Department with id %d not found", departmentId));
        }
        String departmentName = resultSet.getString("department_name");
        close(connection);
        return departmentName;
    }

    public static ArrayList<Department> getDepartments() throws SQLException {
        String query = "SELECT * FROM department";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Department> departments = new ArrayList<>();
        while (resultSet.next()) {
            departments.add(new Department(resultSet));
        }
        close(connection);
        return departments;
    }

    public static Course getCourse(ResultSet courseResultSet) throws SQLException {
        int courseId = courseResultSet.getInt("course_id");
        ArrayList<ClassTime> classTimes = getClassTimes(courseId);
        return new Course(courseResultSet, classTimes);
    }

    public static void check(int Id, String column, String table) throws SQLException, DataBaseException {
        String query = String.format("SELECT EXISTS(SELECT 1 FROM %s WHERE %s = ?)", table, column);
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, Id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean exists = resultSet.getBoolean(1);
        close(connection);
        if (!exists) {
            String Column = column.substring(0, 1).toUpperCase() + column.substring(1).toLowerCase();
            throw new DataBaseException(String.format("%s with id %d not found", Column, Id));
        }
    }

}
