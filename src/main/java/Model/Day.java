package Model;

public enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static Day getDayByFirstTwoLetters(String day) {
        switch (day) {
            case "MO":
                return MONDAY;
            case "TU":
                return TUESDAY;
            case "WE":
                return WEDNESDAY;
            case "TH":
                return THURSDAY;
            case "FR":
                return FRIDAY;
            case "SA":
                return SATURDAY;
            case "SU":
                return SUNDAY;
            default:
                return null;
        }
    }
}