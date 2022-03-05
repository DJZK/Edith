package Functions;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Date;

public class TimeThread {
    /**
     *
     * @return Formatted Time (Hour:Minute AM/PM)
     */
    public static String getTime(){
        DateFormat toTime = new SimpleDateFormat("hh:mm a");
        Date date = new Date();
        return toTime.format(date);
    }

    /**
     *
     * @return Formatted Time + Date (Month  Day  Year . Hour:Minute AM/PM)
     */
    public static String getTimeDate(){
        DateFormat toTime = new SimpleDateFormat("M dd yyyy ・ hh:mm a");
        Date date = new Date();
        return toTime.format(date);
    }

    /**
     *
     * @return Formatted Date (Month Day Year)
     */
    public static String getDate(){
        DateFormat toTime = new SimpleDateFormat("M dd yyyy");
        Date date = new Date();
        return toTime.format(date);
    }

    /**
     *
     * @param offsetDateTime
     * @return Formatted Date
     */
    public static String formatOffsetDate(OffsetDateTime offsetDateTime) {
        String month;
        switch (offsetDateTime.getMonthValue()) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
            default:
                month = "Nope that's not a month and probably you are not living on earth. JK ERROR OCCURRED!";
                break;
        }

        return month + " "
                + offsetDateTime.getDayOfMonth() + " "
                + offsetDateTime.getYear() + " "
                + offsetDateTime.getHour() + ":"
                + offsetDateTime.getMinute();
    }
}
