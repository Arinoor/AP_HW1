package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    // We set our fields according to courses table in our database
    // courses table : course_id, instructor_name, course_name, capacity, number_of_credits, exam_start_time, course_type,  department_id
    // class_times table : course_id, day, start_time, end_time

    private int courseId;
    private String instructorName;
    private String courseName;
    private int capacity;
    private int number_of_credits;
    private ArrayList<ClassTime> classTimes;
    private Date examStartTime;
    private CourseType courseType;
    private int departmentId;

    public Course (int courseId, String instructorName, String courseName, int capacity, int number_of_credits, ArrayList<ClassTime> classTimes, Date examStartTime, CourseType courseType, int departmentId) {
        this.courseId = courseId;
        this.instructorName = instructorName;
        this.courseName = courseName;
        this.capacity = capacity;
        this.number_of_credits = number_of_credits;
        this.classTimes = classTimes;
        this.examStartTime = examStartTime;
        this.courseType = courseType;
        this.departmentId = departmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfCredits() {
        return number_of_credits;
    }

    public List<ClassTime> getClassTimes() {
        return classTimes;
    }

    public Date getExamStartTime() {
        return examStartTime;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setNumberOfCredits(int number_of_credits) {
        this.number_of_credits = number_of_credits;
    }

    public void addClassTime(ClassTime classTime) {
        this.classTimes.add(classTime);
    }

    public void removeClassTime(ClassTime classTime) {
        this.classTimes.remove(classTime);
    }

    public void setExamStartTime(Date examStartTime) {
        this.examStartTime = examStartTime;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String toString() {
        return "Course ID: " + courseId + ", Instructor Name: " + instructorName + ", Course Name: " + courseName + ", Capacity: " + capacity + ", Number of Credits: " + number_of_credits + ", Exam Start Time: " + examStartTime + ", Course Type: " + courseType + ", Department ID: " + departmentId;
    }

}
