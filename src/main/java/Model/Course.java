package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    // We set our fields according to courses table in our database
    // courses table : course_id, instructor_name, course_name, capacity, credits, exam_start_time, course_type,  department_id
    // class_times table : course_id, day, start_time, end_time

    private int courseId;
    private String instructorName;
    private String courseName;
    private int capacity;
    private int credits;
    private ArrayList<ClassTime> classTimes;
    private LocalDateTime examStartTime;
    private CourseType courseType;
    private int departmentId;

    public Course (int courseId, String instructorName, String courseName, int capacity, int credits, ArrayList<ClassTime> classTimes, LocalDateTime examStartTime, CourseType courseType, int departmentId) {
        this.courseId = courseId;
        this.instructorName = instructorName;
        this.courseName = courseName;
        this.capacity = capacity;
        this.credits = credits;
        this.classTimes = classTimes;
        this.examStartTime = examStartTime;
        this.courseType = courseType;
        this.departmentId = departmentId;
    }

    public Course (String instructorName, String courseName, int capacity, int credits, ArrayList<ClassTime> classTimes, LocalDateTime examStartTime, CourseType courseType, int departmentId) {
        this.instructorName = instructorName;
        this.courseName = courseName;
        this.capacity = capacity;
        this.credits = credits;
        this.classTimes = classTimes;
        this.examStartTime = examStartTime;
        this.courseType = courseType;
        this.departmentId = departmentId;
    }

    public Course (ResultSet courseResultSet, ArrayList<ClassTime> classTimes) throws SQLException {
        this.courseId = courseResultSet.getInt("course_id");
        this.instructorName = courseResultSet.getString("instructor_name");
        this.courseName = courseResultSet.getString("course_name");
        this.capacity = courseResultSet.getInt("capacity");
        this.credits = courseResultSet.getInt("credits");
        this.courseType = CourseType.valueOf(courseResultSet.getString("course_type"));
        this.departmentId = courseResultSet.getInt("department_id");
        this.classTimes = classTimes;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = courseResultSet.getString("exam_start_time");
        this.examStartTime = LocalDateTime.parse(dateTimeString, formatter);
    }

    public boolean isExamTimeOverlapping(Course course) {
        return this.examStartTime.equals(course.examStartTime);
    }

    public boolean isClassTimeOverlapping(Course course) {
        for(ClassTime classTime : this.classTimes) {
            for(ClassTime registeredClassTime : course.classTimes) {
                if(classTime.isOverlapping(registeredClassTime)) {
                    return true;
                }
            }
        }
        return false;
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
        return credits;
    }

    public List<ClassTime> getClassTimes() {
        return classTimes;
    }

    public LocalDateTime getExamStartTime() {
        return examStartTime;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setCourseId(int anInt) {
        this.courseId = anInt;
    }

    public String toString() {
        return
                "  courseId=" + courseId +
                "  courseName='" + courseName + '\'' +
                ", instructorName='" + instructorName + '\'' +
                ", capacity=" + capacity +
                ", credits=" + credits +
                ", courseType=" + courseType +
                ", departmentId=" + departmentId +
                ", classTimes=" + classTimes +
                ", examStartTime=" + examStartTime +
                '}';
    }

    public static void showCourses(ArrayList<Course> courses) {
        for(Course course: courses) {
            System.out.println(course.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Course)) {
            return false;
        }
        Course course = (Course) obj;
        return course.courseId == this.courseId;
    }
}
