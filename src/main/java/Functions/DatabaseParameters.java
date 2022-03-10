package Functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseParameters {


    private static String dbLocations[][] = new String[][]{
            // Database Locations
            {
                    // Relative
                    "Attendance.db",
                    "C:\\Database\\Attendance.db",
                    "/home/djzk/Discord/Attendance.db"
            },
            // Database Names
            {
                    "in the same place as executable",
                    "in Windows machine (Dev Mode)",
                    "in Linux Machine! (Super Dev Mode!)"
            }
    };


    public static void initDB() {
        // Will Cycle through available database, first found, first served
        String database = "";
        for (int i = 0; i < dbLocations[0].length; i++) {
            database = "" + dbLocations[0][i];

            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database); Statement statement = connection.createStatement()) {
                statement.execute("SELECT * FROM Config");
                System.out.println("I am in " + dbLocations[1][i] + "\nLocation: " + dbLocations[0][i] + "\n");
                break;
            } catch (SQLException sqlException) {
                System.out.println("Error encountered! " + sqlException);
            }
        }
        finalLocation = database;
    }

    public static String getFinalLocation() {
        return finalLocation;
    }

    public static String getBotToken() {
        return BotToken;
    }

    public static void setBotToken(String botToken) {
        BotToken = botToken;
    }

    public static String getChannelID() {
        return ChannelID;
    }

    public static void setChannelID(String channelID) {
        ChannelID = channelID;
    }

    public static String getBotPrefix() {
        return BotPrefix;
    }

    public static void setBotPrefix(String botPrefix) {
        BotPrefix = botPrefix;
    }

    private static String finalLocation = null;

    private static String BotToken;
    private static String BotPrefix;
    private static String ChannelID;
}
