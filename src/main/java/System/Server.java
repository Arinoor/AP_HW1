package System;

import Model.Course;
import Model.Department;

import java.util.ArrayList;

public class Server {

    private Server() {

    }

    public static void registerCourses(int studentId, ArrayList<Integer> registerCourseIds) {

    }

    public static void dropCourses(int studentId, ArrayList<Integer> dropCourseIds) {

    }

    public static ArrayList<Course> getCoursesByIds(ArrayList<Integer> courseIds) {
        return new ArrayList<>();
    }

    public static ArrayList<Course> getCoursesRegisteredByStudent(int studentId) {
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
