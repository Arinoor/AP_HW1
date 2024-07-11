package Model;

public class Student {
    private int studentId;
    private String passwrord;

    public Student(int studentId, String passwrord) {
        this.studentId = studentId;
        this.passwrord = passwrord;
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
