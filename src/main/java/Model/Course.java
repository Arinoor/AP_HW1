package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Date examStartTime;
    private CourseType courseType;
    private int departmentId;

    public Course (int courseId, String instructorName, String courseName, int capacity, int credits, ArrayList<ClassTime> classTimes, Date examStartTime, CourseType courseType, int departmentId) {
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

    public Course (ResultSet courseResultSet, ArrayList<ClassTime> classTimes) throws SQLException {
        this.courseId = courseResultSet.getInt("course_id");
        this.instructorName = courseResultSet.getString("instructor_name");
        this.courseName = courseResultSet.getString("course_name");
        this.capacity = courseResultSet.getInt("capacity");
        this.credits = courseResultSet.getInt("credits");
        this.classTimes = classTimes;
        this.examStartTime = courseResultSet.getDate("exam_start_time");
        this.courseType = CourseType.valueOf(courseResultSet.getString("course_type"));
        this.departmentId = courseResultSet.getInt("department_id");
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

    public Date getExamStartTime() {
        return examStartTime;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", instructorName='" + instructorName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", capacity=" + capacity +
                ", credits=" + credits +
                ", classTimes=" + classTimes +
                ", examStartTime=" + examStartTime +
                ", courseType=" + courseType +
                ", departmentId=" + departmentId +
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
