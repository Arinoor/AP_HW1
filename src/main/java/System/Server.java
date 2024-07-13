package System;

import Config.Config;
import Model.*;
import Exception.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Server {

    private Server() {

    }

    public static void loginStudent(int studentId, String password) throws SQLException, ValidationException {
        DatabaseManager.loginStudent(studentId, password);
    }

    public static void loginAdmin(String username, String password) throws ValidationException {
        DatabaseManager.loginAdmin(username, password);
    }

    public static void registerStudent(int studentId, String password) throws SQLException, DatabaseException, ValidationException {
        boolean isRegistered = DatabaseManager.isRegistered(studentId);
        if(isRegistered) {
            throw new ValidationException(String.format("Student %d is already registered", studentId));
        }
        DatabaseManager.registerStudent(studentId, password);
    }

    public static void addCourse(Course course) throws SQLException {
        DatabaseManager.addCourse(course);
    }

    public static void registerCourses(int studentId, ArrayList<Integer> registerCourseIds) throws ValidationException, SQLException, DatabaseException {
        ArrayList<Course> registerCourses = getCoursesByIds(registerCourseIds);
        ArrayList<Course> registeredCourses = getRegisteredCourses(studentId);
        for(Course course : registerCourses) {
            registerCourse(studentId, course, registeredCourses);
        }
        for(int courseId : registerCourseIds) {
            DatabaseManager.registerCourse(studentId, courseId);
        }
    }

    public static void registerCourse(int studentId, Course course, ArrayList<Course> registeredCourses) throws ValidationException {
        if(registeredCourses.contains(course)) {
            throw new ValidationException(String.format("Course %d is already registered by student %d", course.getCourseId(), studentId));
        }
        if(course.getCapacity() == 0) {
            throw new ValidationException(String.format("Course %d is full", course.getCourseId()));
        }
        if(course.getNumberOfCredits() + getTotalCredits(registeredCourses) > 20) {
            throw new ValidationException("No student can register more than 20 credits");
        }
        if(course.getCourseType() == CourseType.GENERAL && course.getNumberOfCredits() + getTotalGeneralCredits(registeredCourses) > 5) {
            throw new ValidationException("No student can register more than 5 credits of general courses");
        }
        checkTimeConflict(course, registeredCourses);
        registeredCourses.add(course);
    }

    private static int getTotalGeneralCredits(ArrayList<Course> registeredCourses) {
        int totalGeneralCredits = 0;
        for(Course course : registeredCourses) {
            if(course.getCourseType() == CourseType.GENERAL) {
                totalGeneralCredits += course.getNumberOfCredits();
            }
        }
        return totalGeneralCredits;
    }

    private static int getTotalCredits(ArrayList<Course> courses) {
        int totalCredits = 0;
        for(Course course : courses) {
            totalCredits += course.getNumberOfCredits();
        }
        return totalCredits;
    }

    private static void checkTimeConflict(Course course, ArrayList<Course> registeredCourses) throws ValidationException {
        for(Course registeredCourse : registeredCourses) {
            if(registeredCourse.equals(course)) {
                continue;
            }
            if(course.isExamTimeOverlapping(registeredCourse)) {
                throw new ValidationException(String.format("Course %d exam time is overlapping with course %d", course.getCourseId(), registeredCourse.getCourseId()));
            }
            if(course.isClassTimeOverlapping(registeredCourse)) {
                throw new ValidationException(String.format("Course %d class time is overlapping with course %d", course.getCourseId(), registeredCourse.getCourseId()));
            }
        }
    }

    public static void dropCourses(int studentId, ArrayList<Integer> dropCourseIds) throws ValidationException, SQLException, DatabaseException {
        for(int courseId : dropCourseIds) {
            dropCourse(studentId, courseId);
        }
        for(int courseId : dropCourseIds) {
            DatabaseManager.dropCourse(studentId, courseId);
        }
    }

    private static void dropCourse(int studentId, int courseId) throws ValidationException, SQLException, DatabaseException {
        DatabaseManager.check(studentId, "student_id", "students");
        DatabaseManager.check(courseId, "course_id", "courses");
        if(!DatabaseManager.isRegistered(studentId, courseId)) {
            throw new ValidationException(String.format("Course %d is not registered by student %d", courseId, studentId));
        }
    }

    public static void increaseCapacity(int courseId, int extraCapacity) throws SQLException, DatabaseException{
        DatabaseManager.check(courseId, "course_id", "courses");
        DatabaseManager.changeCourseCapacity(courseId, extraCapacity);
    }

    public static ArrayList<Course> getRegisteredCourses(int studentId) throws SQLException, DatabaseException {
        DatabaseManager.check(studentId, "student_id", "students");
        return DatabaseManager.getRegisteredCourses(studentId);
    }

    public static ArrayList<Course> getAvailableCourses(int studentId, int departmentId) throws SQLException, DatabaseException {
        DatabaseManager.check(studentId, "student_id", "students");
        DatabaseManager.check(departmentId, "department_id", "departments");
        return DatabaseManager.getAvailableCourses(studentId, departmentId);
    }

    public static ArrayList<Course> getCoursesByIds(ArrayList<Integer> courseIds) throws SQLException, DatabaseException {
        ArrayList<Course> courses = new ArrayList<>();
        for(int courseId : courseIds) {
            Course course = DatabaseManager.getCourseById(courseId);
            courses.add(course);
        }
        return courses;
    }

    public static ArrayList<Integer> getCourseIds(int departmentId) throws SQLException, DatabaseException {
        DatabaseManager.check(departmentId, "department_id", "departments");
        return DatabaseManager.getCourseIds(departmentId);
    }

    public static String getDepartmentName(int departmentId) throws SQLException, DatabaseException {
        return DatabaseManager.getDepartmentName(departmentId);
    }

    public static ArrayList<Department> getDepartments() throws SQLException {
        return DatabaseManager.getDepartments();
    }

    private static void initializeDepartments() {
        ArrayList<Department> departments = Config.departments;
        for(Department department : departments) {
            try {
                DatabaseManager.addDepartment(department);
            } catch (SQLException e) {
                System.out.println("Error occurred while adding department " + department);
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        initializeDepartments();
    }
}
