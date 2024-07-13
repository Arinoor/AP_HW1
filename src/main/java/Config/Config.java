package Config;


import Model.Department;

import java.util.ArrayList;

public class Config {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/course_registration_db";
    public static final String DATABASE_USER = "root";
    public static final String DATABASE_PASSWORD = "lmqsy.Arinoor_8053";

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "Admin402";

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

    private Config() {
    }

}
