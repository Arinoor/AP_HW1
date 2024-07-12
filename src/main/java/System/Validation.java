package System;

import Model.*;
import Exception.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Validation {

    private Validation() {

    }

    public static void validateStudentId(String studentId) throws ValidationException {
        // student id should be a 9-digit number and does not start with 0
        if(!studentId.matches("\\d{9}")) {
            throw new ValidationException("Student id should be a 9-digit number");
        }
        if(studentId.charAt(0) == '0') {
            throw new ValidationException("Student id should not start with 0");
        }
    }

    public static void validateUsername(String username) throws ValidationException {
        // username should be at least 5 and at most 50 characters
        // It can consist only of small and capital English letters and numbers and underscore
        // It should not start with a number
        if(username.length() < 5 || username.length() > 50) {
            throw new ValidationException("Username should be at least 5 and at most 50 characters");
        }
        if(!username.matches("[a-zA-Z0-9_]+")) {
            throw new ValidationException("Username can consist only of small and capital English letters and numbers and underscore");
        }
        if(username.matches("[0-9].*")) {
            throw new ValidationException("Username should not start with a number");
        }
    }

    public static void validatePassword(String password) throws ValidationException {
        // password should be at least 8 and at most 50 characters
        // It can consist only of small and capital English letters and numbers and underscore
        // It should contain at least one small letter, one capital letter, and one number
        if(password.length() < 8 || password.length() > 50) {
            throw new ValidationException("Password should be at least 8 and at most 50 characters");
        }
        if(!password.matches(".*[a-z].*")) {
            throw new ValidationException("Password should contain at least one small letter");
        }
        if(!password.matches(".*[A-Z].*")) {
            throw new ValidationException("Password should contain at least one capital letter");
        }
        if(!password.matches(".*\\d.*")) {
            throw new ValidationException("Password should contain at least one number");
        }
        if(!password.matches("[a-zA-Z0-9_]+")) {
            throw new ValidationException("Password can consist only of small and capital English letters and numbers and underscore");
        }
    }

    public static void validateCourseId(String courseId) throws ValidationException {
        // course id should be a positive integer between 1 and 1000
        if(!courseId.matches("\\d+") || Integer.parseInt(courseId) <= 0 || Integer.parseInt(courseId) > 1000) {
            throw new ValidationException("Course id should be a positive integer between 1 and 1000");
        }
    }

    public static void validateDepartmentId(String departmentId) throws SQLException, ValidationException {
        // department id should be a positive integer and should be found in the database
        if(!departmentId.matches("\\d+") || Integer.parseInt(departmentId) <= 0) {
            throw new ValidationException("Department id should be a positive integer");
        }
        int id = Integer.parseInt(departmentId);
        boolean exist = DatabaseManager.isExist(id, "department_id", "department");
        if(!exist) {
            throw new ValidationException(String.format("Department with id %d not found", id));
        }
    }

    public static ArrayList<Integer> getValidatedRegisterCourseIds(String[] registerCourseIds, ArrayList<Course> availableCourses) throws ValidationException {
        // Ids should be a list of integers
        // Ids should be in the availableCourses list
        ArrayList<Integer> validatedRegisterCourseIds = new ArrayList<>();
        for(String Id: registerCourseIds) {
            if(Id.isBlank())
                continue;
            validateCourseId(Id);
            int courseId = Integer.parseInt(Id);
            if(availableCourses.stream().noneMatch(course -> course.getCourseId() == courseId)) {
                throw new ValidationException(String.format("Course %d is not available", courseId));
            }
            if(!validatedRegisterCourseIds.contains(courseId)) {
                validatedRegisterCourseIds.add(courseId);
            }
        }
        return validatedRegisterCourseIds;
    }

    public static ArrayList<Integer> getValidatedDropCourseIds(String[] dropCourseIds, ArrayList<Course> registeredCourses) throws ValidationException {
        // Ids should be a list of integers
        // Ids should be in the registeredCourses list
        ArrayList<Integer> validatedDropCourseIds = new ArrayList<>();
        for(String Id: dropCourseIds) {
            if(Id.isBlank())
                continue;
            validateCourseId(Id);
            int courseId = Integer.parseInt(Id);
            if(registeredCourses.stream().noneMatch(course -> course.getCourseId() == courseId)) {
                throw new ValidationException(String.format("Course %d is not registered", courseId));
            }
            if(!validatedDropCourseIds.contains(courseId)) {
                validatedDropCourseIds.add(courseId);
            }
        }
        return validatedDropCourseIds;
    }
}
