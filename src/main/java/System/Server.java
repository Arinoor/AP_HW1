package System;

import Model.*;
import Exception.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Server {

    private Server() {

    }

    public static void registerCourses(int studentId, ArrayList<Integer> toRegisterCourseIds) throws ValidationException, SQLException, DataBaseException {
        ArrayList<Course> toRegisterCourses = getCoursesByIds(toRegisterCourseIds);
        ArrayList<Course> registeredCourses = getRegisteredCourses(studentId);
        for(Course course : toRegisterCourses) {
            registerCourse(studentId, course, registeredCourses);
        }
        for(int courseId : toRegisterCourseIds) {
            DatabaseManager.registerCourse(studentId, courseId);
        }
    }

    private static void registerCourse(int studentId, Course course, ArrayList<Course> registeredCourses) throws ValidationException {
        if(registeredCourses.contains(course)) {
            throw new ValidationException(String.format("Course %d is already registered by student %d", course.getCourseId(), studentId));
        }
        if(course.getCapacity() == 0) {
            throw new ValidationException(String.format("Course %d is full", course.getCourseId()));
        }
        if(course.getNumberOfCredits() + getTotalCredits(registeredCourses) > 20) {
            throw new ValidationException("No student can register more than 20 credits");
        }
        if(course.getCourseType() == CourseType.General && course.getNumberOfCredits() + getTotalGeneralCredits(registeredCourses) > 5) {
            throw new ValidationException("No student can register more than 5 credits of general courses");
        }
        checkTimeConflict(course, registeredCourses);
        registeredCourses.add(course);
        course.setCapacity(course.getCapacity() - 1);
    }

    private static int getTotalGeneralCredits(ArrayList<Course> registeredCourses) {
        int totalGeneralCredits = 0;
        for(Course course : registeredCourses) {
            if(course.getCourseType() == CourseType.General) {
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

    public static void dropCourses(int studentId, ArrayList<Integer> dropCourseIds) throws ValidationException, SQLException, DataBaseException {
        for(int courseId : dropCourseIds) {
            dropCourse(studentId, courseId);
        }
        for(int courseId : dropCourseIds) {
            DatabaseManager.dropCourse(studentId, courseId);
        }
    }

    private static void dropCourse(int studentId, int courseId) throws ValidationException, SQLException, DataBaseException {
        if(!DatabaseManager.isRegistered(studentId, courseId)) {
            throw new ValidationException(String.format("Course %d is not registered by student %d", courseId, studentId));
        }
    }


    public static ArrayList<Course> getCoursesByIds(ArrayList<Integer> courseIds) throws SQLException, DataBaseException {
        ArrayList<Course> courses = new ArrayList<>();
        for(int courseId : courseIds) {
            Course course = DatabaseManager.getCourseById(courseId);
            courses.add(course);
        }
        return courses;
    }

    public static ArrayList<Course> getRegisteredCourses(int studentId) throws SQLException, DataBaseException {
        return DatabaseManager.getRegisteredCourses(studentId);
    }

    public static ArrayList<Course> getAvailableCourses(int studentId, int departmentId) throws SQLException, DataBaseException {
        return DatabaseManager.getAvailableCourses(studentId, departmentId);
    }

    public static String getDepartmentName(int departmentId) throws SQLException, DataBaseException {
        return DatabaseManager.getDepartmentName(departmentId);
    }

    public static ArrayList<Department> getDepartments() throws SQLException {
        return DatabaseManager.getDepartments();
    }

    public static void loginStudent(int studentId, String password) throws SQLException, ValidationException {
        DatabaseManager.loginStudent(studentId, password);
    }

    public static void loginAdmin(String username, String password) throws SQLException, ValidationException {
        DatabaseManager.loginAdmin(username, password);
    }

    public static void register(int studentId, String password) throws SQLException, DataBaseException, ValidationException {
        boolean isRegistered = DatabaseManager.isRegistered(studentId);
        if(isRegistered) {
            throw new ValidationException(String.format("Student %d is already registered", studentId));
        }
        DatabaseManager.register(studentId, password);
    }

    public static void main(String[] args) {

    }
}
