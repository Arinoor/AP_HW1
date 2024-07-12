package System;

import Model.*;
import Exception.*;

import java.util.ArrayList;

public class Server {

    private Server() {

    }

    public static void registerCourses(int studentId, ArrayList<Integer> toRegisterCourseIds) throws ValidationException {
        ArrayList<Course> toRegisterCourses = getCoursesByIds(toRegisterCourseIds);
        ArrayList<Course> registeredCourses = getRegisteredCoursesByStudent(studentId);
        for(Course course : toRegisterCourses) {
            registerCourse(studentId, course, registeredCourses);
        }
        for(Course course : toRegisterCourses) {
            // register course to database
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

    public static void dropCourses(int studentId, ArrayList<Integer> dropCourseIds) {

    }


    public static ArrayList<Course> getCoursesByIds(ArrayList<Integer> courseIds) {
        return new ArrayList<>();
    }

    public static ArrayList<Course> getRegisteredCoursesByStudent(int studentId) {
        return new ArrayList<>();
    }

    public static ArrayList<Course> getAvailableCourses(int studentId, int departmentId) {
        return new ArrayList<>();
    }

    public static String getDepartmentName(int departmentId) {
        return "";
    }

    public static void main(String[] args) {

    }

    public static ArrayList<Department> getDepartments() {
        return new ArrayList<>();
    }
}
