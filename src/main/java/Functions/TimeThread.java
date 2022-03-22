package Functions;

import net.dv8tion.jda.api.JDA;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeThread {
    private final JDA api;

    public TimeThread(JDA api) {
        this.api = api;
    }

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
    public void realtime() {
        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int NumTime = Integer.parseInt(getNumericalTime('a'));


                // Timed Task
                switch (NumTime) {
                    case 600:
                        sendChannel("Good morning @everyone. Coffee?");
                        break;
                    case 800:
                        sendChannel("Time to get in! @everyone. I'll only give 30 minutes grace period!");
                        break;
                    case 1000:
                        sendChannel("It's 10 AM @everyone. You might wanna take a break?");
                        break;
                    case 1200:
                        sendChannel("It's 12 PM! Time to get out for lunch! @everyone");
                        break;
                    case 1300:
                        sendChannel("@everyone 1 PM mark. Time to get back!");
                        break;
                    case 1500:
                        sendChannel("It's 3 PM @everyone. Let's sip a coffee? only 15 minutes though.");
                        break;
                    case 1700:
                        sendChannel("It's log off time! Don't work too hard come on... Love yourself...");
                        break;
                    case 1900:
                        sendChannel("Take care @everyone!");
                        break;
                    case 2200:
                        sendChannel("Goodnight! @here");
                }

            }
        }, 60000, 60000);
    }

    private void sendChannel(String message) {
        String GuildID = DatabaseParameters.getGuildID();
        String TextChannelID = DatabaseParameters.getChannelID();
        api.getGuildById(GuildID).getTextChannelById(TextChannelID).sendMessage(message).queue();

    }

}
