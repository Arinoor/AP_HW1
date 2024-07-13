package System;

import Model.*;
import Exception.*;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

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
        boolean exist = DatabaseManager.isExist(id, "department_id", "departments");
        if(!exist) {
            throw new ValidationException(String.format("Department with id %d not found", id));
        }
    }

    public static void validateCapacity(String capacity) throws ValidationException {
        // capacity should be a positive integer
        if(!capacity.matches("\\d+") || Integer.parseInt(capacity) <= 0) {
            throw new ValidationException("Capacity should be a positive integer");
        }
    }

    public static ArrayList<Integer> getValidatedCourseIds(String[] courseIds, ArrayList<Course> courses, String validationMessage) throws ValidationException {
        // Ids should be a list of integers
        // Ids should be in the registeredCourses list
        ArrayList<Integer> validatedcourseIds = new ArrayList<>();
        for(String Id: courseIds) {
            if(Id.isBlank())
                continue;
            validateCourseId(Id);
            int courseId = Integer.parseInt(Id);
            if(courses.stream().noneMatch(course -> course.getCourseId() == courseId)) {
                throw new ValidationException(String.format("Course %d %s", courseId, validationMessage));
            }
            if(!validatedcourseIds.contains(courseId)) {
                validatedcourseIds.add(courseId);
            }
        }
        return validatedcourseIds;
    }

    public static LocalTime validateClassTime(String time) throws ValidationException {
        // should be in hh:mm format
        // should be a valid time

        if(!time.matches("\\d{2}:\\d{2}")) {
            throw new ValidationException("Class time should be in hh:mm format");
        }

        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));

        validateHour(hour);
        validateMinute(minute);

        return LocalTime.of(hour, minute);

    }

    public static Date validateExamStartTime(String input) throws ValidationException {
        // should be in yyyy-MM-dd hh:mm format
        // should be a valid date
        // should be after the current date
        if(!input.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
            throw new ValidationException("Exam start time should be in yyyy-MM-dd hh:mm format");
        }

        int year = Integer.parseInt(input.substring(0, 4));
        int month = Integer.parseInt(input.substring(5, 7));
        int day = Integer.parseInt(input.substring(8, 10));
        int hour = Integer.parseInt(input.substring(11, 13));
        int minute = Integer.parseInt(input.substring(14, 16));

        validateYear(year);
        validateMonth(month);
        validateDay(day);
        validateHour(hour);
        validateMinute(minute);

        return new Date(year - 1900, month - 1, day, hour, minute);
    }

    private static void validateMinute(int minute) throws ValidationException {
        // should be a valid minute and should be divisible by 5
        if(minute < 0 || minute > 59 || minute % 5 != 0) {
            throw new ValidationException("Invalid minute");
        }
    }

    private static void validateHour(int hour) throws ValidationException {
        // should be a valid hour
        if(hour < 0 || hour > 23) {
            throw new ValidationException("Invalid hour");
        }
    }

    private static void validateMonth(int month) throws ValidationException {
        // should be a valid month
        if(month < 1 || month > 12) {
            throw new ValidationException("Invalid month");
        }
    }

    private static void validateYear(int year) throws ValidationException {
        // should be a valid year which is 2025
        if(year != 2025) {
            throw new ValidationException("Invalid year");
        }
    }

    private static void validateDay(int day) throws ValidationException {
        // should be a valid day
        if(day < 1 || day > 31) {
            throw new ValidationException("Invalid day");
        }
    }

    public static CourseType validateCourseType(String courseType) throws ValidationException {
        // should be G or S
        // starts with G -> General / starts with S -> Specialized
        courseType = courseType.toLowerCase();
        if(courseType.startsWith("g")) {
            return CourseType.GENERAL;
        } else if(courseType.startsWith("s")) {
            return CourseType.SPECIALIZED;
        } else {
            throw new ValidationException("Invalid course type");
        }
    }

    public static Day validateDay(String day) throws ValidationException {
        // day should be a valid day
        // Or be first 2 letters of a valid day
        day = day.toLowerCase();
        for(Day validDay : Day.values()) {
            String validDayString = validDay.toString().toLowerCase();
            if(validDayString.equals(day) || validDayString.startsWith(day)) {
                return validDay;
            }
        }
        throw new ValidationException("Invalid day");
    }

    public static void validateClassTime(ClassTime classTime) throws ValidationException {
        // start time should be before end time
        if(classTime.getStartTime().isAfter(classTime.getEndTime())) {
            throw new ValidationException("Start time should be before end time");
        }
    }

    public static void validateNumberOfClassTimes(String numberOfClassTimes) throws ValidationException {
        // number of class times should be a positive integer between 1 and 3
        if(!numberOfClassTimes.matches("\\d+") || Integer.parseInt(numberOfClassTimes) < 1 || Integer.parseInt(numberOfClassTimes) > 7) {
            throw new ValidationException("Number of class times should be a positive integer between 1 and 3");
        }
    }

    public static void validateCredits(String credits) throws ValidationException {
        // credits should be a positive integer between 1 and 4
        if(!credits.matches("\\d+") || Integer.parseInt(credits) < 1 || Integer.parseInt(credits) > 4) {
            throw new ValidationException("Credits should be a positive integer between 1 and 4");
        }
    }

    public static void validateInstructorName(String instructorName) throws ValidationException {
        // should consist of only English letters and spaces
        if(!instructorName.matches("[a-zA-Z ]+")) {
            throw new ValidationException("Instructor name should consist of only English letters and spaces");
        }
    }

    public static void validateNewInstructorName(String instructorName, ArrayList<ClassTime> classTimes, Date examStartTime) throws ValidationException, SQLException {
        // should be a valid instructor name
        // this instructor should not have another course which its class time is overlapping with the new class times
        // this instructor should not have another course which its exam time is overlapping with the new exam time
        validateInstructorName(instructorName);
        ArrayList<ClassTime> previousClassTimes = DatabaseManager.getClassTimesOfInstructor(instructorName);
        for(ClassTime previoiusClassTime : previousClassTimes) {
            for(ClassTime classTime : classTimes) {
                if(previoiusClassTime.isOverlapping(classTime)) {
                    throw new ValidationException(String.format("Instructor %s has another course with overlapping class time", instructorName));
                }
            }
        }
    }

    public static void validateCourseName(String courseName) throws ValidationException {
        // should consist of only English letters and spaces
        if(!courseName.matches("[a-zA-Z ]+")) {
            throw new ValidationException("Course name should consist of only English letters and spaces");
        }
    }

    public static void validateNewCourseName(String courseName, int departmentID) throws ValidationException, SQLException, DatabaseException {
        // should be a valid course name
        // should not be an already used course name in the department
        validateCourseName(courseName);
        ArrayList<Integer> courseIds = DatabaseManager.getCourseIds(departmentID);
        ArrayList<Course> courses = Server.getCoursesByIds(courseIds);
        if(courses.stream().anyMatch(course -> course.getCourseName().equals(courseName))) {
            throw new ValidationException(String.format("Course with name %s already exists in the department", courseName));
        }
    }

    public static void validateNewCourseId(String courseId) throws ValidationException, SQLException {
        // should be a valid and not already used course id
        validateCourseId(courseId);
        int id = Integer.parseInt(courseId);
        boolean exist = DatabaseManager.isExist(id, "course_id", "courses");
        if(exist) {
            throw new ValidationException(String.format("Course with id %d already exists", id));
        }
    }
}
