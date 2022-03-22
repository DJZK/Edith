package Functions;

import net.dv8tion.jda.api.JDA;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeThread {

    /**
     * @return Formatted Time (Hour:Minute AM/PM)
     */
    public static String getTime() {
        DateFormat toTime = new SimpleDateFormat("hh:mm a");
        Date date = new Date();
        return toTime.format(date).toUpperCase();
    }

    public static String getNumericalTime(char func) {
        Date time = new Date();
        DateFormat toTime = new SimpleDateFormat("HHmm");
        DateFormat firstTwo = new SimpleDateFormat("HH");

        switch (func) {
            case 'a':
                return toTime.format(time);
            case 'b':
                return firstTwo.format(time);
            default:
                return time.getTime() + "";
        }
    }

    /**
     * @return Formatted Date (Month Day Year)
     */
    public static String getDate() {
        DateFormat toTime = new SimpleDateFormat("M/dd/yyyy");
        Date date = new Date();
        return toTime.format(date);
    }


    // For the realtime clock


}
