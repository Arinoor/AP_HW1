package Config;


import Model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Config {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/course_registration_db";
    public static final String DATABASE_USER = "root";
    public static final String DATABASE_PASSWORD = "lmqsy.Arinoor_8053";

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "Admin402";

    public static final int STUDENT_ID_LENGTH = 9;
    public static final int USERNAME_MIN_LENGTH = 5;
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 50;
    public static final int COURSE_MIN_ID = 1;
    public static final int COURSE_MAX_ID = 1000;
    public static final int MIN_NUMBER_OF_CLASS_TIMES = 1;
    public static final int MAX_NUMBER_OF_CLASS_TIMES = 3;
    public static final LocalTime CLASS_MIN_START_TIME = LocalTime.of(8, 0);
    public static final LocalTime CLASS_MAX_START_TIME = LocalTime.of(18, 0);
    public static final LocalTime CLASS_MIN_END_TIME = LocalTime.of(9, 0);
    public static final LocalTime CLASS_MAX_END_TIME = LocalTime.of(20, 0);
    public static final int CLASS_MIN_DURATION_IN_MINUTES = 60;
    public static final int MIN_CREDITS = 1;
    public static final int MAX_CREDITS = 5;

    public static final ArrayList<Department> departments = new ArrayList<Department>() {{
        add(new Department(1, "Computer Science"));
        add(new Department(2, "Electrical Engineering"));
        add(new Department(3, "Mechanical Engineering"));
        add(new Department(4, "Civil Engineering"));
        add(new Department(5, "Chemical Engineering"));
        add(new Department(6, "Industrial Engineering"));
        add(new Department(7, "Computer Engineering"));
        add(new Department(8, "Economics"));
        add(new Department(9, "Mathematics"));
        add(new Department(10, "Physics"));
        add(new Department(11, "Chemistry"));
    }};


    public static final ArrayList<Course> courses = new ArrayList<>() {{
            add(new Course("Tofighi", "Basic Programming", 20, 4,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 22, 10, 30),
                CourseType.GENERAL, 1));
            add(new Course("Boomeri", "Advanced Programming", 40, 4,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(13, 0), LocalTime.of(15, 0)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(13, 0), LocalTime.of(15, 0)));
                }},
                LocalDateTime.of(2025, 6, 24, 10, 30),
                CourseType.SPECIALIZED, 1));
            add(new Course("Khazaii", "Data Structures", 30, 4,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 26, 10, 30),
                CourseType.SPECIALIZED, 1));
            add(new Course("Arshadi", "Logical Circuits", 40, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(10, 30), LocalTime.of(12, 0)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 0)));
                }},
                LocalDateTime.of(2025, 6, 28, 16, 0),
                CourseType.SPECIALIZED, 7));
            add(new Course("Poornaki", "Calculus 1", 100, 4,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(8, 0), LocalTime.of(10, 0)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(8, 0), LocalTime.of(10, 0)));
                }},
                LocalDateTime.of(2025, 6, 21, 9, 0),
                CourseType.GENERAL, 9));
            add(new Course("Poornaki", "Calculus 2", 150, 4,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 23, 9, 0),
                CourseType.GENERAL, 9));
            add(new Course("Mogadasi", "Calculus 2", 150, 4,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(8, 0), LocalTime.of(10, 0)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(8, 0), LocalTime.of(10, 0)));
                }},
                LocalDateTime.of(2025, 6, 23, 9, 0),
                CourseType.GENERAL, 9));
            add(new Course("Khanedani", "Calculus 2", 250, 4,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(15, 0), LocalTime.of(17, 0)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(15, 0), LocalTime.of(17, 0)));
                }},
                LocalDateTime.of(2025, 6, 23, 9, 0),
                CourseType.GENERAL, 9));
            add(new Course("Ardeshir", "Fundamentals of Mathematics", 50, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 25, 14, 0),
                CourseType.SPECIALIZED, 9));
            add(new Course("Mogimi", "Physics 1", 200, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 24, 15, 0),
                CourseType.GENERAL, 10));
            add(new Course("Mogimi", "Physics 2", 250, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(9, 0), LocalTime.of(10, 30)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30)));
                }},
                LocalDateTime.of(2025, 6, 26, 15, 0),
                CourseType.GENERAL, 10));
            add(new Course("Memarzadeh", "Physics 2", 250, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(9, 0), LocalTime.of(10, 30)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30)));
                }},
                LocalDateTime.of(2025, 6, 26, 15, 0),
                CourseType.GENERAL, 10));
            add(new Course("Rezaii", "Discrete Mathematics", 50, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 28, 10, 0),
                CourseType.SPECIALIZED, 9));
            add(new Course("Fazli", "Basic Programming", 60, 3,
                    new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 22, 9, 30),
                CourseType.GENERAL, 7));
            add(new Course("Fazli", "Advanced Programming", 40, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(13, 0), LocalTime.of(15, 0)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(13, 0), LocalTime.of(15, 0)));
                }},
                LocalDateTime.of(2025, 6, 24, 13, 0),
                CourseType.SPECIALIZED, 7));
            add(new Course("Abam", "Data Structures", 30, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 26, 11, 30),
                CourseType.SPECIALIZED, 7));
            add(new Course("Zarabizadeh", "Discrete Structures", 40, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 6, 28, 16, 0),
                CourseType.SPECIALIZED, 7));
            add(new Course("Daneshgar", "Automata Theory", 40, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(13, 0), LocalTime.of(15, 0)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(13, 0), LocalTime.of(15, 0)));
                }},
                LocalDateTime.of(2025, 6, 30, 10, 30),
                CourseType.SPECIALIZED, 1));
            add(new Course("Ebrahimi", "Probability and Statistics", 50, 4,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SUNDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.TUESDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 7, 27, 10, 30),
                CourseType.SPECIALIZED, 9));
            add(new Course("Torabi", "Game Theory", 40, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)));
                }},
                LocalDateTime.of(2025, 7, 29, 15, 30),
                CourseType.SPECIALIZED, 9));
            add(new Course("Akbari", "Linear Algebra", 40, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(13, 0), LocalTime.of(15, 0)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(13, 0), LocalTime.of(15, 0)));
                }},
                LocalDateTime.of(2025, 7, 26, 9, 0),
                CourseType.SPECIALIZED, 9));
            add(new Course("Abdollahi", "Operating Systems", 40, 3,
                new ArrayList<ClassTime>() {{
                     add(new ClassTime(Day.SATURDAY, LocalTime.of(8, 30), LocalTime.of(10, 30)));
                     add(new ClassTime(Day.MONDAY, LocalTime.of(8, 30), LocalTime.of(10, 30)));
                }},
                LocalDateTime.of(2025, 7, 23, 14, 30),
                CourseType.SPECIALIZED, 1));
    }};


    private Config() {
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(date);
        System.out.println(time);
        System.out.println(dateTime);
        LocalDateTime dateTime1 = LocalDateTime.of(2021, 5, 20, 10, 30);
        System.out.println(CLASS_MAX_END_TIME);

    }

}
