package Model;

import java.sql.ResultSet;

public class Student {
    private int studentId;
    private String passwrord;

    public Student(int studentId, String passwrord) {
        this.studentId = studentId;
        this.passwrord = passwrord;
    }

    public Student(ResultSet resultSet) throws Exception {
        this.studentId = resultSet.getInt("student_id");
        this.passwrord = resultSet.getString("password");
    }

    // getter and setters
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getPasswrord() {
        return passwrord;
    }

    public void setPasswrord(String passwrord) {
        this.passwrord = passwrord;
    }
}
