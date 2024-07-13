package Model;

public enum CourseType {
    GENERAL,
    SPECIALIZED;

    public static CourseType getCourseTypeByStart(String courseType) {
        for(CourseType c : CourseType.values()) {
            if(c.toString().startsWith(courseType.toUpperCase())) {
                return c;
            }
        }
        return null;
    }

}
