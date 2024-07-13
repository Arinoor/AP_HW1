package System;

import Model.*;
import Exception.*;
import Config.Config;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Validation {

    private Validation() {

    }

    public static void validateStudentId(String studentId) throws ValidationException {
        // student id should be a Config.studentIdLength-digit number and does not start with 0
        if(!studentId.matches("\\d{" + Config.STUDENT_ID_LENGTH + "}")) {
            throw new ValidationException(String.format("Student id should be a %d-digit number", Config.STUDENT_ID_LENGTH));
        }
        if(studentId.charAt(0) == '0') {
            throw new ValidationException("Student id should not start with 0");
        }
    }

    public static void validateUsername(String username) throws ValidationException {
        // username should be at least Config.usernameMinLength and at most Config.usernameMaxLength characters
        // It can consist only of small and capital English letters and numbers and underscore
        // It should not start with a number
        if(username.length() < Config.USERNAME_MIN_LENGTH || username.length() > Config.USERNAME_MAX_LENGTH) {
            throw new ValidationException(String.format("Username should be at least %d and at most %d characters", Config.USERNAME_MIN_LENGTH, Config.USERNAME_MAX_LENGTH));
        }
        if(!username.matches("[a-zA-Z0-9_]+")) {
            throw new ValidationException("Username can consist only of small and capital English letters and numbers and underscore");
        }
        if(username.matches("[0-9].*")) {
            throw new ValidationException("Username should not start with a number");
        }
    }

    public static void validatePassword(String password) throws ValidationException {
        // password should be at least Config.passwordMinLength and at most Config.passwordMaxLength characters
        // It can consist only of small and capital English letters and numbers and underscore
        // It should contain at least one small letter, one capital letter, and one number
        if(password.length() < Config.PASSWORD_MIN_LENGTH || password.length() > Config.PASSWORD_MAX_LENGTH) {
            throw new ValidationException(String.format("Password should be at least %d and at most %d characters", Config.PASSWORD_MIN_LENGTH, Config.PASSWORD_MAX_LENGTH));
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
        // course id should be a positive integer between Config.courseMinId and Config.courseMaxId
        if(!courseId.matches("\\d+") || Integer.parseInt(courseId) < Config.COURSE_MIN_ID || Integer.parseInt(courseId) > Config.COURSE_MAX_ID) {
            throw new ValidationException(String.format("Course id should be a positive integer between %d and %d", Config.COURSE_MIN_ID, Config.COURSE_MAX_ID));
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

    public static CourseType validateCourseType(String courseType) throws ValidationException {
        // should be a prefix of general or specialized regardless of case
        CourseType type = CourseType.getCourseTypeByStart(courseType);
        if(type == null) {
            throw new ValidationException("Invalid course type");
        }
        return type;
    }

    public static Day validateDay(String day) throws ValidationException {
        // should be at least of size 2
        // should be a prefix of a valid day regardless of case
        if(day.length() < 2) {
            throw new ValidationException("Please enter at least two first letters of your day");
        }
        Day validatedDay = Day.getDayByStart(day);
        if(validatedDay == null) {
            throw new ValidationException("Invalid day");
        }
        return validatedDay;
    }

    public static LocalDateTime validateExamStartTime(String input) throws ValidationException {
        // should be in yyyy-mm-dd hh:mm format
        // should be a valid date
        // should be at least 3 months later than the current date
        // the minute should be divisible by 15

        if(!input.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
            throw new ValidationException("Exam start time should be in yyyy-mm-dd hh:mm format");
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

        LocalDateTime examStartTime = LocalDateTime.of(year, month, day, hour, minute);

        if(examStartTime.isBefore(LocalDateTime.now().plusMonths(3))) {
            throw new ValidationException("Exam start time should be at least 3 months later than the current date");
        }

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public static LocalTime validateClassTime(String time) throws ValidationException {
        // should be in hh:mm format
        // should be a valid time
        // its minute should be divisible by 15

        if(!time.matches("\\d{2}:\\d{2}")) {
            throw new ValidationException("Class time should be in hh:mm format");
        }

        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));

        validateHour(hour);
        validateMinute(minute);

        return LocalTime.of(hour, minute);
    }

    public static LocalTime validateClassStartTime(String time) throws ValidationException {
        // should be a valid class time
        // should not be earlier than Config.classMinStartTime
        // should not be later than Config.classMaxStartTime

        LocalTime classStartTime = validateClassTime(time);
        if(classStartTime.isBefore(Config.CLASS_MIN_START_TIME) || classStartTime.isAfter(Config.CLASS_MAX_START_TIME)) {
            throw new ValidationException(String.format("Class start time should be between %s and %s", Config.CLASS_MIN_START_TIME, Config.CLASS_MAX_START_TIME));
        }

        return classStartTime;
    }

    public static LocalTime validateClassEndTime(String time) throws ValidationException {
        // should be a valid class time
        // should not be earlier than Config.classMinEndTime
        // should not be later than Config.classMaxEndTime

        LocalTime classEndTime = validateClassTime(time);
        if(classEndTime.isBefore(Config.CLASS_MIN_END_TIME) || classEndTime.isAfter(Config.CLASS_MAX_END_TIME)) {
            throw new ValidationException(String.format("Class end time should be between %s and %s", Config.CLASS_MIN_END_TIME, Config.CLASS_MAX_END_TIME));
        }

        return classEndTime;
    }

    public static void validateClassTime(ClassTime classTime) throws ValidationException {
        // end time should be at least Config.classMinDurationInMinutes after start time
        if(classTime.getStartTime().plusMinutes(Config.CLASS_MIN_DURATION_IN_MINUTES).isAfter(classTime.getEndTime())) {
            throw new ValidationException(String.format("Class duration should be at least %d minutes", Config.CLASS_MIN_DURATION_IN_MINUTES));
        }
    }

    public static void validateNumberOfClassTimes(String numberOfClassTimes) throws ValidationException {
        // number of class times should be a positive integer between Config.minNumberOfClassTimes and Config.maxNumberOfClassTimes
        if(!numberOfClassTimes.matches("\\d+") || Integer.parseInt(numberOfClassTimes) < Config.MIN_NUMBER_OF_CLASS_TIMES || Integer.parseInt(numberOfClassTimes) > Config.MAX_NUMBER_OF_CLASS_TIMES) {
            throw new ValidationException(String.format("Number of class times should be a positive integer between %d and %d", Config.MIN_NUMBER_OF_CLASS_TIMES, Config.MAX_NUMBER_OF_CLASS_TIMES));
        }
    }

    public static void validateCredits(String credits) throws ValidationException {
        // credits should be a positive integer between Config.minCredits and Config.maxCredits
        if(!credits.matches("\\d+") || Integer.parseInt(credits) < Config.MIN_CREDITS || Integer.parseInt(credits) > Config.MAX_CREDITS) {
            throw new ValidationException(String.format("Credits should be a positive integer between %d and %d", Config.MIN_CREDITS, Config.MAX_CREDITS));
        }
    }

    public static void validateInstructorName(String instructorName) throws ValidationException {
        // should consist of only English letters and spaces
        if(!instructorName.matches("[a-zA-Z ]+")) {
            throw new ValidationException("Instructor name should consist of only English letters and spaces");
        }
    }

    public static void validateNewInstructorName(String instructorName, ArrayList<ClassTime> classTimes, LocalDateTime examStartTime) throws ValidationException, SQLException {
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

    public static ArrayList<Integer> getValidatedCourseIds(String[] courseIds, ArrayList<Course> courses, String validationMessage) throws ValidationException {
        // Ids should be a list of integers
        // Ids should be in the registeredCourses list
        ArrayList<Integer> validatedCourseIds = new ArrayList<>();
        for(String Id: courseIds) {
            if(Id.isBlank())
                continue;
            validateCourseId(Id);
            int courseId = Integer.parseInt(Id);
            if(courses.stream().noneMatch(course -> course.getCourseId() == courseId)) {
                throw new ValidationException(String.format("Course %d %s", courseId, validationMessage));
            }
            if(!validatedCourseIds.contains(courseId)) {
                validatedCourseIds.add(courseId);
            }
        }
        return validatedCourseIds;
    }

    private static void validateYear(int year) throws ValidationException {
        // should be a valid year
        // be at least current year
        // be at most one year later than the current year
        if(year < LocalDateTime.now().getYear() || year > LocalDateTime.now().getYear() + 1) {
            throw new ValidationException("Year should be at least current year and at most one year later than the current year");
        }
    }

    private static void validateMonth(int month) throws ValidationException {
        // should be a valid month
        if(month < 1 || month > 12) {
            throw new ValidationException("Invalid month");
        }
    }

    private static void validateDay(int day) throws ValidationException {
        // should be a valid day
        if(day < 1 || day > 31) {
            throw new ValidationException("Invalid day");
        }
    }

    private static void validateHour(int hour) throws ValidationException {
        // should be a valid hour
        if(hour < 0 || hour > 23) {
            throw new ValidationException("Invalid hour");
        }
    }

    private static void validateMinute(int minute) throws ValidationException {
        // should be a valid minute and should be divisible by 15
        if(minute < 0 || minute > 59){
            throw new ValidationException("Invalid minute");
        }
        if(minute % 15 != 0) {
            throw new ValidationException("Minute should be divisible by 15");
        }
    }
}
