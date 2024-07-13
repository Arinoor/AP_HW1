package Model;

public enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static Day getDayByStart(String day) {
        for(Day d : Day.values()) {
            if(d.toString().startsWith(day.toUpperCase())) {
                return d;
            }
        }
        return null;
    }
}