package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class ClassTime {
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;

    public ClassTime(String day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ClassTime(ResultSet resultSet) throws SQLException {
        this.day = resultSet.getString("day");
        this.startTime = resultSet.getTime("start_time").toLocalTime();
        this.endTime = resultSet.getTime("end_time").toLocalTime();
    }

    // Getters and setters
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ClassTime{" +
                "day='" + day + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public boolean isOverlapping(ClassTime classTime) {
        if (!this.day.equals(classTime.day)) {
            return false;
        }
        if (this.startTime.isAfter(classTime.endTime) || this.endTime.isBefore(classTime.startTime)) {
            return false;
        }
        return true;
    }
}